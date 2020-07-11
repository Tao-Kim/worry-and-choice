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

import com.tao.wnc.R;
import com.tao.wnc.databinding.FragmentAddPostBinding;
import com.tao.wnc.util.Constants;
import com.tao.wnc.view.activity.MainActivity;
import com.tao.wnc.viewmodel.AddPostViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddPostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddPostFragment extends Fragment {

    private FragmentAddPostBinding binding;
    private AddPostViewModel viewModel;

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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_post, container, false);
        binding.setFragment(this);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(AddPostViewModel.class);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
        viewModel = null;
    }

    public void onBackClick(View v){
        ((MainActivity)getActivity()).removeAndPop(this);
    }

    public void onDoneClick(View v) {
        showProgressBar();
        viewModel.addItem(binding.edtAddPostTitle.getText().toString(),
                binding.edtAddPostDescription.getText().toString(),
                binding.edtAddPostSelectA.getText().toString(),
                binding.edtAddPostSelectB.getText().toString());
        ((MainActivity)getActivity()).setListFragmentStatusCode(Constants.LIST_FRAGMENT_STATUS.CHANGED);
        ((MainActivity)getActivity()).removeAndPop(this);
    }

    private void showProgressBar()  {
        //
    }

    private void hidProgressBar() {
        //
    }
}