package com.tao.wnc.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.tao.wnc.R;
import com.tao.wnc.databinding.FragmentMyPostsBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyPostsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyPostsFragment extends Fragment {

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
        FragmentMyPostsBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_posts, container, false);
        binding.setFragment(this);

        return binding.getRoot();
    }

    public void onUndoClick(View v){
        ((MainActivity)getActivity()).removeAndPop(this);
    }

    public void onReadPostClick(View v){
        ((MainActivity)getActivity()).replaceWithBackStack(ReadPostFragment.newInstance());
    }

}