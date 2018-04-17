package com.support.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import io.reactivex.disposables.CompositeDisposable;

public abstract class CoreFragment
        <T extends CoreFragment,
                VM extends FragmentViewModel,
                DB extends ViewDataBinding> extends Fragment {
    private CompositeDisposable compositeDisposable;
    private T fragment;
    private DB binding;
    private VM vm;
    private View rootView;

    /**
     * this method disables all click through calling setListeners(false)
     */
    public void showProgressBar(ProgressBar progressBar) {
        progressBar.setVisibility(View.VISIBLE);
        setListeners(false);
    }

    /**
     * this method enables all click through calling setListeners(true)
     */
    public void hideProgressBar(ProgressBar progressBar) {
        setListeners(true);
        progressBar.setVisibility(View.GONE);
    }

    public abstract void setListeners(boolean state);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return getRootView(inflater, container);
    }

    private View getRootView(LayoutInflater inflater, ViewGroup container) {
        this.fragment = getCoreFragment();

        if (rootView == null) {
            binding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false);
            rootView = binding.getRoot();
            setVM(binding);
        }
        getViewModel().coreActivity.getViewModel().isTitleVisible.set(true);
        setTitle();
        setBackButton();
        setRightIconVisibility(false);
        createReference();
        setListeners(true);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        setHasOptionsMenu(hasOptionMenu());
    }

    @Override
    public void onStop() {
        super.onStop();
        setHasOptionsMenu(false);
    }

    public void setBackButton() {
        getViewModel().coreActivity.setBackButton(isBackEnabled());
    }

    public void setRightIconVisibility(boolean isVisible) {
        getViewModel().coreActivity.isRightIconVisible(isVisible);
    }

    public void setRightIcon(Drawable rightIcon) {
        getViewModel().coreActivity.setRightIcon(rightIcon);
    }

    protected abstract boolean isBackEnabled();


    private void setTitle() {
        if (!TextUtils.isEmpty(getTitle()))
            getViewModel().coreActivity.changeTitleTV(getTitle());
    }

    public abstract boolean hasOptionMenu();

    public abstract String getTitle();

    public abstract T getCoreFragment();

    @LayoutRes
    public abstract int getLayoutRes();

    public abstract void createReference();

    /**
     * Write this code binding.setVm(getViewModel());
     */
    public abstract void setVM(DB binding);

    public VM getViewModel() {
        if (vm == null) vm = createViewModel(fragment);
        return vm;
    }

    public DB getBinding() {
        return binding;
    }

    /**
     * Write this code new VM(); where VM is your custom ViewModelClass
     *
     * @return {@link android.databinding.BaseObservable}
     */
    public abstract VM createViewModel(T coreFragment);

    public CompositeDisposable getCompositeDisposable() {
        if (compositeDisposable != null) {
            compositeDisposable = new CompositeDisposable();
        }
        return compositeDisposable;
    }

    @Override
    public void onDestroyView() {
        if (compositeDisposable != null) compositeDisposable.clear();
        super.onDestroyView();
    }

}
