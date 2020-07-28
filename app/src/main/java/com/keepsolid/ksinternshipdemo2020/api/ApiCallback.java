package com.keepsolid.ksinternshipdemo2020.api;


import com.keepsolid.ksinternshipdemo2020.model.GitRepoErrorItem;

import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;

public abstract class ApiCallback<T> implements Callback<T> {

    public abstract void success(Response<T> response);

    public abstract void failure(GitRepoErrorItem gitRepoError);

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (!response.isSuccessful()) {
            Converter<ResponseBody, GitRepoErrorItem> converter = RestClient.getInstance().getRetrofit().responseBodyConverter(GitRepoErrorItem.class, new Annotation[0]);

            try {
                GitRepoErrorItem repoError = converter.convert(response.errorBody());
                failure(repoError);
            } catch (Exception e) {
                failure(new GitRepoErrorItem("Unhandled error! Code: " + response.code()));
            }
        } else {
            success(response);
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        failure(new GitRepoErrorItem("Unexpected error! Info: " + t.getMessage()));
    }
}
