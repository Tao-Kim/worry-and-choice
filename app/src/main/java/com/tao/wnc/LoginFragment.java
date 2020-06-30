package com.tao.wnc;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.tao.wnc.databinding.FragmentLoginBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {


    private FragmentLoginBinding binding;

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

        Glide.with(this).asGif().load(R.raw.login_background).into(binding.loginIvBackground);

        View root = binding.getRoot();
        return root;
    }

    public void onLoginClick(View v) {
        ((LoginActivity) getActivity()).goMainActivity();
    }

    public void onRegisterClick(View v) {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.login_container, RegisterFragment.newInstance()).addToBackStack(null).commit();
    }
}