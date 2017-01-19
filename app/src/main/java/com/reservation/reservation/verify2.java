package com.reservation.reservation;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class verify2 extends AppCompatActivity {

    private Button btn;
    private EditText pinTxt;
    private String JSON_STRING, code, emailB;
    private TextView resend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify2);

        btn = (Button)findViewById(R.id.confirmBtn2);
        pinTxt = (EditText)findViewById(R.id.pin2);

        resend = (TextView)findViewById(R.id.resendBtn2);

        Bundle bundle = getIntent().getExtras();

        code = bundle.getString("code");
        emailB = bundle.getString("email");

        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail(code,emailB);
                Toast.makeText(getApplicationContext(),"Кодот е испратен на вашиот емаил!", Toast.LENGTH_SHORT).show();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = pinTxt.getText().toString();
                getJSON(code);
            }
        });

    }

    private void sendEmail(String code, String emailB){

        Log.i("SendMailActivity", "Send Button Clicked.");

        String fromEmail = Config.EMAIL;
        String fromPassword = Config.PASS;
        String toEmail = emailB;
        String emailSubject = "Kod za verifikacija";
        String emailBody = "Vashiot kod e: " + code;
        new SendMailTask(verify2.this).execute(fromEmail,
                fromPassword, toEmail, emailSubject, emailBody);


    }


    private void showEmployee(){
        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

            if(result.length() < 1){
                Toast.makeText(getApplicationContext(),"Невалиден код", Toast.LENGTH_SHORT).show();
            }
            else{

                updateUser();

            }



        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void getJSON(final String code){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                loading = ProgressDialog.show(verify2.this,"","Се вчитува...",false,false);


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
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("code",code);

                RequestHandler rh = new RequestHandler();

                String s = rh.sendPostRequest(Config.GET_USER3,hashMap);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    private void updateUser(){

        class UpdateEmployee extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(verify2.this,"","Се вчитува...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                startActivity(new Intent(verify2.this, PlaceChooser.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("email",emailB);

                RequestHandler rh = new RequestHandler();

                String s = rh.sendPostRequest(Config.UPDATE_USER,hashMap);

                return s;
            }
        }

        UpdateEmployee ue = new UpdateEmployee();
        ue.execute();
    }

}