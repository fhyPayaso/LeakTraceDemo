package com.example.a41061.leaktrace.chat.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.example.a41061.leaktrace.R;
import com.example.a41061.leaktrace.until.ToastUtil;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddFriendActivity extends AppCompatActivity {

    @BindView(R.id.edit_search_name)
    EditText editSearchName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        ButterKnife.bind(this);
        setTitle("添加好友");
    }


    //添加好友点击事件
    @OnClick(R.id.btn_add_friend)
    public void onViewClicked() {

        try {
            EMClient.getInstance().contactManager().addContact(editSearchName.getText().toString().trim(),"123");
            ToastUtil.showToast("添加成功");
            finish();

        } catch (HyphenateException e) {
            e.printStackTrace();
            ToastUtil.showToast(e.toString());
        }


    }
}
