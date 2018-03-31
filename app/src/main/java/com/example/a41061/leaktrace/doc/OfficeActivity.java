package com.example.a41061.leaktrace.doc;

import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.a41061.leaktrace.R;
import com.example.a41061.leaktrace.until.FileUtil;
import com.tencent.smtt.sdk.TbsReaderView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author FanHongyu.
 * @since 18/3/16 20:43.
 * email fanhongyu@hrsoft.net.
 */


public class OfficeActivity extends AppCompatActivity{


    private static final String TAG = "OfficeActivity";
    @BindView(R.id.tab_office)
    TabLayout mTabLayout;
    @BindView(R.id.pager_office)
    ViewPager mViewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc);
        ButterKnife.bind(this);
        initViewPager();
        FileUtil.verifyStoragePermissions(this);
    }


    private void initViewPager() {
        OfficePagerAdapter pagerAdapter = new OfficePagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setOffscreenPageLimit(5);
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
