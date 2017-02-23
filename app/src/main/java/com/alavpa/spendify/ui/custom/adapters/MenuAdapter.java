package com.alavpa.spendify.ui.custom.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alavpa.spendify.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alavpa on 23/02/17.
 */

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {

    LayoutInflater layoutInflater;
    String[] menu;
    OnMenuClick onMenuClick;

    public void setMenu(String... menu) {
        this.menu = menu;
    }

    public interface OnMenuClick{
        void onClick(int position);
    }

    public MenuAdapter(Context context, OnMenuClick onMenuClick, String... menu){
        this.menu = menu;
        layoutInflater = LayoutInflater.from(context);
        this.onMenuClick= onMenuClick;
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_menu,parent,false);
        return new MenuViewHolder(view,onMenuClick);
    }

    @Override
    public void onBindViewHolder(MenuViewHolder holder, int position) {
        holder.bind(menu[position]);
    }

    @Override
    public int getItemCount() {
        return menu.length;
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_menu)
        TextView tvMenu;

        OnMenuClick onMenuClick;

        public MenuViewHolder(View itemView, OnMenuClick onMenuClick) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            this.onMenuClick = onMenuClick;
        }

        public void bind(String text){
            tvMenu.setText(text);
            tvMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onMenuClick.onClick(getAdapterPosition());
                }
            });
        }
    }
}
