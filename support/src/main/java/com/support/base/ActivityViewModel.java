package com.support.base;

import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.graphics.drawable.Drawable;
import android.view.View;

public class ActivityViewModel<T extends CoreActivity> extends BaseObservable {
    public ObservableField<String> toolBarTitle = new ObservableField<>("");
    public ObservableBoolean isBackEnabled = new ObservableBoolean(false);
    public ObservableBoolean isToolBarVisible = new ObservableBoolean(true);
    public ObservableBoolean isToolBarWhite = new ObservableBoolean(true);
    public ObservableBoolean isRightIconEnabled = new ObservableBoolean(false);
    public ObservableField<Drawable> rightIcon = new ObservableField<>();
    public ObservableBoolean isTitleVisible = new ObservableBoolean(true);

    public T coreActivity;

    public ActivityViewModel(T coreActivity) {
        this.coreActivity = coreActivity;
    }

    public void onBackPressed(View view) {
        if (coreActivity != null)
            if (isBackEnabled.get()) coreActivity.onBackPressed();
    }

    public void rightIconPressed(View view) {
        if (coreActivity != null)
            if (isRightIconEnabled.get()) coreActivity.onRightButtonClick(view);
    }
}

