package com.example.a41061.leaktrace.socket;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a41061.leaktrace.R;
import com.example.a41061.leaktrace.chat.adapters.MessageAdapter;
import com.hyphenate.chat.EMMessage;

import java.util.List;

/**
 * @author FanHongyu.
 * @since 18/4/9 19:38.
 * email fanhongyu@hrsoft.net.
 */

public class SocketChatAdapter extends RecyclerView.Adapter<SocketChatAdapter.ItemHolder> {


    private static final int MESSAGE_TYPE_RECV_TXT = 0;
    private static final int MESSAGE_TYPE_SENT_TXT = 1;


    private List<ChatBean> messages;
    private LayoutInflater inflater;
    private Context context;


    public SocketChatAdapter(Context context, List<ChatBean> messages) {
        this.messages = messages;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        ItemHolder itemHolder = null;
        switch (viewType) {
            case MESSAGE_TYPE_RECV_TXT:
                view = inflater.inflate(R.layout.item_recv_message, parent, false);
                itemHolder = new ItemHolder(view);
                break;
            case MESSAGE_TYPE_SENT_TXT:
                view = inflater.inflate(R.layout.item_send_message, parent, false);
                itemHolder = new ItemHolder(view);
                break;
            default:
        }
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        holder.mTextView.setText(messages.get(position).getContent());
    }


    @Override
    public int getItemViewType(int position) {

        ChatBean chatBean = messages.get(position);
        if (chatBean == null) {
            return -1;
        } else {
            return chatBean.getType() == 0 ? MESSAGE_TYPE_RECV_TXT : MESSAGE_TYPE_SENT_TXT;
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    class ItemHolder extends RecyclerView.ViewHolder {
        TextView mTextView;
        public ItemHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.txt_message);
        }
    }

}
