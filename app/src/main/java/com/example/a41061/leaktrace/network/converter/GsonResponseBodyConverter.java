package com.example.a41061.leaktrace.network.converter;

import com.example.a41061.leaktrace.network.response.ApiException;
import com.example.a41061.leaktrace.network.response.ApiResponse;
import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * 响应时解析ResponseBody
 *
 * @author FanHongyu.
 * @since 18/1/17 22:40.
 * email fanhongyu@hrsoft.net.
 */

public class GsonResponseBodyConverter<T> implements Converter<ResponseBody,T>{

    private final Gson mGson;
    private final Type mType;

    public GsonResponseBodyConverter(Gson gson, Type type) {
        this.mGson = gson;
        this.mType = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        //将返回的json数据储存在String类型的response中
        String response = value.string();
        //将外层的数据解析到ApiResponse中
        ApiResponse apiResponse = mGson.fromJson(response, ApiResponse.class);
        //服务端设定0为正确的请求，故在此为判断标准
        if (apiResponse.getCode() == 0) {
            //直接解析，正确请求不会导致json解析异常
            return mGson.fromJson(response, mType);
        } else {
            //通过抛出自定义异常传递错误码及错误信息
            ApiException apiException = mGson.fromJson(response,ApiException.class);
            throw apiException;
        }
    }
}
