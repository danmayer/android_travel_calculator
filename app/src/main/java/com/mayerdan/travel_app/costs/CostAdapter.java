package com.mayerdan.travel_app.costs;

/**
 * Created by danmayer on 3/6/15.
 */

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.mayerdan.travel_app.R;
import com.mayerdan.travel_app.model.Cost;

import java.text.NumberFormat;
import java.util.List;

/**
 * Created by danmayer on 3/5/15.
 */
public class CostAdapter extends ArrayAdapter<Cost> {
    private Context mContext;
    private List<Cost> mCosts;

    public CostAdapter(Context context, List<Cost> costs) {
        super(context, 0, costs);
        mContext = context;
        mCosts = costs;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) mContext).getLayoutInflater().inflate(R.layout.list_item_tripcost, parent, false);
        }
        Cost cost = getItem(position);

        TextView titleTextView = (TextView) convertView.findViewById(R.id.cost_title);
        titleTextView.setText(cost.title);

        TextView estimateTextView = (TextView) convertView.findViewById(R.id.cost_estimate);
        estimateTextView.setText(NumberFormat.getCurrencyInstance().format(cost.estimate));

        Button tripButton = (Button) convertView.findViewById(R.id.trip_cost_delete_button);
        tripButton.setTag(position);

        return convertView;
    }

}