package com.afeastoffriends.doctorsaathi;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {



    public ImageButton imgbuttonhospital;
    public ImageButton imgbuttonbloodbank;
    public ImageButton imgbuttonfirstaid;
    public ImageButton imgbuttonayurvedic;
    public ImageButton imgbuttonmedicine;
    public ImageButton imgbuttonbasicmass;


    public void init() {
        imgbuttonhospital = (ImageButton) findViewById(R.id.imgHospital);
        imgbuttonbloodbank = (ImageButton) findViewById(R.id.imgBloodBank);
        imgbuttonayurvedic = (ImageButton) findViewById(R.id.imgAyurvedic);
        imgbuttonfirstaid = (ImageButton)findViewById(R.id.imgFirstAid);
        imgbuttonmedicine = (ImageButton)findViewById(R.id.imgMedicine);
        imgbuttonbasicmass = (ImageButton)findViewById(R.id.imgBodyMassIndex);
        imgbuttonhospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mappin = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(mappin);
            }
        });
        imgbuttonbloodbank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent blooding = new Intent(MainActivity.this, BloodActivity.class);
                startActivity(blooding);
            }
        });
        imgbuttonayurvedic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ayurveding = new Intent(MainActivity.this, Ayurvedic.class);
                startActivity(ayurveding);
            }
        });

        imgbuttonfirstaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent firstaiding = new Intent(MainActivity.this, FAMainActivity.class);
                startActivity(firstaiding);
            }
        });
        imgbuttonmedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent medicinereminding = new Intent(MainActivity.this, MedicineReminder.class);
                startActivity(medicinereminding);
            }
        });
        imgbuttonbasicmass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bmindexing = new Intent(MainActivity.this, BasicMassIndex.class);
                startActivity(bmindexing);
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        init();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

//        if (id == R.id.nav_camera) {
//            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
        if (id == R.id.nav_about_us) {
            Intent aboutus = new Intent(MainActivity.this,About_Us.class);
            startActivity(aboutus);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
