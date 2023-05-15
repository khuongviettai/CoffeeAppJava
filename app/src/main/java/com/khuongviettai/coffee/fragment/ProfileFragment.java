package com.khuongviettai.coffee.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.khuongviettai.coffee.R;
import com.khuongviettai.coffee.activity.ChangePasswordActivity;
import com.khuongviettai.coffee.activity.ContactActivity;
import com.khuongviettai.coffee.activity.FeedbackActivity;
import com.khuongviettai.coffee.activity.PolicyActivity;
import com.khuongviettai.coffee.activity.SignInActivity;
import com.khuongviettai.coffee.activity.TermActivity;
import com.khuongviettai.coffee.activity.UserInfoActivity;
import com.khuongviettai.coffee.local.DataStoreManager;
import com.khuongviettai.coffee.utils.GlobalFuntion;

public class ProfileFragment extends Fragment {

    private TextView tv_Email;
    private View view;
    private RelativeLayout btn_term_of_use,btn_policy,btn_contact,btn_sign_out, btn_change_password, btn_feedback,btn_personal_information;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_profile, container, false);
        initUi();
        initListener();
        return view;
    }
    private void initUi(){
        tv_Email = view.findViewById(R.id.tv_Email);
        btn_term_of_use = view.findViewById(R.id.btn_term_of_use);
        btn_policy = view.findViewById(R.id.btn_policy);
        btn_contact = view.findViewById(R.id.btn_contact);
        btn_sign_out = view.findViewById(R.id.btn_sign_out);
        btn_change_password = view.findViewById(R.id.btn_change_password);
        btn_feedback = view.findViewById(R.id.btn_feedback);
        btn_term_of_use = view.findViewById(R.id.btn_term_of_use);
        btn_policy = view.findViewById(R.id.btn_policy);
        btn_personal_information = view.findViewById(R.id.btn_personal_information);
    }

    private void initListener() {
        tv_Email.setText(DataStoreManager.getUser().getEmail());
        btn_sign_out.setOnClickListener(v -> onClickSignOut());
        btn_change_password.setOnClickListener(v -> onClickChangePassword());
        btn_feedback.setOnClickListener(v-> onClickFeedback());
        btn_contact.setOnClickListener(v-> onClickContact());
        tv_Email.setText(DataStoreManager.getUser().getEmail());
        btn_term_of_use.setOnClickListener(v->onClickTerm());
        btn_policy.setOnClickListener(v-> onClickPolicy());
        btn_personal_information.setOnClickListener(v-> onClickUserInfo());
    }
    private void onClickChangePassword() {
        GlobalFuntion.startActivity(getActivity(), ChangePasswordActivity.class);
    }
    private void onClickFeedback(){
        GlobalFuntion.startActivity(getActivity(), FeedbackActivity.class);
    }
    private void onClickContact(){
        GlobalFuntion.startActivity(getActivity(), ContactActivity.class);
    }
    private void onClickUserInfo(){
        GlobalFuntion.startActivity(getActivity(), UserInfoActivity.class);
    }
    private void onClickTerm(){
        GlobalFuntion.startActivity(getActivity(), TermActivity.class);
    }
    private void onClickPolicy(){
        GlobalFuntion.startActivity(getActivity(), PolicyActivity.class);
    }
    private void onClickSignOut() {
        if (getActivity() == null) {
            return;
        }
        FirebaseAuth.getInstance().signOut();
        DataStoreManager.setUser(null);
        GlobalFuntion.startActivity(getActivity(), SignInActivity.class);
        getActivity().finishAffinity();
    }
}