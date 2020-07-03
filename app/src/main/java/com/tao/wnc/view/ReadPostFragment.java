package com.tao.wnc.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.tao.wnc.R;
import com.tao.wnc.databinding.FragmentReadPostsBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReadPostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReadPostFragment extends Fragment {

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
        FragmentReadPostsBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_read_posts, container, false);
        binding.setFragment(this);

        return binding.getRoot();
    }

    public void onUndoClick(View v) {
        ((MainActivity) getActivity()).removeAndPop(this);
    }
}