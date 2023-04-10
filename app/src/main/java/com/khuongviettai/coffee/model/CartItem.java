package com.khuongviettai.coffee.model;

public class CartItem {
    private Product product;
    private String size;
    private String topping;
    private int quantity;

    public CartItem(Product product, String size, String topping, int quantity) {
        this.product = product;
        this.size = size;
        this.topping = topping;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getTopping() {
        return topping;
    }

    public void setTopping(String topping) {
        this.topping = topping;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getTotalPrice() {
        return product.getRealPrice() * quantity;
    }
}

