package com.alavpa.spendify.ui.custom.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alavpa.spendify.R;
import com.alavpa.spendify.domain.model.Category;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alavpa on 18/02/17.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    LayoutInflater inflater;
    List<Category> categories;
    int selected = -1;

    public CategoryAdapter(Context context, List<Category> categories){
        inflater = LayoutInflater.from(context);
        this.categories = categories;
    }
    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.layout_category,parent,false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.bind(category);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public int getSelected(){
        return selected;
    }

    public Category getSelectedCategory(){
        if(selected>=0){
            return categories.get(selected);
        }

        return null;
    }

    public void setSelected(int position){
        this.selected = position;
        notifyDataSetChanged();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_name)
        TextView tvName;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void bind(Category category){
            tvName.setText(category.getName());
            itemView.setSelected(getAdapterPosition()==getSelected());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(getAdapterPosition()==getSelected()){
                        setSelected(-1);
                    }else {
                        setSelected(getAdapterPosition());
                    }
                }
            });
        }
    }
}
