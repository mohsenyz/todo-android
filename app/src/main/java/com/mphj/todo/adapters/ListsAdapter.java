package com.mphj.todo.adapters;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mphj.todo.R;
import com.mphj.todo.repositories.db.entities.UserList;
import com.mphj.todo.view.BorderedDrawable;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ListsAdapter extends RecyclerView.Adapter<ListsAdapter.ViewHolder> {


    private List<UserList> lists;

    OnListClickListener onListClickListener;

    public ListsAdapter(List<UserList> userLists) {
        this.lists = userLists;
    }

    public void setOnListClickListener(OnListClickListener onListClickListener) {
        this.onListClickListener = onListClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ConstraintLayout v = (ConstraintLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lists_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (position == 0) {
            holder.name.setText(R.string.all_tasks);
            holder.container.setBackground(new BorderedDrawable(ContextCompat.getColor(holder.name.getContext(), R.color.colorAccent), Gravity.RIGHT, 4));
        } else {
            holder.name.setText(lists.get(position - 1).name);
        }
        holder.itemView.setOnClickListener((view) -> {
            if (onListClickListener != null) {
                if (position == 0) {
                    onListClickListener.onListClick(position, null, holder.itemView);
                } else {
                    onListClickListener.onListClick(position, lists.get(position - 1), holder.itemView);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return 1 + lists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.name)
        TextView name;

        @BindView(R.id.container)
        ConstraintLayout container;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    public interface OnListClickListener {
        void onListClick(int position, UserList userList, View itemView);
    }
}
