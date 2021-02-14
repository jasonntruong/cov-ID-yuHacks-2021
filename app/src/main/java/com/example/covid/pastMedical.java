package com.example.covid;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class pastMedical extends AppCompatActivity {    //USER SIDE ACTIVITY

    List<Button> allMedical;

    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_medical);

        File dir = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        File[] allFiles = dir.listFiles();
        File medicalHistory = new File(dir, "medicalHistory.txt");  //txt file stored in DOCUMENTS on user's device
        readAndWrite rw = new readAndWrite(medicalHistory); //create a readAndWrite object rw

        Boolean exist = rw.fileExists(allFiles, "medicalHistory.txt");

        if (exist != true) {    //we added what information would look like in the app since we do not have access to people government covid test data. This shows we can
                                //readily display it in our app when given this government data
            rw.writeFile("PASSED - MCKNZIE HEALTH HOSP - 2/14/2021");
            rw.writeFile("FAILED - HUMBER RIVER HOSP - 1/11/2021");
            rw.writeFile("PASSED - WALMART CLINIC - 8/26/2020");
            rw.writeFile("PASSED - SHOPPERS DRUG - 4/1/2020");
        }

        allMedical = new ArrayList();

        allMedical.add((Button)findViewById(R.id.scan));
        allMedical.add((Button)findViewById(R.id.scan0));
        allMedical.add((Button)findViewById(R.id.scan1));
        allMedical.add((Button)findViewById(R.id.scan2));
        allMedical.add((Button)findViewById(R.id.scan3));
        allMedical.add((Button)findViewById(R.id.scan4));
        allMedical.add((Button)findViewById(R.id.scan5));
        allMedical.add((Button)findViewById(R.id.scan6));
        allMedical.add((Button)findViewById(R.id.scan7));
        allMedical.add((Button)findViewById(R.id.scan8));

        ArrayList<String> infoArray = null;
        try {
            infoArray = rw.readFile();  //read all the user's old test data from reading the medicalHistory file
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < Math.min(infoArray.size(), 10); i++) {  //only want to display recent 10

            if (infoArray.get(i).charAt(0) == 'P') {    //since our info is in the syntax RESULT + LOCATION, if charAt(0) is 'P' then RESULT is PASSED
                allMedical.get(i).setBackgroundColor(0xFF77DF79); //make background of button green
            }
            else if (infoArray.get(i).charAt(0) == 'F'){    //since our info is in the syntax RESULT + LOCATION, if charAt(0) is 'F' then RESULT is FAILED
                allMedical.get(i).setBackgroundColor(0xFFC23B22);   //make background of button red
            }

            allMedical.get(i).setText(infoArray.get(i)); //set the button text to info
        }
    }

}