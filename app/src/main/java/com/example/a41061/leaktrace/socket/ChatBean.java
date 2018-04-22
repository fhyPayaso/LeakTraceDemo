package com.example.a41061.leaktrace.socket;

/**
 * @author FanHongyu.
 * @since 18/4/9 12:37.
 * email fanhongyu@hrsoft.net.
 */

public class ChatBean {

    private String content;
    private String name;
    private long time;
    private int type;

    public ChatBean(String content, String name,int type) {
        this.content = content;
        this.name = name;
        this.type = type;
        time = System.currentTimeMillis();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getTime() {
        return time;
    }
}