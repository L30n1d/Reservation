package com.reservation.reservation;

/**
 * Created by Leonid on 16.01.2017.
 */



import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


public class Session {


    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    Context ctx;

    public Session(Context ctx){
        this.ctx = ctx;
        prefs = ctx.getSharedPreferences("myapp", Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void setLoggedIn(boolean loggedIn, String user){
        editor.putBoolean("loggedInmode", loggedIn);
        editor.putString("user", user);
        editor.commit();
    }

    public String getUser(){
        return prefs.getString("user","");
    }

    public boolean loggedIn(){
        return prefs.getBoolean("loggedInmode", false);
    }



    public void setUserId(int id){
        editor.putInt("userId",id);
        editor.commit();
    }

    public void setUserRole(String role){
        editor.putString("role",role);
        editor.commit();
    }

    public String getUserRole(){return prefs.getString("role","");}

    public int getUserId(){
        return prefs.getInt("userId", 0);
    }




}
