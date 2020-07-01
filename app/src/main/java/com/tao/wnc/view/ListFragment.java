package com.tao.wnc.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tao.wnc.R;
import com.tao.wnc.adapter.PostListAdapter;
import com.tao.wnc.databinding.FragmentListBinding;
import com.tao.wnc.viewmodel.ListViewModel;


public class ListFragment extends Fragment {

    private FragmentListBinding binding;
    private ListViewModel viewModel;
    private PostListAdapter adapter;

    public ListFragment() {
        // Required empty public constructor
    }

    public static ListFragment newInstance() {
        return new ListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false);
        binding.setFragment(this);

        RecyclerView recyclerView = binding.rvList;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        adapter = new PostListAdapter();
        recyclerView.setAdapter(adapter);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ListViewModel.class);
        getList();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("listFragment", "onDestory");
        binding = null;
        adapter = null;
        viewModel = null;
    }

    private void getList(){
        adapter.setItems(viewModel.getListItems());
    }


    public void onMyPostsClick(View v) {
        ((MainActivity) getActivity()).replaceWithBackStack(MyPostsFragment.newInstance());
    }

    public void onAddPostClick(View v) {
        ((MainActivity) getActivity()).replaceWithBackStack(AddPostFragment.newInstance());
    }

    public void onReadPostClick(View v) {
        ((MainActivity) getActivity()).replaceWithBackStack(ReadPostFragment.newInstance());
    }

    public void onNotificationsClick(View v) {
        ((MainActivity) getActivity()).replaceWithBackStack(NotificationsFragment.newInstance());
    }


}