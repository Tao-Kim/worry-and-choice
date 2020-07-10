package com.tao.wnc.view.fragment;

import android.os.Bundle;
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
import com.tao.wnc.view.activity.MainActivity;
import com.tao.wnc.view.adapter.PostListAdapter;
import com.tao.wnc.databinding.FragmentMyPostsBinding;
import com.tao.wnc.viewmodel.MyPostsViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyPostsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyPostsFragment extends Fragment {

    private FragmentMyPostsBinding binding;
    private MyPostsViewModel viewModel;
    private PostListAdapter adapter;

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

        RecyclerView recyclerView = binding.rvMyPosts;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        adapter = new PostListAdapter();
        recyclerView.setAdapter(adapter);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(MyPostsViewModel.class);
        getList();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
        adapter = null;
        viewModel = null;
    }

    private void getList(){
        adapter.setItems(viewModel.getListItems());
    }

    public void onBackClick(View v){
        ((MainActivity)getActivity()).removeAndPop(this);
    }

    public void onRefreshClick(View v){

    }

    public void onReadPostClick(View v){
        //((MainActivity)getActivity()).replaceWithBackStack(ReadPostFragment.newInstance());
    }

}