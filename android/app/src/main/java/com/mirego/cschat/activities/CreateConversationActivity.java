package com.mirego.cschat.activities;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.mirego.cschat.CSChatApplication;
import com.mirego.cschat.R;
import com.mirego.cschat.adapters.UserAdapter;
import com.mirego.cschat.controller.UsersController;
import com.mirego.cschat.viewdatas.UserViewData;

import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CreateConversationActivity extends BaseActivity implements UserAdapter.UserAdapterListener {
    public static final String EXTRA_USER_ID = "extraUserId";

    @BindView(R.id.rv_users)
    RecyclerView rvUsers;

    @BindView(R.id.users_root)
    ViewGroup root;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Inject
    UsersController usersController;

    private UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_conversation);
        ((CSChatApplication) getApplication()).component().inject(this);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        toolbar.setTitle(getString(R.string.users));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        configureUsersRecyclerView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchUsers();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void fetchUsers() {
        usersController.getUsers()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Consumer<List<UserViewData>>() {
                    @Override
                    public void accept(@NonNull List<UserViewData> userViewDatas) throws Exception {
                        userAdapter.populateUsers(userViewDatas);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        Snackbar.make(root, R.string.network_error, Snackbar.LENGTH_SHORT).show();
                    }
                });
    }

    private void configureUsersRecyclerView() {
        userAdapter = new UserAdapter(this, this);
        rvUsers.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.list_spacing, null));
        rvUsers.addItemDecoration(itemDecoration);
        rvUsers.setAdapter(userAdapter);
    }

    @Override
    public void onUserClicked(UserViewData userViewData) {
        Intent intent = new Intent();
        intent.putExtra(CreateConversationActivity.EXTRA_USER_ID, userViewData.id());
        setResult(RESULT_OK, intent);
        finish();
    }
}
