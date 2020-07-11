package com.tao.wnc.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.tao.wnc.util.Constants;
import com.tao.wnc.util.SingleLiveEvent;

public class LoginViewModel extends ViewModel {

    private static final String TAG = LoginViewModel.class.getName();
    private FirebaseAuth auth;
    private SingleLiveEvent<Short> loginResultLiveData;

    public LoginViewModel() {
        Log.d(TAG, "LoginViewModel Constructor!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        auth = FirebaseAuth.getInstance();
        loginResultLiveData = new SingleLiveEvent<>();
    }

    public SingleLiveEvent<Short> getLoginResultLiveData() {
        return loginResultLiveData;
    }

    public void login(final String email, final String password) {

        if (email == null || email.length() <= 0) {
            loginResultLiveData.setValue(Constants.AUTH.LOGIN_FAIL_NULL_EMAIL);
        } else if (password == null || password.length() <= 0) {
            loginResultLiveData.setValue(Constants.AUTH.LOGIN_FAIL_NULL_PASSWORD);
        } else {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        loginResultLiveData.setValue(Constants.AUTH.LOGIN_SUCCESS);
                    } else {
                        try {
                            throw task.getException();
                        } catch (FirebaseAuthInvalidUserException e) {
                            loginResultLiveData.setValue(Constants.AUTH.LOGIN_FAIL_WRONG_EMAIL);
                            Log.d(TAG, "login fail wrong email");
                        } catch (FirebaseAuthInvalidCredentialsException e) {
                            loginResultLiveData.setValue(Constants.AUTH.LOGIN_FAIL_WRONG_PASSWORD);
                            Log.d(TAG, "login fail wrong password");
                        } catch (Exception e) {
                            loginResultLiveData.setValue(Constants.AUTH.LOGIN_FAIL);
                            Log.w(TAG, task.getException().toString());
                        }
                    }
                }
            });
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
