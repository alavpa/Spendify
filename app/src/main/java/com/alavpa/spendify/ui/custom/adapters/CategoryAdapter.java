package com.alavpa.spendify.ui.custom.adapters;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alavpa.spendify.R;
import com.alavpa.spendify.domain.model.Category;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alavpa on 18/02/17.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.BaseCategoryViewHolder> {

    public static final int VIEW_TYPE_CATEGORY = 0;
    public static final int VIEW_TYPE_ADD = 1;

    LayoutInflater inflater;
    List<Category> categories;
    OnCategoryClick onCategoryClick;
    Category selected = null;
    int[] backgrounds;
    Drawable addDrawable;
    boolean selectable;

    public CategoryAdapter(Context context,
                           List<Category> categories,
                           int[] backgrounds,
                           boolean selectable,
                           OnCategoryClick onCategoryClick) {

        inflater = LayoutInflater.from(context);
        this.onCategoryClick = onCategoryClick;
        this.backgrounds = backgrounds;
        this.selectable = selectable;
        setCategories(categories);
        addDrawable = ContextCompat.getDrawable(context, R.drawable.ic_action_add);
        int color = ContextCompat.getColor(context, R.color.colorAccent);
        addDrawable.setColorFilter(color, PorterDuff.Mode.SRC_IN);

    }

    @Override
    public BaseCategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        if (viewType == VIEW_TYPE_ADD) {
            view = inflater.inflate(R.layout.layout_category_add, parent, false);
            return new AddCategoryViewHolder(view, onCategoryClick);
        }

        view = inflater.inflate(R.layout.layout_category, parent, false);
        return new CategoryViewHolder(view, onCategoryClick);
    }

    @Override
    public void onBindViewHolder(BaseCategoryViewHolder holder, int position) {
        Category category = categories.get(position);
        if (holder.getItemViewType() == VIEW_TYPE_ADD) {
            holder.bind(addDrawable);
        } else {
            holder.bind(category);

        }
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == categories.size() - 1) ? VIEW_TYPE_ADD : VIEW_TYPE_CATEGORY;
    }

    public Category getSelected() {
        return selected;
    }

    public boolean isSelectable() {
        return selectable;
    }

    public void setCategories(List<Category> categories) {

        this.categories = new ArrayList<>(categories);
        this.categories.add(null);

    }

    public void setSelected(Category category) {
        this.selected = category;
    }

    public abstract class BaseCategoryViewHolder extends RecyclerView.ViewHolder {
        OnCategoryClick onCategoryClick;

        public BaseCategoryViewHolder(View itemView, OnCategoryClick onCategoryClick) {
            super(itemView);
            this.onCategoryClick = onCategoryClick;
        }

        public void bind(Category category) {
        }

        public void bind(Drawable drawable) {
        }
    }

    public class CategoryViewHolder extends BaseCategoryViewHolder {

        @BindView(R.id.ll_bkg)
        LinearLayout llBkg;
        @BindView(R.id.tv_name)
        TextView tvName;

        public CategoryViewHolder(View itemView, OnCategoryClick onCategoryClick) {
            super(itemView, onCategoryClick);
            ButterKnife.bind(this, itemView);
        }

        public void bind(Category category) {

            tvName.setText(category.getName());
            itemView.setBackgroundResource(backgrounds[category.getColor()]);

            if (getSelected() != null) {
                boolean selected = category.getId() == getSelected().getId();
                itemView.setSelected(selected);
            } else {
                itemView.setSelected(false);
            }


            onClick();

        }

        public void onClick() {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Category category = getCategory(getAdapterPosition());
                    if(isSelectable()) {

                        if (getSelected() != null && category.getId() == getSelected().getId()) {
                            setSelected(null);
                        } else {
                            setSelected(category);
                        }
                        notifyDataSetChanged();
                    }

                    if(onCategoryClick!=null) {
                        onCategoryClick.onClick(category);
                    }
                }


            });
        }
    }

    private Category getCategory(int adapterPosition) {
        return categories.get(adapterPosition);
    }

    public class AddCategoryViewHolder extends BaseCategoryViewHolder {

        @BindView(R.id.iv_add)
        ImageView ivAdd;

        public AddCategoryViewHolder(View itemView, OnCategoryClick onCategoryClick) {
            super(itemView, onCategoryClick);
            ButterKnife.bind(this, itemView);
            this.onCategoryClick = onCategoryClick;
        }

        public void bind(Drawable drawable) {
            ivAdd.setImageDrawable(drawable);
            onClick();
        }

        public void onClick() {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onCategoryClick.onAddClick();
                }
            });
        }
    }

    public interface OnCategoryClick {
        void onClick(Category category);

        void onAddClick();
    }
}
