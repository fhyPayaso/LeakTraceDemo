package com.example.a41061.leaktrace.chat.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.TextView;

import com.example.a41061.leaktrace.R;
import com.example.a41061.leaktrace.chat.adapters.MessageAdapter;
import com.example.a41061.leaktrace.until.Utility;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChatActivity extends AppCompatActivity implements EMMessageListener {

    @BindView(R.id.rec_chat)
    RecyclerView recChat;

    @BindView(R.id.edit_chat_content)
    EditText editChatContent;
    @BindView(R.id.btn_send)
    TextView btnSend;
    private String chatId;
    private EMMessage emMessage;
    private EMConversation conversation;
    MessageAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
        setTitle("聊天");


        Bundle bundle = getIntent().getExtras();
        chatId = bundle.getString(FriendListActivity.USER_ID);
        //添加信息监听
        EMClient.getInstance().chatManager().addMessageListener(this);
        conversation = EMClient.getInstance().chatManager().getConversation(chatId,
                EMConversation.EMConversationType.Chat, true);



        adapter = new MessageAdapter(this,conversation);
        recChat.setAdapter(adapter);
        recChat.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        refresh();
    }

    @Override
    public void onMessageReceived(List<EMMessage> messages) {
        refresh();
    }

    @Override
    public void onCmdMessageReceived(List<EMMessage> messages) {

    }

    @Override
    public void onMessageRead(List<EMMessage> messages) {

    }

    @Override
    public void onMessageDelivered(List<EMMessage> messages) {

    }

    @Override
    public void onMessageChanged(EMMessage message, Object change) {

    }

    //刷新消息列表
    private void refresh() {
        if (adapter != null) {
            adapter.refresh();
        }
        scrollToBottom();
    }

    //让最新消息位于最底部
    private void scrollToBottom() {
        Utility.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                recChat.scrollToPosition(adapter.getItemCount() - 1);
            }
        }, 200);
    }

    @OnClick(R.id.btn_send)
    public void onViewClicked() {

        emMessage = EMMessage.createTxtSendMessage(editChatContent
                .getText().toString(), chatId);
        EMClient.getInstance().chatManager().sendMessage(emMessage);
        editChatContent.setText("");
        refresh();
    }
}
