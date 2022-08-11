package net.nedis.simpletaximeter.storage;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class SettingsStorage {

    public static final String ET_MIN_COST = "etMinCost";
    public static final String ET_MIN_COST_DISTANCE = "etMinCostDistance";
    public static final String ET_COST_IN_CITY = "etCostInCity";
    public static final String ET_COST_OUT_CITY = "etCostOutCity";

    private final SharedPreferences prefs;

    public SettingsStorage(@NonNull Context context) {
        prefs = context.getSharedPreferences("settings", MODE_PRIVATE);
    }

    public String getValue(String key) {
        return prefs.getString(key, null);
    }

    public BigDecimal getBigDecimal(String key, int newScale) {
        return new BigDecimal(getValue(key)).setScale(newScale, RoundingMode.HALF_UP);
    }

    public void setValue(String key, String value) {
        prefs.edit().putString(key, value).apply();
    }

    public void setDefaultValue(String key, String defaultValue) {
        String value = prefs.getString(key, null);
        if (value == null) {
            value = defaultValue;
            setValue(key, value);
        }
    }
}
