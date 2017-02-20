package com.reservation.reservation;



import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Activity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.res.Resources;

public class GridViewSupplementActivity extends Activity {



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_grid_view_supplement);

        // Get the widgets reference from XML layout
        GridView gv = (GridView) findViewById(R.id.gv);
        final TextView tv_message = (TextView) findViewById(R.id.tv_message);

        // Initializing a new String Array
        String[] plants = new String[100];

        for(int i = 0;i<100;i++){
            plants[i] = Integer.toString(i);
        }

        // Populate a List from Array elements
        final List<String> plantsList = new ArrayList<String>(Arrays.asList(plants));

        // Data bind GridView with ArrayAdapter (String Array elements)
        gv.setAdapter(new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, plantsList){
            public View getView(int position, View convertView, ViewGroup parent) {

                // Return the GridView current item as a View
                View view = super.getView(position,convertView,parent);

                // Convert the view as a TextView widget
                TextView tv = (TextView) view;

                //tv.setTextColor(Color.DKGRAY);

                // Set the layout parameters for TextView widget
              /*  RelativeLayout.LayoutParams lp =  new RelativeLayout.LayoutParams(
                        LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT
                );
                tv.setLayoutParams(lp);*/

                // Get the TextView LayoutParams
             /*   RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)tv.getLayoutParams();

                // Set the width of TextView widget (item of GridView)
                params.width = getPixelsFromDPs(GridViewSupplementActivity.this,168);

                // Set the TextView layout parameters
                tv.setLayoutParams(params);*/

                // Display TextView text in center position
                tv.setGravity(Gravity.CENTER);

                // Set the TextView text font family and text size
                tv.setTypeface(Typeface.SANS_SERIF, Typeface.NORMAL);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);

                // Set the TextView text (GridView item text)
                tv.setText(plantsList.get(position));

                // Set the TextView background color
                tv.setBackgroundColor(Color.parseColor("#FF82DE81"));



                // Return the TextView widget as GridView item
                return tv;
            }
        });

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                tv_message.setText("Selected Item : " + selectedItem);
            }
        });

    }

    public static int getPixelsFromDPs(Activity activity, int dps){
        Resources r = activity.getResources();
        int  px = (int) (TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dps, r.getDisplayMetrics()));
        return px;
    }

}