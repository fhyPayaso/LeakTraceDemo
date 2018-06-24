package com.example.a41061.leaktrace.service;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author FanHongyu.
 * @since 18/6/23 19:45.
 * email fanhongyu@hrsoft.net.
 */

public class TestBean implements Parcelable {



    protected TestBean(Parcel in) {
    }

    public static final Creator<TestBean> CREATOR = new Creator<TestBean>() {
        @Override
        public TestBean createFromParcel(Parcel in) {
            return new TestBean(in);
        }

        @Override
        public TestBean[] newArray(int size) {
            return new TestBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
