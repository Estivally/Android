package com.example.contacts;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.nfc.Tag;
import android.util.Log;

import androidx.annotation.Nullable;
/*数据库*/
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "DatebaseHelper";
    String str;
/*构造函数*/
    public DatabaseHelper(Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.VERSION_CODE);
    }
/*重写创建函数*/
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "创建");
        /*str赋予数据库建立语句，创建5个列*/
        String str = "create table " + Constants.TABLE_NAME + "(id integer primary key autoincrement,name varchar,Phone varchar,Company varchar,Email varchar)";
        db.execSQL(str);//执行创建数据库语句
    }
/*数据库更新暂时无用*/
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "update");
    }
}
