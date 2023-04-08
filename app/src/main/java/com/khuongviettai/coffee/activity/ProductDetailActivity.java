package com.khuongviettai.coffee.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.room.Room;
import androidx.room.RoomDatabase;


import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;

import com.khuongviettai.coffee.R;
import com.khuongviettai.coffee.databinding.ActivityProductDetailBinding;
import com.khuongviettai.coffee.model.Product;
import com.khuongviettai.coffee.utils.LoadImageProduct;

import java.text.DecimalFormat;
import java.util.List;


public class ProductDetailActivity extends AppCompatActivity {

    private ActivityProductDetailBinding binding;
    private Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getDataIntent();
        setDataFoodDetail();
    }
    private void getDataIntent() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            product = (Product) bundle.get("product");
        }
    }

    private void setDataFoodDetail() {
        if (product == null) {
            return;
        }

        LoadImageProduct.loadUrl(product.getImage(), binding.imageProduct);
        if (product.getSale() <= 0) {
            binding.tvSaleOff.setVisibility(View.GONE);
            binding.tvPrice.setVisibility(View.GONE);

            String strPrice = product.getPrice() + "";
            DecimalFormat formatter = new DecimalFormat("#,### đ");
            String formattedOldPrice = formatter.format(Double.parseDouble(strPrice));
            binding.tvPriceSale.setText(formattedOldPrice);
        } else {
            binding.tvSaleOff.setVisibility(View.VISIBLE);
            binding.tvPrice.setVisibility(View.VISIBLE);

            String strSale = "Sale " + product.getSale() + "%";
            binding.tvSaleOff.setText(strSale);

            String strPriceOld = product.getPrice() + "";
            String strRealPrice = product.getRealPrice() + "";
            DecimalFormat formatter = new DecimalFormat("#,### đ");
            String formattedOldPrice = formatter.format(Double.parseDouble(strPriceOld));
            binding.tvPrice.setText(formattedOldPrice);
            binding.tvPrice.setPaintFlags(binding.tvPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            String formattedRealPrice = formatter.format(Double.parseDouble(strRealPrice));
            binding.tvPriceSale.setText(formattedRealPrice);
        }
        binding.tvFoodName.setText(product.getName());
        binding.tvProductDescription.setText(product.getDescription());


    }



}