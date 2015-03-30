package com.mayerdan.travel_app.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by danmayer on 2/25/15.
 */
public class Destination {
    public int id;
    public String title;

    @SerializedName("default_options")
    public DefaultOption defaultOption;
}
