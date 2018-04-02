package com.example.a41061.leaktrace.Factory;

/**
 * @author FanHongyu.
 * @since 18/3/22 20:22.
 * email fanhongyu@hrsoft.net.
 */

public class Desk implements Product {
    @Override
    public void getProductName() {
        System.out.print("这是桌子");
    }
}
