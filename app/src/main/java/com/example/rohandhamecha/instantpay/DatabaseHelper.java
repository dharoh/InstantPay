package com.example.rohandhamecha.instantpay;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.method.HideReturnsTransformationMethod;

/**
 * Created by Keyur on 1/29/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "InstantPay.db";
    public static final String TABLE_NAME = "User";
    public static final String COL1 = "mobile_no";
    public static final String COL2 = "email";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE "+TABLE_NAME+" (MOBILE_NO TEXT PRIMARY KEY,EMAIL TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean insertData(String mobile_no,String email){

        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);

        onCreate(db);

        ContentValues cv = new ContentValues();
        cv.put(COL1,mobile_no);
        cv.put(COL2,email);

        long reult = db.insert(TABLE_NAME,null,cv);

        if(reult == -1)
            return  false;
        else
            return  true;
    }

    public Cursor getAllData(){

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+TABLE_NAME,null);
        return res;
    }
}
