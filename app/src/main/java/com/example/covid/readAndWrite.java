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
    File mfile;


    public readAndWrite(File file){
        mfile = file;
    }


    public void writeFile(String message){   //write to file
        try {
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

    public void clearFile() {   //clear a file
        try {
            outStream = new FileOutputStream(mfile, false);
            outStream.write("".getBytes()); //write "" to a file (append is off so this clears all data in the file)
            outStream.write("\n".getBytes());
            outStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Boolean fileExists(File[] directory, String fileName) { //check if a file exists within a given directory
        for (File files : directory) {
            if (files.getName().equals(fileName)) {
                return true;
            }
        }

        return false;

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public ArrayList<String> readFile() throws IOException {    //read from file and returns contents as an ArrayList<String>
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(mfile), StandardCharsets.UTF_8));
        String line;
        ArrayList<String> infoArray = new ArrayList<String>();

        while ((line = br.readLine()) != null) {    //while the line in br is not null, then we add that line to the infoArray
            infoArray.add(line);

        }

        br.close();

        return infoArray;   //return the infoArray / ArrayList<String> with all the String data from the file
    }
}
