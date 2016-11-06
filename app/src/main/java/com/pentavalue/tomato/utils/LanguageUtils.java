package com.pentavalue.tomato.utils;

import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;

import com.pentavalue.tomato.application.ApplicationController;
import com.pentavalue.tomato.storage.SharedPrefrencesDataLayer;

/**
 * @author Mahmoud Turki
 */
public class LanguageUtils {
    public static final String LANGUAGE_ENGLISH = "en";
    public static final String LANGUAGE_arabic = "ar";
    public static final String APP_LANGUAGE_CODE_KEY = "com.pentavalue.mysupermarket.languageCode";
    public static String LANGUAGE = LANGUAGE_ENGLISH;

    private static LanguageUtils instance;

    public static LanguageUtils getInstance() {
        if (instance == null)
            instance = new LanguageUtils();
        return instance;
    }

    private LanguageUtils() {
        String savedLanguage = SharedPrefrencesDataLayer.getStringPreferences(
                ApplicationController.getInstance().getApplicationContext(), APP_LANGUAGE_CODE_KEY, LANGUAGE);
        if (savedLanguage != null) {
            LANGUAGE = savedLanguage;
        }
    }

    public String getLanguage() {
        return LANGUAGE;
    }

    public void setLanguage(String language) {
        LANGUAGE = language;
    }

    @SuppressLint("NewApi")
    public static void changeApplicationLanguage(String languageCode, Activity activity, boolean isSetting) {
        Configuration config = new Configuration();
        config.locale = new Locale(languageCode);
        Resources resources = ApplicationController.getInstance().getBaseContext().getResources();
        resources.updateConfiguration(config, resources.getDisplayMetrics());

        // save app language in preferences
        SharedPrefrencesDataLayer.saveStringPreferences(ApplicationController.getInstance().getApplicationContext(), LanguageUtils.APP_LANGUAGE_CODE_KEY, languageCode);
        LanguageUtils.getInstance().setLanguage(languageCode);
        if (isSetting)
            activity.recreate();

    }

    public String getDeviceLanguage() {
        System.out.println("App lang" + SharedPrefrencesDataLayer.getStringPreferences(
                ApplicationController.getInstance().getApplicationContext(), APP_LANGUAGE_CODE_KEY, LANGUAGE));
        return SharedPrefrencesDataLayer.getStringPreferences(
                ApplicationController.getInstance().getApplicationContext(), APP_LANGUAGE_CODE_KEY, LANGUAGE);
    }

    public boolean isRTL(String abbreviation) {
        return isRTL(new Locale(abbreviation));
    }

    private boolean isRTL(Locale locale) {
        String lang = locale.getLanguage();
        if ("iw".equals(lang) || "ar".equals(lang)
                || "fa".equals(lang) || "ur".equals(lang)) {
            return true;
        } else {
            return false;
        }
    }
}
