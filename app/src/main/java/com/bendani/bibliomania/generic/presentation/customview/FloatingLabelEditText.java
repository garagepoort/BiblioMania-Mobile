package com.bendani.bibliomania.generic.presentation.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bendani.bibliomania.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class FloatingLabelEditText extends LinearLayout {

    private TextView textView;
    private EditText editText;
    private String hint;
    private String hintTextview;
    private String inputType;
    private boolean singleLine;
    private Drawable drawableRight;
    private boolean editTextEnabled;
    private boolean fontFit;
    private boolean textCapSentences;
    private boolean allCaps;

    private List<CustomOnFocusChangeListener> customOnFocusChangeListeners = new ArrayList<>();
    private Context context;

    public FloatingLabelEditText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.context = context;

        TypedArray attributes = context.getTheme().obtainStyledAttributes(
                attributeSet,
                R.styleable.FloatingLabelEditText,
                0, 0);

        hint = attributes.getString(R.styleable.FloatingLabelEditText_hint);
        hintTextview = attributes.getString(R.styleable.FloatingLabelEditText_hintTextview);
        inputType = attributes.getString(R.styleable.FloatingLabelEditText_inputType);
        singleLine = attributes.getBoolean(R.styleable.FloatingLabelEditText_singleLine, true);
        fontFit = attributes.getBoolean(R.styleable.FloatingLabelEditText_fontFit, false);
        textCapSentences = attributes.getBoolean(R.styleable.FloatingLabelEditText_textCapSentences, true);
        editTextEnabled = attributes.getBoolean(R.styleable.FloatingLabelEditText_enabled, true);
        allCaps = attributes.getBoolean(R.styleable.FloatingLabelEditText_allCaps, false);
        drawableRight = attributes.getDrawable(R.styleable.FloatingLabelEditText_drawableRight);

        textView = new TextView(context);
        textView.setVisibility(INVISIBLE);

        setFontFit(context);

        editText.setHint(hint);
        editText.addTextChangedListener(getTextChangeListener());
        editText.setOnEditorActionListener(getOnEditorActionListener());
        editText.setSingleLine(true);
        editText.setEnabled(editTextEnabled);
        editText.setAllCaps(allCaps);
        if (allCaps) {
            editText.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        }

        editText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        if (!singleLine) {
            editText.setHorizontallyScrolling(false);
            editText.setMaxLines(Integer.MAX_VALUE);
        }
        if (drawableRight != null) {
            editText.setCompoundDrawablesWithIntrinsicBounds(null, null, drawableRight, null);
        }
        editText.setOnFocusChangeListener(getFocusChangeListener());
        editText.setBackgroundResource(R.drawable.floatinglabel_edittext_bg);
        editText.setPadding(0, convertDpToPx(4), 0, convertDpToPx(14));
        Field f = null;
        try {
            f =TextView.class.getDeclaredField("mCursorDrawableRes");
            f.setAccessible(true);
            f.set(editText, R.drawable.mycursor);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        setInputType();

        if (hintTextview == null) {
            hintTextview = hint;
        }

        textView.setText(hintTextview);
        textView.setFocusable(true);
        textView.setFocusableInTouchMode(true);

        setOrientation(LinearLayout.VERTICAL);
        addView(textView);
        addView(editText);
    }

    private void setFontFit(Context context) {
        if (fontFit) {
            editText = new FontFitEditText(context);
            editText.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, convertDpToPx(131), 1f));
        } else {
            editText = new EditText(context);
            editText.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1f));
        }
    }

    private void setInputType() {
        int input = EditorInfo.TYPE_CLASS_TEXT;
        if (textCapSentences) {
            input = input | EditorInfo.TYPE_TEXT_FLAG_CAP_SENTENCES;
        }
        if(inputType != null && inputType.equals("textEmailAddress")){
            input = input | EditorInfo.TYPE_TEXT_VARIATION_EMAIL_ADDRESS;
        }
        if(inputType != null && inputType.equals("number")){
            input = input | EditorInfo.TYPE_CLASS_NUMBER;
        }
        if(inputType != null && inputType.equals("password")){
            input = input | EditorInfo.TYPE_TEXT_VARIATION_PASSWORD;
        }
        editText.setInputType(input);
    }

    private TextView.OnEditorActionListener getOnEditorActionListener() {
        return new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    closeVirtualKeyboard(editText);
                    editText.clearFocus();
                    textView.requestFocus();
                }
                return false;
            }
        };
    }

    private TextWatcher getTextChangeListener() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if ("".equals(s.toString())) {
                    textView.setVisibility(INVISIBLE);
                } else {
                    textView.setVisibility(VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };
    }

    public void setTextEditText(String text) {
        editText.setText(text);
    }

    public void setTextEditText(CharSequence text) {
        editText.setText(text);
    }

    public String getTextEditText() {
        return editText.getText().toString();
    }

    public OnFocusChangeListener getFocusChangeListener() {
        return new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                for (CustomOnFocusChangeListener customOnFocusChangeListener : customOnFocusChangeListeners) {
                    customOnFocusChangeListener.onFocusChange(v, hasFocus);
                }
                if (hasFocus) {
                    textView.setTextColor(getResources().getColor(R.color.primary_color));
                } else {
                    textView.setTextColor(getResources().getColor(android.R.color.tertiary_text_dark));
                }
            }
        };
    }

    public void addOnfocusChangeListener(CustomOnFocusChangeListener customOnFocusChangeListener) {
        customOnFocusChangeListeners.add(customOnFocusChangeListener);
    }

    public void setErrorEditText(String errorEditText) {
        this.editText.setError(errorEditText);
    }

    public void setEditTextEnabled(boolean enabled) {
        editText.setEnabled(enabled);
    }

    public void addTextListener(TextWatcher textWatcher) {
        editText.addTextChangedListener(textWatcher);
    }

    public void setTextColor(int color) {
        editText.setTextColor(color);
    }

    public void setEditTextTag(Object editTextTag) {
        editText.setTag(editTextTag);
    }

    public static abstract class CustomOnFocusChangeListener {
        public abstract void onFocusChange(View v, boolean hasFocus);
    }

    private int convertDpToPx(int dp) {
        return Math.round(dp * (getResources().getDisplayMetrics().xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public void closeVirtualKeyboard(View focus) {
        InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(focus.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
