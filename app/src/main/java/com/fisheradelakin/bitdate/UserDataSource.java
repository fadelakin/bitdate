package com.fisheradelakin.bitdate;

import com.parse.ParseUser;

/**
 * Created by Fisher on 8/3/15.
 */
public class UserDataSource {

    private static User sCurrentUser;

    private static final String COLUMN_FIRST_NAME = "firstname";
    private static final String COLUMN_PICTURE_URL = "pictureURL";

    public static User getCurrentUser() {
        if(sCurrentUser == null && ParseUser.getCurrentUser() != null) {
            ParseUser user = ParseUser.getCurrentUser();
            sCurrentUser = new User();
            sCurrentUser.setFirstName(user.getString(COLUMN_FIRST_NAME));
            sCurrentUser.setPictureURL(user.getString(COLUMN_PICTURE_URL));
        }

        return sCurrentUser;
    }
}
