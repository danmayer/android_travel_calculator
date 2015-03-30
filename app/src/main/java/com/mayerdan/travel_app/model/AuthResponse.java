package com.mayerdan.travel_app.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by danmayer on 3/1/15.
 */
public class AuthResponse {
    public String error;
    public Boolean success;

    @SerializedName("auth_token")
    public String authToken;

    @SerializedName("data")
    public User userData;
}
