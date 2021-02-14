package com.example.covid;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;

public class MainActivityD extends AppCompatActivity {

    String MAPS_API = "AIzaSyB4WOEpuz73Jnfyo-1XB51N1STp6PHJbTY";
    private int LOCATIONREQCODE = 2002;

    Intent activitylog;

    Button button, rantest;
    TextView locationTxt;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_open);

        activitylog = new Intent(getApplicationContext(), onOpen.class);


        File dir = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        File pastScanHistory = new File(dir, "pastscanhistory.txt");
        readAndWrite rw = new readAndWrite(pastScanHistory);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(MainActivityD.this, Manifest.permission.ACCESS_FINE_LOCATION) //if location access given
                        == PackageManager.PERMISSION_GRANTED) {
                    startActivity(activitylog);

                } else {    //if no location access granted, request permission
                    ActivityCompat.requestPermissions(MainActivityD.this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, LOCATIONREQCODE);
                }

            }
        });


    }
}