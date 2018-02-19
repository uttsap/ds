package com.afeastoffriends.doctorsaathi;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class Details extends AppCompatActivity {
    String disease;
    String symptoms;
    String cures;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView textView = (TextView) findViewById(R.id.textView);
        TextView symptom = (TextView) findViewById(R.id.symptoms);
        TextView cure = (TextView) findViewById(R.id.cure);


        openDAtabase(Integer.toString(getIntent().getIntExtra("position",0)));
        textView.setText(disease);
        symptom.setText(symptoms);
        cure.setText(cures);
    }
    private void openDAtabase(String key) {
        DatabaseOpenHelper databaseOpenHelper = new DatabaseOpenHelper(Details.this);
        SQLiteDatabase sqLiteDatabase = databaseOpenHelper.getReadableDatabase();
        String QUERY = "SELECT "+DatabaseContract.Disease.DISEASE_NAME+","+DatabaseContract.Disease.SYMPTOMS+","+DatabaseContract.Disease.CURE+" FROM "+DatabaseContract.Disease.TABLE_NAME+" WHERE _ID"+"="+key;

        Cursor cursor = sqLiteDatabase.rawQuery(QUERY, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            disease=cursor.getString(
                    cursor.getColumnIndexOrThrow(DatabaseContract.Disease.DISEASE_NAME)

            );
            symptoms=cursor.getString(
                    cursor.getColumnIndexOrThrow(DatabaseContract.Disease.SYMPTOMS)

            );
            cures=cursor.getString(
                    cursor.getColumnIndexOrThrow(DatabaseContract.Disease.CURE)

            );
            cursor.moveToNext();
        }
        cursor.close();
        sqLiteDatabase.close();
    }

}
