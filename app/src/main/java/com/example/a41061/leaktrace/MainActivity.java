package com.example.a41061.leaktrace;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.example.a41061.leaktrace.until.DialogActivity;

public class MainActivity extends AppCompatActivity {


    private  static final String TAG = "MainActivity";
    private AlertDialog mAlertDialog;
    private AlertDialog mFullAlertDialog;
    private TextView mTextView;








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        findViewById(R.id.btn_show_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i(TAG, "onClick: 点击进入FirstActivity按钮");


                //startActivity(new Intent(MainActivity.this,FirstActivity.class));
            }
        });


        findViewById(R.id.btn_show_full_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i(TAG, "onClick: 显示全屏dialog");
                showFullDialog();
            }
        });


        findViewById(R.id.btn_show_dialog_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: 显示DialogActivity");
                startActivity(new Intent(MainActivity.this, DialogActivity.class));
            }
        });

        


        Log.i(TAG, "onCreate:");
    }


    private void showAlertDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("这是标准dialog");
        mAlertDialog = builder.create();
        mAlertDialog.show();
    }



    private void showFullDialog() {



        mFullAlertDialog = new AlertDialog.Builder(MainActivity.this).create();
        mFullAlertDialog.getWindow().setContentView(R.layout.dialog_full);
        mFullAlertDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mFullAlertDialog.show();
        mFullAlertDialog.setMessage("这是全屏dialog");
    }





    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: ");
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ");
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: ");
    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop: ");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: ");
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Log.i(TAG, "onSaveInstanceState: ");
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG, "onRestoreInstanceState: ");
    }


    
    
    
    
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        Log.i(TAG, "onConfigurationChanged: "+newConfig.orientation);
    }
}
