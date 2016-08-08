package com.bendani.bibliomania.generic.presentation.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bendani.bibliomania.R;

public class FloatingLabelTextview extends LinearLayout {

    private final String textviewText;
    private TextView hintTextView;
    private TextView textTextview;
    private String hintText;


    public FloatingLabelTextview(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

        TypedArray attributes = context.getTheme().obtainStyledAttributes(
                attributeSet,
                R.styleable.FloatingLabelTextview,
                0, 0);

        hintText = attributes.getString(R.styleable.FloatingLabelTextview_hintText);
        textviewText = attributes.getString(R.styleable.FloatingLabelTextview_textText);

        hintTextView = new TextView(context);
        hintTextView.setTextColor(getResources().getColor(R.color.primary_color));
        hintTextView.setText(hintText);


        textTextview = new TextView(context);
        textTextview.setTextColor(getResources().getColor(R.color.primary_color));
        textTextview.setText(textviewText);
        textTextview.setBackgroundResource(R.drawable.floatinglabel_edittext_bg);
        textTextview.setPadding(0, convertDpToPx(4), 0, convertDpToPx(14));
        textTextview.setTextSize(16);

        setOrientation(LinearLayout.VERTICAL);
        addView(hintTextView);
        addView(textTextview);
    }


    private int convertDpToPx(int dp) {
        return Math.round(dp * (getResources().getDisplayMetrics().xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public void setText(String text) {
        this.textTextview.setText(text);
    }
}
