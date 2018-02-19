package com.afeastoffriends.doctorsaathi;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class FAMainActivity extends AppCompatActivity {

    DatabaseAccess databaseAccess;

    private ListView lv;
    DatabaseAccess myDb;
    //SearchView s1;
    Boolean onClick=true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_famain);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("FIRST AID");

        this.lv = (ListView) findViewById(R.id.listView);
        // s1 = (SearchView) findViewById(R.id.srch1);

        databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        List<String> titles = databaseAccess.Title();
        final ArrayList<String> new_list = new ArrayList<String>();
        //databaseAccess.close();

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, titles);
        this.lv.setAdapter(adapter);
        //final ArrayAdapter<String> nayaadapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,new_list);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //String temp = String.valueOf(adapterView.getItemAtPosition(i));
                int j = i + 1;
                new_window(j);


            }
        });

//        s1.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                adapter.getFilter().filter(newText);
//                return false;
//            }
//        });
//
//
//
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


    public void new_window(int j)
    {
        Cursor crs = databaseAccess.getData(j);

        StringBuffer buffer_1 = new StringBuffer();
        StringBuffer buffer_2 = new StringBuffer();

        while(crs.moveToNext())
        {
            buffer_1.append(crs.getString(1));
            buffer_2.append(crs.getString(2));
        }

        //Toast.makeText(this , buffer_1 , Toast.LENGTH_LONG);

        Intent intent = new Intent(FAMainActivity.this , FADetailActivity.class);
        intent.putExtra("Title" , buffer_1.toString());
        intent.putExtra("Message" , buffer_2.toString());
        startActivity(intent);


    }

    @Override
    public void onBackPressed() {
        databaseAccess.close();
        super.onBackPressed();
    }
}
