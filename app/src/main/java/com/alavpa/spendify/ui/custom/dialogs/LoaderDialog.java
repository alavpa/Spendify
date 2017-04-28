package com.alavpa.spendify.ui.custom.dialogs;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

import com.alavpa.spendify.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alavpa on 19/02/17.
 */

public class LoaderDialog extends DialogFragment {

    private static final long DURATION = 3600;

    @BindView(R.id.iv_logo)
    public ImageView ivLogo;

    public static LoaderDialog getInstance() {

        LoaderDialog loaderDialog = new LoaderDialog();
        return loaderDialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().getDecorView().setBackgroundResource(android.R.color.transparent);
        dialog.setCancelable(false);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_loader, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        ObjectAnimator animation = ObjectAnimator.ofFloat(ivLogo, "rotationY", 0.0f, 360f);
        animation.setDuration(DURATION);
        animation.setRepeatCount(ObjectAnimator.INFINITE);
        animation.setInterpolator(new AccelerateDecelerateInterpolator());
        animation.start();

    }
}
