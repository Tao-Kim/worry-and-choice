package com.tao.wnc.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.tao.wnc.R;
import com.tao.wnc.databinding.FragmentReadPostBinding;
import com.tao.wnc.model.domain.CommentItem;
import com.tao.wnc.model.domain.PostItem;
import com.tao.wnc.util.Constants;
import com.tao.wnc.view.activity.MainActivity;
import com.tao.wnc.view.adapter.CommentListAdapter;
import com.tao.wnc.viewmodel.ReadPostViewModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReadPostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReadPostFragment extends Fragment {

    private FragmentReadPostBinding binding;
    private ReadPostViewModel viewModel;
    private CommentListAdapter adapter;
    private  RecyclerView recyclerView;
    private FirebaseUser user;
    private boolean isMyPost = false;
    private String postId;

    public ReadPostFragment() {
        // Required empty public constructor
    }

    public static ReadPostFragment newInstance(String postId) {
        ReadPostFragment readPostFragment = new ReadPostFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", postId);
        readPostFragment.setArguments(bundle);
        return readPostFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_read_post, container, false);
        binding.setFragment(this);

        initRecyclerView();

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(this, getDefaultViewModelProviderFactory()).get(ReadPostViewModel.class);
        user = FirebaseAuth.getInstance().getCurrentUser();

        observePostItem();
        observeCommentList();
        loadPost();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
        adapter = null;
        viewModel = null;
    }

    private void initRecyclerView(){
        recyclerView = binding.rvReadPostComment;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        adapter = new CommentListAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void observePostItem() {
        viewModel.getPostItemLiveData().observe(getViewLifecycleOwner(), new Observer<PostItem>() {
            @Override
            public void onChanged(PostItem postItem) {
                String writer = postItem.getWriter();
                if (writer.equals(user.getDisplayName())) {
                    isMyPost = true;
                }
                binding.setUser(user.getDisplayName());
                binding.setItem(postItem);
                hiddeProgressBar();
            }
        });
    }

    private void observeCommentList() {
        viewModel.getCommentListLiveData().observe(getViewLifecycleOwner(), new Observer<ArrayList<CommentItem>>() {
            @Override
            public void onChanged(ArrayList<CommentItem> commentItems) {
                if(commentItems.size() != 0){
                    adapter.setItems(commentItems);
                    hiddeProgressBar();
                }
            }
        });
    }

    private void loadPost() {
        Bundle bundle = getArguments();
        postId = bundle.getString("id");
        viewModel.readPost(postId);
        viewModel.renewalCommentList(postId);
    }



    public void onBackClick(View v) {
        ((MainActivity) getActivity()).removeAndPop(this);
    }

    public void onRefreshClick(View v) {
        viewModel.reloadPost(postId);
        viewModel.renewalCommentList(postId);
        binding.svReadPost.scrollTo(0, 0);
    }

    public void onDeleteClick(View v) {
        viewModel.deletePost(postId);
        ((MainActivity)getActivity()).setListFragmentStatusCode(Constants.LIST_FRAGMENT_STATUS.CHANGED);
        ((MainActivity) getActivity()).removeAndPop(this);
    }

    public void onEditClick(View v) {
        ((MainActivity) getActivity()).replaceWithBackStack(AddPostFragment.newInstance(postId));
    }

    public void onSelectAClick(View v) {
        if (isMyPost) {
            viewModel.select(Constants.SELECT.WRITER_A, postId);
        } else {
            viewModel.select(Constants.SELECT.OTHER_A, postId);
        }
    }

    public void onSelectBClick(View v) {
        if (isMyPost) {
            viewModel.select(Constants.SELECT.WRITER_B, postId);
        } else {
            viewModel.select(Constants.SELECT.OTHER_B, postId);
        }
    }

    public void onCommentSendClick(View v){
        viewModel.sendComment(postId, binding.edtReadPostComment.getText().toString());
        viewModel.renewalCommentList(postId);
        binding.edtReadPostComment.setText("");
    }

    private void showProgressBar(){

    }

    private void hiddeProgressBar(){

    }
}