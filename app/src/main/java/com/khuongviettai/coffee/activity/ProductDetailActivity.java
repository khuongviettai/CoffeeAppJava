package com.khuongviettai.coffee.activity;

import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.khuongviettai.coffee.R;
import com.khuongviettai.coffee.databinding.ActivityProductDetailBinding;
import com.khuongviettai.coffee.model.Product;
import com.khuongviettai.coffee.utils.LoadImageProduct;

import java.text.DecimalFormat;


public class ProductDetailActivity extends AppCompatActivity {

    private ActivityProductDetailBinding binding;
    private Product product;

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        imageView = findViewById(R.id.img_pd);

        imageView.setOnClickListener(v -> onBackPressed());

        getDataIntent();
        setDataFoodDetail();
        initListener();

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

    private void initListener() {
        binding.tvAddToCart.setOnClickListener(v -> onClickAddToCart());

    }

    public void onClickAddToCart() {


        @SuppressLint("InflateParams") View viewDialog = getLayoutInflater().inflate(R.layout.layout_bottom_sheet_cart, null);

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(viewDialog);

        ImageView imgCart = viewDialog.findViewById(R.id.img_product_cart);
        TextView tvNameCart = viewDialog.findViewById(R.id.tv_product_name_cart);
        TextView tvPriceCart = viewDialog.findViewById(R.id.tv_product_price_cart);
        RadioGroup rg_size = viewDialog.findViewById(R.id.rg_size);
        RadioGroup rg_topping = viewDialog.findViewById(R.id.rg_topping);
        TextView tvSubtractCount = viewDialog.findViewById(R.id.tv_subtract);
        TextView tvCount = viewDialog.findViewById(R.id.tv_count);
        TextView tvAddCount = viewDialog.findViewById(R.id.tv_add);
        TextView tvCancel = viewDialog.findViewById(R.id.tv_cancel);
        TextView tvAddCart = viewDialog.findViewById(R.id.tv_add_cart);

        LoadImageProduct.loadUrl(product.getImage(), imgCart);
        tvNameCart.setText(product.getName());

//        bug
        if (product.getSize() == null || product.getSize().isEmpty()) {
            viewDialog.findViewById(R.id.vt_size).setVisibility(View.GONE);
            viewDialog.findViewById(R.id.tv_note_size).setVisibility(View.GONE);
        } else {
            for (int i = 0; i < product.getSize().size(); i++) {
                RadioButton radioButton = new RadioButton(this);
                radioButton.setText(product.getSize().get(i));
                rg_size.addView(radioButton);
                if (i == 0) {
                    radioButton.setChecked(true);
                }
            }
        }

        if (product.getTopping() == null || product.getTopping().isEmpty()) {
            viewDialog.findViewById(R.id.tv_topping).setVisibility(View.GONE);
            viewDialog.findViewById(R.id.tv_note_topping).setVisibility(View.GONE);
        } else {
            for (int i = 0; i < product.getTopping().size(); i++) {
                RadioButton radioButton = new RadioButton(this);
                radioButton.setText(product.getTopping().get(i));
                rg_topping.addView(radioButton);
                if (i == 0) {
                    radioButton.setChecked(true);
                }
            }
        }

// bug
        int totalPrice = product.getRealPrice();
        String strTotalPrice = totalPrice + "";
        DecimalFormat formatter = new DecimalFormat("#,### đ");
        String formattedOldPrice = formatter.format(Double.parseDouble(strTotalPrice));
        tvPriceCart.setText(formattedOldPrice);

        tvSubtractCount.setOnClickListener(v -> {
            int count = Integer.parseInt(tvCount.getText().toString());
            if (count <= 1) {
                return;
            }
            int newCount = Integer.parseInt(tvCount.getText().toString()) - 1;
            tvCount.setText(String.valueOf(newCount));

            int totalPrice1 = product.getRealPrice() * newCount;
            String strTotalPrice1 = totalPrice1 + "";
            DecimalFormat formatter1 = new DecimalFormat("#,### đ");
            String formattedOldPrice1 = formatter1.format(Double.parseDouble(strTotalPrice1));
            tvPriceCart.setText(formattedOldPrice1);


        });

        tvAddCount.setOnClickListener(v -> {
            int newCount = Integer.parseInt(tvCount.getText().toString()) + 1;
            tvCount.setText(String.valueOf(newCount));
            int totalPrice2 = product.getRealPrice() * newCount;
            String strTotalPrice2 = totalPrice2 + "";
            DecimalFormat formatter2 = new DecimalFormat("#,### đ");
            String formattedOldPrice2 = formatter2.format(Double.parseDouble(strTotalPrice2));
            tvPriceCart.setText(formattedOldPrice2);

        });
        tvCancel.setOnClickListener(v -> bottomSheetDialog.dismiss());

        tvAddCart.setOnClickListener(v -> {
            // Get selected size and topping
            int sizeIndex = rg_size.indexOfChild(findViewById(rg_size.getCheckedRadioButtonId()));
            String selectedSize = product.getSize().get(sizeIndex);
            int toppingIndex = rg_topping.indexOfChild(findViewById(rg_topping.getCheckedRadioButtonId()));
            String selectedTopping = product.getTopping().get(toppingIndex);

            // Get selected quantity
            int quantity = Integer.parseInt(tvCount.getText().toString());

            // Close dialog
            bottomSheetDialog.dismiss();
        });

        bottomSheetDialog.show();
    }



}