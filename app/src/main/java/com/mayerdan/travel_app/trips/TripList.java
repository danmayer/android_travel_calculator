package com.mayerdan.travel_app.trips;

import android.support.v4.app.ListFragment;
import android.widget.Toast;

import com.mayerdan.travel_app.model.Trip;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by danmayer on 3/2/15.
 */
public class TripList implements Callback<List<Trip>> {
    private ListFragment mFragment;
    private tripListComplete mCompleteListener;

    public interface tripListComplete { public void onLoadComplete(); };

    public TripList(ListFragment frag) {
        mFragment = frag;
    }

    public TripList(ListFragment frag, tripListComplete completeListener) {
        mFragment = frag;
        mCompleteListener = completeListener;
    }

    @Override
    public void success(List<Trip> trips, Response response) {
        if (trips == null) {
            Toast.makeText(mFragment.getActivity(), "You have no trips!", Toast.LENGTH_SHORT).show();
            return;
        }

        TripAdapter mTripAdapter = new TripAdapter(mFragment, trips);
        mFragment.setListAdapter(mTripAdapter);

        if(mCompleteListener != null) {
            mCompleteListener.onLoadComplete();
        }
    }

    @Override
    public void failure(RetrofitError error) {
        Toast.makeText(mFragment.getActivity(), "You can't access Trips", Toast.LENGTH_SHORT).show();
    }
}
