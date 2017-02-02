package com.reservation.reservation;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.telephony.SmsManager;

import java.util.HashMap;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class usersignup extends AppCompatActivity {

    private Button reg;
    private EditText nameTxt, lastNameTxt, emailTxt, phoneTxt, passTxt;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;
    private Session session;
    private int flagEmail = 0, flagPhone = 0;
    private String JSON_STRING;
    private String codeG,phoneG;
    private TextView loginTxtV;
    private ConnectionDetector cd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usersignup);

        cd = new ConnectionDetector(this);
        session = new Session(this);

        reg = (Button)findViewById(R.id.regBtn);
        nameTxt = (EditText)findViewById(R.id.nameTxt);
        loginTxtV = (TextView)findViewById(R.id.logTxtView);
        lastNameTxt = (EditText)findViewById(R.id.lastNameTxt);
       // emailTxt = (EditText)findViewById(R.id.emailTxtR);
        phoneTxt = (EditText)findViewById(R.id.phoneTxtR);
        passTxt = (EditText)findViewById(R.id.passTxtR);



        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cd.isConnected()){
                    register();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Нема интернет конекција",Toast.LENGTH_SHORT).show();
                }

            }
        });

          loginTxtV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(usersignup.this,usersignin.class));
            }
        });

    }


    private void register(){

        String name = nameTxt.getText().toString();
        String lastName = lastNameTxt.getText().toString();
        String email = "s";
        String phone = phoneTxt.getText().toString();
        String pass = passTxt.getText().toString();

        if(name.isEmpty() || lastName.isEmpty() || email.isEmpty() || phone.isEmpty() || pass.isEmpty()){
            displayToast("Мора да ги внесете сите податоци!");
        }
        else{

            phoneG = phone;

           // getJSON(email);
            getJSON2(phone, email);



        }

    }

    private void addUser(){

        final String name = nameTxt.getText().toString();
        final String lastName = lastNameTxt.getText().toString();
        final String email = "s";
        final String phone = phoneTxt.getText().toString();
        final String pass = passTxt.getText().toString();

        phoneG = phone;


        if(!checkPhone(phone)){
            displayToast("Невалиден формат на број!");
        }
        else {


            int min = 100000, max = 999999;

            Random rand = new Random();
            final int n = rand.nextInt((max - min + 1) + min);

            class AddEmployee extends AsyncTask<Void, Void, String> {

                ProgressDialog loading;

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    loading = ProgressDialog.show(usersignup.this, "", "Се вчитува...", false, false);
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    loading.dismiss();

                    codeG = Integer.toString(n);

                    //sendEmail(email, n);
                    sendSMSMessage();


                    Intent i = new Intent(usersignup.this, verify.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                    Bundle bundle = new Bundle();
                    bundle.putString("code", Integer.toString(n));
                    i.putExtras(bundle);

                    Bundle bundle2 = new Bundle();
                    bundle2.putString("email", phone);
                    i.putExtras(bundle2);

                    startActivity(i);

                }

                @Override
                protected String doInBackground(Void... v) {
                    HashMap<String, String> params = new HashMap<>();
                    params.put("name", name);
                    params.put("lastName", lastName);
                    params.put("email", email);
                    params.put("mobile", phone);
                    params.put("code", Integer.toString(n));
                    params.put("password", pass);

                    RequestHandler rh = new RequestHandler();
                    String res = rh.sendPostRequest(Config.ADD_USER, params);
                    return res;
                }
            }

            AddEmployee ae = new AddEmployee();
            ae.execute();
        //}
        }
    }

    private boolean checkPhone(String phone){

        boolean isValid = false;
        String expression = "^07[0,1,2,5,6,7,8][0-9]{6}$";
        CharSequence inputStr = phone;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;

    }

    private boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
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

        if(flagPhone == 1){
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
                    smsManager.sendTextMessage(phoneG, null, codeG, null, null);
                    Toast.makeText(getApplicationContext(), "SMS sent.",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "SMS faild, please try again.", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }}


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
