package com.afeastoffriends.doctorsaathi;

/**
 * Created by Acer-PC on 2/10/2017.
 */

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class DialogOptions extends Activity {

    int acheivedId; String nameS ,phoneS, emailS , bloodGroupS, lastDateS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_options);
        acheivedId=  getIntent().getIntExtra("id",0);
        nameS = getIntent().getStringExtra("name");
        phoneS = getIntent().getStringExtra("phone");
        emailS = getIntent().getStringExtra("email");
        bloodGroupS = getIntent().getStringExtra("bloodGroup");
        lastDateS = getIntent().getStringExtra("lastDate");


    }






    public void call(View view) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+phoneS.trim()));
        startActivity(intent);

        finish();
    }


    public void mess(View view) {
        Intent messIntent = new Intent(Intent.ACTION_VIEW,Uri.parse("sms:"+phoneS.trim()));
        messIntent.putExtra("sms_body", "I want your bloodgroup urgently, please do contact me on this number if you are willing to. Thank You!");

        startActivity(messIntent);
        finish();
    }

}
