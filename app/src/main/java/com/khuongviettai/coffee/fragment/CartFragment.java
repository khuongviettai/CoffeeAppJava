package com.khuongviettai.coffee.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.khuongviettai.coffee.R;
import com.khuongviettai.coffee.adapter.CartAdapter;
import com.khuongviettai.coffee.databinding.FragmentCartBinding;
import com.khuongviettai.coffee.model.Product;

import java.util.List;


public class CartFragment extends Fragment {

    private FragmentCartBinding binding;
    private CartAdapter cartAdapter;
    private List<Product> productList;
    private int mAmount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCartBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}