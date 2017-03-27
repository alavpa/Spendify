package com.alavpa.spendify.ui.category.add;

import com.alavpa.spendify.di.PerActivity;
import com.alavpa.spendify.domain.model.Category;
import com.alavpa.spendify.domain.usecases.DeleteCategory;
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
    DeleteCategory deleteCategory;
    private Category category;

    @Inject
    public AddCategoryPresenter(InsertOrUpdateCategory insertOrUpdateCategory, DeleteCategory deleteCategory) {
        this.insertOrUpdateCategory = insertOrUpdateCategory;
        this.deleteCategory = deleteCategory;

        addUseCases(insertOrUpdateCategory, deleteCategory);
    }

    public void showLimit(){
        getView().showLimit(category.getLimit());
    }
    public void showColors() {

        int[] colors = resources.getCategoryBackgroundsArray();

        getView().populateColors(colors, category.getColor());
    }

    public void showName() {
        getView().showName(category.getName());
    }

    public void showSelected() {
        getView().setSelected(category.getColor());
    }

    public void send(String name, int color, double limit) {

        category.setName(name);
        category.setColor(color);
        category.setLimit(limit);

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

    public void send(String name, int color) {
        send(name,color,0);
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void showDelete() {
        getView().setDeletable(category.getId()>0);
    }

    public void deleteCategory() {
        deleteCategory.setCategory(category);
        deleteCategory.execute(new DisposableSingleObserver<Boolean>() {
            @Override
            public void onSuccess(Boolean aBoolean) {
                getView().finish();
            }

            @Override
            public void onError(Throwable e) {
                getView().showError(e.getMessage());
            }
        });
    }
}
