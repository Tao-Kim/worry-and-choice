package com.tao.wnc.viewmodel;

import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.tao.wnc.model.domain.PostItem;
import com.tao.wnc.model.domain.SelectItem;
import com.tao.wnc.model.repository.PostRepository;
import com.tao.wnc.model.repository.UserRepository;
import com.tao.wnc.util.Constants;

public class AddPostViewModel extends ViewModel {

    private final static String TAG = AddPostViewModel.class.getName();
    private FirebaseUser user;
    private UserRepository userRepository;
    private PostRepository postRepository;


    public AddPostViewModel() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        userRepository = new UserRepository();
        postRepository = new PostRepository();
    }

    public void addItem(String title, String description, String selectAString, String selectBString) {
        String writer = user.getDisplayName();
        long timeStamp =  System.currentTimeMillis();
        SelectItem selectA = new SelectItem(selectAString);
        SelectItem selectB = new SelectItem(selectBString);
        Short selected = Constants.SELECTED.NOT_SELECTED;

        PostItem item = new PostItem(title, description, writer, timeStamp, selectA, selectB, selected);
        postRepository.insertPost(item, user.getDisplayName());
    }
}
