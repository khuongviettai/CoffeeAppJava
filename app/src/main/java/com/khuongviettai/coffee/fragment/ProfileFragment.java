package com.khuongviettai.coffee.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.khuongviettai.coffee.R;
import com.khuongviettai.coffee.databinding.FragmentProfileBinding;
import com.khuongviettai.coffee.model.User;

public class ProfileFragment extends Fragment {

    private TextView tv_user_name;

    private FragmentProfileBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        tv_user_name = binding.tvUserName;

        Bundle bundle = getArguments();
        if (bundle != null) {
            User user = (User) bundle.getSerializable("user");
            if (user != null) {
                tv_user_name.setText(user.getName());
            }
        }

        return view;
    }
}