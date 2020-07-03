package com.tao.wnc.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.tao.wnc.R;
import com.tao.wnc.databinding.FragmentAddPostBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddPostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddPostFragment extends Fragment {

    public AddPostFragment() {
        // Required empty public constructor
    }

    public static AddPostFragment newInstance() {
        return new AddPostFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentAddPostBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_post, container, false);
        binding.setFragment(this);

        return binding.getRoot();
    }

    public void onBackClick(View v){
        ((MainActivity)getActivity()).removeAndPop(this);
    }

    public void onDoneClick(View v) {

    }
}