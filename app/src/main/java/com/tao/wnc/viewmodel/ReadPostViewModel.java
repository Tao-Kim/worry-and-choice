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
import java.util.List;

public class ReadPostViewModel extends ViewModel {
    private MutableLiveData<ArrayList<CommentItem>> commentListLiveData;
    private ArrayList<CommentItem> commentList;
    private MutableLiveData<PostItem> postItemLiveData;
    private PostRepository postRepository;
    private FirebaseUser user;
    private Observer<PostItem> postItemObserver;
    private Observer<Boolean> doneObserver;
    private Observer<List<CommentItem>> commentListObserver;

    public ReadPostViewModel() {
        commentListLiveData = new MutableLiveData<>();
        commentList = new ArrayList<>();
        postRepository = new PostRepository();
        postItemLiveData = new SingleLiveEvent<>();
        user = FirebaseAuth.getInstance().getCurrentUser();
        observePostItem();
        observeCommentList();
    }

    public MutableLiveData<PostItem> getPostItemLiveData() {
        return postItemLiveData;
    }

    public MutableLiveData<ArrayList<CommentItem>> getCommentListLiveData() {
        return commentListLiveData;
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

    private void observeCommentList() {
        commentListObserver = new Observer<List<CommentItem>>() {
            @Override
            public void onChanged(List<CommentItem> commentItems) {
                if(commentItems != null && commentItems.size() != 0){
                    for(CommentItem commentItem : commentItems){
                        commentList.add(commentItem);
                    }
                    commentListLiveData.setValue(commentList);
                }
            }
        };
        postRepository.getCommentListLiveData().observeForever(commentListObserver);
    }

    public void readPost(String postId) {
        postRepository.readPost(postId, user.getDisplayName());
    }

    public void deletePost(String postId) {
        postRepository.deletePost(postId);
    }

    public void select(final short SELECT, final String postId) {
        postRepository.modifySelect(SELECT, postId, user.getDisplayName());
        doneObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean done) {
                if (done) {
                    reloadPost(postId);
                    postRepository.getDoneLiveData().removeObserver(this);
                }
            }
        };
        postRepository.getDoneLiveData().observeForever(doneObserver);
    }

    public void sendComment(String postId, String commentMessage){
        CommentItem commentItem = new CommentItem(user.getDisplayName(), commentMessage, System.currentTimeMillis());
        postRepository.insertComment(postId, commentItem);
    }

    public void reloadPost(String postId) {
        postRepository.readPost(postId, user.getDisplayName());
    }

    public void renewalCommentList(String postId) {
        if (commentList.size() != 0) {
            commentList.clear();
        }
        postRepository.readCommentList(postId);
    }

    @Override
    protected void onCleared() {
        postRepository.getPostItemLiveData().removeObserver(postItemObserver);
        postRepository.getDoneLiveData().removeObserver(doneObserver);
        super.onCleared();
    }
}
