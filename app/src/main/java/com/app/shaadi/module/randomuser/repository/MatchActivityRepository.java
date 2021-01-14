package com.app.shaadi.module.randomuser.repository;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.app.shaadi.database.RandomUser;
import com.app.shaadi.database.RealmController;
import com.app.shaadi.network.api.RetrofitClient;
import com.app.shaadi.network.responsemodel.RandomUserResponse;
import com.app.shaadi.network.responsemodel.Result;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Repository class for the MatchActivity to do the database operation and calling api
 */
public class MatchActivityRepository {
    //Variable to hold the reference of eventModelLiveData
    public MutableLiveData<Boolean> eventModelLiveData = new MutableLiveData<>();
    //Variable to hold the reference for realmController
    private RealmController realmController;
    //Variable to hold the reference  for application context
    private Application application;

    /**
     * Constructor for the MatchActivityRepository class
     *
     * @param application contex for the application
     */
    public MatchActivityRepository(Application application) {
        realmController = RealmController.getInstance(application);
        this.application = application;
    }

    /**
     * Method to call the api to get the Random user
     */
    public void getRandomUser() {
        //our API Interface
        Call<RandomUserResponse> call = RetrofitClient.getInstance().getApiInterface().getRandomUserApi();

        //to perform the API call we need to call the method enqueue()
        //We need to pass a Callback with enqueue method
        //And Inside the callback functions we will handle success or failure of
        //the result that we got after the API call
        call.enqueue(new Callback<RandomUserResponse>() {
            @Override
            public void onResponse(Call<RandomUserResponse> call, Response<RandomUserResponse> response) {
                //Checking response not null
                if (response.body() != null) {
                    //When the response is success
                    List<Result> result = response.body().getResults();
                    //Saving the data into database
                    realmController.insertRandomUserDB(result);
                    //Setting the value for the mutable observer
                    eventModelLiveData.setValue(true);
                } else {
                    //Check when the response is failure
                    eventModelLiveData.setValue(false);
                }
            }

            @Override
            public void onFailure(Call<RandomUserResponse> call, Throwable t) {
                //When api response fail
                eventModelLiveData.setValue(false);
            }
        });
    }

    /**
     * Method to update the status into db
     *
     * @param status    0-like,1-dislike,2-not changed
     * @param randomUser user data
     */
    public void updateProfileStatus(int status, RandomUser randomUser) {
        RealmController.getInstance(application).updateProfileStatus(status, randomUser);
    }
}