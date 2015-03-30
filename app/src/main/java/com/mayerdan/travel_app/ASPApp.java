package com.mayerdan.travel_app;

import android.app.Application;
import android.content.SharedPreferences;

import com.mayerdan.travel_app.services.ApiService;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

/**
 * Created by danmayer on 2/25/15.
 */
public class ASPApp extends Application{

    public static ApiService service;
    private static String API_ENDPOINT = "http://travel-calculator.herokuapp.com";
    private static String USER_AGENT   = "android-travel-app";

    @Override
    public void onCreate() {
        super.onCreate();
        RestAdapter.LogLevel level = RestAdapter.LogLevel.BASIC;

        /* if(BuildConfig.DEBUG) {
            level = RestAdapter.LogLevel.FULL;
        }*/

        RestAdapter restAdapter = new RestAdapter.Builder()
            .setEndpoint(API_ENDPOINT)
            .setLogLevel(level)
            .setRequestInterceptor(new RequestInterceptor() {
                @Override
                public void intercept(RequestFacade request) {
                    request.addHeader("User-Agent", USER_AGENT);
                }
            })
            .build();
        service = restAdapter.create(ApiService.class);
    }

    public String getToken() {
        SharedPreferences mPreferences = getSharedPreferences("CurrentUser", MODE_PRIVATE);
        String token = mPreferences.getString("AuthToken", null);
        return token;
    }

    public void setToken(String token) {
        SharedPreferences mPreferences = getSharedPreferences("CurrentUser", MODE_PRIVATE);
        SharedPreferences.Editor editor = mPreferences.edit();
        if(token==null) {
            editor.remove("AuthToken");
        } else {
            editor.putString("AuthToken", token);
        }
        editor.commit();
    }
}
