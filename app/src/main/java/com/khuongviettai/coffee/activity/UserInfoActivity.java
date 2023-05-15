package com.khuongviettai.coffee.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.khuongviettai.coffee.R;
import com.khuongviettai.coffee.database.ControllerApplication;
import com.khuongviettai.coffee.local.DataStoreManager;
import com.khuongviettai.coffee.model.UserInfo;

public class UserInfoActivity extends AppCompatActivity {

    private ImageView btn_back;
    private EditText edt_name, edt_phone, edt_address;
    private TextView tv_agree;
    private DatabaseReference userDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(v -> onBackPressed());

        edt_name = findViewById(R.id.edt_name);
        edt_phone = findViewById(R.id.edt_phone);
        edt_address = findViewById(R.id.edt_address);
        tv_agree = findViewById(R.id.tv_agree);

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        userDatabaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(userId);

        // Retrieve the user information from Firebase
        userDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    UserInfo userInfo = dataSnapshot.getValue(UserInfo.class);
                    if (userInfo != null) {
                        // Populate the EditText fields with the existing user information
                        edt_name.setText(userInfo.getFullName());
                        edt_phone.setText(userInfo.getPhone());
                        edt_address.setText(userInfo.getAddress());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Error occurred while retrieving user info
                Toast.makeText(UserInfoActivity.this, "Failed to retrieve user info", Toast.LENGTH_SHORT).show();
            }
        });

        tv_agree.setOnClickListener(v -> {
            String strName = edt_name.getText().toString().trim();
            String strPhone = edt_phone.getText().toString().trim();
            String strAddress = edt_address.getText().toString().trim();

            UserInfo userInfo = new UserInfo(strName, strPhone, strAddress);
            userDatabaseReference.setValue(userInfo, (databaseError, databaseReference) -> {
                if (databaseError == null) {
                    // Successfully saved the user info
                    Toast.makeText(UserInfoActivity.this, "User info saved", Toast.LENGTH_SHORT).show();
                } else {
                    // Error occurred while saving user info
                    Toast.makeText(UserInfoActivity.this, "Failed to save user info", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}

