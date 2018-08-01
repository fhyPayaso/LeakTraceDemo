package com.example.a41061.leaktrace.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author FanHongyu.
 * @since 18/6/18 15:09.
 * email fanhongyu@hrsoft.net.
 */

public class SQLiteDbHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "myTest.db";
    public static final String TABLE_NAME = "student";


    private static final String STUDENTS_CREATE_TABLE_SQL = "create table " + TABLE_NAME + "("
            + "id integer primary key autoincrement,"
            + "name varchar(20) not null,"
            + "age integer not null"
            + ");";


    public SQLiteDbHelper(Context context) {
        // 传递数据库名与版本号给父类
        super(context, DB_NAME, null, DB_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(STUDENTS_CREATE_TABLE_SQL);
    }

    /**
     * 数据库版本变更时被调用
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
