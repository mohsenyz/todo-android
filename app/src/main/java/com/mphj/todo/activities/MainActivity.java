package com.mphj.todo.activities;

import android.content.Intent;
import android.os.Bundle;

import com.mphj.todo.BaseActivity;
import com.mphj.todo.R;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startActivity(new Intent(this, LoginActivity.class));
    }
}
