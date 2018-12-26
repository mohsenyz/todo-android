package com.mphj.todo.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mphj.todo.R;
import com.mphj.todo.adapters.models.TimeSuggestObj;
import com.mphj.todo.utils.DateUtils;

import org.joda.time.DateTimeConstants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class TimeSuggestListAdapter extends RecyclerView.Adapter<TimeSuggestListAdapter.ViewHolder> {

    public static long CURRENT_TIME;

    List<TimeSuggestObj> suggestObjList = new ArrayList<>();

    public TimeSuggestListAdapter() {
        suggestObjList.add(new TimeSuggestObj(R.string.weekend_fri, DateUtils.nextDay(DateTimeConstants.FRIDAY)));
        suggestObjList.add(new TimeSuggestObj(R.string.weekend_thu, DateUtils.nextDay(DateTimeConstants.THURSDAY)));
        suggestObjList.add(new TimeSuggestObj(R.string.today_at_6, DateUtils.todayHour(6)));
        suggestObjList.add(new TimeSuggestObj(R.string.next_5_min, DateUtils.nextMin(5)));
        suggestObjList.add(new TimeSuggestObj(R.string.next_15_min, DateUtils.nextMin(15)));
        suggestObjList.add(new TimeSuggestObj(R.string.next_30_min, DateUtils.nextMin(30)));
        suggestObjList.add(new TimeSuggestObj(R.string.next_1_hour, DateUtils.nextMin(60)));
        suggestObjList.add(new TimeSuggestObj(R.string.next_2_hour, DateUtils.nextMin(120)));
        suggestObjList.add(new TimeSuggestObj(R.string.tomorrow, DateUtils.nextDay()));
        Collections.sort(suggestObjList);
    }

    @NonNull
    @Override
    public TimeSuggestListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.time_suggest_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeSuggestListAdapter.ViewHolder holder, int position) {
        final TimeSuggestObj tsObj = suggestObjList.get(position);
        holder.text.setText(tsObj.getDate(holder.itemView.getContext()));
        if (tsObj.isSelected) {
            holder.text.setTextColor(Color.WHITE);
            holder.container.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.colorPrimary));
        } else {
            holder.text.setTextColor(Color.BLACK);
            holder.container.setCardBackgroundColor(Color.WHITE);
        }
        holder.itemView.setOnClickListener((v) -> {
            for (int i = 0; i < suggestObjList.size(); i++) {
                if (suggestObjList.get(i).isSelected) {
                    suggestObjList.get(i).isSelected = false;
                    notifyItemChanged(i);
                }
                tsObj.isSelected = true;
                notifyItemChanged(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return suggestObjList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text)
        TextView text;

        @BindView(R.id.container)
        CardView container;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
