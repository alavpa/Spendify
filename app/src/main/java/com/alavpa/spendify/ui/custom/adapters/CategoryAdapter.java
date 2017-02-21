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

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.BaseCategoryViewHolder> {

    LayoutInflater inflater;
    List<Category> categories;
    OnAddCategoryClick onAddCategoryClick;
    int selected = -1;

    public CategoryAdapter(Context context, List<Category> categories, OnAddCategoryClick onAddCategoryClick){
        inflater = LayoutInflater.from(context);
        this.onAddCategoryClick = onAddCategoryClick;
        setCategories(context,categories);
    }
    @Override
    public BaseCategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.layout_category,parent,false);
        return (viewType==0)?new CategoryViewHolder(view):new AddCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BaseCategoryViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.bind(category);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position==categories.size()-1)?1:0;
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

    public void setCategories(Context context, List<Category> categories){

        this.categories = categories;
        this.categories.add(new Category(context.getString(R.string.add),false));

    }

    public abstract class BaseCategoryViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_name)
        TextView tvName;

        public BaseCategoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void bind(Category category){
            tvName.setText(category.getName());
            itemView.setSelected(getAdapterPosition()==getSelected());
            onClick();
        }

        public abstract void onClick();
    }
    public class CategoryViewHolder extends BaseCategoryViewHolder{

        public CategoryViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onClick() {
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

    public class AddCategoryViewHolder extends BaseCategoryViewHolder{

        public AddCategoryViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onClick() {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onAddCategoryClick.onAddClick();
                }
            });
        }
    }

    public interface OnAddCategoryClick{
        void onAddClick();
    }
}
