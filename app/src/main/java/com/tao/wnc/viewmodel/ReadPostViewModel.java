package com.tao.wnc.viewmodel;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.tao.wnc.model.domain.CommentItem;
import com.tao.wnc.model.domain.PostItem;
import com.tao.wnc.model.repository.PostRepository;
import com.tao.wnc.util.Constants;
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
    private Observer<Short> doneObserver;
    private Observer<List<CommentItem>> commentListObserver;
    private String postId;

    public ReadPostViewModel() {
        commentListLiveData = new MutableLiveData<>();
        commentList = new ArrayList<>();
        postRepository = new PostRepository();
        postItemLiveData = new SingleLiveEvent<>();
        user = FirebaseAuth.getInstance().getCurrentUser();
        observePostItem();
        observeCommentList();
        observeDone();
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

    private void observeDone() {
        doneObserver = new Observer<Short>() {
            @Override
            public void onChanged(Short done) {
               if(done == Constants.DB.DONE_SELECT){
                   renewalPostBody();
               } else if (done == Constants.DB.DONE_SEND_COMMENT) {
                   renewalPostBody();
                   renewalCommentList();
                }
            }
        };

        postRepository.getDoneLiveData().observeForever(doneObserver);
    }

    public void readPost(String postId) {
        this.postId = postId;
        postRepository.readPost(postId, user.getDisplayName());
        renewalCommentList();
    }

    public void deletePost(String postId) {
        postRepository.deletePost(postId);
    }

    public void select(final short SELECT) {
        postRepository.modifySelect(SELECT, postId, user.getDisplayName());

    }

    public void sendComment(String commentMessage){
        CommentItem commentItem = new CommentItem(user.getDisplayName(), commentMessage, System.currentTimeMillis());
        postRepository.insertComment(postId, commentItem);
    }

    public void reloadPost() {
        renewalPostBody();
        renewalCommentList();
    }

    private void renewalPostBody(){
        postRepository.readPost(postId, user.getDisplayName());
    }

    private void renewalCommentList() {
        if (commentList.size() != 0) {
            commentList.clear();
        }
        postRepository.readCommentList(postId);
    }

    @Override
    protected void onCleared() {
        postRepository.getPostItemLiveData().removeObserver(postItemObserver);
        postRepository.getDoneLiveData().removeObserver(doneObserver);
        postRepository.getCommentListLiveData().removeObserver(commentListObserver);
        postRepository = null;
        super.onCleared();
    }
}
