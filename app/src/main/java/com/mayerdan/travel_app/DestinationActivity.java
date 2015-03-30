package com.mayerdan.travel_app;

import android.os.Bundle;
import android.widget.ListView;

import com.mayerdan.travel_app.tasks.DestinationTask;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class DestinationActivity extends BaseActivity {

    @InjectView(R.id.myListView) ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination);
        ButterKnife.inject(this);

        new DestinationTask(this, savedInstanceState,mListView).execute();
    }

}
