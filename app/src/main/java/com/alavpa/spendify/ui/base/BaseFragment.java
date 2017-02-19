package com.alavpa.spendify.ui.base;

import android.support.v4.app.Fragment;
import android.widget.Toast;

/**
 * Created by alavpa on 14/02/17.
 */

public class BaseFragment extends Fragment implements BaseView {
    @Override
    public void showError(String message) {
        Toast.makeText(getActivity(),message,Toast.LENGTH_LONG).show();
    }
}
