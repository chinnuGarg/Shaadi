package com.app.shaadi.database;

import android.app.Application;
import android.content.Context;

import com.app.shaadi.global.Constants;
import com.app.shaadi.network.responsemodel.Result;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Controller class for Realm Database
 */
public class RealmController implements IDataBaseService {
    //Variable to store the reference of RealmController
    private static RealmController instance;
    //Variable to store the reference of Realm
    private final Realm realm;
    //Variable to store the reference of Application
    private static Application application;

    /**
     * Constructor for the RealmController
     *
     * @param application context for the application class
     */
    public RealmController(Application application) {
        realm = Realm.getDefaultInstance();
        this.application = application;
    }

    /**
     * Method to get the instance of the type RealmController
     *
     * @param application context for the application class
     * @return nstance of the type RealmController
     */
    public static RealmController getInstance(Context application) {
        if (null == instance) {
            synchronized (RealmController.class) {
                instance = new RealmController((Application) application);
            }
        }
        return instance;
    }

    /**
     * Method to return the instance
     *
     * @return the instance
     */
    public Realm getRealmInstance() {
        return Realm.getDefaultInstance();
    }

    /**
     * Method to insert the random user to db
     *
     * @param results data to be saved
     */
    @Override
    public void insertRandomUserDB(List<Result> results) {
        Realm backgroundRealm = getRealmInstance();
        try {
            backgroundRealm.refresh();
        } catch (IllegalStateException e) {
        }
        backgroundRealm.executeTransaction(realm -> {
            for (int i = 0; i < results.size(); i++) {
                Result result = results.get(i);
                RandomUser randomUser = new RandomUser();
                randomUser.setId(getNextKey());
                randomUser.setAge(result.getDob().getAge().toString());
                randomUser.setName(result.getName().getTitle() + " " + result.getName().getFirst() + " " + result.getName().getLast());
                randomUser.setCity(result.getLocation().getCity());
                randomUser.setGender(result.getGender());
                randomUser.setProfileImage(result.getPicture().getLarge());
                randomUser.setLiked(Constants.notChanged);
                realm.insertOrUpdate(randomUser);
            }
        });
    }

    /**
     * Method to save the status like or unlike the profile
     *
     * @param status     0,1,2
     * @param randomUser User information
     */
    @Override
    public void updateProfileStatus(int status, RandomUser randomUser) {
        realm.executeTransaction(realm -> {
            randomUser.setLiked(status);
            realm.createObject(RandomUser.class);
            realm.insertOrUpdate(randomUser);
        });
    }

    /**
     * Method to get All  the random user from db
     *
     * @return the list of all the random users
     */
    @Override
    public List<RandomUser> getAllRandomUsers() {
        return getRealmInstance().where(RandomUser.class).findAll();
    }

    /**
     * Method used to get the incremented key
     *
     * @return the id value
     */
    public int getNextKey() {
        try {
            Number number = realm.where(RandomUser.class).max("id");
            if (number != null) {
                return number.intValue() + 1;
            } else {
                return 0;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return 0;
        }
    }
}
