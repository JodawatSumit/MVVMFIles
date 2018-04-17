package com.support.base;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.support.R;
import com.support.utils.ResourceUtils;

import io.reactivex.disposables.CompositeDisposable;

public abstract class CoreActivity
        <T extends CoreActivity,
                VM extends ActivityViewModel,
                DB extends ViewDataBinding>
        extends AppCompatActivity {
    protected static final int REQUEST_CODE_LOGIN = 1001;
    private final static String APP_THEME_NAME = ResourceUtils.getThemeName(R.style.AppTheme);
    private static final String APP_THEME_WHITE_NAME = ResourceUtils.getThemeName(R.style.AppTheme_ToolBar_White);
    private Bundle savedInstanceState;
    private Toolbar toolbar = null;
    private CompositeDisposable compositeDisposable;
    private T activity;
    private DB binding;
    private VM vm;

    public Bundle getSavedInstanceState() {
        return savedInstanceState;
    }

    public void setSavedInstanceState(Bundle savedInstanceState) {
        this.savedInstanceState = savedInstanceState;
    }

    public void setDefaults(T activity, @LayoutRes int layoutRes, @StringRes int title) {
        setDefaults(activity, layoutRes, ResourceUtils.getString(title), false, false);
    }

    public void setDefaults(T activity, @LayoutRes int layoutRes, @StringRes int title, boolean isBackEnabled,
                            boolean isToolBarEnabled) {
        setDefaults(activity, layoutRes, ResourceUtils.getString(title), isBackEnabled, isToolBarEnabled);
    }

    public void setDefaults(T activity, @LayoutRes int layoutRes, String title) {
        setDefaults(activity, layoutRes, title, false, false);
    }

    public void setDefaults(T activity, @LayoutRes int layoutRes) {
        setDefaults(activity, layoutRes, null, false, false);
    }

    public T getActivity() {
        return activity;
    }

    /**
     * @param layoutRes        id of layout file
     * @param title            activity title in String or @StringRes
     * @param isBackEnabled    enable going back into previous activity with toolbar back button
     * @param isToolBarEnabled enable toolbar
     */
    public void setDefaults(T activity, @LayoutRes int layoutRes, String title, boolean isBackEnabled, boolean isToolBarEnabled) {
        this.activity = activity;
        binding = DataBindingUtil.setContentView(activity, layoutRes);
        setVM(binding);
        setToolBar(isToolBarEnabled, isBackEnabled, title);

        createReference();
        setListeners(true);
    }

    /**
     * Write this code binding.setVm(getViewModel());
     */
    public abstract void setVM(DB binding);

    public VM getViewModel() {
        if (vm == null) vm = createViewModel(activity);
        return vm;
    }

    public DB getBinding() {
        return binding;
    }

    /**
     * Write this code new VM(); where VM is your custom ViewModelClass
     *
     * @return {@link BaseObservable}
     */
    public abstract VM createViewModel(T coreActivity);

    /**
     * setting the toolbar
     *
     * @param isToolBarEnabled true if toolbar must be enabled
     * @param isBackEnabled    true for enabling back button
     * @param title            title title in {@link String} format
     */
    private void setToolBar(boolean isToolBarEnabled, boolean isBackEnabled, String title) {
        if (isToolBarEnabled) {
            activity.setSupportActionBar(getToolBar());

            setToolBarBackGround();
            setToolBarVisibility();
            isRightIconVisible(false);
            setBackButton(isBackEnabled);
            changeTitleTV(title);
        }
    }

    private void setToolBarVisibility() {
        getViewModel().isToolBarVisible.set(!ResourceUtils
                .getThemeName(activity, getTheme())
                .equals(APP_THEME_NAME));
    }

    /**
     * if theme is AppTheme then it will set toolbar background as gradient
     * <p>
     * otherwise toolbar will be transparent
     */
    public void setToolBarBackGround() {
        getViewModel().isToolBarWhite.set(!ResourceUtils
                .getThemeName(activity, getTheme())
                .equals(APP_THEME_WHITE_NAME));
    }

    /**
     * set back button of toolbar
     *
     * @param isBackEnabled true for enabling back button
     */
    public void setBackButton(boolean isBackEnabled) {
        try {
            getViewModel().isBackEnabled.set(isBackEnabled);
        } catch (NullPointerException ignored) {

        }
    }

    /**
     * to set title in the middle of toolbar
     *
     * @param title in {@link String}
     */
    public void changeTitleTV(String title) {

        if (toolbar != null && title != null) {
            ActionBar supportActionBar = getSupportActionBar();
            if (supportActionBar != null)
                supportActionBar.setDisplayShowTitleEnabled(false);

            getViewModel().toolBarTitle.set(title);
        }
    }

    /**
     * to set title in the middle of toolbar
     *
     * @param titleRes ID of string resource
     */
    public void changeTitleTV(@StringRes int titleRes) {
        String title = ResourceUtils.getString(titleRes);
        changeTitleTV(title);
    }

    @SuppressWarnings("unchecked")
    public <V extends View> V _findViewById(int viewId) {
        return (V) findViewById(viewId);
    }

    /**
     * @return {@link Toolbar}
     */
    public Toolbar getToolBar() {
        if (toolbar == null) toolbar = findToolbar();
        return toolbar;
    }

    /**
     * Write Code _findViewById(R.id.tool_bar)
     *
     * @return {@link Toolbar}
     */
    protected abstract Toolbar findToolbar();

    public abstract void createReference();

    /**
     * enables or disables listeners
     *
     * @param state true for enabling the listeners
     */
    protected abstract void setListeners(boolean state);

    /**
     * get {@link CompositeDisposable} for RXJava
     *
     * @return {@link CompositeDisposable}
     */
    public CompositeDisposable getCompositeDisposable() {
        if (compositeDisposable != null) {
            compositeDisposable = new CompositeDisposable();
        }
        return compositeDisposable;
    }

    /**
     * call this in {@link android.app.Activity}.onDestroy()
     */
    public void cancelCalls() {
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
    }

    public void onRightButtonClick(View view) {

    }

    public void isRightIconVisible(boolean isVisible) {
        getViewModel().isRightIconEnabled.set(isVisible);
    }

    public void setRightIcon(Drawable resource) {
        getViewModel().rightIcon.set(resource);
    }

    protected void startLoginActivity(Class c) {
        startActivityForResult(new Intent(this, c), REQUEST_CODE_LOGIN);
    }

    public void showLoginDialog(final Class c, int title, int message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(android.R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                startLoginActivity(c);
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    protected void loginDone(){

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            switch (requestCode) {

                case REQUEST_CODE_LOGIN:
                    loginDone();
                    break;
            }
        }
    }
}
