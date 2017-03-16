package com.alavpa.spendify.ui.custom.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.alavpa.spendify.R;
import com.alavpa.spendify.domain.model.Amount;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/*
 * Copyright (c) 2017 AXA Group Solutions.
 *
 * Licensed under the AXA Group Solutions License (the "License"); you
 * may not use this file except in compliance with the License.
 * A copy of the License can be found in the LICENSE.TXT file distributed
 * together with this file.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class AmountAdapter extends RecyclerView.Adapter<AmountAdapter.AmountVH> {

    List<Amount> amounts;
    LayoutInflater inflater;
    int[] colors;
    DecimalFormat decimalFormat;
    SimpleDateFormat simpleDateFormat;

    public AmountAdapter(Context context,
                         List<Amount> amounts,
                         int[] colors, DecimalFormat decimalFormat,
                         SimpleDateFormat simpleDateFormat){

        inflater = LayoutInflater.from(context);
        this.amounts = amounts;
        this.colors = colors;
        this.decimalFormat = decimalFormat;
        this.simpleDateFormat = simpleDateFormat;
    }
    @Override
    public AmountVH onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.item_amount,parent,false);
        return new AmountVH(view, colors);
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

        int[] colors;
        Calendar calendar = Calendar.getInstance();

        @BindView(R.id.tv_amount)
        TextView tvAmount;

        @BindView(R.id.tv_category)
        TextView tvCategory;

        @BindView(R.id.fl_category)
        FrameLayout flCategory;

        @BindView(R.id.tv_date)
        TextView tvDate;

        public AmountVH(View itemView, int[] colors) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            this.colors = colors;
        }

        public void bind(SimpleDateFormat simpleDateFormat, DecimalFormat decimalFormat, Amount amount){

            calendar.setTimeInMillis(amount.getPeriod().getDate());
            tvDate.setText(simpleDateFormat.format(calendar.getTime()));

            flCategory.setBackgroundResource(colors[amount.getCategory().getColor()]);
            tvCategory.setText(amount.getCategory().getName());
            tvAmount.setText(decimalFormat.format(amount.getAmount()));
        }
    }
}
