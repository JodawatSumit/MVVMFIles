package com.mvvm.demo.support.utils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.mvvm.demo.api.user.User;
import com.support.utils.AppLog;
import com.support.utils.SharedPrefsHelper;

public class SharedPrefs {
    public static User getUser() {
        Gson gson = new Gson();
        String json = SharedPrefsHelper.getInstance().get(AppPrefStrings.USER, "");
        User userModel = null;
        try {
            userModel = gson.fromJson(json, User.class);
        } catch (JsonSyntaxException e) {
            AppLog.log(false, "SharedPrefs: " + "getUser: ", e);
        }
        return userModel;
    }

    public static void setUser(User userModel) {
        Gson gson = new Gson();
        if (userModel != null) {
            String userJson = gson.toJson(userModel);
            SharedPrefsHelper.getInstance().save(AppPrefStrings.USER, userJson);
            setLoginStatus(true);
        } else {
            setLoginStatus(false);
        }
    }

    public static boolean isLoggedIn() {
        return SharedPrefsHelper.getInstance().get(AppPrefStrings.IS_LOGGED_IN, false);
    }

    public static void setLoginStatus(boolean status) {
        SharedPrefsHelper.getInstance().save(AppPrefStrings.IS_LOGGED_IN, status);
    }

    public static boolean isIntroShown() {
        return SharedPrefsHelper.getInstance().get(AppPrefStrings.IS_INTRO_SHOWN, false);
    }

    public static void setIntroShown(boolean status) {
        SharedPrefsHelper.getInstance().save(AppPrefStrings.IS_INTRO_SHOWN, status);
    }

    public static boolean isLauncherShown() {
        return SharedPrefsHelper.getInstance().get(AppPrefStrings.IS_LAUNCHER_SHOWN, false);
    }

    public static void setLauncherShown(boolean state) {
        SharedPrefsHelper.getInstance().save(AppPrefStrings.IS_LAUNCHER_SHOWN, state);
    }

    public static String getSessionId() {
        return SharedPrefsHelper.getInstance().get(AppPrefStrings.TOKEN, "");
    }

    public static void setSessionId(String token) {
        SharedPrefsHelper.getInstance().save(AppPrefStrings.TOKEN, token);
    }

    public interface AppPrefStrings {
        String ADMIN_TOKEN = "adminToken";
        String USER = "USER";
        String TOKEN = "token";
        String IS_LOGGED_IN = "IsLoggedIn";
        String IS_INTRO_SHOWN = "IsIntroShown";
        String IS_LANGUAGE_CHOSEN = "IsLanguageChosen";
        String CHOSEN_LANGUAGE = "ChosenLanguage";
        String IS_LOOK_INSIDE = "IsLookedInside";
        String IS_LAUNCHER_SHOWN = "IsLauncherShown";
    }
}
