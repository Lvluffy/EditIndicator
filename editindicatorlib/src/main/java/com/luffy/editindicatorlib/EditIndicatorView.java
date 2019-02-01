package com.luffy.editindicatorlib;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by lvlufei on 2019/2/1.
 */

public class EditIndicatorView extends LinearLayout {

    /**
     * 组合控件
     */
    private EditText editContent;
    private TextView textNum;

    /**
     * 默认值
     */
    private int defaultEditColor = Color.parseColor("#666666");
    private int defaultEditHintColor = Color.parseColor("#F6F6F6");
    private int defaultTextColor = Color.parseColor("#333333");

    /**
     * 属性值
     */
    /*编辑框*/
    String editText;
    int editColor;
    float editSize;
    int editCursorDrawable;
    float editLineSpacingExtra;
    String editHint;
    int editHintColor;
    int editPaddingTop;
    int editPaddingBottom;
    int editPaddingLeft;
    int editPaddingRight;
    /*文本指示器*/
    int textColor;
    float textSize;
    int textMax;
    int textType;
    int textGravity;
    int textPaddingTop;
    int textPaddingBottom;
    int textPaddingLeft;
    int textPaddingRight;


    public EditIndicatorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public EditIndicatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    /**
     * 初始化
     *
     * @param context
     * @param attrs
     */
    private void init(Context context, AttributeSet attrs) {
        /*初始化组合控件*/
        initWidget(context);
        /*初始化属性*/
        initAttrs(context, attrs);
        /*初始化值*/
        initValue();
        /*处理业务*/
        handlerBusines();
    }

    /**
     * 初始化组合控件
     *
     * @param context
     */
    private void initWidget(Context context) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.view_edit_indicator, null);
        editContent = rootView.findViewById(R.id.edit_content);
        textNum = rootView.findViewById(R.id.text_num);
        LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        addView(rootView, llp);
    }

    /**
     * 初始化属性
     *
     * @param context
     * @param attrs
     */
    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.EditIndicatorView);
        /**编辑框*/
        editText = attributes.getString(R.styleable.EditIndicatorView_editText);
        editColor = attributes.getColor(R.styleable.EditIndicatorView_editColor, defaultEditColor);
        editSize = attributes.getInteger(R.styleable.EditIndicatorView_editSize, 14);
        editCursorDrawable = attributes.getResourceId(R.styleable.EditIndicatorView_editCursorDrawable, R.drawable.base_cursor_color);
        editLineSpacingExtra = attributes.getDimensionPixelSize(R.styleable.EditIndicatorView_editLineSpacingExtra, 5);
        editHint = attributes.getString(R.styleable.EditIndicatorView_editHint);
        editHintColor = attributes.getColor(R.styleable.EditIndicatorView_editHintColor, defaultEditHintColor);
        editPaddingTop = attributes.getDimensionPixelSize(R.styleable.EditIndicatorView_editPaddingTop, 0);
        editPaddingBottom = attributes.getDimensionPixelSize(R.styleable.EditIndicatorView_editPaddingBottom, 0);
        editPaddingLeft = attributes.getDimensionPixelSize(R.styleable.EditIndicatorView_editPaddingLeft, 0);
        editPaddingRight = attributes.getDimensionPixelSize(R.styleable.EditIndicatorView_editPaddingRight, 0);
        /**文本指示器*/
        textColor = attributes.getColor(R.styleable.EditIndicatorView_textColor, defaultTextColor);
        textSize = attributes.getInteger(R.styleable.EditIndicatorView_textSize, 14);
        textMax = attributes.getInteger(R.styleable.EditIndicatorView_textMax, 30);
        textType = attributes.getInteger(R.styleable.EditIndicatorView_textType, 1);
        textGravity = attributes.getInteger(R.styleable.EditIndicatorView_textGravity, 1);
        textPaddingTop = attributes.getDimensionPixelSize(R.styleable.EditIndicatorView_textPaddingTop, 0);
        textPaddingBottom = attributes.getDimensionPixelSize(R.styleable.EditIndicatorView_textPaddingBottom, 0);
        textPaddingLeft = attributes.getDimensionPixelSize(R.styleable.EditIndicatorView_textPaddingLeft, 0);
        textPaddingRight = attributes.getDimensionPixelSize(R.styleable.EditIndicatorView_textPaddingRight, 0);
        /**回收*/
        attributes.recycle();
    }

    /**
     * 初始化值
     */
    private void initValue() {
        /**编辑框*/
        /*内容*/
        editContent.setText(editText);
        /*颜色*/
        editContent.setTextColor(editColor);
        /*大小*/
        editContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, editSize);
        /*文字行间距*/
        editContent.setLineSpacing(editLineSpacingExtra, 1);
        /*提示文字*/
        editContent.setHint(editHint);
        /*提示文字颜色*/
        editContent.setHintTextColor(editHintColor);
        /*光标*/
//        editContent.setSelection(editContent.getText().toString().trim().length());
        /*内边框*/
        editContent.setPadding(editPaddingLeft, editPaddingTop, editPaddingRight, editPaddingBottom);
        /**文本指示器*/
        /*颜色*/
        textNum.setTextColor(textColor);
        /*大小*/
        textNum.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        /*最大长度*/
        textNum.setFilters(new InputFilter[]{new InputFilter.LengthFilter(textMax)});
        /*类型*/
        switch (textType) {
            case 1:
                textNum.setVisibility(GONE);
                break;
            case 2:
                textNum.setVisibility(VISIBLE);
                textNum.setText(editContent.getText().toString().trim().length() + "/" + textMax);
                break;
            case 3:
                textNum.setVisibility(VISIBLE);
                textNum.setText(editContent.getText().toString().trim().length() * 100 / textMax + "%");
                break;
        }
        /*位置*/
        switch (textGravity) {
            case 1:
                textNum.setGravity(Gravity.LEFT);
                break;
            case 2:
                textNum.setGravity(Gravity.CENTER);
                break;
            case 3:
                textNum.setGravity(Gravity.RIGHT);
                break;
        }
        /*内边框*/
        textNum.setPadding(textPaddingLeft, textPaddingTop, textPaddingRight, textPaddingBottom);
    }

    /**
     * 处理业务
     */
    private void handlerBusines() {
        editContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int length = s.toString().trim().length();
                if (length > textMax) {
                    return;
                }
                switch (textType) {
                    case 1:
                        textNum.setVisibility(GONE);
                        break;
                    case 2:
                        textNum.setText(length + "/" + textMax);
                        break;
                    case 3:
                        textNum.setText(length * 100 / textMax + "%");
                        break;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
}
