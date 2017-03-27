package com.alavpa.spendify.ui.category.add;

import com.alavpa.spendify.di.PerActivity;
import com.alavpa.spendify.domain.model.Category;
import com.alavpa.spendify.domain.usecases.InsertOrUpdateCategory;
import com.alavpa.spendify.ui.base.BasePresenter;

import java.text.DecimalFormat;
import java.text.ParseException;

import javax.inject.Inject;

import io.reactivex.observers.DisposableSingleObserver;

/**
 * Created by alavpa on 28/02/17.
 */

@PerActivity
public class AddCategoryPresenter extends BasePresenter<AddCategoryView> {

    DecimalFormat decimalFormat;
    InsertOrUpdateCategory insertOrUpdateCategory;
    private Category category;

    @Inject
    public AddCategoryPresenter(InsertOrUpdateCategory insertOrUpdateCategory, DecimalFormat decimalFormat) {
        this.insertOrUpdateCategory = insertOrUpdateCategory;
        this.decimalFormat = decimalFormat;

        addUseCases(insertOrUpdateCategory);
    }

    public void showLimit(){
        String limit = decimalFormat.format(category.getLimit());
        getView().showLimit(limit);
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

    public void send(String name, int color, String limit) {

        try {
            double limitValue = decimalFormat.parse(limit).doubleValue();
            category.setName(name);
            category.setColor(color);
            category.setLimit(limitValue);

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

        } catch (ParseException e) {
            getView().showError(e.getMessage());
        }

    }

    public void send(String name, int color) {
        send(name,color,"0");
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
