package com.bytedance.fanhongyu.comone;

import android.util.Log;
import com.bytedance.fanhongyu.comoneapi.ComOneService;
import com.bytedance.fanhongyu.comtwoapi.ComTwoService;
import com.bytedance.fanhongyu.core.Graph;

import javax.inject.Inject;

/**
 * @author fanhongyu
 * @since 2019/5/7 2:25 PM
 */
public class ComOneServiceImpl implements ComOneService {


    public ComOneServiceImpl() {
        Graph.getInstance().getGraph(ComOneComponent.class).inject(this);
    }

    @Override
    public void printComOne(String s) {
        Log.i("ComService", "printComOne: ");
    }
}
