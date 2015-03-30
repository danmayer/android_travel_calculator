package com.mayerdan.travel_app.tasks;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.mayerdan.travel_app.trips.TripActivity;
import com.mayerdan.travel_app.model.Trip;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by danmayer on 3/2/15.
 */
public class CreateTripTask implements Callback<Trip> {

    private Context mContext;

    public CreateTripTask(Context context) {
        mContext = context;
    }

    @Override
    public void success(Trip trip, Response response) {
        Intent i = new Intent(mContext, TripActivity.class);
        mContext.startActivity(i);
    }

    @Override
    public void failure(RetrofitError error) {
      Toast.makeText(mContext, error.getMessage(), Toast.LENGTH_LONG).show();
    }
}
