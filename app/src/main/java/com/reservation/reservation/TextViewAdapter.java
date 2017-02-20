package com.reservation.reservation;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import static com.reservation.reservation.R.color.colorPrimary;

/**
 * Created by Home on 2/18/2017.
 */

public class TextViewAdapter extends BaseAdapter {
    private Context context;
    private final String[] textViewValues;

    public TextViewAdapter(Context context, String[] textViewValues) {
        this.context = context;
        this.textViewValues = textViewValues;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {

            gridView = new View(context);

            // get layout from mobile.xml
            gridView = inflater.inflate(R.layout.item, null);

            // set value into textview
            TextView textView = (TextView) gridView
                    .findViewById(R.id.grid_item_label);
            textView.setText(textViewValues[position]);
            textView.setTextSize(30);
            textView.setBackgroundColor(Color.RED);
            textView.setPadding(50,50,50,50);

        } else {
            gridView = (View) convertView;
        }

        return gridView;
    }

    @Override
    public int getCount() {
        return textViewValues.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

}
