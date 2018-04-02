package com.example.a41061.leaktrace.Factory;

/**
 * @author FanHongyu.
 * @since 18/3/22 21:29.
 * email fanhongyu@hrsoft.net.
 */


public class ChairFactory extends ProductFactory {
    @Override
    public Product createProduct() {
        return new Chair();
    }
}
