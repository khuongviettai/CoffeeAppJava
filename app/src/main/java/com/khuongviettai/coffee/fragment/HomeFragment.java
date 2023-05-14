package com.khuongviettai.coffee.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.khuongviettai.coffee.R;
import com.khuongviettai.coffee.activity.ProductDetailActivity;
import com.khuongviettai.coffee.adapter.ProductAdapter;
import com.khuongviettai.coffee.database.ControllerApplication;
import com.khuongviettai.coffee.databinding.FragmentHomeBinding;
import com.khuongviettai.coffee.model.Product;
import com.khuongviettai.coffee.utils.GlobalFuntion;
import com.khuongviettai.coffee.utils.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding fragmentHomeBinding;
    private ImageSlider imageSlider;
    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private List<Product> productList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentHomeBinding =  FragmentHomeBinding.inflate(inflater, container, false);
        recyclerView = fragmentHomeBinding.rcvProductHome;

        imageSlider = fragmentHomeBinding.imageSlide.findViewById(R.id.image_slide);
        ArrayList<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.slider1, ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.slider2, ScaleTypes.CENTER_CROP));
        imageSlider.setImageList(slideModels,ScaleTypes.CENTER_CROP);
        loadDataFromApi("");

        return fragmentHomeBinding.getRoot();
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
        productList = new ArrayList<>(); // initialize productList
        ControllerApplication.get(getActivity().getApplication()).getDatabaseReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Product> allProducts = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Product product = dataSnapshot.getValue(Product.class);
                    if (product != null) {
                        allProducts.add(product);
                    }
                }
                if (!allProducts.isEmpty()) {
                    int randomCount = 4 + (int) (Math.random() * 5);
                    Collections.shuffle(allProducts);
                    List<Product> limitedProducts = allProducts.subList(0, Math.min(allProducts.size(), randomCount));

                    productList.clear();
                    productList.addAll(limitedProducts);
                    displayListFoodSuggest();
                }
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