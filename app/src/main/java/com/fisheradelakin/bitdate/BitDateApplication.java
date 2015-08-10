package com.fisheradelakin.bitdate;

import android.app.Application;

import com.firebase.client.Firebase;
import com.parse.Parse;
import com.parse.ParseFacebookUtils;

/**
 * Created by Fisher on 7/21/15.
 */
public class BitDateApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // parse stuff
        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "GxjbG5WjErNyikhLsG4Dmb276txlYmCsJg1onPgi", "keo5ziWIgz5txlDXqi4Xxx3DiVxcN1afaJzWNoa9");
        ParseFacebookUtils.initialize(this);

        // firebase stuff
        Firebase.setAndroidContext(this);
    }
}
