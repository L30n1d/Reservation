package com.reservation.reservation;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class forget_pass extends AppCompatActivity {

    private Button btn;
    private String JSON_STRING,password,emailTxt,phoneG,passG;
    private EditText email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);


        btn = (Button)findViewById(R.id.checkEmailBtn);
        email = (EditText)findViewById(R.id.emailEditTxt);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                phoneG = email.getText().toString();

                getJSON();

            }
        });

    }

    private void showEmployee(){
        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);



            if(result.length() < 1){

                displayToast("Не постои таков телефонски број");

            }
            else{
                for(int i = 0; i<result.length(); i++){
                    JSONObject jo = result.getJSONObject(i);
                    password = jo.getString("password");
                }
                passG = password;
                sendSMSMessage();
               // sendEmail(emailTxt,password);

                displayToast("Вашата лозинка е испратена на вашиот телефонски број");

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

                loading = ProgressDialog.show(forget_pass.this,"","Се вчитува...",false,false);


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


                hashMap.put("email",emailTxt);

                RequestHandler rh = new RequestHandler();

                String s = rh.sendPostRequest(Config.CHECK_USER_EMAIL,hashMap);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    private void sendEmail(String email, String pass){

        Log.i("SendMailActivity", "Send Button Clicked.");

        String fromEmail = Config.EMAIL;
        String fromPassword = Config.PASS;
        String toEmail = email;
        String emailSubject = "Kod za verifikacija";
        String emailBody = "Vashata lozinka e: " + pass;
        new SendMailTask(forget_pass.this).execute(fromEmail,
                fromPassword, toEmail, emailSubject, emailBody);


    }

    protected void sendSMSMessage() {

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneG, null, passG, null, null);
        Toast.makeText(getApplicationContext(), "SMS sent.",
                Toast.LENGTH_LONG).show();
     /*   ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.SEND_SMS},
                MY_PERMISSIONS_REQUEST_SEND_SMS);*/
    }



   /* @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneG, null, passG, null, null);
                    Toast.makeText(getApplicationContext(), "SMS sent.",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "SMS faild, please try again.", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }}*/

    private void displayToast(String s){
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }

}
