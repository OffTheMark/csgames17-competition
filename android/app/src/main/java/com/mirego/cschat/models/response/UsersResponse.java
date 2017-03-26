package com.mirego.cschat.models.response;

import com.mirego.cschat.models.User;

import java.util.List;

/**
 * Created by Marc-Antoine on 25/03/2017.
 */

public class UsersResponse {
    private List<User> users;

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }
}
