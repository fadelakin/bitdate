package com.fisheradelakin.bitdate;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MatchesFragment extends Fragment implements ActionDataSource.ActionDataCallbacks, UserDataSource.UserDataCallbacks {

    private static final String TAG = "MatchesFragment";

    public MatchesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ActionDataSource.getMatches(this);
        return inflater.inflate(R.layout.fragment_matches, container, false);
    }


    @Override
    public void onFetchedMatches(List<String> matchIds) {
        UserDataSource.getUsersIn(matchIds, this);
    }

    @Override
    public void onUsersFetched(List<User> users) {
        for(User user : users) {
            Log.d(TAG, "User is " + user.getFirstName());
        }
    }
}
