package com.reservation.reservation;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
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
import java.util.Random;
import java.util.concurrent.Semaphore;

public class finalverification extends AppCompatActivity {

    private Session session;
    private String JSON_STRING, id, date, typeC, seats;
    private TextView nameLast, email, seatTxt, place;
    private Button resBtn;
    private EditText people;
    private int userId;
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
        setContentView(R.layout.activity_finalverification);

        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.activity_finalverification);

        animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(3500);

        session = new Session(this);

        userId = session.getUserId();

        Bundle bundle = getIntent().getExtras();

        id = bundle.getString("id");
        date = bundle.getString("date");
        typeC = bundle.getString("type");
        seats = bundle.getString("seats");
        //seat = bundle.getString("seat");

        people = (EditText)findViewById(R.id.peopleNum);
        nameLast = (TextView) findViewById(R.id.nameLastTxt);
        email = (TextView)findViewById(R.id.emailTxtF);
        seatTxt = (TextView)findViewById(R.id.seatTxt);
        place = (TextView)findViewById(R.id.placeTxt);
        resBtn = (Button)findViewById(R.id.rezBtn);

        resBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    getJSON3();
                //addReservation();
            }
        });

       // seatTxt.setText(seat);

        getJSON();
        getJSON2();

    }

    private synchronized void addReservation(){


         final String peoplee = people.getText().toString();


        class AddEmployee extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(finalverification.this,"","Се вчитува...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                /*if(s.equals("Error")){
                    Toast.makeText(getApplicationContext(),"Веќе е резервирана таа маса!", Toast.LENGTH_SHORT).show();
                }
                else {*/
                    startActivity(new Intent(finalverification.this, Successfull.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                //}
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                params.put("caffe_id",id);
                params.put("user_id",Integer.toString(userId));

                SimpleDateFormat sdf2 = new SimpleDateFormat("E MMM dd yyyy");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                Date newDate = null;
                try {
                    newDate = sdf2.parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                String newDate2 = sdf.format(newDate);

                params.put("date",newDate2);
                params.put("seat","0");
                params.put("type",typeC);
                params.put("people",peoplee);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.ADD_RESERVATION, params);
                return res;
            }
        }

        AddEmployee ae = new AddEmployee();
        ae.execute();
    }

    private void displayToast(String s){
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }


    private void showEmployee(){
        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);



                for(int i = 0; i<result.length(); i++){
                    JSONObject jo = result.getJSONObject(i);
                    String placeT = jo.getString("Name");
                    place.setText(placeT);
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

                loading = ProgressDialog.show(finalverification.this,"","Се вчитува...",false,false);


            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showEmployee();
            }

            @Override
            protected String doInBackground(Void... params) {

                RequestHandler rh = new RequestHandler();

                String s = rh.sendGetRequestParam(Config.GET_CAFFE,id);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    private void showEmployee2(){
        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);



            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String name = jo.getString("name");
                String lastName = jo.getString("lastName");
                String emaill = jo.getString("mobile");
                nameLast.setText(name + " " + lastName);
                email.setText(emaill);
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

                loading = ProgressDialog.show(finalverification.this,"","Се вчитува...",false,false);


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

                RequestHandler rh = new RequestHandler();

                String s = rh.sendGetRequestParam(Config.GET_USER2,Integer.toString(userId));
                return s;
            }
        }
        GetJSON2 gj = new GetJSON2();
        gj.execute();
    }

    private synchronized void showSeats3(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

            if(result.length() <= Integer.parseInt(seats)){
                String p = people.getText().toString();
                if(p.equals("") || Integer.parseInt(p) < 2){
                    Toast.makeText(getApplicationContext(),"Масите се за најмалку две особи!", Toast.LENGTH_SHORT).show();
                }
                else if(Integer.parseInt(p) > 20){
                    Toast.makeText(getApplicationContext(),"Масите се за најмногу 20 особи!", Toast.LENGTH_SHORT).show();
                }
                else{
                    addReservation();
                }


            }
            else{
                Toast.makeText(getApplicationContext(),"Сите маси се резервирани!", Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private synchronized void getJSON3(){
        class GetJSON3 extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(finalverification.this,"","Се вчитува...",false,false);
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

                map.put("caffe_id",id);
                map.put("date",newDate2);
               // map.put("seat","0");


                String s = rh.sendPostRequest(Config.GET_SEATS2,map);
                return s;
            }
        }
        GetJSON3 gj = new GetJSON3();
        gj.execute();
    }


}
