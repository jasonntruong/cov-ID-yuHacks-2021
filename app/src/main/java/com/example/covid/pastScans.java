package com.example.covid;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class pastScans extends AppCompatActivity {

    List<Button> allScans;

    Button load;
    reverseGeo revGeo;
    ArrayList<String> infoArray;

    private int LOCATIONREQCODE = 2002;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_scans);

        File dir = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);

        File scanHistory = new File(dir, "scanHistory.txt"); //file scanHistory.txt is stored in DOCUMENTS on user's device
        readAndWrite rw = new readAndWrite(scanHistory);

        load = findViewById(R.id.scan8);
        load.setText("I WAS SCANNED");  //we use a button to make scans as we did not have NFC cards with chips - meaning we could read from the NFC cards, but could not
                                        //make the card contact the user's phone/account to tell them the location of the scan!

                                        //Our work around was to make it so that user's open the app and tap the button whenever they've been scanned if they want to keep
                                        //track of where they've been scanned. Clicking this stores the user's location and time of scan!
        load.setEnabled(true);

        allScans = new ArrayList();

        allScans.add((Button)findViewById(R.id.scan));
        allScans.add((Button)findViewById(R.id.scan0));
        allScans.add((Button)findViewById(R.id.scan1));
        allScans.add((Button)findViewById(R.id.scan2));
        allScans.add((Button)findViewById(R.id.scan3));
        allScans.add((Button)findViewById(R.id.scan4));
        allScans.add((Button)findViewById(R.id.scan5));
        allScans.add((Button)findViewById(R.id.scan6));
        allScans.add((Button)findViewById(R.id.scan7));
        allScans.add((Button)findViewById(R.id.scan8));

        infoArray = null;

        try {
            infoArray = rw.readFile();  //read past contents of scanHistory file and store it in infoArray
            Collections.reverse(infoArray); //reverse Array to order most recent at the top of the buttons
            for (int i = 0; i < Math.min(infoArray.size(), 10); i++) {
                allScans.get(i).setText(infoArray.get(i));
                allScans.get(i).setBackgroundColor(0xFF77DF79);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        load.setOnClickListener(new View.OnClickListener() {    //if user clicks "I WAS SCANNED" button then...
            @Override
            public void onClick(View v) {

                if (ActivityCompat.checkSelfPermission(pastScans.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {//check if location access permissions are granted

                    try{
                        if (infoArray.size() < 9) { //run the revGeo.storeScanLocation() which will update a button to show the time, date, and location of the scan AND
                                                    //will save the information to the scanHistory.txt file.
                            int index = infoArray.size();
                            allScans.get(index).setBackgroundColor(0xFF77DF79);
                            revGeo = new reverseGeo (allScans.get(index), getApplicationContext(), rw);
                            revGeo.storeScanLocation();

                        }
                        else {
                            infoArray.remove(9);
                        }
                    } catch (NullPointerException e) {  //if infoArray has no size, then it is empty. Add the scanned information at index 0 of buttons in allScans
                        allScans.get(0).setBackgroundColor(0xFF77DF79);
                        revGeo = new reverseGeo (allScans.get(0), getApplicationContext(), rw);
                        revGeo.storeScanLocation();

                    }

                } else {    //if no location access granted, request permission
                    ActivityCompat.requestPermissions(pastScans.this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, LOCATIONREQCODE);
                }

            }
        });
    }

}