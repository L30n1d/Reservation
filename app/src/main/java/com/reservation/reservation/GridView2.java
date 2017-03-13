package com.reservation.reservation;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.ActivityCompat;
import android.telephony.SmsManager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.content.res.Resources;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GridView2 extends Activity {

    private String resId,caffeId,date, JSON_STRING,resSeat;
    private ArrayList<String> listt;
    private String seats, mobile, selectedSeat;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view2);

        Bundle bundle = getIntent().getExtras();

        session = new Session(this);

        seats = session.getAdminSeats();
        listt = new ArrayList<String>();

        caffeId = bundle.getString("caffeId");
        date = bundle.getString("date");
        mobile = bundle.getString("mobile");

        getJSON();

    }


    public static int getPixelsFromDPs(Activity activity, int dps){
        Resources r = activity.getResources();
        int  px = (int) (TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dps, r.getDisplayMetrics()));
        return px;
    }


    private void showSeats(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

            ArrayList<String> list1 = new ArrayList<>();


            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                final String idd = jo.getString(Config.TAG_ID);
                resSeat = jo.getString("Seat");

                if(!resSeat.equals("0")){
                    listt.add(resSeat);
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        GridView gv = (GridView) findViewById(R.id.gv2);
        // final TextView tv_message = (TextView) findViewById(R.id.tv_message);

        // Initializing a new String Array
        String[] plants = new String[Integer.parseInt(seats)];

        for(int i = 0;i<Integer.parseInt(seats);i++){

            plants[i] = Integer.toString(i+1);

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


                tv.setTextColor(Color.parseColor("#212121"));


                // Set the layout parameters for TextView widget
              /*  LayoutParams lp =  new LayoutParams(
                        LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT
                );
                tv.setLayoutParams(lp);*/

                // Get the TextView LayoutParams
           /*   LayoutParams params = (LayoutParams)tv.getLayoutParams();

                Set the width of TextView widget (item of GridView)
                params.width = getPixelsFromDPs(GridViewSupplementActivity.this,168);

                // Set the TextView layout parameters
                tv.setLayoutParams(params);*/

                // Display TextView text in center position
                tv.setGravity(Gravity.CENTER);

                // Set the TextView text font family and text size
                tv.setTypeface(Typeface.SANS_SERIF, Typeface.NORMAL);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.textsize));

                // Set the TextView text (GridView item text)





                tv.setText(plantsList.get(position));

                if(listt.contains(Integer.toString(position+1))){
                    tv.setOnClickListener(null);
                    tv.setTextColor(Color.WHITE);
                    tv.setBackgroundResource(R.drawable.boxxcolor2);
                }
                else{

                    tv.setTextColor(Color.parseColor("#FFC107"));
                    tv.setBackgroundResource(R.drawable.boxxcolor);
                }


                // Set the TextView background color

                // tv.setBackgroundColor(Color.parseColor("#FFFFFF"));
                tv.setTextSize(getResources().getDimension(R.dimen.textsize));




                return tv;

            }
        });



    }




    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(GridView2.this,"","Се вчитува...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showSeats();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();

                HashMap<String,String> map = new HashMap<>();

                SimpleDateFormat sdf2 = new SimpleDateFormat("E MMM dd yyyy");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                Date newDate = null;
                try {
                    newDate = sdf2.parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                String newDate2 = sdf.format(newDate);

                map.put("caffe_id",caffeId);
                map.put("date",newDate2);



                String s = rh.sendPostRequest(Config.GET_SEATS,map);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }


}
