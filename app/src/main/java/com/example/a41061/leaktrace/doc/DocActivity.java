package com.example.a41061.leaktrace.doc;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.a41061.leaktrace.R;
import com.example.a41061.leaktrace.until.FileUtil;

/**
 * @author FanHongyu.
 * @since 18/3/16 20:43.
 * email fanhongyu@hrsoft.net.
 */

public class DocActivity extends AppCompatActivity {


    private static final String TAG = "DocActivity";
    private DocBean docBean = new DocBean();
    private int num = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc);


        FileUtil.verifyStoragePermissions(this);
        //queryFiles();
    }


}
