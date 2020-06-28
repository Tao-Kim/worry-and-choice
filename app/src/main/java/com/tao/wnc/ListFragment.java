package com.tao.wnc;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.tao.wnc.databinding.FragmentListBinding;


public class ListFragment extends Fragment {

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
        FragmentListBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false);
        binding.setFragment(this);

        return binding.getRoot();
    }

    public void onMyPostsClick(View v){
        ((MainActivity)getActivity()).replaceWithBackStack(MyPostsFragment.newInstance());
    }
    public void onAddPostClick(View v){
        ((MainActivity)getActivity()).replaceWithBackStack(AddPostFragment.newInstance());
    }
    public void onReadPostClick(View v){
        ((MainActivity)getActivity()).replaceWithBackStack(ReadPostFragment.newInstance());
    }
    public void onNotificationsClick(View v){
        ((MainActivity)getActivity()).replaceWithBackStack(NotificationsFragment.newInstance());
    }


}