package com.example.foodify.Retrofit;

import com.example.foodify.Model.AuthRespose;
import com.example.foodify.Model.Category;
import com.example.foodify.Model.CategoryById;
import com.example.foodify.Model.FoodItem;
import com.example.foodify.Model.LoginData;
import com.example.foodify.Model.RegisterData;
import com.example.foodify.Model.User;

import java.util.ArrayList;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetrofitInterface {
    @POST("user/login")
    Observable<AuthRespose> loginUser(@Body LoginData loginData);

    @POST("user/signup")
    Observable<AuthRespose> registerUser(@Body RegisterData registerData);

    @GET("user/info")
    Observable<User> userData(@Header("accesstoken")String accessToken);

    @GET("category/all")
    Observable<ArrayList<Category>>allCategory();

    @GET("category/{id}")
    Observable<CategoryById>allFood(@Path("id") String id);





}
