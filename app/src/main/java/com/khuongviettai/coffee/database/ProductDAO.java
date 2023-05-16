package com.khuongviettai.coffee.database;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.khuongviettai.coffee.model.Product;

import java.util.List;

@Dao
public interface ProductDAO {

    @Insert
    void insert(Product product);

    @Query("SELECT * FROM product")
    List<Product> list();

    @Query("SELECT * FROM product WHERE _id=:id")
    List<Product> checkProductInCart(String id);


    @Delete
    void delete(Product product);

    @Update
    void update(Product product);

    @Query("DELETE from product")
    void deleteAllFood();


}
