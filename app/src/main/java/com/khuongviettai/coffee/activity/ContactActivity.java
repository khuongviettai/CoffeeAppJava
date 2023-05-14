package com.khuongviettai.coffee.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;
import android.widget.ImageView;

import com.khuongviettai.coffee.R;
import com.khuongviettai.coffee.adapter.ContactAdapter;
import com.khuongviettai.coffee.databinding.ActivityContactBinding;
import com.khuongviettai.coffee.model.Contact;
import com.khuongviettai.coffee.utils.GlobalFuntion;

import java.util.ArrayList;
import java.util.List;

public class ContactActivity extends AppCompatActivity {

    private ContactAdapter mContactAdapter;
    private ImageView btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityContactBinding mContactBinding = ActivityContactBinding.inflate(getLayoutInflater());
        setContentView(mContactBinding.getRoot());

        mContactAdapter = new ContactAdapter(this, getListContact(), () -> GlobalFuntion.callPhoneNumber(this));
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        mContactBinding.rcvData.setNestedScrollingEnabled(false);
        mContactBinding.rcvData.setFocusable(false);
        mContactBinding.rcvData.setLayoutManager(layoutManager);
        mContactBinding.rcvData.setAdapter(mContactAdapter);

        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(v -> onBackPressed());

    }

    public List<Contact> getListContact() {
        List<Contact> contactArrayList = new ArrayList<>();
        contactArrayList.add(new Contact(Contact.FACEBOOK, R.drawable.ic_facebook));
        contactArrayList.add(new Contact(Contact.HOTLINE, R.drawable.ic_hotline));
        contactArrayList.add(new Contact(Contact.GMAIL, R.drawable.ic_gmail));

        contactArrayList.add(new Contact(Contact.ZALO, R.drawable.ic_zalo));

        return contactArrayList;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mContactAdapter.release();
    }
}
