package com.reservation.reservation;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
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
import java.util.Timer;
import java.util.TimerTask;

public class PlaceChooser extends AppCompatActivity {


    private Spinner spinner1, spinner2;
    private String JSON_STRING, caffeId, date, layout, typeC, seats;
    private int userId;
    private Session session;
    private ArrayList<String> dates1;
    private ArrayList<String> dates2;
    private Button btn, logOut;
    private TextView conTxt;
    private ConnectionDetector cd;
    private final Handler handler = new Handler();
    private Timer timer = new Timer();
    private AnimationDrawable animationDrawable;

    @Override
    protected void onResume() {
        super.onResume();
        if (animationDrawable != null && !animationDrawable.isRunning())
            animationDrawable.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (animationDrawable != null && animationDrawable.isRunning())
            animationDrawable.stop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_chooser);

        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.activity_place_chooser);

        animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(3500);


        dates1 = new ArrayList();
        dates2 = new ArrayList();

        session = new Session(this);

        userId = session.getUserId();

        layout = "com.reservation.reservation.TestMap";

        cd = new ConnectionDetector(this);
        spinner1 = (Spinner)findViewById(R.id.spinner);
        spinner2 = (Spinner)findViewById(R.id.spinner2);
        btn = (Button)findViewById(R.id.button3);
        conTxt = (TextView)findViewById(R.id.connTxt);
        logOut = (Button)findViewById(R.id.logOut);

        conTxt.setVisibility(View.INVISIBLE);

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.setLoggedIn(false, "");
                session.setUserRole("");
                finish();
                startActivity(new Intent(PlaceChooser.this, usersignin.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(spinner2.getSelectedItem() == null){

                    Toast.makeText(getApplicationContext(),"Изберете друго место", Toast.LENGTH_SHORT).show();
                }
                else {
                    date = spinner2.getSelectedItem().toString();

                    Intent i = new Intent(PlaceChooser.this, finalverification.class);

                    Bundle bundle = new Bundle();
                    bundle.putString("id", caffeId);
                    i.putExtras(bundle);

                    Bundle bundle2 = new Bundle();
                    bundle2.putString("date", date);
                    i.putExtras(bundle2);

                    Bundle bundle3 = new Bundle();
                    bundle3.putString("type", typeC);
                    i.putExtras(bundle3);

                    Bundle bundle4 = new Bundle();
                    bundle4.putString("seats", seats);
                    i.putExtras(bundle4);

                    startActivity(i);
                   /* try {
                        Class c = Class.forName(layout);
                        Intent i = new Intent(PlaceChooser.this, finalverification.class);

                        Bundle bundle = new Bundle();
                        bundle.putString("id", caffeId);
                        i.putExtras(bundle);

                        Bundle bundle2 = new Bundle();
                        bundle2.putString("date", date);
                        i.putExtras(bundle2);

                        Bundle bundle3 = new Bundle();
                        bundle3.putString("type", typeC);
                        i.putExtras(bundle3);

                        startActivity(i);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }*/

                }

            }
        });

       // checkConnection();

        callTimer();

       // getJSON();




    }

    private void callTimer(){

        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            checkConnection();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, 3000);

    }

    private void checkConnection(){



        if(cd.isConnected() == true){
            conTxt.setVisibility(View.INVISIBLE);
            timer.cancel();
            getJSON();
        }
        else{
            conTxt.setText("Нема интернет конекција");
            conTxt.setVisibility(View.VISIBLE);
        }
    }


    private void showCaffe(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String id = jo.getString(Config.TAG_ID);
                String name = jo.getString(Config.TAG_NAME);
                String days = jo.getString("Days");
                String type = jo.getString("TypeC");


                HashMap<String,String> employees = new HashMap<>();
                employees.put(Config.TAG_ID,id);
                employees.put(Config.TAG_NAME,name);
                employees.put("Days",days);
                employees.put("TypeC",type);
                list.add(employees);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        SimpleAdapter adapter = new SimpleAdapter(
                PlaceChooser.this, list, android.R.layout.simple_spinner_item,
                new String[]{Config.TAG_NAME},
                new int[]{android.R.id.text1});
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);



         spinner1.setAdapter(adapter);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String,String> map =(HashMap)parent.getItemAtPosition(position);
                caffeId = map.get("Id").toString();
                typeC = map.get("TypeC").toString();
                String days = map.get("Days").toString();

                dates1.clear();
                dates2.clear();

                getEmployee();
                getJSON2();
                getJSON3(days);



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(PlaceChooser.this,"","Се вчитува...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showCaffe();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(Config.GET_ALLCAFFES);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }


    private void getEmployee(){
        class GetEmployee extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(PlaceChooser.this,"","Се вчитува...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                showEmployee(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(Config.GET_CAFFE,caffeId);
                return s;
            }
        }
        GetEmployee ge = new GetEmployee();
        ge.execute();
    }

    private void showEmployee(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);
            layout = c.getString("Layout");
            seats = c.getString("Seats");


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void showEmployee2(){
        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);



            if(result.length() < 1){
             //   flag = 0;
              //  Toast.makeText(getApplicationContext(),"Невалиден број/лозинка", Toast.LENGTH_SHORT).show();
            }
            else{
                for(int i = 0; i<result.length(); i++){
                    JSONObject jo = result.getJSONObject(i);
                    String d = jo.getString("Date");

                    SimpleDateFormat sdf2 = new SimpleDateFormat("E MMM dd yyyy");
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                    Date newDate = null;
                    try {
                        newDate = sdf.parse(d);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    String newDate2 = sdf2.format(newDate);

                    dates1.add(newDate2);

                }



            }



        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void getJSON2(){
        class GetJSON2 extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                loading = ProgressDialog.show(PlaceChooser.this,"","Се вчитува...",false,false);


            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showEmployee2();
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("user_id",Integer.toString(userId));
                hashMap.put("caffe_id",caffeId);

                RequestHandler rh = new RequestHandler();

                String s = rh.sendPostRequest(Config.CHECK_DATES,hashMap);
                return s;
            }
        }
        GetJSON2 gj = new GetJSON2();
        gj.execute();
    }

    private void showEmployee3(String days){
        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

            if(result.length() < 1){
                //   flag = 0;
                //Toast.makeText(getApplicationContext(),"Невалиден број/лозинка", Toast.LENGTH_SHORT).show();
            }
            else{
                for(int i = 0; i<result.length(); i++){
                    JSONObject jo = result.getJSONObject(i);
                    String d = jo.getString("Date");

                    SimpleDateFormat sdf2 = new SimpleDateFormat("E MMM dd yyyy");
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                    Date newDate = null;
                    try {
                        newDate = sdf.parse(d);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    String newDate2 = sdf2.format(newDate);

                    dates2.add(newDate2);

                }



            }



        } catch (JSONException e) {
            e.printStackTrace();
        }


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

            if(!dates1.contains(tmp1) && !dates2.contains(tmp1))
            {
                dates.add(tmp1);
            }


            if(!dates1.contains(tmp2) && !dates2.contains(tmp2))
            {
                dates.add(tmp2);
            }

            if(!dates1.contains(tmp3) && !dates2.contains(tmp3))
            {
                dates.add(tmp3);
            }

            if(!dates1.contains(tmp4) && !dates2.contains(tmp4))
            {
                dates.add(tmp4);
            }

            if(!dates1.contains(tmp5) && !dates2.contains(tmp5))
            {
                dates.add(tmp5);
            }

            if(!dates1.contains(tmp6) && !dates2.contains(tmp6))
            {
                dates.add(tmp6);
            }

            if(!dates1.contains(tmp7) && !dates2.contains(tmp7))
            {
                dates.add(tmp7);
            }


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

                if(!dates1.contains(tmp1) && !dates2.contains(tmp1))
                {
                    dates.add(tmp1);
                }


                if(!dates1.contains(tmp2) && !dates2.contains(tmp2))
                {
                    dates.add(tmp2);
                }

                if(!dates1.contains(tmp3) && !dates2.contains(tmp3))
                {
                    dates.add(tmp3);
                }
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

                if(!dates1.contains(tmp1) && !dates2.contains(tmp1))
                {
                    dates.add(tmp1);
                }


                if(!dates1.contains(tmp2) && !dates2.contains(tmp2))
                {
                    dates.add(tmp2);
                }

                if(!dates1.contains(tmp3) && !dates2.contains(tmp3))
                {
                    dates.add(tmp3);
                }
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

                if(!dates1.contains(tmp1) && !dates2.contains(tmp1))
                {
                    dates.add(tmp1);
                }


                if(!dates1.contains(tmp2) && !dates2.contains(tmp2))
                {
                    dates.add(tmp2);
                }

                if(!dates1.contains(tmp3) && !dates2.contains(tmp3))
                {
                    dates.add(tmp3);
                }
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

                if(!dates1.contains(tmp1) && !dates2.contains(tmp1))
                {
                    dates.add(tmp1);
                }


                if(!dates1.contains(tmp2) && !dates2.contains(tmp2))
                {
                    dates.add(tmp2);
                }

                if(!dates1.contains(tmp3) && !dates2.contains(tmp3))
                {
                    dates.add(tmp3);
                }
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

                if(!dates1.contains(tmp1) && !dates2.contains(tmp1))
                {
                    dates.add(tmp1);
                }


                if(!dates1.contains(tmp2) && !dates2.contains(tmp2))
                {
                    dates.add(tmp2);
                }

                if(!dates1.contains(tmp3) && !dates2.contains(tmp3))
                {
                    dates.add(tmp3);
                }
            }
            else  if(dayOfTheWeek.equals("Saturday")){
                d2 = cal.getTime();
                cal.add(Calendar.DAY_OF_YEAR, +1);
                d3 = cal.getTime();




                String tmp2 = sdf2.format(d2);
                String tmp3 = sdf2.format(d3);


                if(!dates1.contains(tmp2) && !dates2.contains(tmp2))
                {
                    dates.add(tmp2);
                }

                if(!dates1.contains(tmp3) && !dates2.contains(tmp3))
                {
                    dates.add(tmp3);
                }
            }
            else  if(dayOfTheWeek.equals("Sunday")){
                d3 = cal.getTime();


                String tmp3 = sdf2.format(d3);


                if(!dates1.contains(tmp3) && !dates2.contains(tmp3))
                {
                    dates.add(tmp3);
                }
            }




        }

        ArrayAdapter<String> adp1=new ArrayAdapter<String>(PlaceChooser.this,android.R.layout.simple_spinner_item,dates);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner2.setAdapter(adp1);


    }

    private void getJSON3(final String days){
        class GetJSON3 extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                loading = ProgressDialog.show(PlaceChooser.this,"","Се вчитува...",false,false);


            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showEmployee3(days);
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("user_id",Integer.toString(userId));
                hashMap.put("type",typeC);

                RequestHandler rh = new RequestHandler();

                String s = rh.sendPostRequest(Config.CHECK_DATES2,hashMap);
                return s;
            }
        }
        GetJSON3 gj = new GetJSON3();
        gj.execute();
    }


}
