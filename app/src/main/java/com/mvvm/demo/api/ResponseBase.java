package com.mvvm.demo.api;

/**
 * Created by amri_fashions_android_git on 3/11/17.
 */

public class ResponseBase {

    private boolean success;

    private Error error;

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }
}
