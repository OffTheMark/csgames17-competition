package com.mirego.cschat.viewdatas;

import android.content.Context;
import android.text.format.DateUtils;

import com.mirego.cschat.models.Message;
import com.mirego.cschat.models.User;

import java.util.Date;

public class UserViewData {

    private final User user;
    private Context context;

    public UserViewData(User user, Context context) {
        this.user = user;
        this.context = context;
    }

    public String username() {
        if (user != null) {
            return user.getUsername();
        }
        return null;
    }

    public String avatarUrl() {
        if (user != null) {
            return user.getAvatarUrl();
        }
        return null;
    }
}
