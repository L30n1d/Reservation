package com.reservation.reservation;


import android.Manifest;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
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
import java.util.Timer;
import java.util.TimerTask;

public class listView extends AppCompatActivity {

    private ListView listView;
    private String id2,date, JSON_STRING, userId,mobile,selectedFromList;
    private ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
    private HashMap<String,String> listt;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;
    private Button btn;
    private final Handler handler = new Handler();
    private Timer timer = new Timer();
    private int num = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        Bundle bundle = getIntent().getExtras();

        id2 = bundle.getString("id");
        date = bundle.getString("date");

        btn = (Button) findViewById(R.id.button2);
        listView = (ListView)findViewById(R.id.listView);

        btn.setOnClickListener(new View.OnClickListener() {
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
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, View view, final int position, long id) {

                listt = (HashMap)(listView.getItemAtPosition(position));



                final AlertDialog.Builder builderSingle = new AlertDialog.Builder(listView.this);
                builderSingle.setIcon(R.drawable.logo72);
                builderSingle.setTitle("Избери");

                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(listView.this, android.R.layout.simple_selectable_list_item);
                arrayAdapter.add("Додели маса");
                arrayAdapter.add("Прати порака");
                arrayAdapter.add("Јави се");
                arrayAdapter.add("Избриши резервација");


                builderSingle.setNegativeButton("Откажи", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String strName = arrayAdapter.getItem(which);

                        if(strName.equals("Додели маса")){
                            HashMap<String,String> map =(HashMap)parent.getItemAtPosition(position);
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

                            Bundle bundle4 = new Bundle();
                            bundle4.putString("mobile", mobile);
                            i.putExtras(bundle4);

                            startActivity(i);
                        }
                        else if(strName.equals("Прати порака")) {
                            sendSMS();
                        }
                        else if(strName.equals("Јави се")) {
                            call();
                        }
                        else if(strName.equals("Избриши резервација")){
                            deleteRes();
                        }

                    }
                });
                builderSingle.show();

            }
        });

        getJSON3();
       // callTimer();

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

    private void deleteRes(){

        class UpdateEmployee extends AsyncTask<Void,Void,String>{
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
                getJSON3();
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("id",listt.get("id").toString());

                RequestHandler rh = new RequestHandler();

                String s = rh.sendPostRequest(Config.DELETE_RES,hashMap);

                return s;
            }
        }

        UpdateEmployee ue = new UpdateEmployee();
        ue.execute();
    }

    private synchronized void showSeats3(){
        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);



            list.clear();

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String id = jo.getString(Config.TAG_ID);
                String seatt = jo.getString("Seat");
                mobile = jo.getString("Mobile");
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

    protected void sendSMS() {
        Log.i("Send SMS", "");
        Intent smsIntent = new Intent(Intent.ACTION_VIEW);

        smsIntent.setData(Uri.parse("smsto:"));
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.putExtra("address"  , new String (mobile));
        smsIntent.putExtra("sms_body"  , "Вашата резервација неможе да биде извршена, бидејќи сите маси за над " + listt.get("people").toString() + " особи се веќе резервирани.");

        try {
            startActivity(smsIntent);
            finish();
            Log.i("Finished sending SMS...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(listView.this,
                    "SMS faild, please try again later.", Toast.LENGTH_SHORT).show();
        }
    }

    private void call(){
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:"+mobile));

        if (ActivityCompat.checkSelfPermission(listView.this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startActivity(callIntent);
    }

    protected void sendSMSMessage() {



        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.SEND_SMS},
                MY_PERMISSIONS_REQUEST_SEND_SMS);
    }



    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(mobile, null, "Успешно направивте резервација", null, null);
                    Toast.makeText(getApplicationContext(), "SMS sent.",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "SMS faild, please try again.", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }}




}
