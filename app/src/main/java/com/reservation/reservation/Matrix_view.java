package com.reservation.reservation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import java.util.ArrayList;

public class Matrix_view extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrix_view);

        String[] list = new String[10];

        for(int i =0;i<10;i++){
            list[i] = Integer.toString(i);
        }

        GridView gridview = (GridView) findViewById(R.id.gridView1);
        gridview.setAdapter(new TextViewAdapter(this, list));




    }
}
