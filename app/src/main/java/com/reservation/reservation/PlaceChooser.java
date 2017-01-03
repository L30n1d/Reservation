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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

public class PlaceChooser extends AppCompatActivity {


    private Spinner spinner1, spinner2;
    private String JSON_STRING, caffeId, date, layout;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_chooser);

        layout = "com.reservation.reservation.TestMap";

        spinner1 = (Spinner)findViewById(R.id.spinner);
        spinner2 = (Spinner)findViewById(R.id.spinner2);
        btn = (Button)findViewById(R.id.button3);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date = spinner2.getSelectedItem().toString();

                getEmployee();

                try {
                    Class c = Class.forName(layout);
                    Intent i = new Intent(PlaceChooser.this, c);

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

        getJSON();

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


                HashMap<String,String> employees = new HashMap<>();
                employees.put(Config.TAG_ID,id);
                employees.put(Config.TAG_NAME,name);
                employees.put("Days",days);
                list.add(employees);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        SimpleAdapter adapter = new SimpleAdapter(
                PlaceChooser.this, list, android.R.layout.simple_spinner_item,
                new String[]{Config.TAG_NAME},
                new int[]{android.R.id.text1});



         spinner1.setAdapter(adapter);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String,String> map =(HashMap)parent.getItemAtPosition(position);
                caffeId = map.get("Id").toString();
                String days = map.get("Days").toString();


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

                    dates.add(sdf.format(d1).toString());
                    dates.add(sdf.format(d2).toString());
                    dates.add(sdf.format(d3).toString());
                    dates.add(sdf.format(d4).toString());
                    dates.add(sdf.format(d5).toString());
                    dates.add(sdf.format(d6).toString());
                    dates.add(sdf.format(d7).toString());


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

                        dates.add(sdf2.format(d1).toString());
                        dates.add(sdf2.format(d2).toString());
                        dates.add(sdf2.format(d3).toString());
                    }
                    else  if(dayOfTheWeek.equals("Tuesday")){
                        cal.add(Calendar.DAY_OF_YEAR, +3);
                        d1 = cal.getTime();
                        cal.add(Calendar.DAY_OF_YEAR, +1);
                        d2 = cal.getTime();
                        cal.add(Calendar.DAY_OF_YEAR, +1);
                        d3 = cal.getTime();

                        dates.add(sdf2.format(d1).toString());
                        dates.add(sdf2.format(d2).toString());
                        dates.add(sdf2.format(d3).toString());
                    }
                    else  if(dayOfTheWeek.equals("Wednesday")){
                        cal.add(Calendar.DAY_OF_YEAR, +2);
                        d1 = cal.getTime();
                        cal.add(Calendar.DAY_OF_YEAR, +1);
                        d2 = cal.getTime();
                        cal.add(Calendar.DAY_OF_YEAR, +1);
                        d3 = cal.getTime();

                        dates.add(sdf2.format(d1).toString());
                        dates.add(sdf2.format(d2).toString());
                        dates.add(sdf2.format(d3).toString());
                    }
                    else  if(dayOfTheWeek.equals("Thursday")){
                        cal.add(Calendar.DAY_OF_YEAR, +1);
                        d1 = cal.getTime();
                        cal.add(Calendar.DAY_OF_YEAR, +1);
                        d2 = cal.getTime();
                        cal.add(Calendar.DAY_OF_YEAR, +1);
                        d3 = cal.getTime();

                        dates.add(sdf2.format(d1).toString());
                        dates.add(sdf2.format(d2).toString());
                        dates.add(sdf2.format(d3).toString());
                    }
                    else  if(dayOfTheWeek.equals("Friday")){
                        d1 = cal.getTime();
                        cal.add(Calendar.DAY_OF_YEAR, +1);
                        d2 = cal.getTime();
                        cal.add(Calendar.DAY_OF_YEAR, +1);
                        d3 = cal.getTime();

                        dates.add(sdf2.format(d1).toString());
                        dates.add(sdf2.format(d2).toString());
                        dates.add(sdf2.format(d3).toString());
                    }
                    else  if(dayOfTheWeek.equals("Saturday")){
                        d2 = cal.getTime();
                        cal.add(Calendar.DAY_OF_YEAR, +1);
                        d3 = cal.getTime();



                        dates.add(sdf2.format(d2).toString());
                        dates.add(sdf2.format(d3).toString());
                    }
                    else  if(dayOfTheWeek.equals("Sunday")){
                        d3 = cal.getTime();


                        dates.add(sdf2.format(d3).toString());
                    }




                }

                ArrayAdapter<String> adp1=new ArrayAdapter<String>(PlaceChooser.this,android.R.layout.simple_spinner_item,dates);

                spinner2.setAdapter(adp1);

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

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
