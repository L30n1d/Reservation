package com.reservation.reservation;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
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
import java.util.Timer;
import java.util.TimerTask;

public class PlaceChooserAdmin extends AppCompatActivity {

    private Spinner spinner2;
    private String JSON_STRING, caffeId, layout, days, date;
    private int userId;
    private ListView listView;
    private ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
    private Session session;
    private Button btn,logOut;
    private int num = 0, notificationId = 0x10;
    private final Handler handler = new Handler();
    private Timer timer = new Timer();

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
                timer.cancel();
                timer.purge();
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

                Intent i = new Intent(PlaceChooserAdmin.this, listView.class);

                Bundle bundle = new Bundle();
                bundle.putString("id", caffeId);
                i.putExtras(bundle);

                Bundle bundle2 = new Bundle();
                bundle2.putString("date", date);
                i.putExtras(bundle2);


                startActivity(i);
              /*  try {
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
                }*/



            }
        });

        populate();
        callTimer();
        
    }

    @Override
    public void onBackPressed() {
        timer.cancel();
        timer.purge();
        session.setLoggedIn(false, "");
        session.setUserRole("");
        finish();
        startActivity(new Intent(PlaceChooserAdmin.this, usersignin.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
    }

    private void callTimer(){

        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            getJSON3();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, 3000);

    }

    private synchronized void showSeats3(){
        JSONObject jsonObject = null;
        String datee = "";

        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

            list.clear();

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String id = jo.getString(Config.TAG_ID);
                String seatt = jo.getString("Seat");
                String people = jo.getString("People");
                datee = jo.getString("Date");
                //  userId = jo.getString("UserId");
                String name = jo.getString("Name");
                if(seatt.equals("0")){
                    //  getJSON2(id);
                    HashMap<String,String> employees = new HashMap<>();
                    employees.put("id",id);
                    employees.put("name",name);
                    employees.put("people",people);
                    employees.put("date",datee);
                    list.add(employees);
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(list.size() > num){
            getnotification(spinner2.getRootView(),datee);
        }
        num = list.size();


    }

    private synchronized void getJSON3(){
        class GetJSON3 extends AsyncTask<Void,Void,String> {

            //  ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //loading = ProgressDialog.show(listView.this,"","Се вчитува...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //   loading.dismiss();
                JSON_STRING = s;
                showSeats3();

            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();

                HashMap<String,String> map = new HashMap<>();

                map.put("caffe_id",caffeId);

                String s = rh.sendPostRequest(Config.GET_SEATS3,map);
                return s;
            }
        }
        GetJSON3 gj = new GetJSON3();
        gj.execute();
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


    public void getnotification(View view, String date){

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationManager notificationmgr = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        Intent intent = new Intent(this, PlaceChooserAdmin.class);



        Bundle bundle = new Bundle();
        bundle.putString("caffe_id", caffeId);
        intent.putExtras(bundle);

        Bundle bundle2 = new Bundle();
        bundle2.putString("days", days);
        intent.putExtras(bundle2);

        Bundle bundle3 = new Bundle();
        bundle3.putString("layout", layout);
        intent.putExtras(bundle3);

        PendingIntent pintent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);

        //   PendingIntent pintent = PendingIntent.getActivities(this,(int)System.currentTimeMillis(),intent, 0);


        Notification notif = new Notification.Builder(this)
                .setSmallIcon(R.drawable.logo192)
                .setContentTitle("Имате резервација")
                .setContentText(date)
                .setContentIntent(pintent)
                .setSound(alarmSound)
                .build();

        notif.flags |= Notification.FLAG_AUTO_CANCEL;
        notificationmgr.notify(notificationId++,notif);

    }

    
}
