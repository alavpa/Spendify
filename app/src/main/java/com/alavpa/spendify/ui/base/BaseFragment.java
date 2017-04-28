package com.alavpa.spendify.ui.base;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.alavpa.spendify.di.HasComponent;
import com.alavpa.spendify.di.activity.ActivityComponent;
import com.alavpa.spendify.di.scopes.PerActivity;

/**
 * Created by alavpa on 14/02/17.
 */

@PerActivity
public class BaseFragment extends Fragment implements BaseView {

    protected
    BaseView parent;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        parent = (BaseView) context;
    }

    @Override
    public void showError(String message) {
        parent.showError(message);
    }

    @Override
    public void finish() {
        parent.finish();
    }

    @Override
    public void setResult(int result) {
        parent.setResult(result);
    }

    @Override
    public void showLoader() {
        parent.showLoader();
    }

    @Override
    public void dismissLoader() {
        parent.showLoader();
    }

    public ActivityComponent getComponent(){
        return ((HasComponent<ActivityComponent>)getActivity()).getComponent();
    }
}
