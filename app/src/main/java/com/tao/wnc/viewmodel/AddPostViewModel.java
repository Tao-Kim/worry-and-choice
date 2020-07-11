package com.tao.wnc.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.tao.wnc.model.domain.PostItem;
import com.tao.wnc.model.domain.SelectItem;
import com.tao.wnc.model.repository.PostRepository;
import com.tao.wnc.util.Constants;

public class AddPostViewModel extends ViewModel {

    private final static String TAG = AddPostViewModel.class.getName();
    private FirebaseUser user;
    private PostRepository postRepository;
    private MutableLiveData<PostItem> postItemLiveData;
    private Observer<PostItem> postItemObserver;

    public AddPostViewModel() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        postRepository = new PostRepository();
        postItemLiveData = new MutableLiveData<>();
        observePostItem();
    }

    public MutableLiveData<PostItem> getPostItemLiveData() {
        return postItemLiveData;
    }

    private void observePostItem() {
        postItemObserver = new Observer<PostItem>() {
            @Override
            public void onChanged(PostItem postItem) {
                postItemLiveData.setValue(postItem);
            }
        };

        postRepository.getPostItemLiveData().observeForever(postItemObserver);
    }

    public void readPost(String postId){
        postRepository.readPost(postId);
    }

    public void editItem(String postId, String title, String description, String selectAString, String selectBString) {
        postRepository.modifyPost(postId, title, description, selectAString, selectBString);
    }

    public void addItem(String title, String description, String selectAString, String selectBString) {
        String writer = user.getDisplayName();
        long timeStamp =  System.currentTimeMillis();
        SelectItem selectA = new SelectItem(selectAString);
        SelectItem selectB = new SelectItem(selectBString);
        Short selected = Constants.SELECTED.NOT_SELECTED;

        PostItem item = new PostItem(title, description, writer, timeStamp, selectA, selectB, selected);
        postRepository.insertPost(item);
    }

    @Override
    protected void onCleared() {
        postRepository.getPostItemLiveData().removeObserver(postItemObserver);
        postRepository = null;
        super.onCleared();
    }
}
