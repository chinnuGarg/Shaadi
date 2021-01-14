package com.app.shaadi.database;

import com.app.shaadi.network.responsemodel.Result;

import java.util.List;

/**
 * Interface for the data class
 */
public interface IDataBaseService {

    /**
     * Method to insert the random user to db
     *
     * @param results data to be saved
     */
    public void insertRandomUserDB(List<Result> results);

    /**
     * Method to get All  the random user from db
     *
     * @return the list of all the random users
     */
    public List<RandomUser> getAllRandomUsers();

    /**
     * Method to save the status like or unlike the profile
     *
     * @param status    0-like,1-dislike,2-not changed
     * @param randomUser User information
     */
    public void updateProfileStatus(int status, RandomUser randomUser);
}
