package com.mayerdan.travel_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.mayerdan.travel_app.trips.TripActivity;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class FirstActivity extends BaseActivity {

    @InjectView(R.id.logout) Button logoutButton;
    @InjectView(R.id.spashView) ImageView spashview;
    private SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        ButterKnife.inject(this);

        mPreferences = getSharedPreferences("CurrentUser", MODE_PRIVATE);
        Picasso.with(spashview.getContext()).load("http://i.imgur.com/DvpvklR.png").into(spashview);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!mPreferences.contains("AuthToken")) {
            logoutButton.setText(getString(R.string.login));
        }
    }

    @OnClick(R.id.buttonDestinations)
    public void startDestinations() {
        Intent i = new Intent(this, DestinationActivity.class);
        startActivity(i);
    }

    @OnClick(R.id.buttonTrips)
    public void startTrips() {
        Intent i = new Intent(this, TripActivity.class);
        startActivity(i);
    }

    @OnClick(R.id.logout)
    public void loginOutOptions() {
        loginOrOut();
    }

    private void loginOrOut() {
        if (mPreferences.contains("AuthToken")) {
            ((ASPApp) this.getApplication()).setToken(null);
            Intent i = new Intent(this, FirstActivity.class);
            FirstActivity.this.startActivity(i);
        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivityForResult(intent, 0);
        }
    }

}
