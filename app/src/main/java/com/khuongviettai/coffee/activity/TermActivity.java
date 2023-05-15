package com.khuongviettai.coffee.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.khuongviettai.coffee.R;

public class TermActivity extends AppCompatActivity {
    private ImageView btn_term_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term);
        btn_term_back = findViewById(R.id.btn_term_back);
        btn_term_back.setOnClickListener(v-> onBackPressed());
    }
}