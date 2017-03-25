package com.mirego.cschat.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mirego.cschat.CSChatApplication;
import com.mirego.cschat.Prefs;
import com.mirego.cschat.R;
import com.mirego.cschat.controller.LoginController;
import com.mirego.cschat.models.User;

import javax.inject.Inject;

import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static android.support.design.widget.Snackbar.LENGTH_SHORT;

public class RegisterActivity extends AppCompatActivity {
    Button register;
    EditText userNameEdit;
    EditText passwordEdit;
    EditText urlEdit;
    ViewGroup root;

    @Inject
    LoginController loginController;


    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_real);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ((CSChatApplication) getApplication()).component().inject(this);

        register = (Button) findViewById(R.id.btnRegister);
        userNameEdit = (EditText) findViewById(R.id.editUsername);
        passwordEdit = (EditText) findViewById(R.id.editPassword);
        urlEdit = (EditText) findViewById(R.id.editUrl);
        root = (ViewGroup) findViewById(R.id.rootLayout);


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.register_loading));

        register.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                progressDialog.show();


                loginController.register(userNameEdit.getText().toString(), passwordEdit.getText().toString(), urlEdit.getText().toString()).observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.newThread())
                        .subscribe(new Consumer<User>() {
                            @Override
                            public void accept(@NonNull User user) throws Exception {
                                progressDialog.dismiss();

                                Context context = getApplicationContext();
                                Toast toast = Toast.makeText(context, getString(R.string.register_done),  Toast.LENGTH_LONG);
                                toast.show();

                                finish();


                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(@NonNull Throwable throwable) throws Exception {
                                progressDialog.dismiss();
                                Snackbar.make(root, R.string.login_error, LENGTH_SHORT).show();
                            }
                        });
            }
        });





    }



}
