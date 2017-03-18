package com.alavpa.spendify.ui.custom.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alavpa.spendify.R;
import com.alavpa.spendify.domain.model.Amount;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AmountAdapter extends RecyclerView.Adapter<AmountAdapter.AmountVH> {

    List<Amount> amounts;
    LayoutInflater inflater;
    DecimalFormat decimalFormat;
    SimpleDateFormat simpleDateFormat;

    public AmountAdapter(Context context,
                         List<Amount> amounts,
                         DecimalFormat decimalFormat,
                         SimpleDateFormat simpleDateFormat){

        inflater = LayoutInflater.from(context);
        this.amounts = amounts;
        this.decimalFormat = decimalFormat;
        this.simpleDateFormat = simpleDateFormat;
    }
    @Override
    public AmountVH onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.item_amount,parent,false);
        return new AmountVH(view);
    }

    @Override
    public void onBindViewHolder(AmountVH holder, int position) {
        holder.bind(simpleDateFormat,decimalFormat,amounts.get(position));
    }

    @Override
    public int getItemCount() {
        return amounts.size();
    }

    public static class AmountVH extends RecyclerView.ViewHolder{

        Calendar calendar = Calendar.getInstance();

        @BindView(R.id.tv_amount)
        TextView tvAmount;

        @BindView(R.id.tv_description)
        TextView tvDescription;

        @BindView(R.id.tv_date)
        TextView tvDate;

        public AmountVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void bind(SimpleDateFormat simpleDateFormat, DecimalFormat decimalFormat, Amount amount){

            calendar.setTimeInMillis(amount.getPeriod().getDate());
            tvDate.setText(simpleDateFormat.format(calendar.getTime()));

            tvDescription.setText(amount.getDescription());
            tvAmount.setText(decimalFormat.format(amount.getAmount()));
        }
    }
}
