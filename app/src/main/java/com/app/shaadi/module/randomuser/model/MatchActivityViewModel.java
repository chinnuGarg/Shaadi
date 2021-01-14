package com.app.shaadi.module.randomuser.model;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.app.shaadi.database.RandomUser;
import com.app.shaadi.module.randomuser.repository.MatchActivityRepository;

/**
 * View Model class for the MatchActivity to do the database operation and calling api
 */
public class MatchActivityViewModel extends AndroidViewModel {
    //Variable to store the reference for MatchActivityRepository
    private MatchActivityRepository mainActivityRepository;

    /**
     * Constructor for the MatchActivityViewModel class
     *
     * @param application Application context
     */
    public MatchActivityViewModel(Application application) {
        super(application);
        //Initialize the mainActivityRepository
        mainActivityRepository = new MatchActivityRepository(application);
    }

    /**
     * Method to get the Random user
     */
    public void getRandomUser() {
        //Calling the api to get the list of users profile
        mainActivityRepository.getRandomUser();
    }

    /**
     * Method to get the Observable for the get Random user api
     *
     * @return the mutable live data
     */
    public MutableLiveData<Boolean> getEventModelLiveData() {
        return mainActivityRepository.eventModelLiveData;
    }

    /**
     * Method to update the status into db
     *
     * @param status     0-like,1-dislike,2-not changed
     * @param randomUser user data
     */
    public void updateProfileStatus(int status, RandomUser randomUser) {
        mainActivityRepository.updateProfileStatus(status, randomUser);
    }
}