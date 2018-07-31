package com.example.a41061.leaktrace.service;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a41061.leaktrace.IMyAidlInterface;
import com.example.a41061.leaktrace.R;

import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author FanHongyu.
 * @since 18/6/13 20:31.
 * email fanhongyu@hrsoft.net.
 */

public class ServiceActivity extends AppCompatActivity {


    @BindView(R.id.txt_aidl)
    TextView txtAidl;
    @BindView(R.id.img_anim)
    ImageView imgAnim;

    private IMyAidlInterface mIMyAidlInterface;


    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mIMyAidlInterface = IMyAidlInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mIMyAidlInterface = null;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        ButterKnife.bind(this);

        Intent intent = new Intent(ServiceActivity.this, TestService.class);
        bindService(intent, mConnection, BIND_AUTO_CREATE);
        findViewById(R.id.btn_start).setOnClickListener(v -> {

            Random random = new Random();
            String str = random.nextInt() + "aaa";
            TestBean testBean = new TestBean(str);
            try {
                mIMyAidlInterface.addTestBean(testBean);
                List<TestBean> list = mIMyAidlInterface.getList();
                txtAidl.setText(list.toString());
            } catch (RemoteException e) {
                e.printStackTrace();
            }


        });
        findViewById(R.id.btn_end).setOnClickListener(v -> startAnim());
    }


    private void startAnim() {

        ObjectAnimator scaleX = ObjectAnimator.ofFloat(imgAnim,"scaleX",0,1);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(imgAnim,"scaleY",0,1);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(scaleX).with(scaleY);
        animatorSet.start();

    }
}
