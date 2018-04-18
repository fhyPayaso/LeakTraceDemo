package com.example.a41061.leaktrace.socket;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * @author fhyPayaso
 * @since 2018/4/9 on 上午9:32
 * fhyPayaso@qq.com
 */
public class ClientActivity extends AppCompatActivity{


    TextView mTextView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initSocket() {
        try {
            socketUser1 = new Socket(URL_PATH, SOCKET_PORT);            //用户1的客户端Socket
            socketUser2 = new Socket(URL_PATH, SOCKET_PORT);            //用户2的客户端Socket
            clientThread = new ClientThread();        //客户端启动ClientThread线程，读取来自服务器的数据
            clientThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    private class MyHandler extends Handler {
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();            //获取Message中发送过来的数据
            String content = bundle.getString(KEY_CONTENT);
            MyContent.setContent(content);            //保存聊天记录
            mTextView.setText(MyContent.getContent());
        }
    }


}
