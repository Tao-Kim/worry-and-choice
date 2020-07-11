package com.tao.wnc.view.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.tao.wnc.R;
import com.tao.wnc.databinding.FragmentRegisterBinding;
import com.tao.wnc.util.Constants;
import com.tao.wnc.view.activity.LoginActivity;
import com.tao.wnc.viewmodel.RegisterViewModel;

public class RegisterFragment extends BaseFragment {

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
                        SmartToast.emotion().backgroundColorRes(R.color.wnc28White).goForAnotherPage().apply().success(R.string.register_result_success);
                        ((LoginActivity) getActivity()).removeAndPop(RegisterFragment.newInstance());

                    } else if (resultCode == Constants.AUTH.REGISTER_FAIL_EXIST_NAME) {
                        binding.edtRegisterName.requestFocus();
                        SmartToast.emotion().backgroundColorRes(R.color.wnc28White).apply().fail(R.string.register_result_exist_name);

                    } else if (resultCode == Constants.AUTH.REGISTER_FAIL_EXIST_EMAIL) {
                        binding.edtRegisterEmail.requestFocus();
                        SmartToast.emotion().backgroundColorRes(R.color.wnc28White).apply().fail(R.string.register_result_exist_email);

                    } else if (resultCode == Constants.AUTH.REGISTER_FAIL_UNSAME_PASSWORDS) {
                        binding.edtRegisterPassword.requestFocus();
                        SmartToast.emotion().backgroundColorRes(R.color.wnc28White).apply().fail(R.string.register_result_unsame_passwords);

                    } else if (resultCode == Constants.AUTH.REGISTER_FAIL_UNVALID_NAME) {
                        binding.edtRegisterName.requestFocus();
                        SmartToast.emotion().backgroundColorRes(R.color.wnc28White).apply().fail(R.string.register_result_unvalid_name);

                    } else if (resultCode == Constants.AUTH.REGISTER_FAIL_UNVALID_EMAIL) {
                        binding.edtRegisterEmail.requestFocus();
                        SmartToast.emotion().backgroundColorRes(R.color.wnc28White).apply().fail(R.string.register_result_unvalid_email);


                    } else if (resultCode == Constants.AUTH.REGISTER_FAIL_UNVALID_PASSWORD) {
                        binding.edtRegisterPassword.requestFocus();
                        SmartToast.emotion().backgroundColorRes(R.color.wnc28White).apply().fail(R.string.register_result_unvalid_password);

                    } else if (resultCode == Constants.AUTH.REGISTER_FAIL) {
                        SmartToast.emotion().backgroundColorRes(R.color.wnc28White).apply().warning(R.string.register_result_fail);

                    } else {
                        SmartToast.emotion().backgroundColorRes(R.color.wnc28White).apply().error(R.string.register_result_fail);
                        Log.e(TAG, "unexpected resultCode");
                    }
                }
            }
        });
    }

}