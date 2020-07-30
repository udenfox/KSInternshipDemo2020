package com.keepsolid.ksinternshipdemo2020.api;


import android.net.Uri;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {

    private static RestClient sInstance = new RestClient();

    private ApiService service;
    private Retrofit retrofit;
    private Gson gson;

    private final static String API_URL = "https://api.github.com";

    private RestClient() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        gson = new GsonBuilder()
                .registerTypeAdapter(Uri.class, new UriDeserializer())
                .registerTypeAdapter(Uri.class, new UriSerializer())
                .create();

        retrofit = new Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(API_URL)
                .build();

        service = retrofit.create(ApiService.class);

    }

    public static RestClient getInstance() {
        return sInstance;
    }

    public ApiService getService() {
        return service;
    }

    public Retrofit getRetrofit() {
        return this.retrofit;
    }

    public Gson getGson() {
        return gson;
    }

}
