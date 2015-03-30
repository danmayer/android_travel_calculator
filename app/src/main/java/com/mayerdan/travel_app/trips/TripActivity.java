package com.mayerdan.travel_app.trips;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.mayerdan.travel_app.SingleFragmentActivity;
import com.mayerdan.travel_app.R;

public class TripActivity extends SingleFragmentActivity {
    public static final String TAG = "TripActivity";
    private Fragment mFragment;

    @Override
    protected Fragment createFragment() {
        mFragment = new TripListFragment();
        return mFragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_fragment);
    }

    public void tripDeleteClick(View v) {
        ((TripListFragment) mFragment).tripDeleteClick(v);
    }

}
