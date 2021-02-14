package com.example.covid;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class readAndWrite {

    FileOutputStream outStream;
    FileInputStream inStream;
    File mfile;
    ArrayList<String> infoHistory;

    File[] mDirectory;
    String mFileName;

    protected readAndWrite(File file){
        mfile = file;
    }

    protected readAndWrite(File[] directory, String fileName) {
        mDirectory = directory;
        mFileName = fileName;
    }

    protected void writeFile(String message){   //write to file
        try {
            Log.d("add", message);
            outStream = new FileOutputStream(mfile, true);
            outStream.write(message.getBytes());
            outStream.write("\n".getBytes());
            outStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected Boolean fileExists(File[] directory, String fileName) {
        for (File files : directory) {
            if (files.getName().equals(fileName)) {
                return true;
            }
        }

        return false;

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    protected ArrayList<String> readFile() throws IOException {    //read from file and returns contents as String
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(mfile), StandardCharsets.UTF_8));
        String line;
        ArrayList<String> infoArray = new ArrayList<String>();

        while ((line = br.readLine()) != null) {
            infoArray.add(line);

        }

        br.close();

        return infoArray;
    }
}
