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

public class TestMap2 extends AppCompatActivity {

    private String JSON_STRING,id,date, typeC;
    private ImageButton iby1,iby2,iby3,iby4,iby5,iby6,iby7,iby8,iby9,iby10,iby11,iby12,iby13,iby14,iby15,iby16,iby17,
            iby18,iby19,iby20,iby21,iby22,iby23,iby24,iby25;

    private ImageButton[] imageButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_map2);

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
                loading = ProgressDialog.show(TestMap2.this,"","Се вчитува...",false,false);
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

        iby1 = (ImageButton)findViewById(R.id.iby1);
        iby2 = (ImageButton)findViewById(R.id.iby2);
        iby3 = (ImageButton)findViewById(R.id.iby3);
        iby4 = (ImageButton)findViewById(R.id.iby4);
        iby5 = (ImageButton)findViewById(R.id.iby5);
        iby6 = (ImageButton)findViewById(R.id.iby6);
        iby7 = (ImageButton)findViewById(R.id.iby7);
        iby8 = (ImageButton)findViewById(R.id.iby8);
        iby9 = (ImageButton)findViewById(R.id.iby9);
        iby10 = (ImageButton)findViewById(R.id.iby10);
        iby11 = (ImageButton)findViewById(R.id.iby11);
        iby12 = (ImageButton)findViewById(R.id.iby12);
        iby13 = (ImageButton)findViewById(R.id.iby13);
        iby14 = (ImageButton)findViewById(R.id.iby14);
        iby15 = (ImageButton)findViewById(R.id.iby15);
        iby16 = (ImageButton)findViewById(R.id.iby16);
        iby17 = (ImageButton)findViewById(R.id.iby17);
        iby18 = (ImageButton)findViewById(R.id.iby18);
        iby19 = (ImageButton)findViewById(R.id.iby19);
        iby20 = (ImageButton)findViewById(R.id.iby20);
        iby21 = (ImageButton)findViewById(R.id.iby21);
        iby22 = (ImageButton)findViewById(R.id.iby22);
        iby23 = (ImageButton)findViewById(R.id.iby23);
        iby24 = (ImageButton)findViewById(R.id.iby24);
        iby25 = (ImageButton)findViewById(R.id.iby25);

        imageButtons[1] = iby1;
        imageButtons[2] = iby2;
        imageButtons[3] = iby3;
        imageButtons[4] = iby4;
        imageButtons[5] = iby5;
        imageButtons[6] = iby6;
        imageButtons[7] = iby7;
        imageButtons[8] = iby8;
        imageButtons[9] = iby9;
        imageButtons[10] = iby10;
        imageButtons[11] = iby11;
        imageButtons[12] = iby12;
        imageButtons[13] = iby13;
        imageButtons[14] = iby14;
        imageButtons[15] = iby15;
        imageButtons[16] = iby16;
        imageButtons[17] = iby17;
        imageButtons[18] = iby18;
        imageButtons[19] = iby19;
        imageButtons[20] = iby20;
        imageButtons[21] = iby21;
        imageButtons[22] = iby22;
        imageButtons[23] = iby23;
        imageButtons[24] = iby24;
        imageButtons[25] = iby25;


        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics ();
        display.getMetrics(outMetrics);

        float density  = getResources().getDisplayMetrics().density;
        float dpHeight = outMetrics.heightPixels / density;
        float dpWidth  = outMetrics.widthPixels / density;
        float width = outMetrics.widthPixels;

        // Toast.makeText(getApplicationContext(),Float.toString(dpWidth),Toast.LENGTH_SHORT).show();
        // Toast.makeText(getApplicationContext(),Float.toString(width),Toast.LENGTH_SHORT).show();


        for(int i = 1; i < 24; i++){
            if(width <= 480){
                imageButtons[i].setImageResource(R.drawable.free32);
            }
        }


        iby1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TestMap2.this, finalverification.class);

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

        iby2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TestMap2.this, finalverification.class);

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


        iby3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TestMap2.this, finalverification.class);

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


        iby4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TestMap2.this, finalverification.class);

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

        iby5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TestMap2.this, finalverification.class);

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

        iby6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TestMap2.this, finalverification.class);

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

        iby7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TestMap2.this, finalverification.class);

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

        iby8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TestMap2.this, finalverification.class);

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

        iby9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TestMap2.this, finalverification.class);

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

        iby10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TestMap2.this, finalverification.class);

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

        iby11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TestMap2.this, finalverification.class);

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

        iby12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TestMap2.this, finalverification.class);

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

        iby13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TestMap2.this, finalverification.class);

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

        iby14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TestMap2.this, finalverification.class);

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

        iby15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TestMap2.this, finalverification.class);

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

        iby16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TestMap2.this, finalverification.class);

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

        iby17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TestMap2.this, finalverification.class);

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

        iby18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TestMap2.this, finalverification.class);

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

        iby19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TestMap2.this, finalverification.class);

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

        iby20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TestMap2.this, finalverification.class);

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

        iby21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TestMap2.this, finalverification.class);

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

        iby22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TestMap2.this, finalverification.class);

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

        iby23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TestMap2.this, finalverification.class);

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

        iby24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TestMap2.this, finalverification.class);

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

        iby25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TestMap2.this, finalverification.class);

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
