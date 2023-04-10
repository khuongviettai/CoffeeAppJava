package com.khuongviettai.coffee.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.khuongviettai.coffee.databinding.ItemLayoutCartBinding;
import com.khuongviettai.coffee.listener.ClickItemListener;
import com.khuongviettai.coffee.model.Product;
import com.khuongviettai.coffee.utils.LoadImageProduct;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private List<Product> productList;
    private ClickItemListener clickItemListener;

    public CartAdapter(List<Product> productList, ClickItemListener clickItemListener) {
        this.productList = productList;
        this.clickItemListener = clickItemListener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemLayoutCartBinding itemLayoutCartBinding = ItemLayoutCartBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CartViewHolder(itemLayoutCartBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Product food = productList.get(position);
        if (food == null) {
            return;
        }
        LoadImageProduct.loadUrl(food.getImage(), holder.itemLayoutCartBinding.imgProductCart);
        holder.itemLayoutCartBinding.tvProductNameCart.setText(food.getName());


        String strPriceCart = food.getPrice() +"";
        if (food.getSale() > 0) {
            strPriceCart = food.getRealPrice() + "";
        }
        holder.itemLayoutCartBinding.tvProductPriceCart.setText(strPriceCart);


        holder.itemLayoutCartBinding.tvSubtract.setOnClickListener(v -> {
            String strCount = holder.itemLayoutCartBinding.tvCount.getText().toString();
            int count = Integer.parseInt(strCount);
            if (count <= 1) {
                return;
            }
            int newCount = count - 1;
            holder.itemLayoutCartBinding.tvCount.setText(String.valueOf(newCount));




        });

        holder.itemLayoutCartBinding.tvAdd.setOnClickListener(v -> {
            int newCount = Integer.parseInt(holder.itemLayoutCartBinding.tvCount.getText().toString()) + 1;
            holder.itemLayoutCartBinding.tvCount.setText(String.valueOf(newCount));

            int totalPrice = food.getRealPrice() * newCount;
        });



    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        private ItemLayoutCartBinding itemLayoutCartBinding;
        public CartViewHolder(ItemLayoutCartBinding itemLayoutCartBinding) {
            super(itemLayoutCartBinding.getRoot());
            this.itemLayoutCartBinding = itemLayoutCartBinding;
        }
    }
}
