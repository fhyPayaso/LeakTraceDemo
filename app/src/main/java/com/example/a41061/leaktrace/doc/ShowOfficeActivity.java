package com.example.a41061.leaktrace.doc;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RelativeLayout;

import com.example.a41061.leaktrace.R;
import com.example.a41061.leaktrace.until.FileUtil;
import com.tencent.smtt.sdk.TbsReaderView;

import java.io.File;

/**
 * @author FanHongyu.
 * @since 18/3/29 10:51.
 * email fanhongyu@hrsoft.net.
 */

public class ShowOfficeActivity extends AppCompatActivity implements TbsReaderView.ReaderCallback {


    private TbsReaderView mTbsReaderView;
    private OfficeModel mOfficeModel;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_office);

        mTbsReaderView = new TbsReaderView(this, this);
        RelativeLayout rootRl = (RelativeLayout) findViewById(R.id.rl_show_office);
        rootRl.addView(mTbsReaderView, new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout
                        .LayoutParams.MATCH_PARENT));


        mOfficeModel = (OfficeModel) getIntent().getSerializableExtra("office");
        displayFile();
    }

    @Override
    public void onCallBackAction(Integer integer, Object o, Object o1) {

    }


    private void displayFile() {


        initTbs();

        if (mOfficeModel != null) {

            Bundle bundle = new Bundle();
            bundle.putString("filePath", mOfficeModel.getPath());
            bundle.putString("tempPath", Environment.getExternalStorageDirectory().getPath());
            boolean result = mTbsReaderView.preOpen(mOfficeModel.getType(), false);
            if (result) {
                mTbsReaderView.openFile(bundle);

            }
        }
    }


    private void initTbs() {

        String tbsReaderPath = "/storage/emulated/0/TbsReaderTemp";
        File tbsReaderFile = new File(tbsReaderPath);



        if(!tbsReaderFile.exists()) {

            boolean isOK = tbsReaderFile.mkdir();

            Log.i("tbs", "initTbs: "+"创建成功"+isOK);
        }



    }


    public static void startActivity(Context context, OfficeModel officeModel) {
        Intent intent = new Intent(context, ShowOfficeActivity.class);

        intent.putExtra("office", officeModel);
        context.startActivity(intent);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTbsReaderView.onStop();
    }
}
