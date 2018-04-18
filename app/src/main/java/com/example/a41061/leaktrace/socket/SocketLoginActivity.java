package com.example.a41061.leaktrace.socket;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.example.a41061.leaktrace.R;
import com.example.a41061.leaktrace.until.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author FanHongyu.
 * @since 18/4/9 19:04.
 * email fanhongyu@hrsoft.net.
 */

public class SocketLoginActivity extends AppCompatActivity {


    @BindView(R.id.edit_user_id)
    EditText editUserId;
    @BindView(R.id.btn_login_chat_room)
    Button btnLoginChatRoom;


    public static final String EMPTY = "";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket_login);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.btn_login_chat_room)
    public void onViewClicked() {
        if (isDataTrue()) {
            ClientActivity.startClientActivity(this, editUserId.getText().toString().trim());
            finish();
        }
    }


    private boolean isDataTrue() {

        if (EMPTY.equals(editUserId.getText().toString())) {
            ToastUtil.showToast("用户名不能为空");
            return false;
        }
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }


    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);
    }
}
