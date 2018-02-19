package com.afeastoffriends.doctorsaathi;

/**
 * Created by Acer-PC on 2/10/2017.
 */

import android.annotation.TargetApi;
import android.content.Intent;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DonorDescription extends AppCompatActivity {
    TextView nameT;
    TextView phoneT;
    TextView emailT;
    TextView blooDgroupT;
    TextView statusT;
    @TargetApi(Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_description);

        nameT= (TextView) findViewById(R.id.DesciNameTextView);
        phoneT= (TextView) findViewById(R.id.DesciPhoneTextView);
        emailT= (TextView) findViewById(R.id.DesciEmailTextView);
        blooDgroupT= (TextView) findViewById(R.id.DesciBGTextView);
        statusT= (TextView) findViewById(R.id.DesciStatusView);

        final String name = getIntent().getStringExtra("name");
        final String phone = getIntent().getStringExtra("phone");
        final String email = getIntent().getStringExtra("email");
        final String bloodGroup = getIntent().getStringExtra("bloodGroup");
        final String lastDate = getIntent().getStringExtra("lastDate");


        nameT.setText(name);
        phoneT.setText(phone);
        emailT.setText(email);
        blooDgroupT.setText(bloodGroup);
        statusT.setText(lastDate);
        /*DateFormat formatter ;
        //String s4 = "04/06/2016";
        Date date = null;
        formatter = new SimpleDateFormat("dd/MM/yyyy");

        try {
            date = formatter.parse(lastDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.e("PD DEBUG" , String.valueOf(date));

        Date date1 = new Date();

        Log.e("PD DEBUG" , String.valueOf(date1));

        long diff = date1.getTime()-date.getTime() ;
        long diffDays = diff / (24 * 60 * 60 * 1000);
        Log.e("PD DEBUG" , String.valueOf(diffDays));
        if(diffDays>=90)
        {
            statusT.setText("Available");

        }
        else
        {
            statusT.setText("Not Available");

        }*/


    }
}
