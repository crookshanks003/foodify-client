package com.example.foodify.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.foodify.Common.Common;
import com.example.foodify.Model.BadRequestException;
import com.example.foodify.Model.User;
import com.example.foodify.R;
import com.example.foodify.Retrofit.NetworkClient;
import com.example.foodify.Retrofit.RetrofitInterface;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.HttpException;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    private RetrofitInterface service;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button register = findViewById(R.id.signUpButton);
        Button login = findViewById(R.id.logInButton);
//        Log.d("Enter","Hello Screen");
        Retrofit retrofitClient = NetworkClient.getInstance();
        service = retrofitClient.create(RetrofitInterface.class);
        Paper.init(this);
        String token= Paper.book().read(Common.token);
        Log.d("Token",token);
//        if(token!=null)
           if(!token.isEmpty())
               loginuser(token);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignUp.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
            }
        });

    }

    private void loginuser(String token) {
        compositeDisposable.add(service.userData(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse, this::handleError)
        );



    }

    private void handleError(Throwable error) {
        if(error instanceof HttpException){
            Gson gson = new GsonBuilder().create();
            try {
                String errorBody = ((HttpException) error).response().errorBody().string();
                BadRequestException response = gson.fromJson(errorBody, BadRequestException.class);
                Toast.makeText(MainActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            Log.d("Error",error.getMessage());
        }
    }

    private void handleResponse(User user) {
        Intent intent=new Intent(MainActivity.this,AllCategories.class);
        startActivity(intent);
    }
}