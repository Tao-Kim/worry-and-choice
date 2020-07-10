package com.tao.wnc.viewmodel;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.tao.wnc.model.domain.PostItem;
import com.tao.wnc.model.repository.PostRepository;
import com.tao.wnc.model.repository.UserRepository;

import java.util.ArrayList;

public class ListViewModel extends ViewModel {

    private final static String TAG = ListViewModel.class.getName();
    private FirebaseUser user;
    private UserRepository userRepository;
    private PostRepository postRepository;
    private MutableLiveData<ArrayList<PostItem>> postsListLiveData;
    private ArrayList<PostItem> postsList;


    public ListViewModel() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        userRepository = new UserRepository();
        postRepository = new PostRepository();
        postsListLiveData = new MutableLiveData<>();
        observeRepositoryPostsList();
    }

    public MutableLiveData<ArrayList<PostItem>> getPostsListLiveData() {
        return postsListLiveData;
    }

    public void setDataBaseToken(String deviceToken) {
        String name = user.getDisplayName();
        userRepository.insertOrModifyUserToken(name, deviceToken);
    }

    private void observeRepositoryPostsList() {
        postRepository.getPostsListLiveData().observeForever(new Observer<ArrayList<PostItem>>() {
            @Override
            public void onChanged(ArrayList<PostItem> postItems) {
                postsList.addAll(postItems);
            }
        });
    }

    public void renewalPostsList() {
        postsList.clear();
        postRepository.readPostsList();
    }
}
