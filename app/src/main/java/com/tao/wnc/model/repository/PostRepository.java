package com.tao.wnc.model.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tao.wnc.model.domain.PostItem;

import java.util.ArrayList;
import java.util.List;

public class PostRepository {

    private static final String TAG = PostRepository.class.getName();
    private FirebaseDatabase db;
    private DatabaseReference ref;
    private MutableLiveData<List<PostItem>> postsListLiveData;
    private List<PostItem> postsList;
    private MutableLiveData<List<PostItem>> myPostsListLiveData;
    private List<PostItem> myPostsList;

    public PostRepository() {
        db = FirebaseDatabase.getInstance();
        ref = db.getReference();
        postsListLiveData = new MutableLiveData<>();
        postsList = new ArrayList<>();
        myPostsListLiveData = new MutableLiveData<>();
        myPostsList = new ArrayList<>();
    }

    public MutableLiveData<List<PostItem>> getPostsListLiveData() {
        return postsListLiveData;
    }

    public MutableLiveData<List<PostItem>> getMyPostsListLiveData() {
        return myPostsListLiveData;
    }

    public void readPostsList() {
        if(postsList != null){
            postsList.clear();
        }
        ref.child("posts").orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot itemDataSnapshot : dataSnapshot.getChildren()) {
                    PostItem item = itemDataSnapshot.getValue(PostItem.class);
                    postsList.add(item);
                }
                Log.d(TAG, Integer.toString(postsList.size()));
                if(postsList != null && postsList.size() != 0){
                    postsListLiveData.setValue(postsList);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, databaseError.toString());
            }
        });
    }

    public void readMyPostsList() {

    }

    public void insertPost(PostItem item) {
        ref.child("posts").push().setValue(item);
    }

    public void readPost() {

    }

    public void modifyPost() {

    }

    public void deletePost() {

    }
}
