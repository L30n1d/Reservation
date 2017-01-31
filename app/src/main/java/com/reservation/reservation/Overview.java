package com.reservation.reservation;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class Overview extends AppCompatActivity {

    private String id,JSON_STRING, userId;
    private TextView nameTxt,numTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        Bundle bundle = getIntent().getExtras();

        id = bundle.getString("id");

        nameTxt = (TextView)findViewById(R.id.haha1);
        numTxt = (TextView)findViewById(R.id.haha2);

        getJSON();

    }


    private void showEmployee(){
        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);



            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                userId = jo.getString("UserId");
            }





        } catch (JSONException e) {
            e.printStackTrace();
        }

        getJSON2();


    }

    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                loading = ProgressDialog.show(Overview.this,"","Се вчитува...",false,false);


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

                String s = rh.sendGetRequestParam(Config.GET_RESERVATION,id);
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
                String mobile = jo.getString("mobile");

                nameTxt.setText(name + " " + lastName);
                numTxt.setText(mobile);
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

                loading = ProgressDialog.show(Overview.this,"","Се вчитува...",false,false);


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

                String s = rh.sendGetRequestParam(Config.GET_USER2,userId);
                return s;
            }
        }
        GetJSON2 gj = new GetJSON2();
        gj.execute();
    }

}
