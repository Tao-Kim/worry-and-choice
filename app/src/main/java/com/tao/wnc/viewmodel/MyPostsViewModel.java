package com.tao.wnc.viewmodel;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.tao.wnc.model.domain.PostItem;
import com.tao.wnc.model.repository.PostRepository;

import java.util.ArrayList;
import java.util.List;

public class MyPostsViewModel extends ViewModel {

    private final static String TAG = MyPostsViewModel.class.getName();
    private FirebaseUser user;
    private PostRepository postRepository;
    private MutableLiveData<ArrayList<PostItem>> myPostsListLiveData;
    private ArrayList<PostItem> myPostsList = new ArrayList<>();
    private Observer<List<PostItem>> myPostsListObserver;

    public MyPostsViewModel() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        postRepository = new PostRepository();
        myPostsListLiveData = new MutableLiveData<>();
        observeRepositoryMyPostsList();
    }

    public MutableLiveData<ArrayList<PostItem>> getMyPostsListLiveData() {
        return myPostsListLiveData;
    }

    private void observeRepositoryMyPostsList() {
        myPostsListObserver = new Observer<List<PostItem>>() {
            @Override
            public void onChanged(List<PostItem> postItems) {
                if (postItems != null && postItems.size() != 0) {
                    for (PostItem post : postItems) {
                        myPostsList.add(post);
                    }
                    myPostsListLiveData.setValue(myPostsList);
                }

            }
        };
        postRepository.getMyPostsListLiveData().observeForever(myPostsListObserver);
    }

    public void renewalMyPostsList() {
        if (myPostsList.size() != 0) {
            myPostsList.clear();
        }
        postRepository.readMyPostsList(user.getDisplayName());
    }

    @Override
    protected void onCleared() {
        postRepository.getMyPostsListLiveData().removeObserver(myPostsListObserver);
        postRepository = null;
        super.onCleared();
    }
}
