package com.example.a41061.leaktrace.service;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.example.a41061.leaktrace.IMyAidlInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * @author FanHongyu.
 * @since 18/6/13 20:28.
 * email fanhongyu@hrsoft.net.
 */

public class TestService extends Service {

    public static final int SERVICE_ID = 1001;

    private List<TestBean> mList;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        mList = new ArrayList<>();

        return mIBinder;

    }



    private IBinder mIBinder = new IMyAidlInterface.Stub() {
        @Override
        public void addTestBean(TestBean bean) throws RemoteException {
            mList.add(bean);
        }

        @Override
        public List<TestBean> getList() throws RemoteException {
            return mList;
        }
    };
}
