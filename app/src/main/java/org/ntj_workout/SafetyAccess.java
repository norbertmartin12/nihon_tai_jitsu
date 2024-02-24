package org.ntj_workout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SafetyAccess {

    public static final int DELAY_ALLOWED_BY_CODE = 7;
    private static final String TXT_PLAIN = "text/plain";
    private static final SafetyAccess SINGLETON = new SafetyAccess();
    private static final String INTENT_CODE = "CODE";
    private static final String INTENT_VALIDITY_DATE = "VALIDITY_DATE";
    private static final String PREF_ACCESS_VALIDITY_DATE = "ACCESS_VALIDITY_DATE";
    private static final SimpleDateFormat DATE_FORMAT;

    static {
        DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    }

    private SafetyAccess() {}

    public static SafetyAccess getInstance() {
        return SINGLETON;
    }
    public boolean accept(Activity activity, Intent intent) {
        if (intent == null) {
            return false;
        }
        if(!Intent.ACTION_SYNC.equals(intent.getAction())) {
            return false;
        }
        if (intent.getType() == null || !TXT_PLAIN.equals(intent.getType())) {
            return false;
        }
        String code = intent.getStringExtra(INTENT_CODE);
        String validityDateString = intent.getStringExtra(INTENT_VALIDITY_DATE);
        if (validityDateString == null) {
            return false;
        }
        Date validityDate;
        try {
            validityDate = DATE_FORMAT.parse(validityDateString);
        } catch (ParseException e) {
            return false;
        }
        return accept(activity, code, validityDate);
    }
    public boolean accept(Activity activity, String code, Date validityDate) {
        if (!isCodeValid(code)) {
            return false;
        }
        saveEndValidityDate(activity, validityDate);
        return true;
    }
    public boolean hasAccessToContent(Activity activity) {
        SharedPreferences appPreferences = activity.getPreferences(Context.MODE_PRIVATE);
        if (!appPreferences.contains(PREF_ACCESS_VALIDITY_DATE)) {
            return false;
        }
        return isDateBeforeNow(getEndValidityDate(activity));
    }
    public Date getEndValidityDate(Activity activity) {
        SharedPreferences appPreferences = activity.getPreferences(Context.MODE_PRIVATE);
        String endValidityDateString = appPreferences.getString(PREF_ACCESS_VALIDITY_DATE,null);
        if (endValidityDateString == null) {
            return null;
        }
        try {
            return DATE_FORMAT.parse(endValidityDateString);
        } catch (ParseException e) {
            return null;
        }
    }
    private void saveEndValidityDate(Activity activity, Date validityDate) {
        SharedPreferences appPreferences = activity.getPreferences(Context.MODE_PRIVATE);
        appPreferences.edit().putString(PREF_ACCESS_VALIDITY_DATE, DATE_FORMAT.format(validityDate)).apply();
    }
    private boolean isCodeValid(String code) {
        if (code == null) {
            return false;
        }
        return BuildConfig.OPEN_KEY.equals(code);
    }
    private boolean isDateBeforeNow(Date referenceDate)  {
        return new Date().before(referenceDate);
    }
}
