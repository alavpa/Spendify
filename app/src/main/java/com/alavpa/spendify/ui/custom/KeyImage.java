package com.alavpa.spendify.ui.custom;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.alavpa.spendify.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alavpa on 9/02/17.
 */

public class KeyImage extends FrameLayout {

    @BindView(R.id.iv_key)
    ImageView ivKey;

    public KeyImage(Context context) {
        super(context);
        init(null);
    }

    public KeyImage(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public KeyImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public KeyImage(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);

    }

    private void init(AttributeSet attrs){
        LayoutInflater.from(getContext())
                .inflate(R.layout.key_image,this,true);
        ButterKnife.bind(this);
        setAttrs(attrs);
    }

    public void setAttrs(AttributeSet attrs){
        TypedArray a = getContext().obtainStyledAttributes(
                attrs,
                R.styleable.KeyImage);

        Drawable drawable;
        try {
            drawable = a.getDrawable(R.styleable.KeyImage_src);
        } finally {
            a.recycle();
        }

        ivKey.setImageDrawable(drawable);
    }
}
