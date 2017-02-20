package com.reservation.reservation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ImageButton;

import java.util.ArrayList;

public class Matrix_view2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matrix_view2);

        ImageButton box[] = new ImageButton[10];

        GridView gridView = (GridView)findViewById(R.id.gridview);
        gridView.setAdapter(new BoxAdapter(this, box));


    }
}
