package com.tao.wnc.view.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.coder.zzq.smartshow.toast.SmartToast;
import com.tao.wnc.R;
import com.tao.wnc.databinding.FragmentLoginBinding;
import com.tao.wnc.util.Constants;
import com.tao.wnc.view.activity.LoginActivity;
import com.tao.wnc.viewmodel.LoginViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends BaseFragment {

    private final static String TAG = LoginFragment.class.getName();
    private FragmentLoginBinding binding;
    private LoginViewModel viewModel;

    public LoginFragment() {
    }


    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
        binding.setFragment(this);

        setBackgroundImage();

        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        observeLoginResult();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
        viewModel = null;
    }

    private void setBackgroundImage() {
        Glide.with(this).asGif().load(R.raw.login_background).into(binding.loginIvBackground);
    }

    public void onRegisterClick(View v) {
        ((LoginActivity)getActivity()).replaceWithBackStack(RegisterFragment.newInstance());
    }

    public void onLoginClick(View v) {
        showProgressBar();
        viewModel.login(binding.edtLoginEmail.getText().toString(),
                binding.edtLoginPassword.getText().toString());
    }

    private void observeLoginResult() {
        viewModel.getLoginResultLiveData().observe(getViewLifecycleOwner(), new Observer<Short>() {
            @Override
            public void onChanged(Short resultCode) {
                if (resultCode != null) {
                    hideProgressBar();

                    if (resultCode == Constants.AUTH.LOGIN_SUCCESS) {
                        ((LoginActivity) getActivity()).goMainActivity();

                    } else if (resultCode == Constants.AUTH.LOGIN_FAIL_WRONG_EMAIL) {
                        binding.edtLoginEmail.requestFocus();
                        SmartToast.emotion().backgroundColorRes(R.color.wnc28White).apply().fail(R.string.login_result_wrong_email);

                    } else if (resultCode == Constants.AUTH.LOGIN_FAIL_WRONG_PASSWORD) {
                        binding.edtLoginPassword.requestFocus();
                        SmartToast.emotion().backgroundColorRes(R.color.wnc28White).apply().fail(R.string.login_result_wrong_password);

                    } else if (resultCode == Constants.AUTH.LOGIN_FAIL_NULL_EMAIL) {
                        binding.edtLoginEmail.requestFocus();
                        SmartToast.emotion().backgroundColorRes(R.color.wnc28White).apply().fail(R.string.login_result_null_email);

                    } else if (resultCode == Constants.AUTH.LOGIN_FAIL_NULL_PASSWORD) {
                        binding.edtLoginPassword.requestFocus();
                        SmartToast.emotion().backgroundColorRes(R.color.wnc28White).apply().fail(R.string.login_result_null_password);

                    } else if (resultCode == Constants.AUTH.LOGIN_FAIL) {
                        SmartToast.emotion().backgroundColorRes(R.color.wnc28White).apply().warning(R.string.login_result_fail);

                    } else {
                        SmartToast.emotion().backgroundColorRes(R.color.wnc28White).apply().error(R.string.login_result_fail);
                        Log.e(TAG, "unexpected resultCode");
                    }
                }
            }
        });
    }

}