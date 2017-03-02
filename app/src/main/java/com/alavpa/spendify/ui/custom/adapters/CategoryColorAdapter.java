package com.alavpa.spendify.ui.custom.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.alavpa.spendify.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alavpa on 18/02/17.
 */

public class CategoryColorAdapter extends RecyclerView.Adapter<CategoryColorAdapter.CategoryViewHolder> {

    LayoutInflater inflater;
    int[] categories;
    Integer selected = null;

    public CategoryColorAdapter(Context context, int[] categories){
        inflater = LayoutInflater.from(context);
        setCategoryColors(categories);
    }
    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.item_category,parent,false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        int category = categories[position];
        holder.bind(category);
    }

    @Override
    public int getItemCount() {
        return categories.length;
    }

    public Integer getSelected(){
        return selected;
    }

    public void setCategoryColors(int[] categories){

        this.categories = categories;

    }

    public void setSelected(Integer category) {
        this.selected = category;
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.fl_category)
        FrameLayout flCategory;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void bind(int color){
            flCategory.setBackgroundResource(color);
            if(getSelected()!=null) {
                itemView.setSelected(getAdapterPosition() == getSelected());
            }else {
                itemView.setSelected(false);
            }
            onClick();
        }

        public void onClick() {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(getSelected()!=null && getAdapterPosition()==getSelected()){
                        setSelected(null);
                    }
                    else {
                        setSelected(getAdapterPosition());
                    }
                    notifyDataSetChanged();
                }
            });
        }
    }
}
