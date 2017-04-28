package com.alavpa.spendify.ui.base;

/**
 * Created by alavpa on 10/02/17.
 */

public interface BaseView {
    void showError(String message);
    void finish();
    void setResult(int result);

    void showLoader();

    void dismissLoader();
}
