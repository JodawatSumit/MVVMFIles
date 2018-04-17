package com.mvvm.demo.modules.home;

import android.databinding.ObservableBoolean;

import com.support.base.ActivityViewModel;

/**
 * Created by parth on 3/10/17.
 */

public class HomeActivityViewModel extends ActivityViewModel<HomeActivity> {
    public ObservableBoolean loading = new ObservableBoolean(false);

    public HomeActivityViewModel(HomeActivity coreActivity) {
        super(coreActivity);
    }

}
