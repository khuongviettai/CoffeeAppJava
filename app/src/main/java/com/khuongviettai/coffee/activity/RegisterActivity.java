package com.khuongviettai.coffee.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.khuongviettai.coffee.R;
import com.khuongviettai.coffee.database.ApiProduct;
import com.khuongviettai.coffee.model.Order;
import com.khuongviettai.coffee.model.User;
import com.khuongviettai.coffee.utils.StringUtil;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText rst_username, rst_phone, rst_password, rst_confirm;
    private Button btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        infoInput();
        clickRegister();
    }

    private void infoInput() {
        rst_username = findViewById(R.id.rst_username);
        rst_phone = findViewById(R.id.rst_phone);
        rst_password = findViewById(R.id.rst_password);
        rst_confirm = findViewById(R.id.rst_confirm);
        btn_register = findViewById(R.id.btn_register);
    }

    private void clickRegister(){
        btn_register.setOnClickListener(v-> validateRegister());
    }

    private void validateRegister() {
        String username = rst_username.getText().toString().trim();
        String phone = rst_phone.getText().toString().trim();
        String password = rst_password.getText().toString().trim();
        String confirm = rst_confirm.getText().toString().trim();

        if (StringUtil.isEmpty(username)) {
            Toast.makeText(RegisterActivity.this, getString(R.string.msg_name), Toast.LENGTH_SHORT).show();
            return;
        }

        if (StringUtil.isEmpty(phone)) {
            Toast.makeText(RegisterActivity.this, getString(R.string.msg_phone), Toast.LENGTH_SHORT).show();
            return;
        }

        if (StringUtil.isEmpty(password)) {
            Toast.makeText(RegisterActivity.this, getString(R.string.msg_password), Toast.LENGTH_SHORT).show();
            return;
        }


        if (!password.equals(confirm)) {
            Toast.makeText(RegisterActivity.this, getString(R.string.msg_confirm_password), Toast.LENGTH_SHORT).show();
        } else {

            ApiProduct.apiProduct.checkPhoneExistence(phone).enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(@NonNull Call<Boolean> call, @NonNull Response<Boolean> response) {
                    if (response.body() != null && response.body()) {
                        Toast.makeText(RegisterActivity.this, getString(R.string.msg_phone_exist), Toast.LENGTH_SHORT).show();
                    } else {

                    }
                }

                @Override
                public void onFailure(@NonNull Call<Boolean> call, @NonNull Throwable t) {
                    Toast.makeText(RegisterActivity.this, "Can't register! Please try again", Toast.LENGTH_LONG).show();
                }
            });

        }
    }




}
