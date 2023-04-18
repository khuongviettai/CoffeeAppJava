package com.khuongviettai.coffee.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;

import com.khuongviettai.coffee.activity.ProductDetailActivity;
import com.khuongviettai.coffee.adapter.ProductAdapter;
import com.khuongviettai.coffee.database.ApiProduct;
import com.khuongviettai.coffee.databinding.FragmentShopBinding;
import com.khuongviettai.coffee.model.Product;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ShopFragment extends Fragment {

    private KProgressHUD kProgressHUD;
    private FragmentShopBinding fragmentShopBinding;
    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private List<Product> productList = new ArrayList<>();

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
        productAdapter = new ProductAdapter(productList, this::goToProductDetail);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(productAdapter);
        loadDataFromApi();


        // set up search
        initListener();
        return fragmentShopBinding.getRoot();
    }

    private void loadDataFromApi() {
        kProgressHUD.show();
        ApiProduct.apiProduct.call("product").enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                kProgressHUD.dismiss();
                if (response.isSuccessful()) {
                    productList.clear();
                    productList.addAll(response.body());
                    productAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                kProgressHUD.dismiss();
            }
        });
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
                if (strKey.isEmpty()) {
                    // Load all products if search text is empty
                    loadDataFromApi();
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
        if (productList == null) {
            return;
        }
        productList.clear();
        for (Product product : productList) {
            if (product.getName().contains(strKey.toLowerCase())) {
                productList.add(product);
            }
        }
        productAdapter.notifyDataSetChanged();
        hideSoftKeyboard(getActivity());
    }

    public static void hideSoftKeyboard(Activity activity) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.
                    getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
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
