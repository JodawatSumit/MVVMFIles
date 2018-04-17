package com.support.utils;

import android.text.TextUtils;
import android.util.Patterns;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validators {
    public static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isValidMobile(String phone) {
        return phone.length() > 9 && Patterns.PHONE.matcher(phone).matches();
    }

//    /**
//     * Checks for 1 Uppercase Alphabet, 1 Number, 1 Special Character and at least 8 character length
//     *
//     * @param password String password to validate
//     * @return returns true if password is in correct format
//     */
//    public static boolean isValidPassword(String password) {
//        password = password.trim();
//        if (!TextUtils.isEmpty(password)) {
//            String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
//
//            return Pattern.compile(PASSWORD_PATTERN).matcher(password.trim()).matches() && password.length() >= 8;
//        } else {
//            return false;
//        }
//    }

    /**
     * @param password
     * @return checks for 1 letter, 1 digit and length should be between 6-20 character
     */
    public static boolean isValidPassword(final String password) {

        Pattern letter = Pattern.compile("[a-zA-z]");
        Pattern digit = Pattern.compile("[0-9]");
        Pattern six = Pattern.compile(".{6,21}");

        Matcher hasLetter = letter.matcher(password);
        Matcher hasDigit = digit.matcher(password);
        Matcher hasSix = six.matcher(password);
        return hasLetter.find() && hasDigit.find() && hasSix.matches();
    }

    public static boolean isFileExtensionValid(String fileExtension) {
        fileExtension = fileExtension.toLowerCase();
        switch (fileExtension) {
            case "txt":
                return true;
            case "doc":
                return true;
            case "docx":
                return true;
            case "pdf":
                return true;
            case "rtf":
                return true;
            default:
                return false;
        }
    }

    public static boolean isFileSizeValid(File file, double size) {
        return FileChooserUtil.getFileSize(file) <= size;
    }
}
