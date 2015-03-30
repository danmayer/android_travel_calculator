package com.mayerdan.travel_app;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.mayerdan.travel_app.model.Trip;
import com.mayerdan.travel_app.tasks.CreateTripTask;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class CreateTripActivity extends BaseActivity {

    @InjectView(R.id.tripTitle) EditText tripTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_trip);
        ButterKnife.inject(this);
    }

    @OnClick(R.id.tripCreateButton)
    public void createTrip() {
        Log.i("create_trip", "create is hit");
        if (TextUtils.isEmpty(tripTitle.getText())) {
            Toast.makeText(this, "Please complete all the fields", Toast.LENGTH_SHORT).show();
        } else {
            String mTripTitle = tripTitle.getText().toString();
            Log.i("create_trip", "creating a trip with title " + mTripTitle);
            Trip trip = new Trip(mTripTitle);
            ASPApp.service.createTrip(trip, ((ASPApp) this.getApplication()).getToken(), new CreateTripTask(this));
        }
    }
}
