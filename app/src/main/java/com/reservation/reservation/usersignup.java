package com.reservation.reservation;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Random;

public class usersignup extends AppCompatActivity {

    private Button reg;
    private EditText nameTxt, lastNameTxt, emailTxt, phoneTxt, passTxt;
    private Session session;
    private int flagEmail = 0, flagPhone = 0;
    private String JSON_STRING;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usersignup);

        session = new Session(this);

        reg = (Button)findViewById(R.id.regBtn);
        nameTxt = (EditText)findViewById(R.id.nameTxt);
        lastNameTxt = (EditText)findViewById(R.id.lastNameTxt);
        emailTxt = (EditText)findViewById(R.id.emailTxtR);
        phoneTxt = (EditText)findViewById(R.id.phoneTxtR);
        passTxt = (EditText)findViewById(R.id.passTxtR);

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

    }


    private void register(){

        String name = nameTxt.getText().toString();
        String lastName = lastNameTxt.getText().toString();
        String email = emailTxt.getText().toString();
        String phone = phoneTxt.getText().toString();
        String pass = passTxt.getText().toString();

        if(name.isEmpty() || lastName.isEmpty() || email.isEmpty() || phone.isEmpty() || pass.isEmpty()){
            displayToast("Мора да ги внесете сите податоци!");
        }
        else{

            getJSON(email);
            getJSON2(phone, email);



        }

    }

    private void addUser(){

        final String name = nameTxt.getText().toString();
        final String lastName = lastNameTxt.getText().toString();
        final String email = emailTxt.getText().toString();
        final String phone = phoneTxt.getText().toString();
        final String pass = passTxt.getText().toString();

        int min = 100000, max = 999999;

        Random rand = new Random();
        final int n = rand.nextInt((max-min+1)+min);

        class AddEmployee extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(usersignup.this,"","Се вчитува...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();

                sendEmail(email, n);

                Intent i = new Intent(usersignup.this, verify.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                Bundle bundle = new Bundle();
                bundle.putString("code", Integer.toString(n));
                i.putExtras(bundle);

                Bundle bundle2 = new Bundle();
                bundle2.putString("email", email);
                i.putExtras(bundle2);

                startActivity(i);

            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                params.put("name",name);
                params.put("lastName",lastName);
                params.put("email",email);
                params.put("mobile",phone);
                params.put("code",Integer.toString(n));
                params.put("password",pass);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.ADD_USER, params);
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

            if(result.length() < 1){
                flagEmail = 1;

            }
            else{
                flagEmail = 0;
                displayToast("Постои таков емаил веќе!");
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void getJSON(final String email){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                loading = ProgressDialog.show(usersignup.this,"","Се вчитува...",false,false);


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
                hashMap.put("email",email);

                RequestHandler rh = new RequestHandler();

                String s = rh.sendPostRequest(Config.CHECK_USER_EMAIL,hashMap);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    private void showEmployee2(String email){
        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

            if(result.length() < 1){
                flagPhone = 1;

            }
            else{
                flagPhone = 0;
                displayToast("Постои веќе таков телефонски број!");
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(flagEmail == 1 && flagPhone == 1){
            addUser();
        }


    }

    private void getJSON2(final String phone, final String email){
        class GetJSON2 extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                  loading = ProgressDialog.show(usersignup.this,"","Се вчитува...",false,false);


            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showEmployee2(email);
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("phone",phone);

                RequestHandler rh = new RequestHandler();

                String s = rh.sendPostRequest(Config.CHECK_USER_PHONE,hashMap);
                return s;
            }
        }
        GetJSON2 gj = new GetJSON2();
        gj.execute();
    }

    private void sendEmail(String email, int code){

        Log.i("SendMailActivity", "Send Button Clicked.");

        String fromEmail = Config.EMAIL;
        String fromPassword = Config.PASS;
        String toEmail = email;
        String emailSubject = "Kod za verifikacija";
        String emailBody = "Vashiot kod e: " + code;
        new SendMailTask(usersignup.this).execute(fromEmail,
                fromPassword, toEmail, emailSubject, emailBody);


    }

}
