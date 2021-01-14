package com.app.shaadi.network.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Class to handle the retrofit operations
 */
public class RetrofitClient {
    //Variable to hold the reference for instance
    private static RetrofitClient instance = null;
    //Variable to hold the reference for ApiInterface
    private ApiInterface apiInterface;

    /**
     * Method to initialize the retrofit instance
     */
    private RetrofitClient() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiInterface = retrofit.create(ApiInterface.class);
    }

    /**
     * Method to return the instance of the RetrofitClient
     *
     * @return the RetrofitClient instance
     */
    public static synchronized RetrofitClient getInstance() {
        if (instance == null) {
            instance = new RetrofitClient();
        }
        return instance;
    }

    /**
     * Method to return the ApiInterface
     *
     * @return the ApiInterface instance
     */
    public ApiInterface getApiInterface() {
        return apiInterface;
    }
}
