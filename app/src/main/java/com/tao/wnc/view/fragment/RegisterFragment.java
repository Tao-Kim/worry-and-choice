package com.tao.wnc.view.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.tao.wnc.R;
import com.tao.wnc.databinding.FragmentRegisterBinding;
import com.tao.wnc.util.Constants;
import com.tao.wnc.view.activity.LoginActivity;
import com.tao.wnc.viewmodel.RegisterViewModel;

public class RegisterFragment extends Fragment {

    private static final String TAG = RegisterFragment.class.getName();
    private FragmentRegisterBinding binding;
    private RegisterViewModel viewModel;

    public RegisterFragment() {
        // Required empty public constructor
    }


    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false);
        binding.setFragment(this);


        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        observeRegisterResult();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
        viewModel = null;
    }

    public void onBackClick(View v) {
        ((LoginActivity) getActivity()).removeAndPop(this);
    }

    public void onSubmitClick(View v) {
        showProgressBar();
        viewModel.registerUser(binding.edtRegisterName.getText().toString(),
                binding.edtRegisterEmail.getText().toString(),
                binding.edtRegisterPassword.getText().toString(),
                binding.edtRegisterPasswordAgain.getText().toString());
    }

    private void observeRegisterResult() {
        viewModel.getSignInResultLiveData().observe(getViewLifecycleOwner(), new Observer<Short>() {
            @Override
            public void onChanged(Short resultCode) {
                if (resultCode != null) {
                    hideProgressBar();

                    if (resultCode == Constants.AUTH.REGISTER_SUCCESS) {
                        Toast.makeText(getContext(), R.string.register_result_success, Toast.LENGTH_SHORT).show();
                        ((LoginActivity) getActivity()).removeAndPop(RegisterFragment.newInstance());

                    } else if (resultCode == Constants.AUTH.REGISTER_FAIL_EXIST_NAME) {
                        binding.edtRegisterName.requestFocus();
                        Toast.makeText(getContext(), R.string.register_result_exist_name, Toast.LENGTH_SHORT).show();

                    } else if (resultCode == Constants.AUTH.REGISTER_FAIL_EXIST_EMAIL) {
                        binding.edtRegisterEmail.requestFocus();
                        Toast.makeText(getContext(), R.string.register_result_exist_email, Toast.LENGTH_SHORT).show();

                    } else if (resultCode == Constants.AUTH.REGISTER_FAIL_UNSAME_PASSWORDS) {
                        binding.edtRegisterPassword.requestFocus();
                        Toast.makeText(getContext(), R.string.register_result_unsame_passwords, Toast.LENGTH_SHORT).show();

                    } else if (resultCode == Constants.AUTH.REGISTER_FAIL_UNVALID_NAME) {
                        binding.edtRegisterName.requestFocus();
                        Toast.makeText(getContext(), R.string.register_result_unvalid_name, Toast.LENGTH_SHORT).show();

                    } else if (resultCode == Constants.AUTH.REGISTER_FAIL_UNVALID_EMAIL) {
                        binding.edtRegisterEmail.requestFocus();
                        Toast.makeText(getContext(), R.string.register_result_unvalid_email, Toast.LENGTH_SHORT).show();

                    } else if (resultCode == Constants.AUTH.REGISTER_FAIL_UNVALID_PASSWORD) {
                        binding.edtRegisterPassword.requestFocus();
                        Toast.makeText(getContext(), R.string.register_result_unvalid_password, Toast.LENGTH_SHORT).show();

                    } else if (resultCode == Constants.AUTH.REGISTER_FAIL) {
                        Toast.makeText(getContext(), R.string.register_result_fail, Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getContext(), R.string.register_result_fail, Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "unexpected resultCode");
                    }
                }
            }
        });
    }

    private void showProgressBar() {
        //
    }

    private void hideProgressBar() {
        //
    }

}