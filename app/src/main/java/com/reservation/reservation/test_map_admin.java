package com.reservation.reservation;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;


public class test_map_admin extends AppCompatActivity {

    private String JSON_STRING,id,date;
    private ImageButton iba1,iba2,iba3,iba4,iba5,iba6,iba7,iba8,iba9,iba10,iba11,iba12,iba13,iba14,iba15,iba16,iba17,
            iba18,iba19,iba20,iba21,iba22,iba23,iba24,iba25;

    private ImageButton[] imageButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_map_admin);


        initialize();

        Bundle bundle = getIntent().getExtras();

        id = bundle.getString("id");
        date = bundle.getString("date");

        getJSON();

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
                final String seat = jo.getString("Seat");

                imageButtons[Integer.parseInt(seat)].setImageResource(R.drawable.bahatowhitereserved);

                imageButtons[Integer.parseInt(seat)].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(test_map_admin.this, Overview.class);

                        Bundle bundle = new Bundle();
                        bundle.putString("id", idd);
                        i.putExtras(bundle);


                        startActivity(i);
                    }
                });
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }



    }

    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(test_map_admin.this,"","Се вчитува...",false,false);
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

                map.put("caffe_id",id);
                map.put("date",newDate2);



                String s = rh.sendPostRequest(Config.GET_SEATS,map);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    private void initialize(){
        imageButtons = new ImageButton[26];

        iba1 = (ImageButton)findViewById(R.id.iba1);
        iba2 = (ImageButton)findViewById(R.id.iba2);
        iba3 = (ImageButton)findViewById(R.id.iba3);
        iba4 = (ImageButton)findViewById(R.id.iba4);
        iba5 = (ImageButton)findViewById(R.id.iba5);
        iba6 = (ImageButton)findViewById(R.id.iba6);
        iba7 = (ImageButton)findViewById(R.id.iba7);
        iba8 = (ImageButton)findViewById(R.id.iba8);
        iba9 = (ImageButton)findViewById(R.id.iba9);
        iba10 = (ImageButton)findViewById(R.id.iba10);
        iba11 = (ImageButton)findViewById(R.id.iba11);
        iba12 = (ImageButton)findViewById(R.id.iba12);
        iba13 = (ImageButton)findViewById(R.id.iba13);
        iba14 = (ImageButton)findViewById(R.id.iba14);
        iba15 = (ImageButton)findViewById(R.id.iba15);
        iba16 = (ImageButton)findViewById(R.id.iba16);
        iba17 = (ImageButton)findViewById(R.id.iba17);
        iba18 = (ImageButton)findViewById(R.id.iba18);
        iba19 = (ImageButton)findViewById(R.id.iba19);
        iba20 = (ImageButton)findViewById(R.id.iba20);
        iba21 = (ImageButton)findViewById(R.id.iba21);
        iba22 = (ImageButton)findViewById(R.id.iba22);
        iba23 = (ImageButton)findViewById(R.id.iba23);
        iba24 = (ImageButton)findViewById(R.id.iba24);
        iba25 = (ImageButton)findViewById(R.id.iba25);


        imageButtons[1] = iba1;
        imageButtons[2] = iba3;
        imageButtons[4] = iba4;
        imageButtons[5] = iba5;
        imageButtons[6] = iba6;
        imageButtons[7] = iba7;
        imageButtons[8] = iba8;
        imageButtons[9] = iba9;
        imageButtons[10] = iba10;
        imageButtons[11] = iba11;
        imageButtons[12] = iba12;
        imageButtons[13] = iba13;
        imageButtons[14] = iba15;
        imageButtons[15] = iba15;
        imageButtons[16] = iba16;
        imageButtons[17] = iba17;
        imageButtons[18] = iba18;
        imageButtons[19] = iba19;
        imageButtons[20] = iba20;
        imageButtons[21] = iba21;
        imageButtons[22] = iba22;
        imageButtons[23] = iba23;
        imageButtons[24] = iba24;
        imageButtons[25] = iba25;



    }


}
