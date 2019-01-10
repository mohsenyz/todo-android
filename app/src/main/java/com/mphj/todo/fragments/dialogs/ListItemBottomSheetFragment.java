package com.mphj.todo.fragments.dialogs;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.mphj.todo.R;
import com.mphj.todo.repositories.Repository;
import com.mphj.todo.repositories.db.entities.UserList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ListItemBottomSheetFragment extends BottomSheetDialogFragment {

    @BindView(R.id.submit)
    Button submit;

    @BindView(R.id.text)
    EditText text;
    UserList userList;
    private int id = -1;

    public static ListItemBottomSheetFragment byId(int id) {
        ListItemBottomSheetFragment fragment = new ListItemBottomSheetFragment();
        Bundle args = new Bundle();
        args.putInt("id", id);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_item_dialog, container);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        fillArgs();
        if (id == -1) {
            userList = new UserList();
            onUserFound();
        } else {
            Repository.db(getContext()).userListDao().byId(id).observe(this, (userList) -> {
                this.userList = userList;
                onUserFound();
            });
        }
        submit.setOnClickListener((v) -> {
            v.setEnabled(false);
            insertListItem();
        });
    }

    void fillArgs() {
        if (getArguments() == null)
            return;
        id = getArguments().getInt("id", -1);
    }

    void onUserFound() {
        text.setText(userList.name);
    }

    public void insertListItem() {
        userList.name = text.getText().toString();
        AsyncTask.execute(() -> {
            if (userList.localId == 0) {
                Repository.db(getContext()).userListDao().insert(userList);
            } else {
                Repository.db(getContext()).userListDao().update(userList);
            }
        });
        dismiss();
    }
}
