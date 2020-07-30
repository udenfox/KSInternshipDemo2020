package com.keepsolid.ksinternshipdemo2020.api;


import com.keepsolid.ksinternshipdemo2020.model.GitRepoItem;
import com.keepsolid.ksinternshipdemo2020.model.GitResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("/search/repositories")
    Call<GitResponse> searchRepos(@Query("q") String query);

    @GET("/users/{username}/repos")
    Call<List<GitRepoItem>> getReposByUserName(@Path("username") String username);
}
