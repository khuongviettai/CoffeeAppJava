package com.khuongviettai.coffee.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.khuongviettai.coffee.R;
import com.khuongviettai.coffee.adapter.CartAdapter;
import com.khuongviettai.coffee.database.ProductDAO;
import com.khuongviettai.coffee.database.ProductDataBase;
import com.khuongviettai.coffee.databinding.FragmentCartBinding;
import com.khuongviettai.coffee.model.Product;

import java.util.ArrayList;
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
        displayListInCart();

        return binding.getRoot();
    }

    private void displayListInCart() {
        if (getActivity() == null) {
            return;
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        binding.rcvCart.setLayoutManager(linearLayoutManager);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        binding.rcvCart.addItemDecoration(itemDecoration);
        initDataCart();
    }

    private void initDataCart() {
        productList = ProductDataBase.getInstance(getActivity()).productDAO().list();
        if (productList == null || productList.isEmpty()) {
            return;
        }

        cartAdapter = new CartAdapter(productList, new CartAdapter.ClickItemListener() {
            @Override
            public void clickDelete(Product product, int position) {
                deleteFromCart(product, position);
            }

            @Override
            public void clickUpdate(Product product, int position) {
                ProductDataBase.getInstance(getActivity()).productDAO().update(product);
                cartAdapter.notifyItemChanged(position);

                calculateTotalPrice();
            }
        });
        binding.rcvCart.setAdapter(cartAdapter);

        calculateTotalPrice();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void clearCart() {
        if (productList != null) {
            productList.clear();
        }
        cartAdapter.notifyDataSetChanged();
        calculateTotalPrice();
    }

    private void calculateTotalPrice() {
        List<Product> listCart = ProductDataBase.getInstance(getActivity()).productDAO().list();
        if (listCart == null || listCart.isEmpty()) {
            String strZero = 0 + "";
            binding.tvTotalPrice.setText(strZero);
            mAmount = 0;
            return;
        }

        int totalPrice = 0;
        for (Product product : listCart) {
            totalPrice += product.getPrice();
        }

        String strTotalPrice = totalPrice + "";
        binding.tvTotalPrice.setText(strTotalPrice);
        mAmount = totalPrice;
    }

    private void deleteFromCart(Product product, int position) {
        new AlertDialog.Builder(getActivity())
                .setTitle(getString(R.string.confirm_delete))
                .setMessage(getString(R.string.message_delete))
                .setPositiveButton(getString(R.string.delete), (dialog, which) -> {
                    ProductDataBase.getInstance(getActivity()).productDAO().delete(product);
                    productList.remove(position);
                    cartAdapter.notifyItemRemoved(position);

                    calculateTotalPrice();
                })
                .setNegativeButton(getString(R.string.cancel), (dialog, which) -> dialog.dismiss())
                .show();
    }

}

