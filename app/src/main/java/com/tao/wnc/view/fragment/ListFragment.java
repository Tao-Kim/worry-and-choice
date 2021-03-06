package com.tao.wnc.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.tao.wnc.R;
import com.tao.wnc.databinding.FragmentListBinding;
import com.tao.wnc.model.domain.PostItem;
import com.tao.wnc.util.Constants;
import com.tao.wnc.view.activity.MainActivity;
import com.tao.wnc.view.adapter.PostListAdapter;
import com.tao.wnc.viewmodel.ListViewModel;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;


public class ListFragment extends BaseFragment {

    private FragmentListBinding binding;
    private ListViewModel viewModel;
    private PostListAdapter adapter;
    private RecyclerView recyclerView;
    private short status;

    public ListFragment() {
        // Required empty public constructor
    }

    public static ListFragment newInstance() {
        return new ListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToken();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false);
        binding.setFragment(this);

        initRecyclerView();

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        status = ((MainActivity)getActivity()).getListFragmentStatusCode();

        viewModel = new ViewModelProvider(this).get(ListViewModel.class);
        observePostsList();

        if(status == Constants.LIST_FRAGMENT_STATUS.NONE || status == Constants.LIST_FRAGMENT_STATUS.CHANGED){
            showProgressBar();
            viewModel.renewalPostsList();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
        adapter = null;
        viewModel = null;
    }

    private void initRecyclerView(){
        recyclerView = binding.rvList;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        adapter = new PostListAdapter(){
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

    private void initToken(){
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(getActivity(), new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String deviceToken = instanceIdResult.getToken();
                viewModel.setDataBaseToken(deviceToken);
            }
        });
    }
    private void observePostsList() {
        viewModel.getPostsListLiveData().observe(getViewLifecycleOwner(), new Observer<ArrayList<PostItem>>() {
            @Override
            public void onChanged(ArrayList<PostItem> postItems) {
                binding.containerListNone.setVisibility(View.GONE);
                if(postItems != null && postItems.size() != 0){
                    adapter.setItems(postItems);
                    hideProgressBar();
                } else {
                    binding.containerListNone.setVisibility(View.VISIBLE);
                    hideProgressBar();
                }
            }
        });
    }

    public void onMyPostsClick(View v) {
        ((MainActivity) getActivity()).replaceWithBackStack(MyPostsFragment.newInstance());
    }

    public void onAddPostClick(View v) {
        ((MainActivity) getActivity()).replaceWithBackStack(AddPostFragment.newInstance());
    }

    public void onRefreshClick(View v){
        showProgressBar();
        viewModel.renewalPostsList();
        recyclerView.getLayoutManager().scrollToPosition(0);
    }

}