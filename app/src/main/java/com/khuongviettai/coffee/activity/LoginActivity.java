package com.khuongviettai.coffee.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.khuongviettai.coffee.R;
import com.khuongviettai.coffee.database.ApiProduct;
import com.khuongviettai.coffee.model.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextUser, editTextPassword;

    private List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextUser = findViewById(R.id.edt_login_username);
        editTextPassword = findViewById(R.id.edt_password_login);
        Button btnLogin = findViewById(R.id.btn_login);
        TextView tvChangeToRegister = findViewById(R.id.tv_login_change_register);
        userList = new ArrayList<>();
        getListUser();

        tvChangeToRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        btnLogin.setOnClickListener(v -> clickLogin());
    }
    private void clickLogin() {
        String strUsername = editTextUser.getText().toString().trim();
        String strPassword = editTextPassword.getText().toString().trim();

        if (userList == null || userList.isEmpty()){
            return;
        }
        boolean isSuccessLogin = false;
        for (User user : userList){
            if (strUsername.equals(user.getPhone()) && strPassword.equals((user.getPassword()))){
                isSuccessLogin = true;
                break;
            }
        }
        if (isSuccessLogin){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(LoginActivity.this, "phone or password wrong", Toast.LENGTH_LONG).show();
        }
    }

    private void getListUser() {
        ApiProduct.apiProduct.getUserList("userName").enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(@NonNull Call<List<User>> call, @NonNull Response<List<User>> response) {
                userList = response.body();
            }

            @Override
            public void onFailure(@NonNull Call<List<User>> call, @NonNull Throwable t) {
                Toast.makeText(LoginActivity.this, "Unstable network! Please try again later", Toast.LENGTH_LONG).show();
            }
        });
    }
}