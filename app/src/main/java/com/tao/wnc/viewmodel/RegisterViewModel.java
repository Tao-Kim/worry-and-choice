package com.tao.wnc.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.tao.wnc.model.repository.UserRepository;
import com.tao.wnc.util.Constants;
import com.tao.wnc.util.SingleLiveEvent;

import java.util.regex.Pattern;

public class RegisterViewModel extends ViewModel {

    private static final String TAG = RegisterViewModel.class.getName();
    private UserRepository repository;
    private FirebaseAuth auth;
    private SingleLiveEvent<Short> signInResultLiveData;
    private Observer<Short> dbObserver;
    private String email;
    private String password;
    private String name;

    public RegisterViewModel() {
        auth = FirebaseAuth.getInstance();
        repository = new UserRepository();
        signInResultLiveData = new SingleLiveEvent<>();
        dbObserver = new Observer<Short>() {
            @Override
            public void onChanged(Short dbResult) {
                if (dbResult != null) {
                    if (dbResult == Constants.DB.INSERT_USER_FAIL) {
                        signInResultLiveData.setValue(Constants.AUTH.REGISTER_FAIL);
                    } else if (dbResult == Constants.DB.INSERT_USER_FAIL_EXIST_NAME) {
                        signInResultLiveData.setValue(Constants.AUTH.REGISTER_FAIL_EXIST_NAME);
                    } else if (dbResult == Constants.DB.INSERT_USER_FAIL_EXIST_EMAIL) {
                        signInResultLiveData.setValue(Constants.AUTH.REGISTER_FAIL_EXIST_EMAIL);
                    } else if (dbResult == Constants.DB.INSERT_USER_SUCCESS) {
                        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    final FirebaseUser user = auth.getCurrentUser();
                                    UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(name).build();
                                    user.updateProfile(profileChangeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                signInResultLiveData.setValue(Constants.AUTH.REGISTER_SUCCESS);
                                            } else {
                                                signInResultLiveData.setValue(Constants.AUTH.REGISTER_FAIL);
                                                user.delete();
                                                repository.deleteUser(name);
                                                Log.e(TAG, task.getException().toString());
                                            }
                                        }
                                    });

                                } else {
                                    try {
                                        throw task.getException();
                                    } catch (Exception e) {
                                        signInResultLiveData.setValue(Constants.AUTH.REGISTER_FAIL);
                                        repository.deleteUser(name);
                                        Log.w(TAG, e.toString());
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

//    public void delete(String name){
//        FirebaseUser user = auth.getCurrentUser();
//        user.delete();
//        repository.deleteUser(name);
//    }

    public SingleLiveEvent<Short> getSignInResultLiveData() {
        return signInResultLiveData;
    }

    public void registerUser(final String name, final String email, final String password, String passwordAgain) {
        if (!isValidName(name)) {
            signInResultLiveData.setValue(Constants.AUTH.REGISTER_FAIL_UNVALID_NAME);
        } else if (!isValidEmail(email)) {
            signInResultLiveData.setValue(Constants.AUTH.REGISTER_FAIL_UNVALID_EMAIL);
        } else if (!isSamePasswords(password, passwordAgain)) {
            signInResultLiveData.setValue(Constants.AUTH.REGISTER_FAIL_UNSAME_PASSWORDS);
        } else if (!isValidPassword(password)) {
            signInResultLiveData.setValue(Constants.AUTH.REGISTER_FAIL_UNVALID_PASSWORD);
        } else {
            this.email = email;
            this.password = password;
            this.name = name;
            repository.insertUser(name, email);
        }
    }

    private boolean isValidName(String name) {
        return name != null && Pattern.matches("^[가-힣]*$", name) && name.length() > 0;
    }

    private boolean isValidEmail(String email) {
        return email != null && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isSamePasswords(String password, String passwordAgain) {
        return password != null && password.equals(passwordAgain);
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 6;
    }


    @Override
    protected void onCleared() {
        repository.getResultCodeLiveData().removeObserver(dbObserver);
        super.onCleared();
    }
}
