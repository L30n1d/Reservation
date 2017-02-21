package com.reservation.reservation;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

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
import java.util.List;
public class PlaceChooserAdmin extends AppCompatActivity {

    private Spinner spinner2;
    private String JSON_STRING, caffeId, layout, days, date;
    private int userId;
    private Session session;
    private Button btn,logOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_chooser_admin);


        session = new Session(this);

        userId = session.getUserId();

        Bundle bundle = getIntent().getExtras();

        caffeId = bundle.getString("caffe_id");
        days = bundle.getString("days");
        layout = bundle.getString("layout");

        spinner2 = (Spinner)findViewById(R.id.spinner2A);
        btn = (Button)findViewById(R.id.button3A);
        logOut = (Button)findViewById(R.id.logOut2);

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.setLoggedIn(false, "");
                session.setUserRole("");
                finish();
                startActivity(new Intent(PlaceChooserAdmin.this, usersignin.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date = spinner2.getSelectedItem().toString();

                try {
                    Class c = Class.forName(layout);
                    Intent i = new Intent(PlaceChooserAdmin.this, listView.class);

                    Bundle bundle = new Bundle();
                    bundle.putString("id", caffeId);
                    i.putExtras(bundle);

                    Bundle bundle2 = new Bundle();
                    bundle2.putString("date", date);
                    i.putExtras(bundle2);


                    startActivity(i);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }



            }
        });

        populate();
        
    }

    private void populate(){
        ArrayList<String> dates = new ArrayList<String>();

        if(days.equals("7")){

            Calendar cal = GregorianCalendar.getInstance();
            cal.setTime(new Date());

            Date d1 = cal.getTime();

            cal.add(Calendar.DAY_OF_YEAR, +1);

            Date d2 = cal.getTime();

            cal.add(Calendar.DAY_OF_YEAR, +1);

            Date d3 = cal.getTime();

            cal.add(Calendar.DAY_OF_YEAR, +1);

            Date d4 = cal.getTime();

            cal.add(Calendar.DAY_OF_YEAR, +1);

            Date d5 = cal.getTime();

            cal.add(Calendar.DAY_OF_YEAR, +1);

            Date d6 = cal.getTime();

            cal.add(Calendar.DAY_OF_YEAR, +1);

            Date d7 = cal.getTime();

            SimpleDateFormat sdf = new SimpleDateFormat("E MMM dd yyyy");

            String tmp1 = sdf.format(d1);
            String tmp2 = sdf.format(d2);
            String tmp3 = sdf.format(d3);
            String tmp4 = sdf.format(d4);
            String tmp5 = sdf.format(d5);
            String tmp6 = sdf.format(d6);
            String tmp7 = sdf.format(d7);


            dates.add(tmp1);
            dates.add(tmp2);
            dates.add(tmp3);
            dates.add(tmp4);
            dates.add(tmp5);
            dates.add(tmp6);
            dates.add(tmp7);
        }

        else{

            SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
            SimpleDateFormat sdf2 = new SimpleDateFormat("E MMM dd yyyy");
            Date d = new Date();
            String dayOfTheWeek = sdf.format(d);

            Calendar cal = GregorianCalendar.getInstance();
            cal.setTime(new Date());

            Date d1,d2,d3;


            if(dayOfTheWeek.equals("Monday")){
                cal.add(Calendar.DAY_OF_YEAR, +4);
                d1 = cal.getTime();
                cal.add(Calendar.DAY_OF_YEAR, +1);
                d2 = cal.getTime();
                cal.add(Calendar.DAY_OF_YEAR, +1);
                d3 = cal.getTime();

                String tmp1 = sdf2.format(d1);
                String tmp2 = sdf2.format(d2);
                String tmp3 = sdf2.format(d3);

                dates.add(tmp1);
                dates.add(tmp2);
                dates.add(tmp3);

            }
            else  if(dayOfTheWeek.equals("Tuesday")){
                cal.add(Calendar.DAY_OF_YEAR, +3);
                d1 = cal.getTime();
                cal.add(Calendar.DAY_OF_YEAR, +1);
                d2 = cal.getTime();
                cal.add(Calendar.DAY_OF_YEAR, +1);
                d3 = cal.getTime();

                String tmp1 = sdf2.format(d1);
                String tmp2 = sdf2.format(d2);
                String tmp3 = sdf2.format(d3);

                dates.add(tmp1);
                dates.add(tmp2);
                dates.add(tmp3);
            }
            else  if(dayOfTheWeek.equals("Wednesday")){
                cal.add(Calendar.DAY_OF_YEAR, +2);
                d1 = cal.getTime();
                cal.add(Calendar.DAY_OF_YEAR, +1);
                d2 = cal.getTime();
                cal.add(Calendar.DAY_OF_YEAR, +1);
                d3 = cal.getTime();

                String tmp1 = sdf2.format(d1);
                String tmp2 = sdf2.format(d2);
                String tmp3 = sdf2.format(d3);
                dates.add(tmp1);
                dates.add(tmp2);
                dates.add(tmp3);
            }
            else  if(dayOfTheWeek.equals("Thursday")){
                cal.add(Calendar.DAY_OF_YEAR, +1);
                d1 = cal.getTime();
                cal.add(Calendar.DAY_OF_YEAR, +1);
                d2 = cal.getTime();
                cal.add(Calendar.DAY_OF_YEAR, +1);
                d3 = cal.getTime();

                String tmp1 = sdf2.format(d1);
                String tmp2 = sdf2.format(d2);
                String tmp3 = sdf2.format(d3);
                dates.add(tmp1);
                dates.add(tmp2);
                dates.add(tmp3);
            }
            else  if(dayOfTheWeek.equals("Friday")){
                d1 = cal.getTime();
                cal.add(Calendar.DAY_OF_YEAR, +1);
                d2 = cal.getTime();
                cal.add(Calendar.DAY_OF_YEAR, +1);
                d3 = cal.getTime();

                String tmp1 = sdf2.format(d1);
                String tmp2 = sdf2.format(d2);
                String tmp3 = sdf2.format(d3);

                dates.add(tmp1);
                dates.add(tmp2);
                dates.add(tmp3);
            }
            else  if(dayOfTheWeek.equals("Saturday")){
                d2 = cal.getTime();
                cal.add(Calendar.DAY_OF_YEAR, +1);
                d3 = cal.getTime();




                String tmp2 = sdf2.format(d2);
                String tmp3 = sdf2.format(d3);



                dates.add(tmp2);
                dates.add(tmp3);
            }
            else  if(dayOfTheWeek.equals("Sunday")){
                d3 = cal.getTime();


                String tmp3 = sdf2.format(d3);



                dates.add(tmp3);
            }




        }

        ArrayAdapter<String> adp1=new ArrayAdapter<String>(PlaceChooserAdmin.this,android.R.layout.simple_spinner_item,dates);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adp1);
    }

    
}
