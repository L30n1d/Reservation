package com.reservation.reservation;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class listView extends AppCompatActivity {

    private ListView listView;
    private String id2,date, JSON_STRING, userId;
    private ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        Bundle bundle = getIntent().getExtras();

        id2 = bundle.getString("id");
        date = bundle.getString("date");

        btn = (Button) findViewById(R.id.button2);
        listView = (ListView)findViewById(R.id.listView);

        /*btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(listView.this, listView2.class);

                Bundle bundle = new Bundle();
                bundle.putString("id", id2);
                i.putExtras(bundle);

                Bundle bundle3 = new Bundle();
                bundle3.putString("date", date);
                i.putExtras(bundle3);

                startActivity(i);
            }
        });*/

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                /*HashMap<String,String> map =(HashMap)parent.getItemAtPosition(position);
                String idd = map.get("id").toString();

                Intent i = new Intent(listView.this, GridViewSupplementActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString("id", idd);
                i.putExtras(bundle);

                Bundle bundle2 = new Bundle();
                bundle2.putString("caffeId", id2);
                i.putExtras(bundle2);

                Bundle bundle3 = new Bundle();
                bundle3.putString("date", date);
                i.putExtras(bundle3);

                startActivity(i);*/

            }
        });

        getJSON3();

    }

    private synchronized void showSeats3(){
        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String id = jo.getString(Config.TAG_ID);
                String seatt = jo.getString("Seat");
                String mobile = jo.getString("Mobile");
                String people = jo.getString("People");
              //  userId = jo.getString("UserId");
                String name = jo.getString("Name");
                if(seatt.equals("0")){
                 //  getJSON2(id);
                    HashMap<String,String> employees = new HashMap<>();
                    employees.put("id",id);
                    employees.put("name",name);
                    employees.put("mobile",mobile);
                    employees.put("people",people);
                    list.add(employees);
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


      ListAdapter adapter = new SimpleAdapter(
                listView.this, list, R.layout.listitem,
                new String[]{"name","mobile","people"}, new int[]{R.id.textView8,R.id.textView9,R.id.textView10});





        listView.setAdapter(adapter);

    }

    private synchronized void getJSON3(){
        class GetJSON3 extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(listView.this,"","Се вчитува...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showSeats3();

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

                map.put("caffe_id",id2);
                map.put("date",newDate2);



                String s = rh.sendPostRequest(Config.GET_SEATS2,map);
                return s;
            }
        }
        GetJSON3 gj = new GetJSON3();
        gj.execute();
    }


    private void showEmployee2(String id){
        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);



            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String name = jo.getString("name");
                String lastName = jo.getString("lastName");
                String mobile = jo.getString("mobile");

                HashMap<String,String> employees = new HashMap<>();
                employees.put("id",id);
                employees.put("name",name);
                employees.put("lastName",lastName);
                employees.put("mobile",mobile);
                list.add(employees);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void getJSON2(final String id){
        class GetJSON2 extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                loading = ProgressDialog.show(listView.this,"","Се вчитува...",false,false);


            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showEmployee2(id);



            }

            @Override
            protected String doInBackground(Void... params) {

                RequestHandler rh = new RequestHandler();

                String s = rh.sendGetRequestParam(Config.GET_USER2,userId);
                return s;
            }
        }
        GetJSON2 gj = new GetJSON2();
        gj.execute();
    }

    public void getnotification(View view){

        NotificationManager notificationmgr = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        Intent intent = new Intent(this, resultpage.class);
        PendingIntent pintent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);

        //   PendingIntent pintent = PendingIntent.getActivities(this,(int)System.currentTimeMillis(),intent, 0);


        Notification notif = new Notification.Builder(this)
                .setSmallIcon(R.drawable.busy32)
                .setContentTitle("Hello Android Hari")
                .setContentText("Welcome to Notification Service")
                .setContentIntent(pintent)
                .build();


        notificationmgr.notify(0,notif);





    }

}
