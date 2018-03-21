package com.example.a41061.leaktrace.chat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.a41061.leaktrace.MainActivity;
import com.example.a41061.leaktrace.R;
import com.example.a41061.leaktrace.until.AppNetWorkUtil;
import com.example.a41061.leaktrace.until.ToastUtil;
import com.example.a41061.leaktrace.until.Utility;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.squareup.haha.perflib.Main;
import com.squareup.haha.trove.THash;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {


    private EditText editUsername;
    private EditText editPassword;
    private final String TAG = "find";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setTitle("ChatDemo");
        initView();
    }


    private void initView() {

        editUsername = (EditText) findViewById(R.id.edit_username);
        editPassword = (EditText) findViewById(R.id.edit_password);


        findViewById(R.id.btn_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                register();

            }
        });


        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }


    private void register() {

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    EMClient.getInstance().createAccount(editUsername.getText().toString().trim(), editPassword.getText()
                            .toString().trim());
                    Log.i(TAG, "register: 注册成功");
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    Log.i(TAG, "register: 注册失败" + e.getErrorCode() + " , " + e.getMessage());
                }
            }
        }).start();


    }

    private void login() {

        EMClient.getInstance().login(editUsername.getText().toString().trim(), editPassword.getText().toString().trim
                (), new EMCallBack() {
            @Override
            public void onSuccess() {

                //保证进入主页面后本地会话和群组都 load 完毕
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                startActivity(new Intent(LoginActivity.this, FriendListActivity.class));
                Log.i(TAG, "onSuccess: 登录成功");
            }

            @Override
            public void onError(int code, String error) {

                Log.i(TAG, "onError: 登录失败，" + error);
            }

            @Override
            public void onProgress(int progress, String status) {

            }
        });


    }


}
