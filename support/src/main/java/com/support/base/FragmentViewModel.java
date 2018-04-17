package com.support.base;

import android.databinding.BaseObservable;
import android.support.v4.app.FragmentActivity;

public class FragmentViewModel<T extends CoreFragment> extends BaseObservable {
    public T coreFragment;

    public CoreActivity coreActivity;

    public FragmentViewModel(T coreFragment) {
        this.coreFragment = coreFragment;
        FragmentActivity activity = coreFragment.getActivity();
        if (activity instanceof CoreActivity) {
            this.coreActivity = (CoreActivity) activity;
        }
    }


}
