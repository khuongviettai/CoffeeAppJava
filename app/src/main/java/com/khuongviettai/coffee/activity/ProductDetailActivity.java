package com.khuongviettai.coffee.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;


import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.khuongviettai.coffee.R;
import com.khuongviettai.coffee.database.ProductDataBase;
import com.khuongviettai.coffee.databinding.ActivityProductDetailBinding;
import com.khuongviettai.coffee.listener.ReloadListCartEvent;
import com.khuongviettai.coffee.model.Product;
import com.khuongviettai.coffee.utils.LoadImageProduct;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.List;


public class ProductDetailActivity extends AppCompatActivity {

    private ActivityProductDetailBinding binding;
    private Product product;
    

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        imageView = binding.imgPd;
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

        setStatusButtonAddToCart();


    }




//    bottom sheet chose option
    public void onClickAddToCart() {

        if (isProductInCart()) {
            return;
        }

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
        product.setCount(1);
        product.setSaveTopping("Không Topping");
        product.setSaveSize("S");


        //        check size have null

        if (product.getSize() == null || product.getSize().isEmpty()) {
            viewDialog.findViewById(R.id.vt_size).setVisibility(View.GONE);
            viewDialog.findViewById(R.id.tv_note_size).setVisibility(View.GONE);
        } else {
            for (int i = 0; i < product.getSize().size(); i++) {
                RadioButton radioButton = new RadioButton(this);
                radioButton.setText(product.getSize().get(i));
                radioButton.setTag(i);
                rg_size.addView(radioButton);
                if (i == 0) {
                    radioButton.setChecked(true);
                }
            }
        }

//        check topping have null

        if (product.getTopping() == null || product.getTopping().isEmpty()) {
            viewDialog.findViewById(R.id.tv_topping).setVisibility(View.GONE);
            viewDialog.findViewById(R.id.tv_note_topping).setVisibility(View.GONE);
        } else {
            for (int i = 0; i < product.getTopping().size(); i++) {
                RadioButton radioButton = new RadioButton(this);
                radioButton.setText(product.getTopping().get(i));
                radioButton.setTag(i);
                rg_topping.addView(radioButton);
                if (i == 0) {
                    radioButton.setChecked(true);
                }
            }
        }

//        total default

        int totalPrice = product.getRealPrice();
        String strTotalPrice = totalPrice + "";
        DecimalFormat formatter = new DecimalFormat("#,### đ");
        String formattedOldPrice = formatter.format(Double.parseDouble(strTotalPrice));
        tvPriceCart.setText(formattedOldPrice);
        product.setTotalPrice(totalPrice);


//        logic chose option size

        rg_size.setOnCheckedChangeListener((group, checkedId) -> {
            int newPrice = product.getRealPrice();
            int sizeOption = (int) rg_size.findViewById(checkedId).getTag();
            if (sizeOption >= 0) {
                newPrice += 10000 * (sizeOption);
                String selectedSize = product.getSize().get(sizeOption);
                product.setSaveSize(selectedSize);

            }




            int newTotalPrice = newPrice;
            int toppingOption = rg_topping.getCheckedRadioButtonId() != -1 ? (int) rg_topping.findViewById(rg_topping.getCheckedRadioButtonId()).getTag() : 0;
            if (toppingOption >= 1) {
                newTotalPrice += 10000;
            }

            int count = Integer.parseInt(tvCount.getText().toString());
            int totalPrice1 = newTotalPrice * count;
            product.setTotalPrice(totalPrice1);
            String strTotalPrice1 = totalPrice1 + "";
            String formattedNewPrice = formatter.format(Double.parseDouble(strTotalPrice1));
            tvPriceCart.setText(formattedNewPrice);


        });

//        logic chose option topping
        rg_topping.setOnCheckedChangeListener((group, checkedId) -> {
            int newPrice2 = product.getRealPrice();
            int toppingOption = rg_topping.getCheckedRadioButtonId() != -1 ? (int) rg_topping.findViewById(rg_topping.getCheckedRadioButtonId()).getTag() : 0;
            if (toppingOption >= 1) {
                newPrice2 += 10000;
                String selectedTopping = product.getTopping().get(toppingOption);
                product.setSaveTopping(selectedTopping);


            }
//            product.setSaveTopping(toppingOption);

            int newTotalPrice = newPrice2;
            int sizeOption = rg_size.getCheckedRadioButtonId() != -1 ? (int) rg_size.findViewById(rg_size.getCheckedRadioButtonId()).getTag() : 0;
            if (sizeOption >= 0) {
                newTotalPrice += 10000 * (sizeOption);

            }

            int count = Integer.parseInt(tvCount.getText().toString());
            int totalPrice1 = newTotalPrice * count;
            product.setTotalPrice(totalPrice1);
            String strTotalPrice1 = totalPrice1 + "";
            String formattedNewPrice = formatter.format(Double.parseDouble(strTotalPrice1));
            tvPriceCart.setText(formattedNewPrice);

        });


        //   btn +
        tvAddCount.setOnClickListener(v -> {
            int newCount = Integer.parseInt(tvCount.getText().toString()) + 1;
            tvCount.setText(String.valueOf(newCount));
            int newTotalPrice = product.getRealPrice();
            int sizeOption = rg_size.getCheckedRadioButtonId() != -1 ? (int) rg_size.findViewById(rg_size.getCheckedRadioButtonId()).getTag() : 0;
            if (sizeOption >= 0) {
                newTotalPrice += 10000 * (sizeOption);
            }
            int toppingOption = rg_topping.getCheckedRadioButtonId() != -1 ? (int) rg_topping.findViewById(rg_topping.getCheckedRadioButtonId()).getTag() : 0;
            if (toppingOption >= 1) {
                newTotalPrice += 10000;
            }
            product.setCount(newCount);
            int totalPrice2 = newTotalPrice * newCount;
            product.setTotalPrice(totalPrice2);
            String strTotalPrice2 = totalPrice2 + "";
            String formattedNewPrice = formatter.format(Integer.parseInt(strTotalPrice2));
            tvPriceCart.setText(formattedNewPrice);

        });


        //        btn -
        tvSubtractCount.setOnClickListener(v -> {
            int count = Integer.parseInt(tvCount.getText().toString());
            if (count <= 1) {
                return;
            }
            int newCount = count - 1;
            tvCount.setText(String.valueOf(newCount));

            int newTotalPrice = product.getRealPrice();
            int sizeOption = rg_size.getCheckedRadioButtonId() != -1 ? (int) rg_size.findViewById(rg_size.getCheckedRadioButtonId()).getTag() : 0;
            if (sizeOption >= 0) {
                newTotalPrice += 10000 * (sizeOption);
            }
            int toppingOption = rg_topping.getCheckedRadioButtonId() != -1 ? (int) rg_topping.findViewById(rg_topping.getCheckedRadioButtonId()).getTag() : 0;
            if (toppingOption >= 1) {
                newTotalPrice += 10000;
            }
            product.setCount(newCount);
            int totalPrice1 = newTotalPrice * newCount;
            product.setTotalPrice(totalPrice1);
            String strTotalPrice1 = totalPrice1 + "";
            DecimalFormat formatter1 = new DecimalFormat("#,### đ");
            String formattedNewPrice = formatter1.format(Double.parseDouble(strTotalPrice1));
            tvPriceCart.setText(formattedNewPrice);
        });


        tvCancel.setOnClickListener(v -> bottomSheetDialog.dismiss());

        tvAddCart.setOnClickListener(v -> {
            ProductDataBase.getInstance(ProductDetailActivity.this).productDAO().insert(product);
            bottomSheetDialog.dismiss();
            setStatusButtonAddToCart();
//            reload khi them san pham vao gio hang
            EventBus.getDefault().post(new ReloadListCartEvent());


        });

        bottomSheetDialog.show();
    }

    private void initListener() {
        binding.tvAddToCart.setOnClickListener(v -> onClickAddToCart());
    }


    private void setStatusButtonAddToCart() {
        if (isProductInCart()) {
            binding.tvAddToCart.setBackgroundResource(R.drawable.bg_disable_shape_corner_6);
            binding.tvAddToCart.setText(getString(R.string.added_to_cart));
            binding.tvAddToCart.setTextColor(ContextCompat.getColor(this, R.color.textColorPrimary));

        } else {
            binding.tvAddToCart.setBackgroundResource(R.drawable.bg_brown_shape_corner_6);
            binding.tvAddToCart.setText(getString(R.string.add_to_cart));
            binding.tvAddToCart.setTextColor(ContextCompat.getColor(this, R.color.white));
        }
    }


    private boolean isProductInCart() {
        List<Product> list = ProductDataBase.getInstance(this).productDAO().checkProductInCart(product.get_id());
        return list != null && !list.isEmpty();
    }


}