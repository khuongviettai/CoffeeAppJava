package com.khuongviettai.coffee.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.List;

@Entity(tableName = "product")
public class Product implements Serializable {
    @PrimaryKey
    @NonNull
    private String _id;
    private String name;
    private int price;
    private int sale;
    private int quantity;
    private String image;
    private List<String> topping;
    private List<String> size;
    private String description;
    private String category;
    private int count;
    private int saveTopping;
    private int saveSize;
    private int totalPrice;

    public Product(@NonNull String _id, String name, int price, int sale, int quantity, String image, List<String> topping, List<String> size, String description, String category, int count, int saveTopping, int saveSize, int totalPrice) {
        this._id = _id;
        this.name = name;
        this.price = price;
        this.sale = sale;
        this.quantity = quantity;
        this.image = image;
        this.topping = topping;
        this.size = size;
        this.description = description;
        this.category = category;
        this.count = count;
        this.saveTopping = saveTopping;
        this.saveSize = saveSize;
        this.totalPrice = totalPrice;
    }

    public int getRealPrice() {
        if (sale <= 0) {
            return price;
        }
        return price - (price * sale / 100);
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getSale() {
        return sale;
    }

    public void setSale(int sale) {
        this.sale = sale;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<String> getTopping() {
        return topping;
    }

    public void setTopping(List<String> topping) {
        this.topping = topping;
    }

    public List<String> getSize() {
        return size;
    }

    public void setSize(List<String> size) {
        this.size = size;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getSaveTopping() {
        return saveTopping;
    }

    public void setSaveTopping(int saveTopping) {
        this.saveTopping = saveTopping;
    }

    public int getSaveSize() {
        return saveSize;
    }

    public void setSaveSize(int saveSize) {
        this.saveSize = saveSize;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}
