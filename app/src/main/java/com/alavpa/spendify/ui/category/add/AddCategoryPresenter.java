package com.alavpa.spendify.ui.category.add;

import com.alavpa.spendify.di.PerActivity;
import com.alavpa.spendify.domain.model.Category;
import com.alavpa.spendify.domain.usecases.InsertCategory;
import com.alavpa.spendify.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.observers.DisposableSingleObserver;

/**
 * Created by alavpa on 28/02/17.
 */

@PerActivity
public class AddCategoryPresenter extends BasePresenter<AddCategoryView> {

    InsertCategory insertCategory;

    @Inject
    public AddCategoryPresenter(InsertCategory insertCategory){
        this.insertCategory = insertCategory;
        addUseCases(insertCategory);
    }
    public void showColors(){

        int[] colors = resources.getCategoryBackgroundsArray();

        getView().populateColors(colors);
    }

    public void send() {
        String name = getView().name();
        boolean income = getView().income();
        int color = getView().color();

        Category category = new Category(name,income,color);

        insertCategory.setCategory(category);
        insertCategory.execute(new DisposableSingleObserver<Category>() {
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
}
