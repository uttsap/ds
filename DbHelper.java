package com.afeastoffriends.doctorsaathi;

/**
 * Created by Acer-PC on 2/10/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;


public class DbHelper extends SQLiteOpenHelper {
    Context context;
    public static final String DATABASE_NAME = "BloodDonorsy";
    public static final String TABLE_NAME = "donorlist";
    public static final String COLOUMN_1_ID ="id";
    public static final String COLOUMN_2_NAME = "name";
    public static final String COLOUMN_3_PHONE = "phone";
    public static final String COLOUMN_4_EMAIL = "email";
    public static final String COLOUMN_5_BLOODGROUP = "bloodgroup";
    public static final String COLOUMN_6_LAST_DATE = "lastDate";
    public static final int VERSION = 1;
    int acheivedId;
    String nameS ,phoneS, emailS , bloodGroupStr, lastDateS;

    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ( " + COLOUMN_1_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLOUMN_2_NAME + " text, " + COLOUMN_3_PHONE + " text, " +
                    COLOUMN_4_EMAIL + " text, " + COLOUMN_5_BLOODGROUP + " text, "+ COLOUMN_6_LAST_DATE + " text);";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sp) {
        sp.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public long insert(AddContacts addContacts, com.afeastoffriends.doctorsaathi.DonorInformation donorInfo) {
        SQLiteDatabase sp=getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COLOUMN_2_NAME,donorInfo.name);
        cv.put(COLOUMN_3_PHONE,donorInfo.phone);
        cv.put(COLOUMN_4_EMAIL,donorInfo.email);
        cv.put(COLOUMN_5_BLOODGROUP,donorInfo.bloodGroup);
        cv.put(COLOUMN_5_BLOODGROUP,donorInfo.bloodGroup);
        cv.put(COLOUMN_6_LAST_DATE,donorInfo.lastDate);
        final long insert = sp.insert(TABLE_NAME, null, cv);
        sp.close();
        return insert;
    }

    public ArrayList<DonorInformation> getDatas(String SelctedBG) {
        ArrayList<DonorInformation> donorList = new ArrayList<>();
        String selectedBloodGroup=SelctedBG;
        Cursor cursor=null;
        SQLiteDatabase sp=getReadableDatabase();
        if(selectedBloodGroup=="All")
        {
            cursor = sp.query(TABLE_NAME, null, null, null, null, null, null);
        }
        else
        {
            String params[]=new String[]{selectedBloodGroup};
            cursor=  sp.query(TABLE_NAME, null,
                    COLOUMN_5_BLOODGROUP+" = ?", params,
                    null, null, null);
            // cursor=sp.rawQuery("select * from "+ TABLE_NAME+" where "+COLOUMN_5_BLOODGROUP+" ="+selectedBloodGroup,null);
        }

        cursor.moveToFirst();
        String s,s1,s2,s3,s4;
        int id;
        for (int i=0; i<cursor.getCount();i++)
        {
            id=cursor.getInt(cursor.getColumnIndex(COLOUMN_1_ID));
            s=cursor.getString(cursor.getColumnIndex(COLOUMN_2_NAME));
            s1=cursor.getString(cursor.getColumnIndex(COLOUMN_3_PHONE));
            s2=cursor.getString(cursor.getColumnIndex(COLOUMN_4_EMAIL));
            s3=cursor.getString(cursor.getColumnIndex(COLOUMN_5_BLOODGROUP));
            s4=cursor.getString(cursor.getColumnIndex(COLOUMN_6_LAST_DATE));



            /*DateFormat formatter ;
            //String s4 = "04/06/2016";
            Date date = null;
            formatter = new SimpleDateFormat("dd/MM/yyyy");

            try {
                date = formatter.parse(s4);
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

                donorList.add(new DonorInformation(id,s,s1,s2,s3,s4));
            }*/
            donorList.add(new DonorInformation(id,s,s1,s2,s3,s4));

            cursor.moveToNext();

        }
        sp.close();
        return donorList;
    }


    public int delete(int id)
    {
        SQLiteDatabase db=getWritableDatabase();
        final int delete = db.delete(TABLE_NAME, COLOUMN_1_ID + " =" + id, null);
        db.close();
        return delete;
    }

    public int update(Context addContacts, DonorInformation donorInfo) {

        SQLiteDatabase db=getWritableDatabase();
        Context context=addContacts;
        ContentValues cv=new ContentValues();
        cv.put(COLOUMN_2_NAME,donorInfo.name);
        cv.put(COLOUMN_3_PHONE,donorInfo.phone);
        cv.put(COLOUMN_4_EMAIL,donorInfo.email);
        cv.put(COLOUMN_5_BLOODGROUP,donorInfo.bloodGroup);
        cv.put(COLOUMN_6_LAST_DATE,donorInfo.lastDate);
        Log.e("update test",donorInfo.id+"");
        Log.e("update name",donorInfo.name.toString().trim()+"");

        // String selectQuery = "UPDATE  donorlist SET name='uttam' WHERE id="+donorInfo.id+"";

        final int update = db.update(TABLE_NAME, cv, COLOUMN_1_ID + " = ?",new String[]{String.valueOf(donorInfo.id)});

        db.close();
        return 0;

    }
}
