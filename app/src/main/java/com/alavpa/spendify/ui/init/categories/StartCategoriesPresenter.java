package com.alavpa.spendify.ui.init.categories;

import com.alavpa.spendify.di.PerActivity;
import com.alavpa.spendify.domain.model.Category;
import com.alavpa.spendify.domain.usecases.GetCategories;
import com.alavpa.spendify.ui.base.BasePresenter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.observers.DisposableSingleObserver;

@PerActivity
public class StartCategoriesPresenter extends BasePresenter<StartCategoriesView>{

    GetCategories getCategoriesIncome;
    GetCategories getCategoriesOutcome;

    @Inject
    public StartCategoriesPresenter(GetCategories getCategoriesIncome, GetCategories getCategoriesOutcome){

        this.getCategoriesIncome = getCategoriesIncome;
        this.getCategoriesOutcome = getCategoriesOutcome;

        addUseCases(getCategoriesIncome,getCategoriesOutcome);

    }

    public void showIncomes() {
        getCategoriesIncome.setIncome(true);
        getCategoriesIncome.execute(new DisposableSingleObserver<List<Category>>() {
            @Override
            public void onSuccess(List<Category> categories) {
                getView().populateCategoriesIncome(categories, resources.getCategoryBackgroundsArray());
            }

            @Override
            public void onError(Throwable e) {
                getView().showError(e.getMessage());
            }
        });
    }

    public void showOutcomes() {
        getCategoriesOutcome.setIncome(false);
        getCategoriesOutcome.execute(new DisposableSingleObserver<List<Category>>() {
            @Override
            public void onSuccess(List<Category> categories) {
                getView().populateCategoriesOutcome(categories, resources.getCategoryBackgroundsArray());
            }

            @Override
            public void onError(Throwable e) {
                getView().showError(e.getMessage());
            }
        });
    }

    public void onAddCategory(boolean income) {
        Category category = new Category();
        category.setIncome(income);
        navigator.openAddCategory(category);
    }


    public void editCategory(Category category) {
        navigator.openAddCategory(category);
    }

    public void onClickNext() {
        navigator.openReminder();
        getView().finish();
    }
}
