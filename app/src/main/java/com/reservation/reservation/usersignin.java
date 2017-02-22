package com.reservation.reservation;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class usersignin extends AppCompatActivity {


    private String JSON_STRING, id, phone, password, active, name, code,emailB, seats;
    private Session session;
    private Button login, exit, reg;
    private EditText phoneTxt, passTxt;
    private TextView lost;
    private ConnectionDetector cd;




    private int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usersignin);

        cd = new ConnectionDetector(this);
        session = new Session(this);

        if(session.loggedIn()){
            String role = session.getUserRole();
            if(!role.equals("admin")){
                startActivity(new Intent(usersignin.this, PlaceChooser.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
            }

        }

        login = (Button)findViewById(R.id.loginBtn);
        reg = (Button)findViewById(R.id.goToReg);
        phoneTxt = (EditText)findViewById(R.id.phoneTxt);
        passTxt = (EditText)findViewById(R.id.passTxt);
        lost = (TextView)findViewById(R.id.lostpass);
        //phoneTxt.setSelection(2);
        //passTxt.setSelection(2);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cd.isConnected()){
                    login();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Нема интернет конекција",Toast.LENGTH_SHORT).show();
                }

            }
        });

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(usersignin.this, usersignup.class));
            }
        });

        lost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(usersignin.this,forget_pass.class));
            }
        });

    }


    private void showEmployee(String user, String pass){
        JSONObject jsonObject = null;
        String role = "";
        String caffeId = "";
        String days = "";
        String layout = "";

        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

            if(result.length() < 1){
                flag = 0;
                Toast.makeText(getApplicationContext(),"Невалиден број/лозинка", Toast.LENGTH_SHORT).show();
            }
            else{
                for(int i = 0; i<result.length(); i++){
                    JSONObject jo = result.getJSONObject(i);
                    id = jo.getString("id");
                    phone = jo.getString("mobile");
                    name = jo.getString("name");
                    password = jo.getString("password");
                    active = jo.getString("active");
                    code = jo.getString("code");
                    emailB = jo.getString("email");
                    role = jo.getString("role");
                    caffeId = jo.getString("caffeId");
                    days = jo.getString("days");
                    layout = jo.getString("layout");
                    seats = jo.getString("seats");
                    flag = 1;
                }

                if(active.equals("0")){
                    Intent i = new Intent(usersignin.this, verify2.class);

                    Bundle bundle = new Bundle();
                    bundle.putString("code", code);
                    i.putExtras(bundle);

                    Bundle bundle2 = new Bundle();
                    bundle2.putString("email", phone);
                    i.putExtras(bundle2);

                    startActivity(i);
                }
                else if(role.equals("admin")){
                    session.setLoggedIn(true, phone);
                    session.setUserRole("admin");
                    session.setAdminSeats(seats);
                    session.setUserId(Integer.parseInt(id));

                    Intent i = new Intent(usersignin.this, PlaceChooserAdmin.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                    Bundle bundle = new Bundle();
                    bundle.putString("caffe_id", caffeId);
                    i.putExtras(bundle);

                    Bundle bundle2 = new Bundle();
                    bundle2.putString("days", days);
                    i.putExtras(bundle2);

                    Bundle bundle3 = new Bundle();
                    bundle3.putString("layout", layout);
                    i.putExtras(bundle3);

                    startActivity(i);

                }
                else
                {
                    session.setLoggedIn(true, phone);
                    session.setUserId(Integer.parseInt(id));
                    session.setUserRole("user");
                    startActivity(new Intent(usersignin.this, PlaceChooser.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                }

            }



        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void getJSON(final String phone, final String pass){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                    loading = ProgressDialog.show(usersignin.this,"","Се вчитува...",false,false);


            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showEmployee(phone,pass);
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("phone",phone);
                hashMap.put("password",pass);

                RequestHandler rh = new RequestHandler();

                String s = rh.sendPostRequest(Config.GET_USER,hashMap);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    private void login(){
        String phone = phoneTxt.getText().toString();
        String pass = passTxt.getText().toString();
        // User user1 = db.getUser(user,pass);
        getJSON(phone,pass);

       /* if(user.equals("ad") && pass.equals("ad")){
            session.setLoggedIn(true, user);
            startActivity(new Intent(MainActivity.this, AdminActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
            finish();
        }
        else if(flag == 1){
            session.setLoggedIn(true, usernameC);
            session.setUserId(Integer.parseInt(idC));
            session.setUserRole(roleC);
            if(roleC.equals("kelner")){

                startActivity(new Intent(MainActivity.this, OrdersActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
            }
            else
            {
                startActivity(new Intent(MainActivity.this, Orders2.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
            }

            finish();
        }
        else if(user.equals("sh") && pass.equals("sh")){
            session.setLoggedIn(true, user);

            startActivity(new Intent(MainActivity.this, Overview.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));

        }
        else{
            // Toast.makeText(getApplicationContext(),"Невалидни корисничко име/лозинка", Toast.LENGTH_SHORT).show();
        }*/
    }

}
