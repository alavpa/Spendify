package com.alavpa.spendify.ui.custom.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.alavpa.spendify.R;
import com.alavpa.spendify.domain.model.Sector;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SectorAdapter extends RecyclerView.Adapter<SectorAdapter.SectorVH> {

    List<Sector> sectors;
    LayoutInflater inflater;
    List<Integer> colors;
    DecimalFormat decimalFormat;

    OnClickSector onClickSector;

    public void setSectors(List<Sector> sectors) {
        this.sectors.clear();
        this.sectors.addAll(sectors);
    }

    public interface OnClickSector{
        void onClick(Sector sector);
    }

    public SectorAdapter(Context context,
                         List<Sector> sectors,
                         List<Integer> colors,
                         DecimalFormat decimalFormat,
                         OnClickSector onClickSector){

        inflater = LayoutInflater.from(context);
        this.sectors = sectors;
        this.colors = colors;
        this.decimalFormat = decimalFormat;
        this.onClickSector = onClickSector;
    }
    @Override
    public SectorVH onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.item_sector,parent,false);
        return new SectorVH(view, colors, onClickSector);
    }

    @Override
    public void onBindViewHolder(SectorVH holder, int position) {
        holder.bind(decimalFormat, sectors.get(position));
    }

    @Override
    public int getItemCount() {
        return sectors.size();
    }

    public static class SectorVH extends RecyclerView.ViewHolder{

        List<Integer> colors;

        @BindView(R.id.tv_amount)
        TextView tvAmount;

        @BindView(R.id.tv_category)
        TextView tvCategory;

        @BindView(R.id.fl_category)
        FrameLayout flCategory;

        OnClickSector onClickSector;

        public SectorVH(View itemView, List<Integer> colors, OnClickSector onClickSector) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            this.colors = colors;
            this.onClickSector = onClickSector;
        }

        public void bind(DecimalFormat decimalFormat, final Sector sector){

            flCategory.setBackgroundResource(colors.get(sector.getCategory().getColor()));
            tvCategory.setText(sector.getCategory().getName());
            tvAmount.setText(decimalFormat.format(sector.getAmount()));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(onClickSector != null){
                        onClickSector.onClick(sector);
                    }
                }
            });
        }
    }
}
