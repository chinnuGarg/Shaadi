package com.app.shaadi.network.api;

import com.app.shaadi.network.responsemodel.RandomUserResponse;
import com.app.shaadi.network.responsemodel.Result;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Interface for the Api
 */
public interface ApiInterface {
    //Base Url for the api call
    String BASE_URL = "https://randomuser.me/";

    @GET("api/?results=10")
    Call<RandomUserResponse> getRandomUserApi();
}
