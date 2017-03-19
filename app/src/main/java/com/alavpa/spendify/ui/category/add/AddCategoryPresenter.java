package com.alavpa.spendify.ui.category.add;

import com.alavpa.spendify.di.PerActivity;
import com.alavpa.spendify.domain.model.Category;
import com.alavpa.spendify.domain.usecases.InsertOrUpdateCategory;
import com.alavpa.spendify.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.observers.DisposableSingleObserver;

/**
 * Created by alavpa on 28/02/17.
 */

@PerActivity
public class AddCategoryPresenter extends BasePresenter<AddCategoryView> {

    InsertOrUpdateCategory insertOrUpdateCategory;
    private Category category;

    @Inject
    public AddCategoryPresenter(InsertOrUpdateCategory insertOrUpdateCategory){
        this.insertOrUpdateCategory = insertOrUpdateCategory;
        addUseCases(insertOrUpdateCategory);
    }
    public void showColors(){

        int[] colors = resources.getCategoryBackgroundsArray();

        getView().populateColors(colors, category.getColor());
    }

    public void showIncome(){
        getView().showIncome(category.isIncome());
    }

    public void showName(){
        getView().showName(category.getName());
    }

    public void showSelected(){
        getView().setSelected(category.getColor());
    }

    public void send(String name, boolean income, int color) {

        category.setName(name);
        category.setIncome(income);
        category.setColor(color);

        insertOrUpdateCategory.setCategory(category);
        insertOrUpdateCategory.execute(new DisposableSingleObserver<Category>() {
            @Override
            public void onSuccess(Category category) {
                getView().onSendSuccess();
            }

            @Override
            public void onError(Throwable e) {
                getView().showError(e.getMessage());
            }
        });

    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
