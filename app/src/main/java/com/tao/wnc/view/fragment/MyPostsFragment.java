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

import com.tao.wnc.R;
import com.tao.wnc.databinding.FragmentMyPostsBinding;
import com.tao.wnc.model.domain.PostItem;
import com.tao.wnc.view.activity.MainActivity;
import com.tao.wnc.view.adapter.PostListAdapter;
import com.tao.wnc.viewmodel.MyPostsViewModel;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyPostsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyPostsFragment extends BaseFragment {

    private FragmentMyPostsBinding binding;
    private MyPostsViewModel viewModel;
    private PostListAdapter adapter;
    RecyclerView recyclerView;

    public MyPostsFragment() {
        // Required empty public constructor
    }

    public static MyPostsFragment newInstance() {
        return new MyPostsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_posts, container, false);
        binding.setFragment(this);

        initRecyclerView();

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(MyPostsViewModel.class);
        observeMyPostsList();

        showProgressBar();
        viewModel.renewalMyPostsList();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
        adapter = null;
        viewModel = null;
    }

    private void initRecyclerView() {
        recyclerView = binding.rvMyPosts;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        adapter = new PostListAdapter() {
            @Override
            public void onItemClick(PostItem item, int position) {
                super.onItemClick(item, position);
                ((MainActivity) getActivity()).replaceWithBackStack(ReadPostFragment.newInstance(item.getPostId()));
            }
        };
        ScaleInAnimationAdapter animationAdapter = new ScaleInAnimationAdapter(adapter);
        animationAdapter.setFirstOnly(false);
        recyclerView.setAdapter(animationAdapter);
    }

    private void observeMyPostsList() {
        viewModel.getMyPostsListLiveData().observe(getViewLifecycleOwner(), new Observer<ArrayList<PostItem>>() {
            @Override
            public void onChanged(ArrayList<PostItem> postItems) {
                binding.containerListNone.setVisibility(View.GONE);
                if (postItems != null && postItems.size() != 0) {
                    adapter.setItems(postItems);
                    hideProgressBar();
                } else {
                    binding.containerListNone.setVisibility(View.VISIBLE);
                    hideProgressBar();
                }
            }
        });
    }

    public void onBackClick(View v) {
        ((MainActivity) getActivity()).removeAndPop(this);
    }

    public void onRefreshClick(View v) {
        showProgressBar();
        viewModel.renewalMyPostsList();
        recyclerView.getLayoutManager().scrollToPosition(0);
    }

}