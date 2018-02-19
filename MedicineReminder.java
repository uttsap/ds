package com.afeastoffriends.doctorsaathi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

public class MedicineReminder extends AppCompatActivity implements View.OnClickListener {

    int katiota_medicine;
    Intent agiloscreen_intent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_reminder);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button agadilagne_button = (Button) findViewById(R.id.agadilane_button);

        agadilagne_button.setOnClickListener(MedicineReminder.this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    public void selectNumber(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.euta:
                if (checked == true) {
                    katiota_medicine = 1;
                }
                break;
            case R.id.duita:
                if (checked == true) {
                    katiota_medicine = 2;
                }
                break;
            case R.id.tinta:
                if (checked == true) {
                    katiota_medicine = 3;
                }
                break;
            case R.id.charota:
                if (checked == true) {
                    katiota_medicine = 4;
                }
                break;
            case R.id.panchota:
                if (checked == true) {
                    katiota_medicine = 5;
                }
                break;
            default:
        }
    }

    @Override
    public void onClick(View v) {
        String number = String.valueOf(katiota_medicine);
        if (katiota_medicine == 1)
        {
            Intent agiloscreen_intent = new Intent(MedicineReminder.this, eutaausadhi.class);
            startActivity(agiloscreen_intent);
        }
        else if (katiota_medicine == 2)
        {
            Intent agiloscreen_intent = new Intent(MedicineReminder.this, duitaausadhi.class);
            startActivity(agiloscreen_intent);
        }
        else if (katiota_medicine == 3)
        {
            Intent agiloscreen_intent = new Intent(MedicineReminder.this, tintaausadhi.class);
            startActivity(agiloscreen_intent);
        }
        else if (katiota_medicine == 4)
        {
            Intent agiloscreen_intent = new Intent(MedicineReminder.this, charotaausadhi.class);
            startActivity(agiloscreen_intent);
        }
        else if (katiota_medicine == 5)
        {
            Intent agiloscreen_intent = new Intent(MedicineReminder.this, panchotaausadhi.class);
            startActivity(agiloscreen_intent);
        }

    }
}
