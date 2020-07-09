package com.tao.wnc.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.tao.wnc.model.repository.FirebaseRepository;
import com.tao.wnc.util.Constants;
import com.tao.wnc.util.SingleLiveEvent;

public class RegisterViewModel extends ViewModel {

    private FirebaseRepository repository;
    private FirebaseAuth auth;
    private SingleLiveEvent<Short> signInResult;
    private Observer<Short> dbObserver;
    private String email;
    private String password;

    public RegisterViewModel() {
        auth = FirebaseAuth.getInstance();
        repository = new FirebaseRepository();
        signInResult = new SingleLiveEvent<>();
        dbObserver = new Observer<Short>() {
            @Override
            public void onChanged(Short dbResult) {
                Log.d("dbChange", "change!!!!!!");
                if (dbResult == null) Log.d("dbChange", "null!!!!!");
                if (dbResult != null) {
                    Log.d("dbChange", Short.toString(dbResult));
                    if (dbResult == Constants.DB.INSERT_USER_FAIL) {
                        signInResult.setValue(Constants.AUTH.REGISTER_FAIL);
                    } else if (dbResult == Constants.DB.INSERT_USER_FAIL_EXIST_NAME) {
                        signInResult.setValue(Constants.AUTH.REGISTER_FAIL_EXIST_NAME);
                    } else if (dbResult == Constants.DB.INSERT_USER_FAIL_EXIST_EMAIL) {
                        signInResult.setValue(Constants.AUTH.REGISTER_FAIL_EXIST_EMAIL);
                    } else if (dbResult == Constants.DB.INSERT_USER_SUCCESS) {
                        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    signInResult.setValue(Constants.AUTH.REGISTER_SUCCESS);
                                } else {
                                    try {
                                        throw task.getException();
                                    } catch (FirebaseAuthUserCollisionException e) {
                                        signInResult.setValue(Constants.AUTH.REGISTER_FAIL_EXIST_EMAIL);
                                    } catch (Exception e) {
                                        signInResult.setValue(Constants.AUTH.REGISTER_FAIL);
                                    }
                                }
                            }
                        });
                    }
                }
            }
        };
        repository.getResultCodeLiveData().observeForever(dbObserver);
    }

    public SingleLiveEvent<Short> getSignInResultLiveData() {
        return signInResult;
    }

    public void registerUser(final String name, final String email, final String password, String passwordAgain) {
        if (!isValidName(name)) {
            signInResult.setValue(Constants.AUTH.REGISTER_FAIL_UNVALID_NAME);
        } else if (!isValidEmail(email)) {
            signInResult.setValue(Constants.AUTH.REGISTER_FAIL_UNVALID_EMAIL);
        } else if (!isSamePasswords(password, passwordAgain)) {
            signInResult.setValue(Constants.AUTH.REGISTER_FAIL_UNSAME_PASSWORDS);
        } else if (!isValidPassword(password)) {
            signInResult.setValue(Constants.AUTH.REGISTER_FAIL_UNVALID_PASSWORD);
        } else {
            this.email = email;
            this.password = password;
            repository.insertUser(name, email);
        }
    }

    private boolean isValidName(String name) {
        Log.d("test", "idvalidname");
        return true;
    }

    private boolean isValidEmail(String email) {
        Log.d("test", "idvalidEmail");
        return true;
    }

    private boolean isSamePasswords(String password, String passwordAgain) {
        Log.d("test", "idsamepassword");
        return password.equals(passwordAgain);
    }

    private boolean isValidPassword(String password) {
        Log.d("test", "idvalidnpassword");
        return true;
    }


    @Override
    protected void onCleared() {
        repository.getResultCodeLiveData().removeObserver(dbObserver);
        super.onCleared();
    }
}
