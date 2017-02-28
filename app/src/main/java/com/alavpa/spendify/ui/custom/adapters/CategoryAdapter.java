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
    Category selected = null;

    public CategoryAdapter(Context context, List<Category> categories, OnAddCategoryClick onAddCategoryClick){
        inflater = LayoutInflater.from(context);
        this.onAddCategoryClick = onAddCategoryClick;
        setCategories(context,categories);
    }
    @Override
    public BaseCategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.layout_category,parent,false);
        return (viewType==0)?new CategoryViewHolder(view):new AddCategoryViewHolder(view,onAddCategoryClick);
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

    public Category getSelected(){
        return selected;
    }

    public void setCategories(Context context, List<Category> categories){

        this.categories = categories;
        this.categories.add(new Category(context.getString(R.string.add),false,0));

    }

    public void setSelected(Category category) {
        this.selected = category;
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
            if(getSelected()!=null) {
                itemView.setSelected(category.getId() == getSelected().getId());
            }
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
                    Category category = getCategory(getAdapterPosition());

                    if(getSelected()!=null && category.getId()==getSelected().getId()){
                        setSelected(null);
                    }
                    else {
                        setSelected(category);
                    }
                    notifyDataSetChanged();
                }
            });
        }
    }

    private Category getCategory(int adapterPosition) {
        return categories.get(adapterPosition);
    }

    public class AddCategoryViewHolder extends BaseCategoryViewHolder{

        OnAddCategoryClick onAddCategoryClick;
        public AddCategoryViewHolder(View itemView, OnAddCategoryClick onAddCategoryClick) {
            super(itemView);
            this.onAddCategoryClick = onAddCategoryClick;
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
