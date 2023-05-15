package com.khuongviettai.coffee.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.khuongviettai.coffee.R;
import com.khuongviettai.coffee.activity.ProductDetailActivity;
import com.khuongviettai.coffee.adapter.ProductAdapter;
import com.khuongviettai.coffee.database.ControllerApplication;
import com.khuongviettai.coffee.databinding.FragmentShopBinding;
import com.khuongviettai.coffee.model.Product;
import com.khuongviettai.coffee.utils.GlobalFuntion;
import com.khuongviettai.coffee.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;


public class ShopFragment extends Fragment {
    private KProgressHUD kProgressHUD;
    private FragmentShopBinding fragmentShopBinding;
    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private List<Product> productList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentShopBinding = FragmentShopBinding.inflate(inflater, container, false);
        recyclerView = fragmentShopBinding.rcvProduct;

        if (getActivity() != null) {
            kProgressHUD = KProgressHUD.create(getActivity())
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setLabel("Please wait...")
                    .setCancellable(false)
                    .setAnimationSpeed(2)
                    .setDimAmount(0.5f);
        }
        // set up the adapter and layout manager
        loadDataFromApi("");
        initListener();

        return fragmentShopBinding.getRoot();
    }

    private void initListener() {
        fragmentShopBinding.edtSearchName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Do nothing
            }

            @Override
            public void afterTextChanged(Editable s) {
                String strKey = s.toString().trim();
                if (strKey.equals("") || strKey.length() == 0) {
                    if (productList != null) productList.clear();
                    loadDataFromApi("");
                }
            }
        });

        fragmentShopBinding.imgSearch.setOnClickListener(view -> search());

        fragmentShopBinding.edtSearchName.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                search();
                return true;
            }
            return false;
        });
    }

    private void search() {
        String strKey = fragmentShopBinding.edtSearchName.getText().toString().trim();
        if (productList != null) productList.clear();
        loadDataFromApi(strKey);
        GlobalFuntion.hideSoftKeyboard(getActivity());
    }


    private void displayListFoodSuggest() {


        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
//        fragmentShopBinding.rcvProduct.setLayoutManager(gridLayoutManager);


        productAdapter = new ProductAdapter(productList, this::goToProductDetail);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(productAdapter);

    }

    private void loadDataFromApi(String key) {
        if (getActivity() == null) {
            return;
        }
        ControllerApplication.get(getActivity().getApplication()).getDatabaseReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                productList = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Product product = dataSnapshot.getValue(Product.class);
                    if (product == null) {
                        return;
                    }

                    if (StringUtil.isEmpty(key)) {
                        productList.add(0, product);
                    } else {
                        if (GlobalFuntion.getTextSearch(product.getName()).toLowerCase().trim()
                                .contains(GlobalFuntion.getTextSearch(key).toLowerCase().trim())) {
                            productList.add(0, product);
                        }
                    }
                }
                displayListFoodSuggest();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                GlobalFuntion.showToastMessage(getActivity(), getString(R.string.msg_get_date_error));
            }
        });
    }


    private void goToProductDetail(@NonNull Product product) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("product", product);
        startActivity(requireContext(), ProductDetailActivity.class, bundle);
    }
    public static void startActivity(Context context, Class<?> clz, Bundle bundle) {
        Intent intent = new Intent(context, clz);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}