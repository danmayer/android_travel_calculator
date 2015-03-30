package com.mayerdan.travel_app.trips;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mayerdan.travel_app.ASPApp;
import com.mayerdan.travel_app.BaseActivity;
import com.mayerdan.travel_app.R;
import com.mayerdan.travel_app.costs.CostAdapter;
import com.mayerdan.travel_app.model.Cost;

import java.text.NumberFormat;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class TripDetailActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    public static final String TAG = "TripDetailActivity";
    public int mTripId = -1;
    public List<Cost> mCosts;
    public float estimated_cost = 0.0f;
    CostAdapter mCostAdapter;

    @InjectView(R.id.cost_list) ListView costList;
    @InjectView(R.id.estimated_total) TextView estimatedTotal;
    @InjectView(R.id.swipe_container) SwipeRefreshLayout swipeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_detail);
        ButterKnife.inject(this);
        swipeLayout.setOnRefreshListener(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mTripId = extras.getInt("TRIP_ID");
        }

        if (mTripId != -1) {
            ASPApp.service.tripCosts(mTripId, ((ASPApp) this.getApplication()).getToken(), new CostCallback());
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_trip_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        Log.i(TAG, "onRefresh");
        ASPApp.service.tripCosts(mTripId, ((ASPApp) this.getApplication()).getToken(), new CostCallback());
    }

    public void tripCostDeleteClick(View v) {
        Log.i(TAG, "fragment delete trip click");
        int position = (Integer) v.getTag();
        Cost cost = ((CostAdapter) mCostAdapter).getItem(position);
        ASPApp.service.deleteCost(mTripId, cost.id, authToken(), new Callback<Void>() {
            @Override
            public void success(Void aVoid, Response response) {
                onRefresh();
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(TripDetailActivity.this, error.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            }
        });
    }

    private class CostCallback implements Callback<List<Cost>> {

        @Override
        public void success(List<Cost> costs, Response response) {
            mCosts = costs;
            estimated_cost = 0.0f;
            for (Cost cost : costs) {
                estimated_cost += cost.estimate;
            }
            estimatedTotal.setText(NumberFormat.getCurrencyInstance().format(
                estimated_cost));
            mCostAdapter = new CostAdapter(TripDetailActivity.this, mCosts);
            costList.setAdapter(mCostAdapter);
            TripDetailActivity.this.swipeLayout.setRefreshing(false);
        }

        @Override
        public void failure(RetrofitError error) {

        }
    }
}
