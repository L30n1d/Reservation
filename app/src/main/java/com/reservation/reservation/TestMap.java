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

public class TestMap extends AppCompatActivity {

    private String JSON_STRING,id,date;
    private ImageButton ib1,ib2,ib3,ib4,ib5,ib6,ib7,ib8,ib9,ib10,ib11,ib12,ib13,ib14,ib15,ib16,ib17,
            ib18,ib19,ib20,ib21,ib22,ib23,ib24,ib25;

    private ImageButton[] imageButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_map);


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
                final String id = jo.getString(Config.TAG_ID);
                final String seat = jo.getString("Seat");

                imageButtons[Integer.parseInt(seat)].setImageResource(R.drawable.redtable);

                imageButtons[Integer.parseInt(seat)].setOnClickListener(null);
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
                loading = ProgressDialog.show(TestMap.this,"","Се вчитува...",false,false);
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

        /*ib1 = (ImageButton)findViewById(R.id.ib1);
        ib2 = (ImageButton)findViewById(R.id.ib2);
        ib3 = (ImageButton)findViewById(R.id.ib3);
        ib4 = (ImageButton)findViewById(R.id.ib4);
        ib5 = (ImageButton)findViewById(R.id.ib5);
        ib6 = (ImageButton)findViewById(R.id.ib6);
        ib7 = (ImageButton)findViewById(R.id.ib7);
        ib8 = (ImageButton)findViewById(R.id.ib8);
        ib9 = (ImageButton)findViewById(R.id.ib9);
        ib10 = (ImageButton)findViewById(R.id.ib10);
        ib11 = (ImageButton)findViewById(R.id.ib11);
        ib12 = (ImageButton)findViewById(R.id.ib12);
        ib13 = (ImageButton)findViewById(R.id.ib13);
        ib14 = (ImageButton)findViewById(R.id.ib14);
        ib15 = (ImageButton)findViewById(R.id.ib15);
        ib16 = (ImageButton)findViewById(R.id.ib16);
        ib17 = (ImageButton)findViewById(R.id.ib17);
        ib18 = (ImageButton)findViewById(R.id.ib18);
        ib19 = (ImageButton)findViewById(R.id.ib19);
        ib20 = (ImageButton)findViewById(R.id.ib20);
        ib21 = (ImageButton)findViewById(R.id.ib21);
        ib22 = (ImageButton)findViewById(R.id.ib22);
        ib23 = (ImageButton)findViewById(R.id.ib23);
        ib24 = (ImageButton)findViewById(R.id.ib24);
        ib25 = (ImageButton)findViewById(R.id.ib25);


        imageButtons[1] = ib1;
        imageButtons[2] = ib3;
        imageButtons[4] = ib4;
        imageButtons[5] = ib5;
        imageButtons[6] = ib6;
        imageButtons[7] = ib7;
        imageButtons[8] = ib8;
        imageButtons[9] = ib9;
        imageButtons[10] = ib10;
        imageButtons[11] = ib11;
        imageButtons[12] = ib12;
        imageButtons[13] = ib13;
        imageButtons[14] = ib15;
        imageButtons[15] = ib15;
        imageButtons[16] = ib16;
        imageButtons[17] = ib17;
        imageButtons[18] = ib18;
        imageButtons[19] = ib19;
        imageButtons[20] = ib20;
        imageButtons[21] = ib21;
        imageButtons[22] = ib22;
        imageButtons[23] = ib23;
        imageButtons[24] = ib24;
        imageButtons[25] = ib25;


        ib1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TestMap.this, MainActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                i.putExtras(bundle);

                Bundle bundle2 = new Bundle();
                bundle2.putString("date", date);
                i.putExtras(bundle2);

                Bundle bundle3 = new Bundle();
                bundle3.putString("seat","1");
                i.putExtras(bundle3);

                startActivity(i);
            }
        });

        ib2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TestMap.this, MainActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                i.putExtras(bundle);

                Bundle bundle2 = new Bundle();
                bundle2.putString("date", date);
                i.putExtras(bundle2);

                Bundle bundle3 = new Bundle();
                bundle3.putString("seat","2");
                i.putExtras(bundle3);

                startActivity(i);
            }
        });


        ib3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TestMap.this, MainActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                i.putExtras(bundle);

                Bundle bundle2 = new Bundle();
                bundle2.putString("date", date);
                i.putExtras(bundle2);

                Bundle bundle3 = new Bundle();
                bundle3.putString("seat","3");
                i.putExtras(bundle3);

                startActivity(i);
            }
        });


        ib4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TestMap.this, MainActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                i.putExtras(bundle);

                Bundle bundle2 = new Bundle();
                bundle2.putString("date", date);
                i.putExtras(bundle2);

                Bundle bundle3 = new Bundle();
                bundle3.putString("seat","4");
                i.putExtras(bundle3);

                startActivity(i);
            }
        });

        ib5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TestMap.this, MainActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                i.putExtras(bundle);

                Bundle bundle2 = new Bundle();
                bundle2.putString("date", date);
                i.putExtras(bundle2);

                Bundle bundle3 = new Bundle();
                bundle3.putString("seat","5");
                i.putExtras(bundle3);

                startActivity(i);
            }
        });

        ib6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TestMap.this, MainActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                i.putExtras(bundle);

                Bundle bundle2 = new Bundle();
                bundle2.putString("date", date);
                i.putExtras(bundle2);

                Bundle bundle3 = new Bundle();
                bundle3.putString("seat","6");
                i.putExtras(bundle3);

                startActivity(i);
            }
        });

        ib7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TestMap.this, MainActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                i.putExtras(bundle);

                Bundle bundle2 = new Bundle();
                bundle2.putString("date", date);
                i.putExtras(bundle2);

                Bundle bundle3 = new Bundle();
                bundle3.putString("seat","7");
                i.putExtras(bundle3);

                startActivity(i);
            }
        });

        ib8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TestMap.this, MainActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                i.putExtras(bundle);

                Bundle bundle2 = new Bundle();
                bundle2.putString("date", date);
                i.putExtras(bundle2);

                Bundle bundle3 = new Bundle();
                bundle3.putString("seat","8");
                i.putExtras(bundle3);

                startActivity(i);
            }
        });

        ib9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TestMap.this, MainActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                i.putExtras(bundle);

                Bundle bundle2 = new Bundle();
                bundle2.putString("date", date);
                i.putExtras(bundle2);

                Bundle bundle3 = new Bundle();
                bundle3.putString("seat","9");
                i.putExtras(bundle3);

                startActivity(i);
            }
        });

        ib10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TestMap.this, MainActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                i.putExtras(bundle);

                Bundle bundle2 = new Bundle();
                bundle2.putString("date", date);
                i.putExtras(bundle2);

                Bundle bundle3 = new Bundle();
                bundle3.putString("seat","10");
                i.putExtras(bundle3);

                startActivity(i);
            }
        });

        ib11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TestMap.this, MainActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                i.putExtras(bundle);

                Bundle bundle2 = new Bundle();
                bundle2.putString("date", date);
                i.putExtras(bundle2);

                Bundle bundle3 = new Bundle();
                bundle3.putString("seat","11");
                i.putExtras(bundle3);

                startActivity(i);
            }
        });

        ib12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TestMap.this, MainActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                i.putExtras(bundle);

                Bundle bundle2 = new Bundle();
                bundle2.putString("date", date);
                i.putExtras(bundle2);

                Bundle bundle3 = new Bundle();
                bundle3.putString("seat","12");
                i.putExtras(bundle3);

                startActivity(i);
            }
        });

        ib13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TestMap.this, MainActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                i.putExtras(bundle);

                Bundle bundle2 = new Bundle();
                bundle2.putString("date", date);
                i.putExtras(bundle2);

                Bundle bundle3 = new Bundle();
                bundle3.putString("seat","13");
                i.putExtras(bundle3);

                startActivity(i);
            }
        });

        ib14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TestMap.this, MainActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                i.putExtras(bundle);

                Bundle bundle2 = new Bundle();
                bundle2.putString("date", date);
                i.putExtras(bundle2);

                Bundle bundle3 = new Bundle();
                bundle3.putString("seat","14");
                i.putExtras(bundle3);

                startActivity(i);
            }
        });

        ib15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TestMap.this, MainActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                i.putExtras(bundle);

                Bundle bundle2 = new Bundle();
                bundle2.putString("date", date);
                i.putExtras(bundle2);

                Bundle bundle3 = new Bundle();
                bundle3.putString("seat","15");
                i.putExtras(bundle3);

                startActivity(i);
            }
        });

        ib16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TestMap.this, MainActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                i.putExtras(bundle);

                Bundle bundle2 = new Bundle();
                bundle2.putString("date", date);
                i.putExtras(bundle2);

                Bundle bundle3 = new Bundle();
                bundle3.putString("seat","16");
                i.putExtras(bundle3);

                startActivity(i);
            }
        });

        ib17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TestMap.this, MainActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                i.putExtras(bundle);

                Bundle bundle2 = new Bundle();
                bundle2.putString("date", date);
                i.putExtras(bundle2);

                Bundle bundle3 = new Bundle();
                bundle3.putString("seat","17");
                i.putExtras(bundle3);

                startActivity(i);
            }
        });

        ib18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TestMap.this, MainActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                i.putExtras(bundle);

                Bundle bundle2 = new Bundle();
                bundle2.putString("date", date);
                i.putExtras(bundle2);

                Bundle bundle3 = new Bundle();
                bundle3.putString("seat","18");
                i.putExtras(bundle3);

                startActivity(i);
            }
        });

        ib19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TestMap.this, MainActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                i.putExtras(bundle);

                Bundle bundle2 = new Bundle();
                bundle2.putString("date", date);
                i.putExtras(bundle2);

                Bundle bundle3 = new Bundle();
                bundle3.putString("seat","19");
                i.putExtras(bundle3);

                startActivity(i);
            }
        });

        ib20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TestMap.this, MainActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                i.putExtras(bundle);

                Bundle bundle2 = new Bundle();
                bundle2.putString("date", date);
                i.putExtras(bundle2);

                Bundle bundle3 = new Bundle();
                bundle3.putString("seat","20");
                i.putExtras(bundle3);

                startActivity(i);
            }
        });

        ib21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TestMap.this, MainActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                i.putExtras(bundle);

                Bundle bundle2 = new Bundle();
                bundle2.putString("date", date);
                i.putExtras(bundle2);

                Bundle bundle3 = new Bundle();
                bundle3.putString("seat","21");
                i.putExtras(bundle3);

                startActivity(i);
            }
        });

        ib22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TestMap.this, MainActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                i.putExtras(bundle);

                Bundle bundle2 = new Bundle();
                bundle2.putString("date", date);
                i.putExtras(bundle2);

                Bundle bundle3 = new Bundle();
                bundle3.putString("seat","22");
                i.putExtras(bundle3);

                startActivity(i);
            }
        });

        ib23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TestMap.this, MainActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                i.putExtras(bundle);

                Bundle bundle2 = new Bundle();
                bundle2.putString("date", date);
                i.putExtras(bundle2);

                Bundle bundle3 = new Bundle();
                bundle3.putString("seat","23");
                i.putExtras(bundle3);

                startActivity(i);
            }
        });

        ib24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TestMap.this, MainActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                i.putExtras(bundle);

                Bundle bundle2 = new Bundle();
                bundle2.putString("date", date);
                i.putExtras(bundle2);

                Bundle bundle3 = new Bundle();
                bundle3.putString("seat","24");
                i.putExtras(bundle3);

                startActivity(i);
            }
        });

        ib25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TestMap.this, MainActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                i.putExtras(bundle);

                Bundle bundle2 = new Bundle();
                bundle2.putString("date", date);
                i.putExtras(bundle2);

                Bundle bundle3 = new Bundle();
                bundle3.putString("seat","25");
                i.putExtras(bundle3);

                startActivity(i);
            }
        });*/



    }

}
