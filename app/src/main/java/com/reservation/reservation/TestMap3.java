package com.reservation.reservation;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.ImageButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class TestMap3 extends AppCompatActivity {

    private String JSON_STRING,id,date, typeC;
    private ImageButton ibv1,ibv2,ibv3,ibv4,ibv5,ibv6,ibv7,ibv8,ibv9,ibv10,ibv11,ibv12,ibv13,ibv14,ibv15,ibv16,ibv17,
            ibv18,ibv19,ibv20,ibv21,ibv22,ibv23,ibv24,ibv25;

    private ImageButton[] imageButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_map3);

        initialize();

        Bundle bundle = getIntent().getExtras();

        id = bundle.getString("id");
        date = bundle.getString("date");
        typeC = bundle.getString("type");

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

                Display display = getWindowManager().getDefaultDisplay();
                DisplayMetrics outMetrics = new DisplayMetrics ();
                display.getMetrics(outMetrics);

                float density  = getResources().getDisplayMetrics().density;
                float dpHeight = outMetrics.heightPixels / density;
                float dpWidth  = outMetrics.widthPixels / density;
                float width = outMetrics.widthPixels;

                // Toast.makeText(getApplicationContext(),Float.toString(dpWidth),Toast.LENGTH_SHORT).show();
                // Toast.makeText(getApplicationContext(),Float.toString(width),Toast.LENGTH_SHORT).show();


                if(width <= 480){
                    imageButtons[Integer.parseInt(seat)].setImageResource(R.drawable.busy32);
                }
                else if(dpHeight == 320){
                    imageButtons[Integer.parseInt(seat)].setImageResource(R.drawable.busy64);
                }
                else if(width > 1920){
                    imageButtons[Integer.parseInt(seat)].setImageResource(R.drawable.busy192);
                }
                else if(dpHeight == 360){
                    imageButtons[Integer.parseInt(seat)].setImageResource(R.drawable.busy150);
                }


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
                loading = ProgressDialog.show(TestMap3.this,"","Се вчитува...",false,false);
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

        ibv1 = (ImageButton)findViewById(R.id.ibv1);
        ibv2 = (ImageButton)findViewById(R.id.ibv2);
        ibv3 = (ImageButton)findViewById(R.id.ibv3);
        ibv4 = (ImageButton)findViewById(R.id.ibv4);
        ibv5 = (ImageButton)findViewById(R.id.ibv5);
        ibv6 = (ImageButton)findViewById(R.id.ibv6);
        ibv7 = (ImageButton)findViewById(R.id.ibv7);
        ibv8 = (ImageButton)findViewById(R.id.ibv8);
        ibv9 = (ImageButton)findViewById(R.id.ibv9);
        ibv10 = (ImageButton)findViewById(R.id.ibv10);
        ibv11 = (ImageButton)findViewById(R.id.ibv11);
        ibv12 = (ImageButton)findViewById(R.id.ibv12);
        ibv13 = (ImageButton)findViewById(R.id.ibv13);
        ibv14 = (ImageButton)findViewById(R.id.ibv14);
        ibv15 = (ImageButton)findViewById(R.id.ibv15);
        ibv16 = (ImageButton)findViewById(R.id.ibv16);
        ibv17 = (ImageButton)findViewById(R.id.ibv17);
        ibv18 = (ImageButton)findViewById(R.id.ibv18);
        ibv19 = (ImageButton)findViewById(R.id.ibv19);
        ibv20 = (ImageButton)findViewById(R.id.ibv20);
        ibv21 = (ImageButton)findViewById(R.id.ibv21);
        ibv22 = (ImageButton)findViewById(R.id.ibv22);
        ibv23 = (ImageButton)findViewById(R.id.ibv23);
        ibv24 = (ImageButton)findViewById(R.id.ibv24);
        ibv25 = (ImageButton)findViewById(R.id.ibv25);

        imageButtons[1] = ibv1;
        imageButtons[2] = ibv2;
        imageButtons[3] = ibv3;
        imageButtons[4] = ibv4;
        imageButtons[5] = ibv5;
        imageButtons[6] = ibv6;
        imageButtons[7] = ibv7;
        imageButtons[8] = ibv8;
        imageButtons[9] = ibv9;
        imageButtons[10] = ibv10;
        imageButtons[11] = ibv11;
        imageButtons[12] = ibv12;
        imageButtons[13] = ibv13;
        imageButtons[14] = ibv14;
        imageButtons[15] = ibv15;
        imageButtons[16] = ibv16;
        imageButtons[17] = ibv17;
        imageButtons[18] = ibv18;
        imageButtons[19] = ibv19;
        imageButtons[20] = ibv20;
        imageButtons[21] = ibv21;
        imageButtons[22] = ibv22;
        imageButtons[23] = ibv23;
        imageButtons[24] = ibv24;
        imageButtons[25] = ibv25;


        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics ();
        display.getMetrics(outMetrics);

        float density  = getResources().getDisplayMetrics().density;
        float dpHeight = outMetrics.heightPixels / density;
        float dpWidth  = outMetrics.widthPixels / density;
        float width = outMetrics.widthPixels;

        // Toast.makeText(getApplicationContext(),Float.toString(dpWidth),Toast.LENGTH_SHORT).show();
        // Toast.makeText(getApplicationContext(),Float.toString(width),Toast.LENGTH_SHORT).show();


        for(int i = 1; i < 26; i++){
            if(width <= 480){
                imageButtons[i].setImageResource(R.drawable.free32);
            }
        }


        ibv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TestMap3.this, finalverification.class);

                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                i.putExtras(bundle);

                Bundle bundle2 = new Bundle();
                bundle2.putString("date", date);
                i.putExtras(bundle2);

                Bundle bundle3 = new Bundle();
                bundle3.putString("seat","1");
                i.putExtras(bundle3);

                Bundle bundle4 = new Bundle();
                bundle4.putString("type", typeC);
                i.putExtras(bundle4);

                startActivity(i);
            }
        });

        ibv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TestMap3.this, finalverification.class);

                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                i.putExtras(bundle);

                Bundle bundle2 = new Bundle();
                bundle2.putString("date", date);
                i.putExtras(bundle2);

                Bundle bundle3 = new Bundle();
                bundle3.putString("seat","2");
                i.putExtras(bundle3);

                Bundle bundle4 = new Bundle();
                bundle4.putString("type", typeC);
                i.putExtras(bundle4);

                startActivity(i);
            }
        });


        ibv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TestMap3.this, finalverification.class);

                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                i.putExtras(bundle);

                Bundle bundle2 = new Bundle();
                bundle2.putString("date", date);
                i.putExtras(bundle2);

                Bundle bundle3 = new Bundle();
                bundle3.putString("seat","3");
                i.putExtras(bundle3);

                Bundle bundle4 = new Bundle();
                bundle4.putString("type", typeC);
                i.putExtras(bundle4);

                startActivity(i);
            }
        });


        ibv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TestMap3.this, finalverification.class);

                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                i.putExtras(bundle);

                Bundle bundle2 = new Bundle();
                bundle2.putString("date", date);
                i.putExtras(bundle2);

                Bundle bundle3 = new Bundle();
                bundle3.putString("seat","4");
                i.putExtras(bundle3);

                Bundle bundle4 = new Bundle();
                bundle4.putString("type", typeC);
                i.putExtras(bundle4);

                startActivity(i);
            }
        });

        ibv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TestMap3.this, finalverification.class);

                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                i.putExtras(bundle);

                Bundle bundle2 = new Bundle();
                bundle2.putString("date", date);
                i.putExtras(bundle2);

                Bundle bundle3 = new Bundle();
                bundle3.putString("seat","5");
                i.putExtras(bundle3);

                Bundle bundle4 = new Bundle();
                bundle4.putString("type", typeC);
                i.putExtras(bundle4);

                startActivity(i);
            }
        });

        ibv6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TestMap3.this, finalverification.class);

                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                i.putExtras(bundle);

                Bundle bundle2 = new Bundle();
                bundle2.putString("date", date);
                i.putExtras(bundle2);

                Bundle bundle3 = new Bundle();
                bundle3.putString("seat","6");
                i.putExtras(bundle3);

                Bundle bundle4 = new Bundle();
                bundle4.putString("type", typeC);
                i.putExtras(bundle4);

                startActivity(i);
            }
        });

        ibv7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TestMap3.this, finalverification.class);

                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                i.putExtras(bundle);

                Bundle bundle2 = new Bundle();
                bundle2.putString("date", date);
                i.putExtras(bundle2);

                Bundle bundle3 = new Bundle();
                bundle3.putString("seat","7");
                i.putExtras(bundle3);

                Bundle bundle4 = new Bundle();
                bundle4.putString("type", typeC);
                i.putExtras(bundle4);

                startActivity(i);
            }
        });

        ibv8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TestMap3.this, finalverification.class);

                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                i.putExtras(bundle);

                Bundle bundle2 = new Bundle();
                bundle2.putString("date", date);
                i.putExtras(bundle2);

                Bundle bundle3 = new Bundle();
                bundle3.putString("seat","8");
                i.putExtras(bundle3);

                Bundle bundle4 = new Bundle();
                bundle4.putString("type", typeC);
                i.putExtras(bundle4);

                startActivity(i);
            }
        });

        ibv9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TestMap3.this, finalverification.class);

                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                i.putExtras(bundle);

                Bundle bundle2 = new Bundle();
                bundle2.putString("date", date);
                i.putExtras(bundle2);

                Bundle bundle3 = new Bundle();
                bundle3.putString("seat","9");
                i.putExtras(bundle3);

                Bundle bundle4 = new Bundle();
                bundle4.putString("type", typeC);
                i.putExtras(bundle4);

                startActivity(i);
            }
        });

        ibv10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TestMap3.this, finalverification.class);

                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                i.putExtras(bundle);

                Bundle bundle2 = new Bundle();
                bundle2.putString("date", date);
                i.putExtras(bundle2);

                Bundle bundle3 = new Bundle();
                bundle3.putString("seat","10");
                i.putExtras(bundle3);

                Bundle bundle4 = new Bundle();
                bundle4.putString("type", typeC);
                i.putExtras(bundle4);

                startActivity(i);
            }
        });

        ibv11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TestMap3.this, finalverification.class);

                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                i.putExtras(bundle);

                Bundle bundle2 = new Bundle();
                bundle2.putString("date", date);
                i.putExtras(bundle2);

                Bundle bundle3 = new Bundle();
                bundle3.putString("seat","11");
                i.putExtras(bundle3);

                Bundle bundle4 = new Bundle();
                bundle4.putString("type", typeC);
                i.putExtras(bundle4);

                startActivity(i);
            }
        });

        ibv12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TestMap3.this, finalverification.class);

                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                i.putExtras(bundle);

                Bundle bundle2 = new Bundle();
                bundle2.putString("date", date);
                i.putExtras(bundle2);

                Bundle bundle3 = new Bundle();
                bundle3.putString("seat","12");
                i.putExtras(bundle3);

                Bundle bundle4 = new Bundle();
                bundle4.putString("type", typeC);
                i.putExtras(bundle4);

                startActivity(i);
            }
        });

        ibv13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TestMap3.this, finalverification.class);

                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                i.putExtras(bundle);

                Bundle bundle2 = new Bundle();
                bundle2.putString("date", date);
                i.putExtras(bundle2);

                Bundle bundle3 = new Bundle();
                bundle3.putString("seat","13");
                i.putExtras(bundle3);

                Bundle bundle4 = new Bundle();
                bundle4.putString("type", typeC);
                i.putExtras(bundle4);

                startActivity(i);
            }
        });

        ibv14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TestMap3.this, finalverification.class);

                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                i.putExtras(bundle);

                Bundle bundle2 = new Bundle();
                bundle2.putString("date", date);
                i.putExtras(bundle2);

                Bundle bundle3 = new Bundle();
                bundle3.putString("seat","14");
                i.putExtras(bundle3);

                Bundle bundle4 = new Bundle();
                bundle4.putString("type", typeC);
                i.putExtras(bundle4);

                startActivity(i);
            }
        });

        ibv15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TestMap3.this, finalverification.class);

                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                i.putExtras(bundle);

                Bundle bundle2 = new Bundle();
                bundle2.putString("date", date);
                i.putExtras(bundle2);

                Bundle bundle3 = new Bundle();
                bundle3.putString("seat","15");
                i.putExtras(bundle3);

                Bundle bundle4 = new Bundle();
                bundle4.putString("type", typeC);
                i.putExtras(bundle4);

                startActivity(i);
            }
        });

        ibv16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TestMap3.this, finalverification.class);

                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                i.putExtras(bundle);

                Bundle bundle2 = new Bundle();
                bundle2.putString("date", date);
                i.putExtras(bundle2);

                Bundle bundle3 = new Bundle();
                bundle3.putString("seat","16");
                i.putExtras(bundle3);

                Bundle bundle4 = new Bundle();
                bundle4.putString("type", typeC);
                i.putExtras(bundle4);

                startActivity(i);
            }
        });

        ibv17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TestMap3.this, finalverification.class);

                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                i.putExtras(bundle);

                Bundle bundle2 = new Bundle();
                bundle2.putString("date", date);
                i.putExtras(bundle2);

                Bundle bundle3 = new Bundle();
                bundle3.putString("seat","17");
                i.putExtras(bundle3);

                Bundle bundle4 = new Bundle();
                bundle4.putString("type", typeC);
                i.putExtras(bundle4);

                startActivity(i);
            }
        });

        ibv18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TestMap3.this, finalverification.class);

                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                i.putExtras(bundle);

                Bundle bundle2 = new Bundle();
                bundle2.putString("date", date);
                i.putExtras(bundle2);

                Bundle bundle3 = new Bundle();
                bundle3.putString("seat","18");
                i.putExtras(bundle3);

                Bundle bundle4 = new Bundle();
                bundle4.putString("type", typeC);
                i.putExtras(bundle4);

                startActivity(i);
            }
        });

        ibv19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TestMap3.this, finalverification.class);

                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                i.putExtras(bundle);

                Bundle bundle2 = new Bundle();
                bundle2.putString("date", date);
                i.putExtras(bundle2);

                Bundle bundle3 = new Bundle();
                bundle3.putString("seat","19");
                i.putExtras(bundle3);

                Bundle bundle4 = new Bundle();
                bundle4.putString("type", typeC);
                i.putExtras(bundle4);

                startActivity(i);
            }
        });

        ibv20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TestMap3.this, finalverification.class);

                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                i.putExtras(bundle);

                Bundle bundle2 = new Bundle();
                bundle2.putString("date", date);
                i.putExtras(bundle2);

                Bundle bundle3 = new Bundle();
                bundle3.putString("seat","20");
                i.putExtras(bundle3);

                Bundle bundle4 = new Bundle();
                bundle4.putString("type", typeC);
                i.putExtras(bundle4);

                startActivity(i);
            }
        });

        ibv21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TestMap3.this, finalverification.class);

                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                i.putExtras(bundle);

                Bundle bundle2 = new Bundle();
                bundle2.putString("date", date);
                i.putExtras(bundle2);

                Bundle bundle3 = new Bundle();
                bundle3.putString("seat","21");
                i.putExtras(bundle3);

                Bundle bundle4 = new Bundle();
                bundle4.putString("type", typeC);
                i.putExtras(bundle4);

                startActivity(i);
            }
        });

        ibv22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TestMap3.this, finalverification.class);

                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                i.putExtras(bundle);

                Bundle bundle2 = new Bundle();
                bundle2.putString("date", date);
                i.putExtras(bundle2);

                Bundle bundle3 = new Bundle();
                bundle3.putString("seat","22");
                i.putExtras(bundle3);

                Bundle bundle4 = new Bundle();
                bundle4.putString("type", typeC);
                i.putExtras(bundle4);

                startActivity(i);
            }
        });

        ibv23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TestMap3.this, finalverification.class);

                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                i.putExtras(bundle);

                Bundle bundle2 = new Bundle();
                bundle2.putString("date", date);
                i.putExtras(bundle2);

                Bundle bundle3 = new Bundle();
                bundle3.putString("seat","23");
                i.putExtras(bundle3);

                Bundle bundle4 = new Bundle();
                bundle4.putString("type", typeC);
                i.putExtras(bundle4);

                startActivity(i);
            }
        });

        ibv24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TestMap3.this, finalverification.class);

                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                i.putExtras(bundle);

                Bundle bundle2 = new Bundle();
                bundle2.putString("date", date);
                i.putExtras(bundle2);

                Bundle bundle3 = new Bundle();
                bundle3.putString("seat","24");
                i.putExtras(bundle3);

                Bundle bundle4 = new Bundle();
                bundle4.putString("type", typeC);
                i.putExtras(bundle4);

                startActivity(i);
            }
        });

        ibv25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TestMap3.this, finalverification.class);

                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                i.putExtras(bundle);

                Bundle bundle2 = new Bundle();
                bundle2.putString("date", date);
                i.putExtras(bundle2);

                Bundle bundle3 = new Bundle();
                bundle3.putString("seat","25");
                i.putExtras(bundle3);

                Bundle bundle4 = new Bundle();
                bundle4.putString("type", typeC);
                i.putExtras(bundle4);

                startActivity(i);
            }
        });



    }

}
