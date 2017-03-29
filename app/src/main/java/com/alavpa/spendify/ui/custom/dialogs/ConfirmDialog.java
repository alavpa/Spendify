package com.alavpa.spendify.ui.custom.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.alavpa.spendify.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by alavpa on 19/02/17.
 */

public class ConfirmDialog extends DialogFragment {

    public static final String ARGS_MESSAGE = "ARGS_MESSAGE";

    @BindView(R.id.tv_message)
    TextView tvMessage;

    ConfirmDialogListener confirmDialogListener;

    public interface ConfirmDialogListener{
        void onOk();
        void onCancel();
    }

    public static ConfirmDialog getInstance(String message,ConfirmDialogListener confirmDialogListener ){

        ConfirmDialog confirmDialog =  new ConfirmDialog();
        Bundle args = new Bundle();
        args.putString(ARGS_MESSAGE,message);
        confirmDialog.setArguments(args);
        confirmDialog.confirmDialogListener = confirmDialogListener;
        return confirmDialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_confirm,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);

        String message = getArguments().getString(ARGS_MESSAGE);
        tvMessage.setText(message);


    }

    @OnClick(R.id.btn_ok)
    public void onSelectDate(View view){
        confirmDialogListener.onOk();
        dismiss();
    }

    @OnClick(R.id.btn_cancel)
    public void onCancel(View view){
        dismiss();
    }
}
