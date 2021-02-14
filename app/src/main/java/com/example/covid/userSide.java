package com.example.covid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Random;

public class userSide extends AppCompatActivity {
    private String [] tip_phrases = {" Clean your hands before and after putting your mask on", " Make sure your mask covers your nose, mouth, and chin",
            "Regularly and thoroughly clean your hands", "Avoid touching your eyes, nose and mouth", "Cover your mouth and nose with your elbow/tissue when you sneeze/cough",
            "Stay home and self-isolate if you have minor COVID symptoms", "Clean and disinfect areas that have high-touch surfaces", "Use alcohol-based hand sanitizers if you can’t use soap and water",
            "Wash your body and your hair often", "Remember to brush your teeth twice every day", "Cut your fingernails and toenails to prevent dirt and germs to accumulate",
            "If you’ve been to a party recently, self-isolate yourself!"};  //many tips to staying covid-free

    TextView tip;
    ImageButton scanbtn, medicalbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_side);
        tip = findViewById(R.id.tip);

        int index = new Random().nextInt(tip_phrases.length);

        String tipString = tip_phrases[index];

        tip.setText("Tip: " + tipString); //set tip TextView to a random String in tip_phrases

        scanbtn = findViewById(R.id.scanbtn);
        medicalbtn = findViewById(R.id.medicalbtn);

        scanbtn.setOnClickListener(new View.OnClickListener() { //if scanbtn / scan history button clicked, start scanIntent / pastScans
            @Override
            public void onClick(View v) {
                Intent scanIntent = new Intent(getApplicationContext(), pastScans.class);
                startActivity(scanIntent);
            }
        });

        medicalbtn.setOnClickListener(new View.OnClickListener() { //if medicalbtn / medical history button clicked, start medicalIntent / pastMedical
            @Override
            public void onClick(View v) {
                Intent medicalIntent = new Intent(getApplicationContext(), pastMedical.class);
                startActivity(medicalIntent);
            }
        });



    }
}