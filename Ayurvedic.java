package com.afeastoffriends.doctorsaathi;

import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

/**
 * Created by Mr Mojo Risin on 3/5/2017.
 */

public class Ayurvedic extends AppCompatActivity {
    private final String QUERY = "SELECT * FROM "+DatabaseContract.Disease.TABLE_NAME;
    ArrayList<String> disease= new ArrayList<>();
    Button button;
    boolean buttFlag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayurvedic);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        button = (Button) findViewById(R.id.sync);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                importDatabase("ayurvedic.db");
            }
        });
        button.setVisibility(View.INVISIBLE);
        openDAtabase();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        RecyclerAdapter adapter = new RecyclerAdapter(disease,this);
        RecyclerView.LayoutManager layoutmanager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutmanager);
        recyclerView.setAdapter(adapter);
        //exportDatabse("ayurvedic.db");

    }
    /* public void exportDatabse(String databaseName) {
         try {
             File sd = Environment.getExternalStorageDirectory();
             File data = Environment.getDataDirectory();

             if (sd.canWrite()) {
                 String currentDBPath = "//data//"+getPackageName()+"//databases//"+databaseName+"";
                 String backupDBPath = "ayurvedic.db";
                 File currentDB = new File(data, currentDBPath);
                 File backupDB = new File(sd, backupDBPath);

                 if (currentDB.exists()) {
                     FileChannel src = new FileInputStream(currentDB).getChannel();
                     FileChannel dst = new FileOutputStream(backupDB).getChannel();
                     dst.transferFrom(src, 0, src.size());
                     src.close();
                     dst.close();
                 }
             }
         } catch (Exception e) {
             Toast.makeText(MainActivity.this, "Export Error", Toast.LENGTH_SHORT).show();
         }
     }*/
    public void importDatabase(String databaseName) {
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                // public void onRequestPermissionsResult(int requestCode, String[] A,
                //                                          int[] grantResults)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                    requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},1);
                }

                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }


            if (sd.canWrite()) {


                //String currentDBPath = "android.resource://np.com.rajan.drsathi/raw/ayurvedic.db";
                String currentDBPath = "//data//"+getPackageName()+"//databases//"+databaseName+"";
                String backupDBPath = "ayurvedic.db";
                Log.i("Import wala","1");
                File currentDB = new File(data, currentDBPath);
                Log.i("Import wala","2");
                File backupDB = new File(sd, backupDBPath);
                Log.i("Import wala","3");

                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(backupDB).getChannel();
                    Log.i("Import wala","4");
                    FileChannel dst = new FileOutputStream(currentDB).getChannel();
                    Log.i("Import wala","5");
                    dst.transferFrom(src, 0, src.size());
                    Log.i("Import wala","6");
                    src.close();
                    dst.close();
                }
            }

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Import Error", Toast.LENGTH_SHORT).show();


        }

    }
    private void openDAtabase() {
        DatabaseOpenHelper databaseOpenHelper = new DatabaseOpenHelper(Ayurvedic.this);
        SQLiteDatabase sqLiteDatabase = databaseOpenHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(QUERY, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            disease.add(cursor.getString(
                    cursor.getColumnIndexOrThrow(DatabaseContract.Disease.DISEASE_NAME)

            ));
            cursor.moveToNext();
        }
        cursor.close();
        sqLiteDatabase.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
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
}
