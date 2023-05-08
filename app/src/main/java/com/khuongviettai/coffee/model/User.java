package com.khuongviettai.coffee.model;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {
    private String _id;
    private String name;
    private String phone;
    private String password;
    private boolean admin;
    private List<Order> orders;
    private String address;

    public User(String _id, String name, String phone, String password, boolean admin, List<Order> orders, String address) {
        this._id = _id;
        this.name = name;
        this.phone = phone;
        this.password = password;
        this.admin = admin;
        this.orders = orders;
        this.address = address;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}




