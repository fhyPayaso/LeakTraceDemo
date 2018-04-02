package com.example.a41061.leaktrace.Factory;

/**
 * @author FanHongyu.
 * @since 18/3/22 20:24.
 * email fanhongyu@hrsoft.net.
 */

public class Chair implements Product {

    @Override
    public void getProductName() {
        System.out.print("这是椅子");
    }
}
