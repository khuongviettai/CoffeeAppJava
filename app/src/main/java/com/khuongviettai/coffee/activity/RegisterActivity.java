package com.khuongviettai.coffee.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.khuongviettai.coffee.R;
import com.khuongviettai.coffee.database.ApiProduct;
import com.khuongviettai.coffee.model.Order;
import com.khuongviettai.coffee.model.User;
import com.khuongviettai.coffee.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText rst_username, rst_phone, rst_password, rst_confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        infoInput();
        validateRegister();
    }

    private void infoInput() {
        rst_username = findViewById(R.id.rst_username);
        rst_phone = findViewById(R.id.rst_phone);
        rst_password = findViewById(R.id.rst_password);
        rst_confirm = findViewById(R.id.rst_confirm);
    }

    private void validateRegister() {
        String username = rst_username.getText().toString().trim();
        String phone = rst_phone.getText().toString().trim();
        String password = rst_password.getText().toString().trim();
        String confirm = rst_confirm.getText().toString().trim();
        // Check if the username is empty
        if (StringUtil.isEmpty(username)) {
            Toast.makeText(RegisterActivity.this, getString(R.string.msg_name), Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if the phone is empty
        if (StringUtil.isEmpty(phone)) {
            Toast.makeText(RegisterActivity.this, getString(R.string.msg_phone), Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if the password is empty
        if (StringUtil.isEmpty(password)) {
            Toast.makeText(RegisterActivity.this, getString(R.string.msg_password), Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if the confirm field matches the password
        if (!password.equals(confirm)) {
            Toast.makeText(RegisterActivity.this, getString(R.string.msg_confirm_password), Toast.LENGTH_SHORT).show();
            return;
        } else {
            // Check if the phone number already exists
            ApiProduct.apiProduct.checkPhoneExistence(phone).enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.body() != null && response.body()) {
                        // If the phone number already exists, show an error message
                        Toast.makeText(RegisterActivity.this, getString(R.string.msg_phone_exist), Toast.LENGTH_SHORT).show();
                    } else {
                        // If the phone number does not exist, register the user
                        registerUser(username, phone, password);
                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    Toast.makeText(RegisterActivity.this, "Can't register! Please try again", Toast.LENGTH_LONG).show();
                }
            });

        }
    }

    private void registerUser(String name, String phone, String password) {
        // Create a new user object
        User user = new User(name, phone, password, "", false, new ArrayList<Order>());


        // Make an API call to register the new user
        ApiProduct.apiProduct.registerUser(user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    // If the registration is successful, show a success message
                    Toast.makeText(RegisterActivity.this, getString(R.string.msg_register_success), Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    // If the API call fails, show an error message
                    Toast.makeText(RegisterActivity.this, getString(R.string.msg_register_failed), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Unstable network! Please try again later", Toast.LENGTH_LONG).show();
            }
        });
    }

}
