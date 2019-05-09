package com.bytedance.fanhongyu.comtwo;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.bytedance.fanhongyu.comoneapi.ComOneService;
import com.bytedance.fanhongyu.comtwoapi.ComTwoService;
import com.bytedance.fanhongyu.core.Graph;

/**
 * @author fanhongyu
 * @since 2019/5/7 2:29 PM
 */
public class ComTwoServiceImpl implements ComTwoService {


    public ComTwoServiceImpl() {

    }

    @Override
    public void printComTwo(String s) {
        Log.i("ComService", "printComTwo: " + s);
//        ComOneService comOneService = (ComOneService) Graph.getInstance().get(ComOneService.class);
//        if (comOneService != null) {
//            comOneService.printComOne("");
//        }
    }

    @Override
    public void startTwoActivity(Context context) {
        Intent intent = new Intent(context, TwoActivity.class);
        context.startActivity(intent);
    }
}
