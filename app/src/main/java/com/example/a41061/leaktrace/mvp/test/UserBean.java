package com.example.a41061.leaktrace.mvp.test;

import java.io.Serializable;

/**
 * @author FanHongyu.
 * @since 18/4/22 18:34.
 * email fanhongyu@hrsoft.net.
 */

public class UserBean implements Serializable{


    private String username;
    private String password;

    public UserBean(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
