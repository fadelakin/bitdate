package com.fisheradelakin.bitdate;

import android.support.annotation.NonNull;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

/**
 * Created by Fisher on 8/5/15.
 */
public class ActionDataSource {

    private static final String TABLE_NAME = "Action";
    private static final String COLUMN_BY_USER = "byUser";
    private static final String COLUMN_TO_USER = "toUser";
    private static final String COLUMN_TYPE = "type";

    private static final String TYPE_LIKED = "Liked";
    private static final String TYPE_MATCHED = "matched";
    private static final String TYPE_SKIPPED = "skipped";

    // liked a user and see if there is a match
    public static void saveUserLiked(final String userId) {

        ParseQuery<ParseObject> query = new ParseQuery<>(TABLE_NAME);
        query.whereEqualTo(COLUMN_TO_USER, ParseUser.getCurrentUser().getObjectId());
        query.whereEqualTo(COLUMN_BY_USER, userId);
        query.whereEqualTo(COLUMN_TYPE, TYPE_LIKED);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                ParseObject action = null;
                if(e == null && list.size() > 0) {
                    // we have a match and want to match users together
                    ParseObject otherAction = list.get(0);
                    otherAction.put(COLUMN_TYPE, TYPE_MATCHED);
                    otherAction.saveInBackground();

                    action = createAction(userId, TYPE_MATCHED);
                } else {
                    action = createAction(userId, TYPE_LIKED);
                }
                action.saveInBackground();
            }
        });
    }

    // swipe left action (basically saying no)
    public static void saveUserSkipped(String userId) {
        ParseObject action = createAction(userId, TYPE_SKIPPED);
        action.saveInBackground();
    }

    @NonNull
    private static ParseObject createAction(String userId, String type) {
        ParseObject action = new ParseObject(TABLE_NAME);
        action.put(COLUMN_BY_USER, ParseUser.getCurrentUser().getObjectId());
        action.put(COLUMN_TO_USER, userId);
        action.put(COLUMN_TYPE, type);
        return action;
    }
}
