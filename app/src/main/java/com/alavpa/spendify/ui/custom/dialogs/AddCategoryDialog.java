package com.alavpa.spendify.ui.custom.dialogs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.alavpa.spendify.R;
import com.alavpa.spendify.domain.model.Category;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by alavpa on 19/02/17.
 */

public class AddCategoryDialog extends DialogFragment {

    public static final String ARG_INCOME = "income";
    @BindView(R.id.et_name)
    EditText etName;

    private
    boolean income;

    private
    OnAddCategoryListener onAddCategoryListener;

    public interface OnAddCategoryListener{
        void onOk(Category category);
    }

    public static AddCategoryDialog getInstance(boolean income, OnAddCategoryListener onAddCategoryListener){

        AddCategoryDialog addCategoryDialog =  new AddCategoryDialog();
        Bundle args = new Bundle();
        args.putBoolean(ARG_INCOME,income);
        addCategoryDialog.setArguments(args);
        addCategoryDialog.onAddCategoryListener = onAddCategoryListener;
        return addCategoryDialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_add_category,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);

        getDialog().setTitle(R.string.add_category);
        income = getArguments().getBoolean(ARG_INCOME,false);
    }

    @OnClick(R.id.btn_ok)
    public void onOk(View view){
        Category category = new Category();
        category.setName(etName.getText().toString());
        category.setIncome(income);
        onAddCategoryListener.onOk(category);
        dismiss();
    }

    @OnClick(R.id.btn_cancel)
    public void onCancel(View view){
        dismiss();
    }
}
