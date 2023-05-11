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

import java.text.DecimalFormat;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private List<Product> productList;
    private ClickItemListener clickItemListener;

    public interface ClickItemListener {
        void clickDelete(Product product, int position);

        void clickUpdate(Product product, int position);
    }

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
        Product product = productList.get(position);
        if (product == null) {
            return;
        }
        LoadImageProduct.loadUrl(product.getImage(), holder.binding.imgProductCart);
        holder.binding.tvProductNameCart.setText(product.getName());

        DecimalFormat formatter = new DecimalFormat("#,###đ");


        holder.binding.tvCount.setText(String.valueOf(product.getCount()));
        holder.binding.tvProductToppingCart.setText("Topping: " + String.valueOf((product.getSaveTopping())));
        holder.binding.tvProductSizeCart.setText("Size: " + String.valueOf(product.getSaveSize()));

        String strCount2 = holder.binding.tvCount.getText().toString();
        int counts = Integer.parseInt(strCount2);
        int Price =  (product.getTotalPrice() / counts);
        String formattedOldPrice = formatter.format(Price);
        holder.binding.tvProductPriceCart.setText(String.valueOf(formattedOldPrice + "  X" + "("+ String.valueOf(product.getCount())) + ")") ;




        holder.binding.tvSubtract.setOnClickListener(v -> {
            String strCount = holder.binding.tvCount.getText().toString();
            int count = Integer.parseInt(strCount);
            if (count <= 1) {
                return;
            }
            int newCount = count - 1;
            holder.binding.tvCount.setText(String.valueOf(newCount));

            int totalPrice =  Price * newCount;
            product.setCount(newCount);
            product.setTotalPrice(totalPrice);

            clickItemListener.clickUpdate(product, holder.getAdapterPosition());
        });

        holder.binding.tvAdd.setOnClickListener(v -> {
            int newCount = Integer.parseInt(holder.binding.tvCount.getText().toString()) + 1;
            holder.binding.tvCount.setText(String.valueOf(newCount));


            int totalPrice = Price * newCount;
            product.setCount(newCount);
            product.setTotalPrice(totalPrice);

            clickItemListener.clickUpdate(product, holder.getAdapterPosition());
        });

        holder.binding.tvDelete.setOnClickListener(v
                -> clickItemListener.clickDelete(product, holder.getAdapterPosition()));


    }

    @Override
    public int getItemCount() {
        return null == productList ? 0 : productList.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        private ItemLayoutCartBinding binding;
        public CartViewHolder(ItemLayoutCartBinding itemLayoutCartBinding) {
            super(itemLayoutCartBinding.getRoot());
            this.binding = itemLayoutCartBinding;
        }
    }
}
