package com.mphj.todo.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mphj.todo.BaseActivity;
import com.mphj.todo.R;
import com.mphj.todo.adapters.ListsAdapter;
import com.mphj.todo.fragments.TodoListFragment;

import java.util.List;

import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.animators.FadeInAnimator;

public class TodoListActivity extends BaseActivity {


    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.menu)
    ImageView menu;

    @BindViews({R.id.tab_list, R.id.tab_settings, R.id.tab_statistics})
    List<LinearLayout> tabs;

    @BindView(R.id.lists)
    RecyclerView lists;

    ListsAdapter listsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);
        ButterKnife.bind(this);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        initView();
    }

    void initView() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment, TodoListFragment.create());
        ft.commit();

        menu.setOnClickListener((view) -> {
            if (!drawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                drawerLayout.openDrawer(Gravity.RIGHT);
            }
        });

        for (LinearLayout linearLayout : tabs) {
            linearLayout.setOnClickListener((v) -> {
                resetTabs();
                ViewGroup vg = (ViewGroup) v;
                ((ImageView) vg.getChildAt(0)).setColorFilter(ContextCompat.getColor(getApplication(), R.color.colorPrimary));
                ((TextView) vg.getChildAt(1)).setTextColor(ContextCompat.getColor(getApplication(), R.color.colorPrimary));
            });
        }

        LinearLayoutManager timeSuggestLayoutManager = new LinearLayoutManager(this);
        listsAdapter = new ListsAdapter();
        lists.setHasFixedSize(false);
        lists.setAdapter(listsAdapter);
        lists.setItemAnimator(new FadeInAnimator());
        lists.setLayoutManager(timeSuggestLayoutManager);
    }

    void resetTabs() {
        for (LinearLayout linearLayout : tabs) {
            ((ImageView) linearLayout.getChildAt(0)).setColorFilter(null);
            ((TextView) linearLayout.getChildAt(1)).setTextColor(Color.BLACK);
        }
    }

    public void hideTabs() {
        ((View) tabs.get(0).getParent()).setVisibility(View.GONE);
    }

    public void showTabs() {
        ((View) tabs.get(0).getParent()).setVisibility(View.VISIBLE);
    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(Gravity.RIGHT)) {
            drawerLayout.closeDrawer(Gravity.RIGHT);
        } else {
            super.onBackPressed();
        }
    }
}
