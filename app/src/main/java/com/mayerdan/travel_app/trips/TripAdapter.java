package com.mayerdan.travel_app.trips;

import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.mayerdan.travel_app.R;
import com.mayerdan.travel_app.model.Trip;

import java.util.List;

/**
 * Created by danmayer on 3/5/15.
 */
public class TripAdapter extends ArrayAdapter<Trip> {
    private Fragment mFragment;

    public TripAdapter(ListFragment frag, List<Trip> trips) {
        super(frag.getActivity(), 0, trips);
        mFragment = frag;
    }

    //todo move to butterknife
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mFragment.getActivity().getLayoutInflater().inflate(R.layout.list_item_trip, parent, false);
        }
        Trip trip = getItem(position);

        TextView titleTextView = (TextView) convertView.findViewById(R.id.trip_title);
        titleTextView.setText(trip.title);

        Button tripButton = (Button) convertView.findViewById(R.id.trip_delete_button);
        tripButton.setTag(position);

        return convertView;
    }

}