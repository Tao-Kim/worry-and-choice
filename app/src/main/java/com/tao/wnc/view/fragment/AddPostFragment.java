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

import com.tao.wnc.R;
import com.tao.wnc.databinding.FragmentAddPostBinding;
import com.tao.wnc.model.domain.PostItem;
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
    private String postId;

    public AddPostFragment() {
        // Required empty public constructor
    }

    public static AddPostFragment newInstance() {
        return new AddPostFragment();
    }

    public static AddPostFragment newInstance(String postId){
        AddPostFragment addPostFragment = new AddPostFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", postId);
        addPostFragment.setArguments(bundle);
        return addPostFragment;
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

        checkEditPost();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
        viewModel = null;
    }

    private void checkEditPost(){
        Bundle bundle = getArguments();
        if(bundle != null){
            postId = bundle.getString("id");
            observePostItem();
            loadPostItem();
        }
    }

    private void observePostItem(){
        viewModel.getPostItemLiveData().observe(getViewLifecycleOwner(), new Observer<PostItem>() {
            @Override
            public void onChanged(PostItem postItem) {
                binding.setItem(postItem);
            }
        });
    }

    private void loadPostItem() {
        viewModel.readPost(postId);
    }

    public void onBackClick(View v){
        ((MainActivity)getActivity()).removeAndPop(this);
    }

    public void onDoneClick(View v) {
        showProgressBar();
        if(postId != null){
            viewModel.editItem(postId,
                    binding.edtAddPostTitle.getText().toString(),
                    binding.edtAddPostDescription.getText().toString(),
                    binding.edtAddPostSelectA.getText().toString(),
                    binding.edtAddPostSelectB.getText().toString());
        } else {
            viewModel.addItem(binding.edtAddPostTitle.getText().toString(),
                    binding.edtAddPostDescription.getText().toString(),
                    binding.edtAddPostSelectA.getText().toString(),
                    binding.edtAddPostSelectB.getText().toString());
            ((MainActivity)getActivity()).setListFragmentStatusCode(Constants.LIST_FRAGMENT_STATUS.CHANGED);
        }
        ((MainActivity)getActivity()).removeAndPop(this);
    }

    private void showProgressBar()  {
        //
    }

    private void hidProgressBar() {
        //
    }
}