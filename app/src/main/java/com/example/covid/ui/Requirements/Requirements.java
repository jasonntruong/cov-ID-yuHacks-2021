package com.example.covid.ui.Requirements;

 import android.app.Activity;
 import android.os.Build;
 import android.os.Bundle;
 import android.os.Environment;
 import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
 import android.widget.Button;
 import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
 import android.widget.Toast;
 import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
 import androidx.annotation.RequiresApi;
 import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.covid.R;
 import com.example.covid.readAndWrite;

 import java.io.File;

public class Requirements extends Fragment{

    private RequirementsViewModel requirementsViewModel;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        requirementsViewModel = new ViewModelProvider(this).get(RequirementsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_requirements, container, false); // This variable allows us to call upon the objects on the fragments
        final TextView textView = root.findViewById(R.id.text_notifications); //Initialize's variable for button
        requirementsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() { //Accessing the RequirementsViewModel class to retrieve text that it is setting
            
            @Override
            public void onChanged(@Nullable String s) { textView.setText(s);
            }

        });

        final Switch negativeswitch = root.findViewById(R.id.NEGATIVE); //Initializing switch for negative requirement
        final Switch vaccinatedswitch = root.findViewById(R.id.Vaccine); // Initializing switch for vaccinated requirement
        final Button savebutton = root.findViewById(R.id.Save); // Initializing button for save button
        final TextView days = root.findViewById(R.id.editTextNumber); //Initializing textview for the amount of days the restaurant owner would like to restrict the user upon entering

        negativeswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) { //The CompounButton is the the compound button view whose state has changed and the isChecked is a boolean statement the new checked state of buttonview
                if (isChecked){

                }
                else{

                }
            }
        });

        savebutton.setOnClickListener(new View.OnClickListener() { //method which helps us to link a listener with certain attributes. While this method runs, a callback function will run
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                String str1, str2;
                File dir = getActivity().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);

                File requirementsFile = new File(dir, "requirements.txt");  //create new File requirements.txt
                readAndWrite rw = new readAndWrite(requirementsFile);


                if (negativeswitch.isChecked()) {
                    str1 = negativeswitch.getTextOn().toString(); //The value of the negative switch attribute when it's on

                }
                else {
                    str1 = negativeswitch.getTextOff().toString(); //The value of the negative switch attribute when it's off
                }

                if (vaccinatedswitch.isChecked()) {
                    str2 = vaccinatedswitch.getTextOn().toString(); //The value of the vaccinated switch attribute when it's on
                }
                else {
                    str2 = vaccinatedswitch.getTextOff().toString(); //The value of the vaccinated switch attribute when it's off
                }
                rw.clearFile(); //when saving, empty the old info and store the new data (below)

                rw.writeFile(str1);
                rw.writeFile(String.valueOf(days.getText()));
                rw.writeFile(str2);
                Toast.makeText(getActivity(), "Negative Test- " + str1 + "\n" + "Vaccinated- " + str2 + "\nNumber of Days Since Previous Test- " +  days.getText(),Toast.LENGTH_SHORT).show(); //Outputs to given attribute

            }
        });


        return root;






    }





}