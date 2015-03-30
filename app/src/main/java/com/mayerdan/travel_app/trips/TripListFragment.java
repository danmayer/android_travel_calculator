package com.mayerdan.travel_app.trips;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.mayerdan.travel_app.ASPApp;
import com.mayerdan.travel_app.CreateTripActivity;
import com.mayerdan.travel_app.R;
import com.mayerdan.travel_app.model.Trip;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by danmayer on 3/4/15.
 */
public class TripListFragment extends ListFragment implements SwipeRefreshLayout.OnRefreshListener,
                                                              TripList.tripListComplete {
    private static final String TAG = "TripListFragment";

//    // TODO: Rename and change types and number of parameters
//    public static PhotoGalleryFragment newInstance(String param1, String param2) {
//        PhotoGalleryFragment fragment = new PhotoGalleryFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @InjectView(android.R.id.list) ListView mListView;
    @InjectView(R.id.swipe_container) SwipeRefreshLayout swipeLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        ASPApp.service.listTrips(authToken(), new TripList(this));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_trips, container, false);
        ButterKnife.inject(this, view);

        swipeLayout.setOnRefreshListener(this);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case 33:
                Log.i(TAG, "how did you get here");
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Trip trip = ((TripAdapter) getListAdapter()).getItem(position);
        Intent i = new Intent(getActivity(), TripDetailActivity.class);
        i.putExtra("TRIP_ID", trip.id);
        startActivity(i);
    }

    @OnClick(R.id.buttonCreateTrip)
    public void createTrip() {
        Intent i = new Intent(getActivity(), CreateTripActivity.class);
        startActivity(i);
    }

    @Override
    public void onRefresh() {
        Log.i(TAG, "onRefresh");
        ASPApp.service.listTrips(authToken(), new TripList(this, this));
    }

    @Override
    public void onLoadComplete() {
        Log.i(TAG, "onLoadComplete");
        swipeLayout.setRefreshing(false);
    }

    public void tripDeleteClick(View v) {
        Log.i(TAG, "fragment delete trip click");
        int position = (Integer) v.getTag();
        Trip trip = ((TripAdapter) getListAdapter()).getItem(position);
        ASPApp.service.deleteTrip(trip.id, authToken(), new Callback<Void>() {
            @Override
            public void success(Void aVoid, Response response) {
                onRefresh();
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String authToken() {
        return ((ASPApp) this.getActivity().getApplication()).getToken();
    }
}
