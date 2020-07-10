package com.tao.wnc.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.tao.wnc.model.domain.PostItem;
import com.tao.wnc.model.domain.SelectItem;
import com.tao.wnc.model.repository.PostRepository;
import com.tao.wnc.model.repository.UserRepository;
import com.tao.wnc.util.Constants;

import java.util.ArrayList;

public class PostViewModel extends ViewModel {

    private final static String TAG = PostViewModel.class.getName();
    private FirebaseUser user;
    private UserRepository userRepository;
    private PostRepository postRepository;
    private MutableLiveData<ArrayList<PostItem>> postsListLiveData;
    private ArrayList<PostItem> postsList;
    private MutableLiveData<ArrayList<PostItem>> myPostsListLiveData;
    private ArrayList<PostItem> myPostsList;

    public PostViewModel() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        userRepository = new UserRepository();
        postRepository = new PostRepository();
        postsListLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<ArrayList<PostItem>> getPostsListLiveData() {
        return postsListLiveData;
    }

    public void setDataBaseToken(String deviceToken) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String name = user.getDisplayName();
        userRepository.insertOrModifyUserToken(name, deviceToken);
    }

    public void renewalPostsList() {

    }

    public void renewalMyPostsList() {

    }
    public void addItem(String title, String description, String selectAString, String selectBString) {
        String writer = user.getDisplayName();
        String timeStamp = null;
        SelectItem selectA = new SelectItem(selectAString);
        SelectItem selectB = new SelectItem(selectBString);
        Short selected = Constants.SELECTED.NOT_SELECTED;

        PostItem item = new PostItem(title, description, writer, timeStamp, selectA, selectB, selected);
        postRepository.insertPost(item);
    }

    public void readPost() {

    }

    public void updatePost() {

    }

    public void deletePost() {

    }


}
