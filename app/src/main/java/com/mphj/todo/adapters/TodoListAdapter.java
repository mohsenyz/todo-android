package com.mphj.todo.adapters;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mphj.todo.R;
import com.mphj.todo.repositories.Repository;
import com.mphj.todo.repositories.db.entities.Todo;
import com.mphj.todo.utils.DateUtils;
import com.mphj.todo.utils.spans.CustomBackgroundSpan;
import com.mphj.todo.view.BorderedDrawable;

import net.igenius.customcheckbox.CustomCheckBox;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.mphj.todo.view.RichEditText.patternTag;

public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.ViewHolder> {

    private static final int TYPE_LABEL = 1, TYPE_TODO = 2, TYPE_EMPTY = 3;
    List<Object> items = new ArrayList<>();
    int currentLabel = 0;

    public TodoListAdapter(List<Todo> todoList) {
        items.add(Integer.valueOf(R.string.today));
        List<Todo> todayTodoList = new ArrayList<>();
        for (Todo todo : todoList) {
            if (!DateUtils.isToday(todo.date) && todo.date != -1)
                continue;
            todayTodoList.add(todo);
        }
        if (todayTodoList.size() == 0) {
            items.add(null);
        } else {
            items.addAll(todayTodoList);
        }

        items.add(Integer.valueOf(R.string.tomorrow));
        List<Todo> tomorrowTodoList = new ArrayList<>();
        for (Todo todo : todoList) {
            if (!DateUtils.isTomorrow(todo.date))
                continue;
            tomorrowTodoList.add(todo);
        }

        if (tomorrowTodoList.size() == 0) {
            items.add(null);
        } else {
            items.addAll(tomorrowTodoList);
        }

        items.add(Integer.valueOf(R.string.others));
        List<Todo> afterTomorrowTodoList = new ArrayList<>();
        for (Todo todo : todoList) {
            if ((DateUtils.isAfterTomorrow(todo.date) || (DateUtils.isBeforeToday(todo.date) && todo.date != -1)) && !todo.done)
                afterTomorrowTodoList.add(todo);
        }
        if (afterTomorrowTodoList.size() == 0) {
            items.add(null);
        } else {
            items.addAll(afterTomorrowTodoList);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_EMPTY) {
            ConstraintLayout v = (ConstraintLayout) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.todo_list_empty, parent, false);
            return new EmptyViewHolder(v);
        } else if (viewType == TYPE_LABEL) {
            ConstraintLayout v = (ConstraintLayout) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.todo_list_label, parent, false);
            return new LabelViewHolder(v);
        } else {
            ConstraintLayout v = (ConstraintLayout) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.todo_list_item, parent, false);
            return new TodoViewHolder(v);
        }
    }

    @Override
    public int getItemViewType(int position) {
        Object current = items.get(position);
        if (current == null)
            return TYPE_EMPTY;
        else if (current instanceof Todo)
            return TYPE_TODO;
        else
            return TYPE_LABEL;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (holder instanceof LabelViewHolder) {
            ((LabelViewHolder) holder).text.setText((int) items.get(position));
            currentLabel++;
        } else if (holder instanceof EmptyViewHolder) {
            EmptyViewHolder emptyViewHolder = (EmptyViewHolder) holder;
            if (currentLabel == 1) {
                emptyViewHolder.image.setImageResource(R.drawable.approval);
                emptyViewHolder.text.setText(R.string.done_today);
            } else {
                emptyViewHolder.image.setImageResource(R.drawable.packaging);
                emptyViewHolder.text.setText(R.string.no_task_yet);
            }
        } else {
            Todo todo = (Todo) items.get(position);
            TodoViewHolder todoViewHolder = (TodoViewHolder) holder;
            String contentStr = todo.content.replaceAll("\\!{2,4}", "");
            Spannable content = new SpannableString(contentStr);
            Matcher tag = patternTag.matcher(contentStr);
            while (tag.find()) {
                content.setSpan(
                        new CustomBackgroundSpan(Color.parseColor("#297cde"), 10, todoViewHolder.text.getTypeface(), todoViewHolder.text.getTextSize()),
                        tag.start(),
                        tag.end(),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            todoViewHolder.text.setText(content);
            BorderedDrawable borderedDrawable = null;
            if (todo.priority == 1) {
                borderedDrawable = new BorderedDrawable(Color.parseColor("#c62828"), Gravity.RIGHT, 4);
            } else if (todo.priority == 2) {
                borderedDrawable = new BorderedDrawable(Color.parseColor("#EF6C00"), Gravity.RIGHT, 4);
            } else if (todo.priority == 3) {
                borderedDrawable = new BorderedDrawable(Color.parseColor("#FDD835"), Gravity.RIGHT, 4);
            }
            todoViewHolder.container.setBackground(borderedDrawable);

            todoViewHolder.checkBox.setOnCheckedChangeListener(null);
            todoViewHolder.checkBox.setTickColor(todoViewHolder.checkBox.getCheckedColor());

            if (todo.done) {
                todoViewHolder.text.setPaintFlags(todoViewHolder.text.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                todoViewHolder.checkBox.setChecked(true, false);
                todoViewHolder.text.setTextColor(Color.GRAY);
            }

            todoViewHolder.checkBox.setOnCheckedChangeListener((v, c) -> {
                todo.done = !todo.done;
                todo.updatedAt = System.currentTimeMillis();
                AsyncTask.execute(() -> Repository.db(null).todoDao().update(todo));
            });
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public class TodoViewHolder extends ViewHolder {

        @BindView(R.id.text)
        TextView text;

        @BindView(R.id.checkbox)
        CustomCheckBox checkBox;

        @BindView(R.id.container)
        ConstraintLayout container;

        public TodoViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class LabelViewHolder extends ViewHolder {

        @BindView(R.id.text)
        TextView text;

        public LabelViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class EmptyViewHolder extends ViewHolder {

        @BindView(R.id.image)
        ImageView image;

        @BindView(R.id.text)
        TextView text;

        public EmptyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
