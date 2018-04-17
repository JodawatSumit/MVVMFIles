package com.support.widgets;

import android.content.Context;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.AttributeSet;

public class CheckBoxPlus extends AppCompatCheckBox {

    public CheckBoxPlus(Context context) {
        super(context);
    }

    public CheckBoxPlus(Context context, AttributeSet attrs) {
        super(context, attrs);
        CustomFontHelper.setCustomTypeFace(this, context, attrs);
    }

    public CheckBoxPlus(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        CustomFontHelper.setCustomTypeFace(this, context, attrs);
    }
}
