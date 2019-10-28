package com.example.myweight;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "MyWeight.db";
    public static final String TABLE_NAME = "user";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_BERAT_BADAN="beratbadan";
    public static final String COLUMN_TINGGI_BADAN="tinggibadan";

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        final String SQL = "CREATE TABLE "+ TABLE_NAME +"("+COLUMN_EMAIL+" TEXT NOT NULL PRIMARY KEY, "+COLUMN_BERAT_BADAN+" TEXT NOT NULL, "+COLUMN_TINGGI_BADAN+" TEXT NOT NULL)";
        db.execSQL(SQL);
    }

}
