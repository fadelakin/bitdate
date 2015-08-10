package com.fisheradelakin.bitdate;

import java.io.Serializable;

/**
 * Created by Fisher on 8/3/15.
 */
public class User implements Serializable {

    private String mFirstName;
    private String mPictureURL;
    private String mId;
    private String mFacebookId;

    public String getLargePictureURL() {
        return "https://graph.facebook.com/v2.3/" + mFacebookId + "/picture?type=large";
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    public String getPictureURL() {
        return mPictureURL;
    }

    public void setPictureURL(String pictureURL) {
        mPictureURL = pictureURL;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getFacebookId() {
        return mFacebookId;
    }

    public void setFacebookId(String facebookId) {
        mFacebookId = facebookId;
    }
}
