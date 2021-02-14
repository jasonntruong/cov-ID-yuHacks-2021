package com.example.covid;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.transform.Result;

public class pastScans extends AppCompatActivity {

    List<Button> allScans;

    Button load;
    reverseGeo revGeo;
    ArrayList<String> infoArray;
    String key;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_scans);

        File dir = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);

        File scanHistory = new File(dir, "scanHistory.txt");
        readAndWrite rw = new readAndWrite(scanHistory);

        load = findViewById(R.id.scan8);
        load.setText("FAKE SCAN");
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
            infoArray = rw.readFile();
            Collections.reverse(infoArray);
            for (int i = 0; i < Math.min(infoArray.size(), 10); i++) {
                allScans.get(i).setText(infoArray.get(i));
                allScans.get(i).setBackgroundColor(0xFF77DF79);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if (infoArray.size() < 9) {
                        int index = infoArray.size();
                        allScans.get(index).setBackgroundColor(0xFF77DF79);
                        revGeo = new reverseGeo (allScans.get(index), getApplicationContext(), rw);
                        revGeo.getCurrentLocation();

                    }
                    else {
                        infoArray.remove(9);
                    }
                } catch (NullPointerException e) {
                    Log.d("Null", "True");
                    allScans.get(0).setBackgroundColor(0xFF77DF79);
                    revGeo = new reverseGeo (allScans.get(0), getApplicationContext(), rw);
                    revGeo.getCurrentLocation();

                }
            }
        });
    }

}