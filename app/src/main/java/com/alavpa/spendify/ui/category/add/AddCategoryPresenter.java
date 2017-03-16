package com.alavpa.spendify.ui.category.add;

import com.alavpa.spendify.data.resources.ResDatasource;
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

    ResDatasource resDatasource;
    InsertCategory insertCategory;

    @Inject
    public AddCategoryPresenter(ResDatasource resDatasource,
                                InsertCategory insertCategory){

        super(insertCategory);

        this.resDatasource = resDatasource;
        this.insertCategory = insertCategory;

    }
    public void showColors(){

        int[] colors = resDatasource.getCategoryBackgroundsArray();

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
