package com.khuongviettai.coffee.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.khuongviettai.coffee.R;

public class PolicyActivity extends AppCompatActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policy);

        imageView = findViewById(R.id.btn_policy_back);
        imageView.setOnClickListener(v -> onBackPressed());
    }
}