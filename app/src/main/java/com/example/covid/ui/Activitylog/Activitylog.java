package com.example.covid.ui.Activitylog;

import android.os.Bundle;
import android.os.Environment;
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
import com.example.covid.nfc_reader;
import com.example.covid.readAndWrite;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import com.example.covid.readAndWrite;


public class Activitylog extends Fragment {

    private Activitylog1ViewModel dashboardViewModel;

    List<Button> allscanentry;
    public static ArrayList<String> accountnum;



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel = new ViewModelProvider(this).get(Activitylog1ViewModel.class);
        View root = inflater.inflate(R.layout.fragment_activity_log, container, false);
        final TextView textView = root.findViewById(R.id.text_dashboard);
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        Button button;

        File dir = getActivity().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        File Activitylog = new File(dir, "Activitylog.txt");
        readAndWrite rw = new readAndWrite(Activitylog);

        try {
            accountnum = rw.readFile();
        } catch (IOException e) {
            e.printStackTrace();
        }


        allscanentry = new ArrayList();

        allscanentry.add((button) = root.findViewById(R.id.scan));
        allscanentry.add((button) = root.findViewById(R.id.scan0));
        allscanentry.add((button) = root.findViewById(R.id.scan1));
        allscanentry.add((button) = root.findViewById(R.id.scan2));
        allscanentry.add((button) = root.findViewById(R.id.scan3));
        allscanentry.add((button) = root.findViewById(R.id.scan4));
        allscanentry.add((button) = root.findViewById(R.id.scan5));
        allscanentry.add((button) = root.findViewById(R.id.scan6));
        allscanentry.add((button) = root.findViewById(R.id.scan7));
        allscanentry.add((button) = root.findViewById(R.id.scan8));

        for (int count =0; count < Math.min(allscanentry.size(),accountnum.size()); count++){
            allscanentry.get(count).setEnabled(true);
            allscanentry.get(count).setBackgroundColor(0xFF77DF79);
            allscanentry.get(count).setText(accountnum.get(count));

        }

        return root;
    }


}