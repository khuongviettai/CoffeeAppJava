package com.khuongviettai.coffee.database;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.khuongviettai.coffee.model.Product;
import com.khuongviettai.coffee.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiProduct {
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    ApiProduct apiProduct = new Retrofit.Builder()
            .baseUrl("https://api-coffee-e8kl.onrender.com/api/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiProduct.class);


    @GET("product")
    Call<List<Product>> call(@Query("product") String products);

    @GET("user")
    Call<List<User>> getUserList(@Query("userName") String userName);
    @GET("user")
    Call<Boolean> checkPhoneExistence(@Query("phone") String phone);

    @POST("/user/add")
    Call<User> registerUser(@Body User user);

}
