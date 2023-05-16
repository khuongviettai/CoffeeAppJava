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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.khuongviettai.coffee.R;
import com.khuongviettai.coffee.adapter.CartAdapter;
import com.khuongviettai.coffee.database.ProductDAO;
import com.khuongviettai.coffee.database.ProductDataBase;
import com.khuongviettai.coffee.databinding.FragmentCartBinding;
import com.khuongviettai.coffee.listener.ReloadListCartEvent;
import com.khuongviettai.coffee.model.Order;
import com.khuongviettai.coffee.model.Product;
import com.khuongviettai.coffee.utils.StringUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
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
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        displayListInCart();
        binding.tvOrderCart.setOnClickListener(v -> onClickOrderCart());


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
        productList = ProductDataBase.getInstance(getActivity()).productDAO().  list();
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
        List<Product> listFoodCart = ProductDataBase.getInstance(getActivity()).productDAO().list();
        if (listFoodCart == null || listFoodCart.isEmpty()) {
            String strZero = 0 + "";
            binding.tvTotalPrice.setText(strZero);
            mAmount = 0;
            return;
        }

        int totalPrice = 0;
        for (Product food : listFoodCart) {
            totalPrice = totalPrice + food.getTotalPrice();
        }


        String strTotalPrice = totalPrice + "";
        DecimalFormat formatter = new DecimalFormat("#,### đ");
        String formattedOldPrice = formatter.format(Double.parseDouble(strTotalPrice));
        binding.tvTotalPrice.setText(formattedOldPrice);
//        mAmount = totalPrice;
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ReloadListCartEvent event) {
        displayListInCart();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }


    public void onClickOrderCart() {
        if (getActivity() == null) {
            return;
        }
        if (productList == null || productList.isEmpty()) {
            return;
        }
        @SuppressLint("InflateParams") View viewDialog = getLayoutInflater().inflate(R.layout.layout_bottom_sheet_order, null);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity());
        bottomSheetDialog.setContentView(viewDialog);
        bottomSheetDialog.getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED);


        TextView tvFoodsOrder = viewDialog.findViewById(R.id.tv_foods_order);
        TextView tvPriceOrder = viewDialog.findViewById(R.id.tv_price_order);
        TextView edtNameOrder = viewDialog.findViewById(R.id.edt_name_order);
        TextView edtPhoneOrder = viewDialog.findViewById(R.id.edt_phone_order);
        TextView edtAddressOrder = viewDialog.findViewById(R.id.edt_address_order);
        TextView tvCancelOrder = viewDialog.findViewById(R.id.tv_cancel_order);
        TextView tvCreateOrder = viewDialog.findViewById(R.id.tv_create_order);


        tvFoodsOrder.setText(getStringListFoodsOrder());
        tvPriceOrder.setText(binding.tvTotalPrice.getText().toString());
        tvCancelOrder.setOnClickListener(v -> bottomSheetDialog.dismiss());


        tvCreateOrder.setOnClickListener(v -> {
            String strName = edtNameOrder.getText().toString().trim();
            String strPhone = edtPhoneOrder.getText().toString().trim();
            String strAddress = edtAddressOrder.getText().toString().trim();


        });

        bottomSheetDialog.show();


    }

    private String getStringListFoodsOrder() {
        if (productList == null || productList.isEmpty()) {
            return "";
        }
        String result = "";
        for (Product product : productList) {
            if (StringUtil.isEmpty(result)) {
                result = "- " + product.getName() + " (" + product.getRealPrice() + "" + ") "
                        + "- " + getString(R.string.quantity) + " " + product.getCount() + "- " + getString(R.string.topping) + " " + product.getSaveTopping() + "- " + getString(R.string.size) + " " + product.getSaveSize();
            } else {
                result = result + "\n" + "- " + product.getName() + " (" + product.getRealPrice() + "" + ") "
                        + "- " + getString(R.string.quantity) + " " + product.getCount() + "- " + getString(R.string.topping) + " " + product.getSaveTopping() + "- " + getString(R.string.size) + " " + product.getSaveSize();
            }
        }
        return result;
    }

}

