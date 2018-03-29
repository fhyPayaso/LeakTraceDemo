package com.example.a41061.leaktrace.chat.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.example.a41061.leaktrace.R;
import com.example.a41061.leaktrace.network.RetrofitService;
import com.example.a41061.leaktrace.network.response.ApiException;
import com.example.a41061.leaktrace.network.response.ApiResponse;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author FanHongyu.
 * @since 18/1/31 15:37.
 * email fanhongyu@hrsoft.net.
 */

public class TestActivity extends AppCompatActivity {


    @BindView(R.id.btn_rx_login)
    Button btnRxLogin;

    private Retrofit retrofit;

    public final static String APP_SERVER_BASE_URL = "http://www.thmaoqiu.cn/saiyou/public/index.php/";

    public final static String TAG = "RxJavaTest";

    private String phone = "987654325";
    private String password = "987654321";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);


        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(APP_SERVER_BASE_URL)
                .build();
        //loginAndRegister();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                break;
            default:
                break;
        }
        return true;
    }

    @OnClick(R.id.btn_rx_login)
    public void onViewClicked() {


        loginAndRegister();

    }


    private void rxTest() {

        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {

                e.onNext("事件1");
                e.onNext("事件2");
                e.onNext("事件3");
                e.onComplete();
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

                Log.d(TAG, "onSubscribe: 达成订阅");
            }

            @Override
            public void onNext(String s) {

                Log.d(TAG, "onNext: 响应了" + s);
            }

            @Override
            public void onError(Throwable e) {

                Log.d(TAG, "onError: 执行错误");
            }

            @Override
            public void onComplete() {

                Log.d(TAG, "onComplete: 执行完成");
            }
        });
    }


    private void loginAndRegister() {

        RetrofitService service = retrofit.create(RetrofitService.class);
        Observable<ApiResponse> register = service.register(phone, password);
        final Observable<ApiResponse> login = service.login(phone, password);


        register.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<ApiResponse>() {
                    @Override
                    public void accept(ApiResponse apiResponse) throws Exception {

                        Log.d(TAG, "注册成功");
                    }
                })
                .observeOn(Schedulers.io())
                .flatMap(new Function<ApiResponse, ObservableSource<ApiResponse>>() {
                    @Override
                    public ObservableSource<ApiResponse> apply(ApiResponse apiResponse) throws Exception {
                        return login;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse>() {
                    @Override
                    public void accept(ApiResponse apiResponse) throws Exception {

                        Log.d(TAG, "登录成功");
                    }
                });
    }


    public void register() {


        retrofit.create(RetrofitService.class)
                .register(phone, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ApiResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ApiResponse apiResponse) {

                        Log.d(TAG, "onNext: 注册成功，开始登录");
                        login();
                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.d(TAG, "onError: 注册失败");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    public void login() {

        retrofit.create(RetrofitService.class)
                .login(phone, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ApiResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ApiResponse apiResponse) {

                        Log.d(TAG, "onNext: 登录成功");
                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.d(TAG, "onError: 登录失败");
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
}
