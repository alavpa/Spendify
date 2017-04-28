package com.alavpa.spendify.ui.init.categories;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alavpa.spendify.R;
import com.alavpa.spendify.di.scopes.PerActivity;
import com.alavpa.spendify.domain.model.Category;
import com.alavpa.spendify.ui.base.BaseActivity;
import com.alavpa.spendify.ui.custom.GridLayoutManager;
import com.alavpa.spendify.ui.custom.adapters.CategoryAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

@PerActivity
public class StartCategoriesActivity extends BaseActivity implements StartCategoriesView{

    @BindView(R.id.rv_income)
    RecyclerView rvIncome;
    @BindView(R.id.rv_outcome)
    RecyclerView rvOutcome;
    @BindView(R.id.btn_apply)
    TextView btnApply;

    @Inject
    StartCategoriesPresenter presenter;

    CategoryAdapter adapterIncome;
    CategoryAdapter adapterOutcome;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_categories);
        ButterKnife.bind(this);

        getActivityComponent().inject(this);
        setPresenter(presenter);

        rvIncome.setLayoutManager(new GridLayoutManager(this));
        rvOutcome.setLayoutManager(new GridLayoutManager(this));

        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onClickNext();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.showIncomes();
        presenter.showOutcomes();
    }

    @Override
    public void populateCategoriesIncome(List<Category> categories, List<Integer> colors) {
        if(adapterIncome==null){
            adapterIncome = new CategoryAdapter(this, categories, colors, false, new CategoryAdapter.OnCategoryClick() {
                @Override
                public void onClick(Category category) {
                    presenter.editCategory(category);
                }

                @Override
                public void onAddClick() {
                    presenter.onAddCategory(true);
                }
            });

            rvIncome.setAdapter(adapterIncome);
        }else{
            adapterIncome.setCategories(categories);
            adapterIncome.notifyDataSetChanged();
        }
    }

    @Override
    public void populateCategoriesOutcome(final List<Category> categories, List<Integer> colors) {

        if(adapterOutcome==null){
            adapterOutcome = new CategoryAdapter(this, categories, colors,false, new CategoryAdapter.OnCategoryClick() {
                @Override
                public void onClick(Category category) {
                    presenter.editCategory(category);
                }

                @Override
                public void onAddClick() {
                    presenter.onAddCategory(false);
                }
            });

            rvOutcome.setAdapter(adapterOutcome);
        }{
            adapterOutcome.setCategories(categories);
            adapterOutcome.notifyDataSetChanged();
        }

    }
}
