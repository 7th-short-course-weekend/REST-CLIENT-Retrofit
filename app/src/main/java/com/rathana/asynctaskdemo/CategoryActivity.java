package com.rathana.asynctaskdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.rathana.asynctaskdemo.data.ServiceGenerator;
import com.rathana.asynctaskdemo.data.service.CategoryService;
import com.rathana.asynctaskdemo.model.CategoryResponse;

import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryActivity extends AppCompatActivity {

    CategoryService categoryService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        categoryService= ServiceGenerator.createService(CategoryService.class);
        //execute or request
        Call<CategoryResponse> call= categoryService.getCategories();
        //synchronous > do on UI thread
//        try {
//         Response<JSONObject>  response=call.execute();
//            System.out.println("data >>> "+response.body());
//         //get data response
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        //asynchronous
        call.enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                Log.e(TAG, "onResponse: data > >>"+ response.body().toString());
            }
            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: error >>>>"+ t.toString());
            }
        });
    }

    private static final String TAG = "CategoryActivity";
}
