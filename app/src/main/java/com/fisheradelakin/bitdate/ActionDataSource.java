package com.fisheradelakin.bitdate;

import com.parse.ParseObject;
import com.parse.ParseUser;

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

    public static void saveUserSkipped(String userId) {
        ParseObject action = new ParseObject(TABLE_NAME);
        action.put(COLUMN_BY_USER, ParseUser.getCurrentUser().getObjectId());
        action.put(COLUMN_TO_USER, userId);
        action.put(COLUMN_TYPE, TYPE_SKIPPED);
        action.saveInBackground();
    }
}
