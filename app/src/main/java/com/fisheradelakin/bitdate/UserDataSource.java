package com.fisheradelakin.bitdate;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fisher on 8/3/15.
 */
public class UserDataSource {

    private static User sCurrentUser;

    private static final String COLUMN_FIRST_NAME = "firstName";
    private static final String COLUMN_PICTURE_URL = "pictureURL";
    private static final String COLUMN_FACEBOOK_ID = "facebookId";

    public static User getCurrentUser() {
        if(sCurrentUser == null && ParseUser.getCurrentUser() != null) {
            sCurrentUser = parseUserToUser(ParseUser.getCurrentUser());
        }

        return sCurrentUser;
    }

    public static void getUnseenUsers(final UserDataCallbacks callbacks) {
        ParseQuery<ParseObject> seenUsersQuery = new ParseQuery<ParseObject>(ActionDataSource.TABLE_NAME);
        seenUsersQuery.whereEqualTo(ActionDataSource.COLUMN_BY_USER, ParseUser.getCurrentUser().getObjectId());
        seenUsersQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if(e == null) {
                    List<String> ids = new ArrayList<String>();
                    for(ParseObject parseObject : list) {
                        ids.add(parseObject.getString(ActionDataSource.COLUMN_TO_USER));
                    }
                    ParseQuery<ParseUser> query = ParseUser.getQuery();
                    query.whereNotEqualTo("objectId", getCurrentUser().getId());
                    query.whereNotContainedIn("objectId", ids);
                    query.findInBackground(new FindCallback<ParseUser>() {
                        @Override
                        public void done(List<ParseUser> parseUsers, ParseException e) {
                            if(e == null) {
                                List<User> users = new ArrayList<>();
                                for(ParseUser parseUser : parseUsers) {
                                    User user = parseUserToUser(parseUser);
                                    users.add(user);
                                }
                                if(callbacks != null) {
                                    callbacks.onUsersFetched(users);
                                }
                            }
                        }
                    });
                }
            }
        });
    }

    private static User parseUserToUser(ParseUser parseUser) {
        User user = new User();
        user.setFirstName(parseUser.getString(COLUMN_FIRST_NAME));
        user.setPictureURL(parseUser.getString(COLUMN_PICTURE_URL));
        user.setId(parseUser.getObjectId());
        user.setFacebookId(parseUser.getString(COLUMN_FACEBOOK_ID));
        return user;
    }

    public interface UserDataCallbacks {
        void onUsersFetched(List<User> users);
    }
}
