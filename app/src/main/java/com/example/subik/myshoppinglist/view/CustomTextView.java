package com.example.subik.myshoppinglist.view;

import android.content.Context;
import android.util.AttributeSet;

import com.example.subik.myshoppinglist.myapplication.MyApplication;

/**
 * Created by subik on 11/29/17.
 */

public class CustomTextView extends android.support.v7.widget.AppCompatTextView {
    public CustomTextView(Context context) {
        super(context);
        setFont();
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont();
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFont();
    }

    private void setFont() {
        setTypeface(MyApplication.Fonts.FontIcon);
    }
}
