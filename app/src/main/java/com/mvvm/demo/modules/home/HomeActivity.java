package com.mvvm.demo.modules.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;
import android.widget.TextView;

import com.mvvm.demo.R;
import com.mvvm.demo.databinding.ActivityHomeBinding;
import com.squareup.otto.Subscribe;
import com.support.base.CoreActivity;
import com.support.events.DismisLoading;
import com.support.events.ShowError;
import com.support.events.ShowLoading;
import com.support.utils.Event;

public class HomeActivity extends CoreActivity<HomeActivity, HomeActivityViewModel, ActivityHomeBinding> {

    public static Intent getIntent(Context context, Bundle bundle) {
        return new Intent(context, HomeActivity.class).putExtras(bundle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDefaults(this, R.layout.activity_home, R.string.app_name, false, true);

        getViewModel().isTitleVisible.set(false);

    }

    @Override
    protected void onResume() {
        super.onResume();

        Event.bus.register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        Event.bus.unregister(this);
    }

    @Subscribe
    public void showLoading(ShowLoading showLoading) {
        getViewModel().loading.set(true);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    @Subscribe
    public void dismissLoading(DismisLoading userDismiss) {
        getViewModel().loading.set(false);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    @Subscribe
    public void showError(ShowError error) {

        if (error == null) {
            Snackbar.make(getBinding().getRoot(), getString(R.string.str_global_error_msg), Snackbar.LENGTH_SHORT).show();
            return;
        }
        Snackbar snackbar = Snackbar.make(getBinding().getRoot(), error.getError(), Snackbar.LENGTH_SHORT);
        TextView tv = (TextView) snackbar.getView()
                .findViewById(android.support.design.R.id.snackbar_text);
        tv.setMaxLines(8);
        snackbar.show();
    }

    @Override
    public void setVM(ActivityHomeBinding binding) {
        binding.setVm(getViewModel());
    }

    @Override
    public HomeActivityViewModel createViewModel(HomeActivity coreActivity) {
        return new HomeActivityViewModel(coreActivity);
    }

    @Override
    protected Toolbar findToolbar() {
        return _findViewById(R.id.tool_bar);
    }

    @Override
    public void createReference() {

    }

    @Override
    protected void setListeners(boolean state) {

    }

   /* ObserverUtil
            .subscribeToSingle(ApiClient.getClient().create(IUser.class).login(strEmail, strPass)
                        , coreActivity.getCompositeDisposable(), WebserviceBuilder.ApiNames.login, this);

    @Override
    public void onSingleSuccess(Object o, WebserviceBuilder.ApiNames apiNames) {

        Event.bus.post(new DismisLoading());

        switch (apiNames) {
            case login:

                if (o instanceof LoginSignUpBaseResponse) {

                    LoginSignUpBaseResponse loginSignUpBaseResponse = (LoginSignUpBaseResponse) o;

                    if (loginSignUpBaseResponse.getError() == null && loginSignUpBaseResponse.getLoginSignUpResponse() != null && loginSignUpBaseResponse.getLoginSignUpResponse().getGenerationOnlineUser() != null) {
                        SharedPrefs.setUser(loginSignUpBaseResponse.getLoginSignUpResponse().getGenerationOnlineUser());
                        SharedPrefs.setSessionId(loginSignUpBaseResponse.getLoginSignUpResponse().getSessionId());
                        coreActivity.setResult(Activity.RESULT_OK);
                        coreActivity.finish();
                    } else if (loginSignUpBaseResponse.getError() != null)
                        Event.bus.post(new ShowError(loginSignUpBaseResponse.getError().getMessage()));
                    else
                        Event.bus.post(new ShowError(ResourceUtils.getString(R.string.str_global_error_msg)));

                }
                break;
        }
    }

    @Override
    public void onFailure(Throwable throwable, WebserviceBuilder.ApiNames apiNames) {

        Event.bus.post(new DismisLoading());

        Event.bus.post(new ShowError(throwable.getMessage()));

    }*/

}
