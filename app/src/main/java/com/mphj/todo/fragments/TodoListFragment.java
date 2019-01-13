package com.mphj.todo.fragments;

import android.animation.Animator;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.mphj.todo.R;
import com.mphj.todo.activities.TodoListActivity;
import com.mphj.todo.adapters.TimeSuggestListAdapter;
import com.mphj.todo.adapters.TodoListAdapter;
import com.mphj.todo.interfaces.BackHandler;
import com.mphj.todo.repositories.Repository;
import com.mphj.todo.repositories.db.entities.Flag;
import com.mphj.todo.repositories.db.entities.Todo;
import com.mphj.todo.utils.Animate;
import com.mphj.todo.utils.RealTimeDatabase;
import com.mphj.todo.view.RichEditText;

import java.util.ArrayList;
import java.util.List;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import jp.wasabeef.recyclerview.animators.FadeInAnimator;

public class TodoListFragment extends Fragment implements BackHandler {


    public TimeSuggestListAdapter timeSuggestListAdapter;

    @BindView(R.id.time_suggest)
    RecyclerView timeSuggestRecyclerView;

    @BindView(R.id.todo_content)
    RichEditText todoContentEditText;

    @BindView(R.id.content_container)
    ConstraintLayout contentContainer;

    @BindView(R.id.submit)
    ImageView submit;

    @BindView(R.id.todo_list)
    RecyclerView todoList;

    public static TodoListFragment create() {
        return new TodoListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.todo_list, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        init();
    }

    void init() {
        timeSuggestRecyclerView.setTranslationY(timeSuggestRecyclerView.getHeight() + timeSuggestRecyclerView.getBottom());
        LinearLayoutManager timeSuggestLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        timeSuggestListAdapter = new TimeSuggestListAdapter();
        timeSuggestRecyclerView.setHasFixedSize(false);
        timeSuggestRecyclerView.setAdapter(timeSuggestListAdapter);
        timeSuggestRecyclerView.setItemAnimator(new FadeInAnimator());
        timeSuggestLayoutManager.setReverseLayout(true);
        timeSuggestRecyclerView.setLayoutManager(timeSuggestLayoutManager);

        contentContainer.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();

                contentContainer.getWindowVisibleDisplayFrame(r);

                int heightDiff = contentContainer.getRootView().getHeight() - (r.bottom - r.top);
                if (heightDiff > 50) {
                    todoContentEditText.requestFocus();
                    showTimeSuggestion();
                    hideTabLayout();
                } else {
                    todoContentEditText.clearFocus();
                    hideTimeSuggestion();
                    showTabLayout();
                }
            }
        });

        Repository.db(getActivity()).todoDao().all().observe(this, (list) -> {
            LinearLayoutManager todoListLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
            TodoListAdapter todoListAdapter = new TodoListAdapter(list);
            todoList.setHasFixedSize(false);
            todoList.setAdapter(todoListAdapter);
            todoList.setItemAnimator(new FadeInAnimator());
            timeSuggestLayoutManager.setReverseLayout(true);
            todoList.setLayoutManager(todoListLayoutManager);
        });
    }

    @OnClick(R.id.submit)
    void onSubmit() {
        if (todoContentEditText.getText().toString().trim().isEmpty()) {
            Toasty.error(getActivity(), R.string.empty_task).show();
            return;
        }
        insertTodo();
        submit.animate()
                .translationX(submit.getWidth() + 100)
                .setDuration(200)
                .setStartDelay(0)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        submit.setTranslationX(0);
                        submit.setScaleX(0f);
                        submit.setScaleY(0f);
                        submit.setImageResource(R.drawable.success);
                        submit.animate()
                                .setDuration(200)
                                .scaleX(1)
                                .scaleY(1)
                                .setListener(null)
                                .rotation(360)
                                .setStartDelay(0)
                                .setListener(new Animator.AnimatorListener() {
                                    @Override
                                    public void onAnimationStart(Animator animation) {

                                    }

                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        submit.animate()
                                                .setDuration(200)
                                                .setStartDelay(1000)
                                                .scaleY(0)
                                                .scaleX(0)
                                                .setListener(new Animator.AnimatorListener() {
                                                    @Override
                                                    public void onAnimationStart(Animator animation) {

                                                    }

                                                    @Override
                                                    public void onAnimationEnd(Animator animation) {
                                                        submit.setImageResource(R.drawable.send);
                                                        submit.setTranslationY(submit.getHeight());
                                                        submit.animate()
                                                                .setDuration(200)
                                                                .translationY(0)
                                                                .scaleX(1)
                                                                .scaleY(1)
                                                                .setStartDelay(0)
                                                                .setListener(null)
                                                                .start();
                                                    }

                                                    @Override
                                                    public void onAnimationCancel(Animator animation) {

                                                    }

                                                    @Override
                                                    public void onAnimationRepeat(Animator animation) {

                                                    }
                                                })
                                                .start();
                                    }

                                    @Override
                                    public void onAnimationCancel(Animator animation) {

                                    }

                                    @Override
                                    public void onAnimationRepeat(Animator animation) {

                                    }
                                })
                                .start();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                })
                .start();
        todoContentEditText.setText(null);
        timeSuggestListAdapter.clear();
    }

    void insertTodo() {
        Todo todo = new Todo();
        todo.content = todoContentEditText.getText().toString();
        todo.date = timeSuggestListAdapter.getCurrentSelectedDate();
        todo.priority = todoContentEditText.getPriority();
        AsyncTask.execute(() -> Repository.db(getActivity()).todoDao().insert(todo));

        List<String> tags = todoContentEditText.getTags();
        List<Flag> flags = new ArrayList<>();
        for (String tag : tags) {
            Flag flag = new Flag();
            flag.flag = tag;
            flag.todoId = todo.localId;
            flags.add(flag);
        }
        AsyncTask.execute(() -> Repository.db(getActivity()).flagDao().insert(flags));
        RealTimeDatabase.sync();
    }

    void hideTabLayout() {
        if (getActivity() instanceof TodoListActivity) {
            ((TodoListActivity) getActivity()).hideTabs();
        }
    }


    void showTabLayout() {
        if (getActivity() instanceof TodoListActivity) {
            ((TodoListActivity) getActivity()).showTabs();
        }
    }

    void showTimeSuggestion() {
        Animate.toTop(timeSuggestRecyclerView, 300);
    }

    void hideTimeSuggestion() {
        Animate.toBottom(timeSuggestRecyclerView, 300);
    }


    @Override
    public boolean onBackPressed() {
        todoContentEditText.clearFocus();
        return true;
    }
}
