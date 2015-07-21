package com.fisheradelakin.bitdate;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by Fisher on 7/21/15.
 */
public class BitDateApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "GxjbG5WjErNyikhLsG4Dmb276txlYmCsJg1onPgi", "keo5ziWIgz5txlDXqi4Xxx3DiVxcN1afaJzWNoa9");

    }
}
