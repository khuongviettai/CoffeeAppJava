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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.installations.Utils;
import com.khuongviettai.coffee.R;
import com.khuongviettai.coffee.activity.UserInfoActivity;
import com.khuongviettai.coffee.adapter.CartAdapter;
import com.khuongviettai.coffee.database.ControllerApplication;
import com.khuongviettai.coffee.database.ProductDataBase;
import com.khuongviettai.coffee.databinding.FragmentCartBinding;
import com.khuongviettai.coffee.listener.ReloadListCartEvent;

import com.khuongviettai.coffee.local.DataStoreManager;
import com.khuongviettai.coffee.model.Order;
import com.khuongviettai.coffee.model.Product;
import com.khuongviettai.coffee.model.UserInfo;
import com.khuongviettai.coffee.utils.Constant;
import com.khuongviettai.coffee.utils.GlobalFuntion;
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
    private DatabaseReference userDatabaseReference;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCartBinding.inflate(inflater, container, false);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        displayListInCart();
        binding.tvOrderCart.setOnClickListener(v -> onClickOrderCart());

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        userDatabaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(userId);

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

        userDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    UserInfo userInfo = dataSnapshot.getValue(UserInfo.class);
                    if (userInfo != null) {
                        // Populate the EditText fields with the existing user information
                        edtNameOrder.setText(userInfo.getFullName());
                        edtPhoneOrder.setText(userInfo.getPhone());
                        edtAddressOrder.setText(userInfo.getAddress());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Error occurred while retrieving user info
                Toast.makeText(getActivity(), "Failed to retrieve user info", Toast.LENGTH_SHORT).show();
            }
        });


        tvCreateOrder.setOnClickListener(v -> {
            if (edtNameOrder != null && edtPhoneOrder != null && edtAddressOrder != null) {
                String strName = edtNameOrder.getText().toString().trim();
                String strPhone = edtPhoneOrder.getText().toString().trim();
                String strAddress = edtAddressOrder.getText().toString().trim();



                UserInfo userInfo = new UserInfo( strPhone, strAddress,strName);
                userDatabaseReference.setValue(userInfo, (databaseError, databaseReference) -> {
                    if (databaseError == null) {
                        // Successfully saved the user info

                    } else {
                        // Error occurred while saving user info

                    }
                });

                if (StringUtil.isEmpty(strName) || StringUtil.isEmpty(strPhone) || StringUtil.isEmpty(strAddress)) {
                    GlobalFuntion.showToastMessage(getActivity(), getString(R.string.message_enter_infor_order));
                } else {
                    long id = System.currentTimeMillis();
                    Order order = new Order(id, strName, strPhone, strAddress,
                            mAmount, getStringListFoodsOrder(), Constant.TYPE_PAYMENT_CASH);
                    ControllerApplication.get(getActivity()).getBookingDatabaseReference()
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .child(String.valueOf(id))
                            .setValue(order, (error1, ref1) -> {
                                GlobalFuntion.showToastMessage(getActivity(), getString(R.string.msg_order_success));
                                GlobalFuntion.hideSoftKeyboard(getActivity());
                                bottomSheetDialog.dismiss();
                                ProductDataBase.getInstance(getActivity()).productDAO().deleteAllFood();
                                clearCart();
                            });
                }
            }
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