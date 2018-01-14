package com.example.a41061.leaktrace.chat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.a41061.leaktrace.R;
import com.example.a41061.leaktrace.chat.adapters.BaseRecyclerViewAdapter;
import com.example.a41061.leaktrace.chat.adapters.ContactListAdapter;
import com.example.a41061.leaktrace.until.ToastUtil;
import com.example.a41061.leaktrace.until.Utility;
import com.getbase.floatingactionbutton.AddFloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FriendListActivity extends AppCompatActivity implements BaseRecyclerViewAdapter.OnItemClicked<String> {

    @BindView(R.id.fab_add_friend)
    FloatingActionButton fabAddFriend;
    @BindView(R.id.fab_sign_out)
    FloatingActionButton fabSignOut;
    private List<String> userNames;
    private ContactListAdapter adapter;
    public static String USER_ID = "USER_ID";

    @BindView(R.id.rec_contact)
    RecyclerView recContact;
    @BindView(R.id.swipe_contact_list)
    SwipeRefreshLayout swipeContactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        ButterKnife.bind(this);
        initView();
        initRec();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        initView();
        initRec();

        Utility.runOnNewThread(new Runnable() {
            @Override
            public void run() {
                try {
                    userNames = EMClient.getInstance().contactManager().getAllContactsFromServer();
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        });
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        swipeContactList.setRefreshing(false);
                        adapter.setData(userNames);
                    }
                });
            }
        }, 2000);

    }

    private void initView() {

        setTitle("好友列表");
        fabAddFriend.setTitle("添加好友");
        fabSignOut.setTitle("退出登录");
        userNames = new ArrayList<>();
        initSwipe();

        //初始化好友列表
        Utility.runOnNewThread(new Runnable() {
            @Override
            public void run() {
                try {
                    userNames = EMClient.getInstance().contactManager().getAllContactsFromServer();
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        });
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        swipeContactList.setRefreshing(false);
                        adapter.setData(userNames);
                    }
                });
            }
        }, 2000);

    }


    //初始化Swipe
    private void initSwipe() {
        swipeContactList.setRefreshing(true);
        swipeContactList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //每次刷新重新拉取好友列表
                Utility.runOnNewThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //获得好友列表
                            userNames = EMClient.getInstance().contactManager().getAllContactsFromServer();
                        } catch (HyphenateException e) {
                            e.printStackTrace();
                        }
                    }
                });

                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                swipeContactList.setRefreshing(false);
                                adapter.setData(userNames);
                            }
                        });
                    }
                }, 2000);
            }
        });
    }


    private void initRec() {

        Utility.runOnNewThread(new Runnable() {
            @Override
            public void run() {
                try {
                    userNames = EMClient.getInstance().contactManager().getAllContactsFromServer();
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    ToastUtil.showToast(e.toString());
                }
            }
        });
        adapter = new ContactListAdapter(this, userNames);
        adapter.setOnItemClickedListener(this);
        recContact.setAdapter(adapter);
        recContact.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }


    @Override
    public void onItemClicked(String s, BaseRecyclerViewAdapter.ViewHolder holder) {

        Intent intent = new Intent(this, ChatActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(USER_ID, s);
        intent.putExtras(bundle);
        startActivity(intent);

    }

    //添加好友点击事件
    @OnClick(R.id.fab_add_friend)
    public void onFabAddFriendClicked() {
        startActivity(new Intent(FriendListActivity.this,AddFriendActivity.class));
    }

    //退出账户点击事件
    @OnClick(R.id.fab_sign_out)
    public void onFabSignOutClicked() {


        EMClient.getInstance().logout(true, new EMCallBack() {
            @Override
            public void onSuccess() {
                ToastUtil.showToast("退出成功");
                startActivity(new Intent(FriendListActivity.this, LoginActivity.class));
                finish();
            }

            @Override
            public void onError(int code, String error) {
                ToastUtil.showToast("退出失败，请稍后再试");
            }

            @Override
            public void onProgress(int progress, String status) {

            }
        });


    }
}
