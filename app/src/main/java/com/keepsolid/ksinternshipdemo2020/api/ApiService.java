package com.keepsolid.ksinternshipdemo2020.api;


import com.keepsolid.ksinternshipdemo2020.model.GitResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("/search/repositories")
    Call<GitResponse> getUserRepos(@Query("q") String query);


}
