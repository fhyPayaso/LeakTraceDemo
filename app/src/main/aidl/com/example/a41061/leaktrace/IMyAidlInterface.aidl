// IMyAidlInterface.aidl
package com.example.a41061.leaktrace;

// Declare any non-default types here with import statements
import com.example.a41061.leaktrace.service.TestBean;

interface IMyAidlInterface {

    void addTestBean(in TestBean bean);

    List<TestBean> getList();
}
