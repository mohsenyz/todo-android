package com.mphj.todo.activities;

import android.content.Intent;
import android.os.Bundle;

import com.mphj.todo.BaseActivity;
import com.mphj.todo.R;
import com.mphj.todo.utils.Auth;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Auth.isLoggedIn(this)) {
            startActivity(new Intent(this, TodoListActivity.class));
        } else {
            startActivity(new Intent(this, LoginActivity.class));
        }
        finish();
    }
}
