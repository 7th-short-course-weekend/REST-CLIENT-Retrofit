package com.rathana.asynctaskdemo.data;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    public static final String BASE_URL="http://api-ams.me";

    static Retrofit.Builder builder
            =new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    public static <S> S createService(Class<S> service){
        return builder.build().create(service);
    }
}
