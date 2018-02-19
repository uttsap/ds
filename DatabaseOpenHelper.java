package com.afeastoffriends.doctorsaathi;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lognod on 9/17/16.
 */
public class DatabaseOpenHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ayurvedic.db";
    private static final String TEXT_TYPE = " TEXT";

    private static final String COMMA_SEP = ",";
    private static final String DISEASE_SCHEMA = "CREATE TABLE " + DatabaseContract.Disease.TABLE_NAME + " (" +
            DatabaseContract.Disease._ID + " INTEGER PRIMARY KEY," +
            DatabaseContract.Disease.DISEASE_CODE + TEXT_TYPE+COMMA_SEP +
            DatabaseContract.Disease.DISEASE_NAME + TEXT_TYPE + COMMA_SEP +
            DatabaseContract.Disease.SYMPTOMS + TEXT_TYPE + COMMA_SEP +
            DatabaseContract.Disease.CURE + TEXT_TYPE +
            " )";


    private static final String DISEASE_DELETE =
            "DROP TABLE IF EXISTS " + DatabaseContract.Disease.TABLE_NAME;

    Context context;


    public DatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DISEASE_SCHEMA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DISEASE_DELETE);
        onCreate(sqLiteDatabase);
    }

}
