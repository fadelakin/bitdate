package com.fisheradelakin.bitdate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SignInActivity extends AppCompatActivity {

    private static final String TAG = "SignInActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        Button loginButton = (Button) findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> permissions = new ArrayList<String>();
                permissions.add("user_birthday");
                ParseFacebookUtils.logInWithReadPermissionsInBackground(SignInActivity.this, permissions, new LogInCallback() {
                    @Override
                    public void done(ParseUser parseUser, ParseException e) {
                        if (parseUser == null) {
                            Log.i(TAG, "User is null. Error creating user.");
                        } else if (parseUser.isNew()) {
                            Log.i(TAG, "User is new. Created new user.");
                            getFacebookInfo();
                        } else {
                            Log.i(TAG, "Logged in existing user.");
                            finish();
                        }
                    }
                });
            }
        });
    }

    private void getFacebookInfo() {
        Bundle parameters = new Bundle();
        parameters.putString("fields", "picture, first_name");
        new GraphRequest(AccessToken.getCurrentAccessToken(), "/me", parameters, HttpMethod.GET, new GraphRequest.Callback() {
            @Override
            public void onCompleted(GraphResponse graphResponse) {
                JSONObject user = graphResponse.getJSONObject();
                ParseUser currentUser = ParseUser.getCurrentUser();
                currentUser.put("firstName", user.optString("first_name"));
                currentUser.put("pictureURL", user.optJSONObject("picture").optJSONObject("data").optString("url"));
                currentUser.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e == null) {
                            Log.i(TAG, "User saved");
                            finish();
                        }
                    }
                });
            }
        }).executeAsync();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_in, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
