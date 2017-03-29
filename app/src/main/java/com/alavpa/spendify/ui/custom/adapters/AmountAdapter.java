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
    OnClickAmount onClickAmount;

    public void setAmounts(List<Amount> amounts) {
        this.amounts.clear();
        this.amounts.addAll(amounts);
    }

    public interface OnClickAmount{
        void onClick(Amount amount);
    }

    public AmountAdapter(Context context,
                         List<Amount> amounts,
                         DecimalFormat decimalFormat,
                         SimpleDateFormat simpleDateFormat,
                         OnClickAmount onClickAmount){

        inflater = LayoutInflater.from(context);
        this.amounts = amounts;
        this.decimalFormat = decimalFormat;
        this.simpleDateFormat = simpleDateFormat;
        this.onClickAmount = onClickAmount;
    }
    @Override
    public AmountVH onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.item_amount,parent,false);
        return new AmountVH(view);
    }

    @Override
    public void onBindViewHolder(AmountVH holder, int position) {
        holder.bind(simpleDateFormat,decimalFormat,amounts.get(position), onClickAmount);
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

        public void bind(SimpleDateFormat simpleDateFormat, DecimalFormat decimalFormat,
                         final Amount amount, final OnClickAmount onClickAmount){

            calendar.setTimeInMillis(amount.getPeriod().getDate());
            tvDate.setText(simpleDateFormat.format(calendar.getTime()));

            tvDescription.setText(amount.getDescription());
            tvAmount.setText(decimalFormat.format(amount.getAmount()));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickAmount.onClick(amount);
                }
            });
        }
    }
}
