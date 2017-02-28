package com.alavpa.spendify.ui.menu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alavpa.spendify.R;
import com.alavpa.spendify.di.PerActivity;
import com.alavpa.spendify.domain.model.Amount;
import com.alavpa.spendify.ui.Navigator;
import com.alavpa.spendify.ui.base.BaseFragment;
import com.alavpa.spendify.ui.custom.LinearLayoutManager;
import com.alavpa.spendify.ui.custom.adapters.MenuAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alavpa on 23/02/17.
 */
@PerActivity
public class MenuFragment extends BaseFragment implements MenuView{

    @Inject
    Navigator navigator;

    @BindView(R.id.rv_menu)
    public
    RecyclerView rvMenu;

    @Inject
    public MenuPresenter menuPresenter;

    private MenuAdapter adapter;

    public static MenuFragment getInstance(){
        return new MenuFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getActivity())
                .inflate(R.layout.fragment_menu,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        menuPresenter.attachView(this);
        rvMenu.setLayoutManager(new LinearLayoutManager(getActivity()));
        menuPresenter.showMenu();
    }

    @Override
    public void goToDashboard() {
        navigator.openDashboard(getActivity());
    }

    @Override
    public void goToMain() {
        navigator.openMain(getActivity(),new Amount());
    }

    @Override
    public void populateMenu(String[] menu) {
        if(adapter==null){
            adapter = new MenuAdapter(getActivity(), new MenuAdapter.OnMenuClick() {
                @Override
                public void onClick(int position) {
                    menuPresenter.goTo(position);
                }
            }, menu);

            rvMenu.setAdapter(adapter);
        }else {
            adapter.setMenu(menu);
            adapter.notifyDataSetChanged();
        }
    }
}
