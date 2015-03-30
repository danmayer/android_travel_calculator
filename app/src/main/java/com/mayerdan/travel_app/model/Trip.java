package com.mayerdan.travel_app.model;

import java.util.List;

/**
 * Created by danmayer on 3/2/15.
 */
public class Trip {
    public int id;
    public String title;
    public List<Cost> costs;

    public Trip(String title) {
        this.title = title;
    }
}