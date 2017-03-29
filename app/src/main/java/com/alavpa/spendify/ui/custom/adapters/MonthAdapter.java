package com.alavpa.spendify.ui.custom.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alavpa.spendify.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MonthAdapter extends RecyclerView.Adapter<MonthAdapter.MonthVH>{

    SimpleDateFormat simpleDateFormat;
    List<Long> months;
    List<String> monthNames;
    Calendar calendar = Calendar.getInstance();
    LayoutInflater layoutInflater;
    OnMonthClick onMonthClick;

    public void setMonths(List<Long> months) {
        this.months.clear();
        this.months.addAll(months);

        this.monthNames.clear();
        for(long month : months){
            calendar.setTimeInMillis(month);
            String name = simpleDateFormat.format(calendar.getTime());
            monthNames.add(name);
        }
    }

    public interface OnMonthClick{
        void onMonthClick(long month);
    }

    public MonthAdapter(Context context, List<Long> months, OnMonthClick onMonthClick){
        this.months = new ArrayList<>();
        this.monthNames = new ArrayList<>();

        this.onMonthClick = onMonthClick;
        simpleDateFormat = new SimpleDateFormat("MMMM yyyy");

        setMonths(months);
        layoutInflater = LayoutInflater.from(context);
    }
    @Override
    public MonthVH onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.item_menu,parent,false);
        return new MonthVH(view);
    }

    @Override
    public void onBindViewHolder(MonthVH holder, int position) {
        holder.bind(monthNames.get(position), months.get(position), onMonthClick);
    }

    @Override
    public int getItemCount() {
        return months.size();
    }

    public class MonthVH extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_menu)
        TextView tvMenu;

        public MonthVH(View view){
            super(view);
            ButterKnife.bind(this,view);
        }

        public void bind(String monthName, final long month, final OnMonthClick onMonthClick){
            tvMenu.setText(monthName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onMonthClick.onMonthClick(month);
                }
            });
        }
    }
}
