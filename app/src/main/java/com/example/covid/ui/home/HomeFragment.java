package com.example.covid.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.covid.R;

import java.util.Random;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    private String [] tip_phrases = {" Clean your hands before and after putting your mask on", " Make sure your mask covers your nose, mouth, and chin",
            "Regularly and thoroughly clean your hands", "Avoid touching your eyes, nose and mouth", "Cover your mouth and nose with your elbow/tissue when you sneeze/cough",
            "Stay home and self-isolate if you have minor COVID symptoms", "Clean and disinfect areas that have high-touch surfaces", "Use alcohol-based hand sanitizers if you can’t use soap and water",
            "Wash your body and your hair often", "Remember to brush your teeth twice every day", "Cut your fingernails and toenails to prevent dirt and germs to accumulate",
            "If you’ve been to a party recently, self-isolate yourself!"}; // list of tips we came up with

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false); // This variable allows us to call upon the objects on the fragments
        final TextView textView = root.findViewById(R.id.text_home); //Initialize's variable for button
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() { //Accessing the RequirementsViewModel class to retrieve text that it is setting
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        final TextView tips = root.findViewById(R.id.tips); // Initialize's Text view for the tips being drawn

        int rnd = new Random().nextInt(tip_phrases.length); // randomises tips from array of tip_phrases

        String element = tip_phrases[rnd]; //randomises element from array

        tips.setText("Tip: " + element); //Outputs the element










        return root;
    }
}