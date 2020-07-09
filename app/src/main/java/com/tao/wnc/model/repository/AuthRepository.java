package com.tao.wnc.model.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tao.wnc.model.domain.RegisterByEmailItem;
import com.tao.wnc.util.Constants;
import com.tao.wnc.util.SingleLiveEvent;

public class AuthRepository {

    private static final String TAG = AuthRepository.class.getName();
    private FirebaseDatabase db;
    private DatabaseReference ref;
    private SingleLiveEvent<Short> resultCodeLiveData;

    public AuthRepository() {
        db = FirebaseDatabase.getInstance();
        ref = db.getReference();
        resultCodeLiveData = new SingleLiveEvent<>();
    }

    public SingleLiveEvent<Short> getResultCodeLiveData() {
        return resultCodeLiveData;
    }

    public void insertUser(final String name, final String email) {
        ref.child("users").orderByChild("name").equalTo(name).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    ref.child("users").orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (!dataSnapshot.exists()) {
                                RegisterByEmailItem item = new RegisterByEmailItem(name, email);
                                ref.child("users").push().setValue(item);
                                resultCodeLiveData.setValue(Constants.DB.INSERT_USER_SUCCESS);
                            } else {
                                resultCodeLiveData.setValue(Constants.DB.INSERT_USER_FAIL_EXIST_EMAIL);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            resultCodeLiveData.setValue(Constants.DB.INSERT_USER_FAIL);
                            Log.w(TAG, "insert user fail in email check");
                        }
                    });

                } else {
                    resultCodeLiveData.setValue(Constants.DB.INSERT_USER_FAIL_EXIST_NAME);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                resultCodeLiveData.setValue(Constants.DB.INSERT_USER_FAIL);
                Log.w(TAG, "insert user fail in name check");
            }
        });
    }

}
