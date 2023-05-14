package com.khuongviettai.coffee.activity;


import android.os.Bundle;
import android.view.View;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.khuongviettai.coffee.R;
import com.khuongviettai.coffee.database.ControllerApplication;
import com.khuongviettai.coffee.local.DataStoreManager;
import com.khuongviettai.coffee.model.Feedback;
import com.khuongviettai.coffee.utils.BaseActivity;
import com.khuongviettai.coffee.utils.GlobalFuntion;
import com.khuongviettai.coffee.utils.StringUtil;

public class FeedbackActivity extends BaseActivity {

    private EditText mEdtName, mEdtPhone, mEdtEmail, mEdtComment;
    private TextView mBtnSend;
    private ImageView btn_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        mEdtName = findViewById(R.id.edt_name);
        mEdtPhone = findViewById(R.id.edt_phone);
        mEdtEmail = findViewById(R.id.edt_email);
        mEdtComment = findViewById(R.id.edt_comment);
        mBtnSend = findViewById(R.id.tv_send_feedback);
        btn_back = findViewById(R.id.btn_back);
        mEdtEmail.setText(DataStoreManager.getUser().getEmail());

        mBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendFeedback();
            }
        });
        btn_back.setOnClickListener(v -> onBackPressed());
    }

    private void sendFeedback() {
        String strName = mEdtName.getText().toString();
        String strPhone = mEdtPhone.getText().toString();
        String strEmail = mEdtEmail.getText().toString();
        String strComment = mEdtComment.getText().toString();

        if (StringUtil.isEmpty(strName)) {
            GlobalFuntion.showToastMessage(this, getString(R.string.name_require));
        } else if (StringUtil.isEmpty(strComment)) {
            GlobalFuntion.showToastMessage(this, getString(R.string.comment_require));
        } else {
            showProgressDialog(true);
            Feedback feedback = new Feedback(strName, strPhone, strEmail, strComment);
            ControllerApplication.get(this).getFeedbackDatabaseReference()
                    .child(String.valueOf(System.currentTimeMillis()))
                    .setValue(feedback, (databaseError, databaseReference) -> {
                        showProgressDialog(false);
                        sendFeedbackSuccess();
                    });
        }
    }

    public void sendFeedbackSuccess() {
        GlobalFuntion.hideSoftKeyboard(this);
        GlobalFuntion.showToastMessage(this, getString(R.string.send_feedback_success));
        mEdtName.setText("");
        mEdtPhone.setText("");
        mEdtEmail.setText(mEdtEmail.getText().toString());
        mEdtComment.setText("");
    }

}
