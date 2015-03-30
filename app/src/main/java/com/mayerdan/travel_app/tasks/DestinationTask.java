package com.mayerdan.travel_app.tasks;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.mayerdan.travel_app.ASPApp;
import com.mayerdan.travel_app.model.Destination;

import java.util.List;

/**
 * Created by danmayer on 3/1/15.
 */
public class DestinationTask extends AsyncTask<String, Integer, List<Destination>> {

    private Context mContext;
    private SharedPreferences mPreferences;
    String[] myStringArray;

    private ListView mListView;
    private ArrayAdapter mArrayAdapter;
    private Bundle mSavedInstanceState;
    private static final String LIST_STATE = "destinations_list";

    public DestinationTask(Context context, Bundle savedInstanceState, ListView listView) {
        mContext = context;
        mSavedInstanceState = savedInstanceState;
        mListView = listView;
    }

    @Override
    protected List<Destination> doInBackground(String... params) {
        try {
            mPreferences = mContext.getSharedPreferences("CurrentUser", mContext.MODE_PRIVATE);
            String token = mPreferences.getString("AuthToken", null);
            List<Destination> json;

            if(mSavedInstanceState!=null && mSavedInstanceState.containsKey(LIST_STATE)) {
                json = (List<Destination>) mSavedInstanceState.getSerializable(LIST_STATE);
            } else {
                json = ASPApp.service.listDestinations(token);
            }

            return json;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(List<Destination> json) {
        super.onPostExecute(json);

        List<Destination> destinations = json;
        if (destinations == null) {
            Toast.makeText(mContext, "No destinations", Toast.LENGTH_SHORT).show();
            return;
        }

        myStringArray = new String[destinations.size()];

        for (int i = 0; i < destinations.size(); i++) {
            Destination destination = destinations.get(i);
            myStringArray[i] = destination.title + " costs: " + destination.defaultOption.costs.size();
        }

        mArrayAdapter = new ArrayAdapter(mContext, android.R.layout.simple_list_item_1, myStringArray);
        mListView.setAdapter(mArrayAdapter);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }
}