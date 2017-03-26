package com.mirego.cschat.controller;

import android.content.Context;

import com.mirego.cschat.models.User;
import com.mirego.cschat.models.response.ConversationsResponse;
import com.mirego.cschat.models.response.UsersResponse;
import com.mirego.cschat.services.CSChatService;
import com.mirego.cschat.services.StorageService;
import com.mirego.cschat.viewdatas.UserViewData;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

public class UsersController {

    private final CSChatService chatService;
    private final StorageService storageService;
    private Context context;

    public UsersController(CSChatService chatService, StorageService storageService, Context context) {
        this.chatService = chatService;
        this.storageService = storageService;
        this.context = context;
    }

    public Flowable<List<UserViewData>> getUsers() {
        return chatService.fetchUsers()
                .map(new Function<List<User>, List<UserViewData>>() {
                    @Override
                    public List<UserViewData> apply(@NonNull List<User> users) throws Exception {
                        List<UserViewData> conversationViewDatum = new ArrayList<>();
                        for (User user : users) {
                            conversationViewDatum.add(new UserViewData(user, context));
                        }
                        return conversationViewDatum;
                    }
                });
    }
}
