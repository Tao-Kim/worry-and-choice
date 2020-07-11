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
import java.util.List;

public class ListViewModel extends ViewModel {

    private final static String TAG = ListViewModel.class.getName();
    private FirebaseUser user;
    private UserRepository userRepository;
    private PostRepository postRepository;
    private MutableLiveData<ArrayList<PostItem>> postsListLiveData;
    private ArrayList<PostItem> postsList = new ArrayList<>();
    private Observer<List<PostItem>> postListObserver;


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
        postListObserver = new Observer<List<PostItem>>() {
            @Override
            public void onChanged(List<PostItem> postItems) {
                if (postItems != null && postItems.size() != 0) {
                    for (PostItem post : postItems) {
                        postsList.add(post);
                    }
                    postsListLiveData.setValue(postsList);
                } else {
                    postsListLiveData.setValue(null);
                }

            }
        };
        postRepository.getPostsListLiveData().observeForever(postListObserver);
    }

    public void renewalPostsList() {
        if (postsList.size() != 0) {
            postsList.clear();
        }
        postRepository.readPostsList();
    }

    @Override
    protected void onCleared() {
        postRepository.getPostsListLiveData().removeObserver(postListObserver);
        userRepository =null;
        postRepository = null;
        super.onCleared();
    }
}
