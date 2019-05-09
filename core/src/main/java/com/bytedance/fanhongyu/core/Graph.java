package com.bytedance.fanhongyu.core;

import java.util.HashMap;
import java.util.Map;

/**
 * @author fanhongyu
 * @since 2019/5/7 2:33 PM
 */
public class Graph {


    private Map<Class, Object> map;

    private Object appComponent;

    private Graph() {
        map = new HashMap<>();
    }

    private static class GraphHolder {
        private static final Graph INSTANCE = new Graph();
    }

    public static Graph getInstance() {
        return GraphHolder.INSTANCE;
    }


    public void init(Object appComponent) {
        this.appComponent = appComponent;
    }


    public <T> T getGraph(Class<T> cls) {
        return (T) appComponent;
    }


    public void register(Class clazz, Object impl) {
        map.put(clazz, impl);
    }

    public Object get(Class clazz) {
        return map.get(clazz);
    }

}
