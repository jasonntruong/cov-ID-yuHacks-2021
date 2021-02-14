package com.example.covid;

import androidx.annotation.RequiresApi;//Importing all needed functions
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import com.example.covid.ui.Requirements.Requirements;
import com.example.covid.ui.Activitylog.Activitylog;
import com.example.covid.readAndWrite;

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class nfc_reader extends AppCompatActivity {
    public static final String Error_Detected = "No NFC Tag Detected";//These statements are for if the NFC is not read or written properly
    public static final String Write_Success = "Text Written Successfully";
    public static final String Write_Error = "Error During Writing, Try again.";
    public static final String Write_Pass = "Pass";//This statemenet is displayed if the user passes the restriction
    public static final String Write_Fail = "Failed";
    public static String text = "";
    NfcAdapter nfcAdapter;//Initializing variables used for NFC fuctions
    PendingIntent pendingIntent;
    IntentFilter writingTagFilters[];
    boolean writeMode;
    Tag myTag;
    Context context;
    TextView nfc_reader;
    String testvalue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc_reader);//set page to equal the nfc reader xml file

        nfc_reader = (TextView) findViewById(R.id.nfc_reader); // Intializes the text box for the NFC text will be displayed
        context = this;


        readFromIntent(getIntent());//Calls upon function to read NFC chip
        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),0);//Intialize tag reading, meaning it trying to discover new nfc tags around it
        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        tagDetected.addCategory(Intent.CATEGORY_DEFAULT);
        writingTagFilters = new IntentFilter[] {tagDetected};

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);//Initializes the NFC adapter built in our phones
        if(nfcAdapter == null){
            Toast.makeText(this,"This device does not support NFC", Toast.LENGTH_SHORT).show();//This checks if the phone has a built in NFC adpater as if they don't the app will no open
            finish();
        }






    }
    private void readFromIntent(Intent intent){//This Function is where the NFC Adapter in our phones read the nfc card
        String action = intent.getAction();
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action) || NfcAdapter.ACTION_TECH_DISCOVERED.equals(action) || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {//See if an NFC chip has enter in te scanning range of the phone
            Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES); //Obtaining any message on the NFC chip in this case it obtains the text message on the chip
            NdefMessage[] msgs = null;
            if (rawMsgs != null){
                msgs = new NdefMessage[rawMsgs.length];//Allows for the message to be more easily read
                for (int i = 0; i < rawMsgs.length; i++){
                    msgs[i] = (NdefMessage) rawMsgs[i];
                }
            }
            buildTagViews(msgs);//Calls upon another function to help convert the message into strings

        }
    }
    private void buildTagViews(NdefMessage[] msgs) {
        if (msgs == null || msgs.length == 0) {
            return;
        }

        byte[] payload = msgs[0].getRecords()[0].getPayload();//Stores the data in byte array; Data stored on NFC chips are in Bytes
        String textEncoding = ((payload[0] & 128) == 0) ? "UTF-8" : "UTF-16"; //Get the Text Encoding
        int languageCodeLength = payload[0] & 0063;//Gets the language "en"

        try{
            text = new String(payload, languageCodeLength + 1, payload.length - languageCodeLength - 1, textEncoding);//Finally converts all the text into string text

        }
        catch (UnsupportedEncodingException e){//catches to see if you are return an error
            Log.e("UnsupportedEncoding", e.toString());
        }
        nfc_reader.setText(text);//Prints the text into the TextView box

        if (text.contains("Positive")){//Reads the text to see if the user has test positive or negative
            testvalue = "Positive";
        }
        else if (text.contains("Negative")){
            testvalue = "Negative";
        }

        char[] chars = text.toCharArray();//reads the file to get the date of when last tested
        StringBuilder sb = new StringBuilder();
        for(int count = 0; count < 30; count++){
            if(Character.isDigit(chars[count])){
                sb.append(chars[count]);
            }
        }
        pass_fail(testvalue, Integer.parseInt(String.valueOf(sb)));//puts the above obtained value into a function to test if it passes the test

        sb = new StringBuilder();//Reading for the Account Number
        for(int count = 30 ; count > 60; count++){
            if(Character.isDigit(chars[count])){
                sb.append(chars[count]);
            }
        }

        File dir = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);//Initializing a text file to store the account
        File Activitylog = new File(dir, "Activitylog.txt");
        readAndWrite rw = new readAndWrite(Activitylog);
        rw.writeFile(String.valueOf(sb));//write account number to file




    }

    private void write(String text, Tag tag) throws IOException, FormatException{//This is a function used to write to the nfc, We used this during testing but this is not accessable in the able
        NdefRecord[] records = { createRecord(text) };
        NdefMessage message = new NdefMessage(records);
        Ndef ndef = Ndef.get(tag);
        ndef.connect();
        ndef.writeNdefMessage(message);
        ndef.close();
    }

    private NdefRecord createRecord(String text) throws UnsupportedEncodingException{//Again This function is for writing the NFC chip, it converts all the string value or integer we enter into bytes for the NFC chip to store
        String lang = "en";
        byte[] textBytes = text.getBytes();
        byte[] langBytes = lang.getBytes("US-ASCII");
        int langLength = langBytes.length;
        int textLength = textBytes.length;
        byte[] payload = new byte[1+langLength+textLength];

        payload[0] = (byte) langLength;

        System.arraycopy(langBytes, 0, payload, 1, langLength);
        System.arraycopy(textBytes, 0, payload, 1 +langLength, textLength);

        NdefRecord recordNFC = new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, new byte[0], payload);

        return recordNFC;
    }

    @Override
    protected void onNewIntent(Intent intent){//This allows the App to constantly look and update the textview box without refreshing
        super.onNewIntent(intent);
        setIntent(intent);
        readFromIntent(intent);
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())){
            myTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);


        }
    }

    @Override
    public void onPause(){//This was used for the writing function to the NFC chip, This checks when the button is press to turn on the nfc writer
        super.onPause();
        Writemodeoff();
    }

    @Override
    public void onResume(){
        super.onResume();
        Writemodeon();
    }




    private void Writemodeon(){//this enables the NFC adapter in the phone to write
        writeMode = true;
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, writingTagFilters,null);

    }
    private void Writemodeoff(){//Disables the writing to nfc chip
        writeMode = false;
        nfcAdapter.disableForegroundDispatch(this);


    }

    public void pass_fail(String testresults, int days){//This function is used to check if the user has passed or fail

        if (Requirements.str1.equals("ON") && testresults.equals("Negative") || Requirements.str1.equals("OFF") && testresults.equals("Positive")){//conditions to pass the test
            if(days <= Requirements.numdays){
                Mycustompopuppass();//calls upon a function that shows a check mark pop up
            }
            else {
                Mycustompopupfail();//calls upon a function that shows a x pop up
            }
        }
        if (Requirements.str1.equals("OFF") && testresults.equals("Negative") || Requirements.str1.equals("ON") && testresults.equals("Positive")){//condition to fail
            Mycustompopupfail();
        }

    }



    public void Mycustompopuppass(){
        final Dialog Mydialog = new Dialog(nfc_reader.this);//Initializes the pop up features built into android studio;
        Mydialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Mydialog.setContentView(R.layout.customdialog);
        Mydialog.setTitle("My Custom Dialog");

        Toast.makeText(context, Write_Pass, Toast.LENGTH_LONG).show();//Shows a notification for the user to see if they pass or fail on the terminal

        Button close = (Button)Mydialog.findViewById(R.id.close);//initialize button to close the pop up
        close.setEnabled(true);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Mydialog.cancel();
            }
        });

        Mydialog.show();


    }
    public void Mycustompopupfail(){
        final Dialog Mydialog = new Dialog(nfc_reader.this);//This function does the same thing above but showing an x instead of a check mark
        Mydialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Mydialog.setContentView(R.layout.customdialog2);
        Mydialog.setTitle("My Custom Dialog");

        Toast.makeText(context, Write_Fail, Toast.LENGTH_LONG).show();

        Button close = (Button)Mydialog.findViewById(R.id.close);
        close.setEnabled(true);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Mydialog.cancel();
            }
        });

        Mydialog.show();


    }






}
