package com.tao.wnc.viewmodel;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.tao.wnc.model.domain.CommentItem;
import com.tao.wnc.model.domain.PostItem;
import com.tao.wnc.model.repository.PostRepository;
import com.tao.wnc.util.SingleLiveEvent;

import java.util.ArrayList;

public class ReadPostViewModel extends ViewModel {
    private ArrayList<CommentItem> commentItems;
    private MutableLiveData<PostItem> postItemLiveData;
    private PostRepository postRepository;
    private FirebaseUser user;
    private Observer<PostItem> postItemObserver;

    public ReadPostViewModel() {
        commentItems = new ArrayList<>();
        setTestDataSet();

        postRepository = new PostRepository();
        postItemLiveData = new SingleLiveEvent<>();
        user = FirebaseAuth.getInstance().getCurrentUser();
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

    public void readPost(String postId) {
        postRepository.readPost(postId, user.getDisplayName());
    }

    public void reloadPost(String postId) {
        postRepository.readPost(postId, user.getDisplayName());
    }

    public void deletePost(String postId) {
        postRepository.deletePost(postId);
    }

    public void editPost(){

    }

    public void select(final short SELECT, final String postId) {
        postRepository.modifySelect(SELECT, postId, user.getDisplayName());
        postRepository.getDoneLiveData().observeForever(new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean done) {
                if(done){
                    reloadPost(postId);
                    postRepository.getDoneLiveData().removeObserver(this);
                }
            }
        });
    }

    public ArrayList<CommentItem> getListItems() {

        return commentItems;
    }

    private void setTestDataSet() {

        CommentItem item = new CommentItem("길동이",
                "피시방 알바 좋아요!!!",
                "2시간전");
        commentItems.add(item);
        CommentItem item2 = new CommentItem("호동이",
                "편의점 알바 좋아요!!!",
                "3시간전");
        commentItems.add(item2);
        CommentItem item3 = new CommentItem("타오",
                "둘다 좋아요!!!",
                "4시간전");
        commentItems.add(item3);
        CommentItem item4 = new CommentItem("TEST",
                "test",
                "0시간전");
        commentItems.add(item4);
        commentItems.add(item4);
        commentItems.add(item4);
        commentItems.add(item4);
        commentItems.add(item4);
        commentItems.add(item4);
        commentItems.add(item4);
        commentItems.add(item4);
        commentItems.add(item4);
        commentItems.add(item4);
        commentItems.add(item4);
        commentItems.add(item4);
        commentItems.add(item4);
        commentItems.add(item4);


    }

    @Override
    protected void onCleared() {
        postRepository.getPostItemLiveData().removeObserver(postItemObserver);
        super.onCleared();
    }
}
