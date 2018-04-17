package com.support.widgets;

import android.content.Context;
import android.support.v7.widget.AppCompatRadioButton;
import android.util.AttributeSet;

public class RadioButtonPlus extends AppCompatRadioButton {

    public RadioButtonPlus(Context context) {
        super(context);
    }

    public RadioButtonPlus(Context context, AttributeSet attrs) {
        super(context, attrs);
        CustomFontHelper.setCustomTypeFace(this, context, attrs);
    }

    public RadioButtonPlus(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        CustomFontHelper.setCustomTypeFace(this, context, attrs);
    }
}
