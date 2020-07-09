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
import com.tao.wnc.view.adapter.CommentListAdapter;
import com.tao.wnc.databinding.FragmentReadPostBinding;
import com.tao.wnc.viewmodel.ReadPostViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReadPostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReadPostFragment extends Fragment {

    private FragmentReadPostBinding binding;
    private ReadPostViewModel viewModel;
    private CommentListAdapter adapter;

    public ReadPostFragment() {
        // Required empty public constructor
    }

    public static ReadPostFragment newInstance() {
        return new ReadPostFragment();
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

        RecyclerView recyclerView = binding.rvReadPostComment;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        adapter = new CommentListAdapter();
        recyclerView.setAdapter(adapter);

        return binding.getRoot();
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ReadPostViewModel.class);
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

    public void onBackClick(View v) {
        ((MainActivity) getActivity()).removeAndPop(this);
    }

    public void onRefreshClick(View v) {

    }
}