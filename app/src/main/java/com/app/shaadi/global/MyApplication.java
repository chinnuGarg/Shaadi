package com.app.shaadi.global;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Application class for the app
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //Initializes the realm database
        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name(Realm.DEFAULT_REALM_NAME)
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }
}