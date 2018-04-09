package com.example.a41061.leaktrace.socket;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.a41061.leaktrace.R;
import com.example.a41061.leaktrace.until.ToastUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author FanHongyu.
 * @since 18/4/9 13:09.
 * email fanhongyu@hrsoft.net.
 */

public class ClientActivity extends AppCompatActivity {


    @BindView(R.id.rec_chat)
    RecyclerView recChat;
    @BindView(R.id.edit_chat_content)
    EditText mEditContent;
    @BindView(R.id.btn_send)
    TextView mBtnSend;

    public static final String TAG = "ClientActivity";
    private static final String SERVERIP = "192.168.1.15";
    private String clientIp = "";
    private static final int SERVERPORT = 9999;


    private SocketChatAdapter mSocketChatAdapter;
    private List<ChatBean> mChatBeanList;
    private Socket socket = null;
    private BufferedReader in = null;
    private PrintWriter out = null;
    private String msg = "";
    private String userId = "";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        ButterKnife.bind(this);
        initChat();
        initRec();
        initSocket();
    }


    private void initChat() {

        userId = getIntent().getStringExtra("userId");
    }


    private void initRec() {

        mChatBeanList = new ArrayList<>();
        mSocketChatAdapter = new SocketChatAdapter(this, mChatBeanList);
        recChat.setAdapter(mSocketChatAdapter);
        recChat.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }


    private void initSocket() {


        new Thread(new Runnable() {
            @Override
            public void run() {

                try {

                    Log.i(TAG, "run: 开始连接");
                    socket = new Socket(SERVERIP, SERVERPORT);
                    Log.i(TAG, "run: 连接成功");

                    //创建输出实例
                    out = new PrintWriter(socket.getOutputStream());
                    //创建输入实例
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                    //反馈连接成功信息
                    out.println(userId + "进入聊天室");
                    out.flush();


                    while (true) {
                        if (socket.isConnected() && !socket.isInputShutdown()) {
                            try {
                                if ((msg = in.readLine()) != null) {
                                    //接收服务端信息

                                    //Log.i(TAG, "run: " + msg);

                                    Message message = mHandler.obtainMessage();
                                    message.what = 1;
                                    message.obj = msg;
                                    mHandler.sendMessage(message);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    /**
     * 发送消息内容
     */
    @OnClick(R.id.btn_send)
    public void onViewClicked() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                if (!"".equals(mEditContent.getText().toString().trim())) {

                    //获取输入框内容
                    String content = mEditContent.getText().toString();
                    out.println(content);
                    out.flush();
                } else {
                    ToastUtil.showToast("发送内容不能为空");
                }
            }
        }).start();
        //清空输入框
        mEditContent.setText("");
    }


    public Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == 1) {
                String newCotent = (String) msg.obj;
                Log.i(TAG, "handleMessage: " + newCotent);
//                String oldContent = txtMessage.getText().toString();
//                txtMessage.setText(oldContent + "\n" + newCotent);


                mChatBeanList.add(new ChatBean(newCotent, "aaa", 0));
                mSocketChatAdapter.notifyDataSetChanged();
                recChat.scrollToPosition(mChatBeanList.size() - 1);
            }
        }
    };


    public static void startClientActivity(Context context, String userId) {
        Intent intent = new Intent(context, ClientActivity.class);
        intent.putExtra("userId", userId);
        context.startActivity(intent);
    }


}
