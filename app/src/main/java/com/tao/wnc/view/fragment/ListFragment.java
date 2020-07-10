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


public class ListFragment extends Fragment {

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

        if(status == Constants.LIST_FRAGMENT_STATUS.NONE || status == Constants.LIST_FRAGMENT_STATUS.POST_ADDED){
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
                ((MainActivity) getActivity()).replaceWithBackStack(ReadPostFragment.newInstance());
            }
        };
        recyclerView.setAdapter(adapter);
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
                if(postItems != null){
                    adapter.setItems(postItems);
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

    public void onNotificationsClick(View v) {
        ((MainActivity) getActivity()).replaceWithBackStack(NotificationsFragment.newInstance());
    }

    private void showProgressBar() {
        //
    }

    private void hideProgressBar() {
        //
    }

}