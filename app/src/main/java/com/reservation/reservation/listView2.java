package com.reservation.reservation;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class listView2 extends AppCompatActivity {

    private ListView listView;
    private String id2,date, JSON_STRING, userId;
    private ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view2);

        Bundle bundle = getIntent().getExtras();

        id2 = bundle.getString("id");
        date = bundle.getString("date");

        listView = (ListView)findViewById(R.id.listView2);

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
                //  userId = jo.getString("UserId");
                String name = jo.getString("Name");
                if(!seatt.equals("0")){
                    //  getJSON2(id);
                    HashMap<String,String> employees = new HashMap<>();
                    employees.put("id",id);
                    employees.put("name",name);
                    employees.put("seat",seatt);
                    list.add(employees);
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        ListAdapter adapter = new SimpleAdapter(
                listView2.this, list, R.layout.listitem,
                new String[]{"name","seat"},
                new int[]{R.id.textView8,R.id.textView9});



        listView.setAdapter(adapter);
    }

    private synchronized void getJSON3(){
        class GetJSON3 extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(listView2.this,"","Се вчитува...",false,false);
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



}
