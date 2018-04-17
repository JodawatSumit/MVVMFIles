package com.support.widgets;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewParent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;

public class EditTextPlus extends AppCompatEditText {

    public EditTextPlus(Context context) {
        super(context);
    }

    public EditTextPlus(Context context, AttributeSet attrs) {
        super(context, attrs);
        CustomFontHelper.setCustomTypeFace(this, context, attrs);
    }

    public EditTextPlus(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        CustomFontHelper.setCustomTypeFace(this, context, attrs);
    }

    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        final InputConnection ic = super.onCreateInputConnection(outAttrs);
        if (ic != null && outAttrs.hintText == null) {
            // If we don't have a hint and our parent is a TextInputLayout, use it's hint for the
            // EditorInfo. This allows us to display a hint in 'extract mode'.
            ViewParent parent = getParent();
            while (parent instanceof View) {
                if (parent instanceof TextInputLayout) {
                    outAttrs.hintText = ((TextInputLayout) parent).getHint();
                    break;
                }
                parent = parent.getParent();
            }
        }
        return ic;
    }
}
