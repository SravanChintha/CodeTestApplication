package com.sravan.codetestapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

interface RequestInterface {

    @GET("search_by_date")
    Call<Example> getJson(@Query("tags") String story, @Query("page") int page);
}
