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
import android.widget.DatePicker;

import com.alavpa.spendify.R;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by alavpa on 19/02/17.
 */

public class DatePickerDialog extends DialogFragment {

    public static final String ARGS_DATE = "date";

    @BindView(R.id.pk_date)
    DatePicker datePicker;

    Calendar calendar = Calendar.getInstance();

    OnDatePickerSelected onDatePickerSelected;

    public interface OnDatePickerSelected{
        void onDateSet(long date);
    }

    public static DatePickerDialog getInstance(long date, OnDatePickerSelected onDatePickerSelected){

        DatePickerDialog datePickerDialog =  new DatePickerDialog();
        Bundle args = new Bundle();
        args.putLong(ARGS_DATE,date);
        datePickerDialog.setArguments(args);
        datePickerDialog.onDatePickerSelected = onDatePickerSelected;
        return datePickerDialog;
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
        return inflater.inflate(R.layout.dialog_date,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);

        long date = getArguments().getLong(ARGS_DATE);

        calendar.setTimeInMillis(date);

        datePicker.init(calendar.get(Calendar.YEAR),
                Calendar.MONTH,
                Calendar.DAY_OF_MONTH,
                null);

    }

    @OnClick(R.id.btn_ok)
    public void onSelectDate(View view){
        calendar.set(Calendar.YEAR,datePicker.getYear());
        calendar.set(Calendar.MONTH,datePicker.getMonth());
        calendar.set(Calendar.DAY_OF_MONTH,datePicker.getDayOfMonth());
        onDatePickerSelected.onDateSet(calendar.getTimeInMillis());
        dismiss();
    }

    @OnClick(R.id.btn_cancel)
    public void onCancel(View view){
        dismiss();
    }
}
