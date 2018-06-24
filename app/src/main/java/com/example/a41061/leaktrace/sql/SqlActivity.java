package com.example.a41061.leaktrace.sql;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * @author FanHongyu.
 * @since 18/6/18 15:11.
 * email fanhongyu@hrsoft.net.
 */

public class SqlActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        SQLiteDatabase db = openOrCreateDatabase("stu.db",1,null);



        String stuTable = "create table user_table(" +
                "id integer primary key autoincrement" +
                "" +
                "" +
                ")";





    }
}
