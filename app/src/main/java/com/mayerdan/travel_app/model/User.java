package com.mayerdan.travel_app.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by danmayer on 3/1/15.
 */
public class User {
    public String email;
    public String name;
    public String password;

    @SerializedName("password_confirmation")
    public String passwordConfirmation;

    @SerializedName("auth_token")
    public String authToken;

    public User(String mail, String pass) {
        email    = mail;
        password = pass;
    }

    public User(String mail, String c_name, String pass, String passConf) {
        email                 = mail;
        name                  = c_name;
        password              = pass;
        passwordConfirmation = passConf;
    }
}
