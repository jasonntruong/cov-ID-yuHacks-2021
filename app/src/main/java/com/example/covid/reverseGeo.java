package com.example.covid;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.widget.Button;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class reverseGeo {

    private FusedLocationProviderClient mFusedLocationClient;
    private int LOCATIONREQCODE = 2002;
    Context mContext;
    String mdate, fulladdress;
    Button mButton;
    readAndWrite mfile;
    public reverseGeo(Button button, Context context, readAndWrite file) {
        mContext = context;
        mButton = button;
        mfile = file;


        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(mContext);
    }


    public void storeScanLocation() {
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Task<Location> task = mFusedLocationClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) { //if found a location via Google Maps API (mFusedLocationClient.getLastLocation()) then we obtain the latitude and longitude values
                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    try {
                        getDate();  //obtain the date
                        getStreet((float)location.getLatitude(), (float)location.getLongitude()); // call getStreet (this is reverse Geocoding: going from coordinates to street names)

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    private void getDate() {
        Date date = Calendar.getInstance().getTime();

        SimpleDateFormat dateform = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy", Locale.getDefault()); //format date in the form HH:mm:ss for time and dd/MM/yyyy for date

        mdate = dateform.format(date);

    }


    private void getStreet(float latitude, float longitude) throws IOException {
        Geocoder geoCoder = new Geocoder(mContext);
        //List<Address> matches = geoCoder.getFromLocation(latitude,longitude, 1);  //THIS METHOD WORKS FOR GETTING LOCATION OF USER. BUT FOR THE DEMO WE DON'T WANT TO REVEAL OUR ADDRESS
        List<Address> matches = geoCoder.getFromLocation(43.7507571,-79.4749313, 1);  //SO WE CALL GETSTREET WITH LAT AND LONG OF YORK UNIVERSITY CAMPUS

        Address address = (matches.isEmpty() ? null : matches.get(0));
        String streetnumber = address.getFeatureName();
        String street = address.getThoroughfare();
        street = street.substring(0, Math.min(10, street.length()));    //street will only be max length of 10
        String province = address.getAdminArea().substring(0, 2).toUpperCase();
        String postal = address.getPostalCode();

        fulladdress = (streetnumber + " " + street + ", " + province + ", " + postal).toUpperCase();    //this is the address
        String scanInfo = mdate + " " + fulladdress;

        mfile.writeFile(scanInfo);  //write to file

        mButton.setText(scanInfo);  //update button

    }

}
