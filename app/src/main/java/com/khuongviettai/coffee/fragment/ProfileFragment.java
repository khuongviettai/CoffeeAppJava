package com.khuongviettai.coffee.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.khuongviettai.coffee.R;
import com.khuongviettai.coffee.activity.PolicyActivity;
import com.khuongviettai.coffee.databinding.FragmentProfileBinding;
import com.khuongviettai.coffee.model.User;

public class ProfileFragment extends Fragment {

    private TextView tv_user_name;
    private RelativeLayout btn_policy;

    private FragmentProfileBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        tv_user_name = binding.tvUserName;

        btn_policy = view.findViewById(R.id.btn_policy);
        btn_policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PolicyActivity.class);
                startActivity(intent);
            }
        });


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