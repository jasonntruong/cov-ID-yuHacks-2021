package com.example.covid.ui.scanner;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.covid.R;
import com.example.covid.nfc_reader;
import com.example.covid.ui.scanner.ScannerViewModel;

public class Scanner extends Fragment {


    private ScannerViewModel scannerViewModel;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        scannerViewModel = new ViewModelProvider(this).get(ScannerViewModel.class);
        View root = inflater.inflate(R.layout.fragment_scanner, container, false); // This variable allows us to call upon the objects on the fragments
        final TextView textView = root.findViewById(R.id.text_scanner); //Initialize's variable for button
        scannerViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() { //Accessing the RequirementsViewModel class to retrieve text that it is setting

            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }

        });

        final Button nfcbutton = root.findViewById(R.id.NFCbutton);
        
        nfcbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getActivity(), nfc_reader.class);
                startActivity(intent);
            }

        });

        return root;


    }



}
