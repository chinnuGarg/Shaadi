package com.app.shaadi.module.randomuser.view;


import android.os.Bundle;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.app.shaadi.R;
import com.app.shaadi.database.RandomUser;
import com.app.shaadi.database.RealmController;
import com.app.shaadi.module.randomuser.model.MatchActivityViewModel;
import com.app.shaadi.module.randomuser.adapter.MatchListAdapter;
import com.app.shaadi.module.randomuser.adapter.RecyclerViewWithNavigationArrows;
import com.app.shaadi.network.responsemodel.Result;

import java.util.ArrayList;
import java.util.List;


public class MatchActivity extends AppCompatActivity implements MatchListAdapter.OnProfileLikeDislike {

    private List<Result> appsList = new ArrayList<>();
    MatchActivityViewModel mainActivityViewModel;
    private RealmController realmController;
    private RecyclerViewWithNavigationArrows recyclerViewWithNavigationArrows;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivityViewModel = ViewModelProviders.of(this).get(MatchActivityViewModel.class);
        setContentView(R.layout.activity_match);
        //Calling the init method
        init();
    }

    /**
     * Method to initialize the variables
     */
    private void init() {
        realmController = RealmController.getInstance(this);
        recyclerViewWithNavigationArrows = findViewById(R.id.recycler_view_one);
        //Showing the progress bar
        recyclerViewWithNavigationArrows.showProgressBar();
        //Checking if the database having the data not to hit the api
        if (RealmController.getInstance(this).getAllRandomUsers().isEmpty()) {
            //Calling the api to get data
            getRandomUserApi();
        } else {
            //Setting the adapter
            setAdapter();
        }
    }

    /**
     * Method to call the random user api
     */
    public void getRandomUserApi() {
        //Calling the Observer
        setObserver();
        //Calling the api to get the user list
        mainActivityViewModel.getRandomUser();
    }

    /**
     * Observer class to observe the data
     */
    public void setObserver() {
        mainActivityViewModel.getEventModelLiveData().observe(this, baseUIEventModel -> {
            //Hide the progress bar
            recyclerViewWithNavigationArrows.hideProgressBar();
            if (baseUIEventModel) {
                //on Success set the adapter
                setAdapter();
            } else {
                //on Failure show the message
                Toast.makeText(this, R.string.error_message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Function used to set the adapter
     */
    public void setAdapter() {
        recyclerViewWithNavigationArrows.hideProgressBar();
        MatchListAdapter appListAdapter = new MatchListAdapter(this, realmController.getAllRandomUsers(), this);
        recyclerViewWithNavigationArrows.setAdapter(appListAdapter);
    }

    /**
     * Method to update the status into db
     *
     * @param status     0-like,1-dislike,2-not changed
     * @param randomUser user data
     */
    @Override
    public void onStatusChanged(int status, RandomUser randomUser) {
        mainActivityViewModel.updateProfileStatus(status, randomUser);
    }
}