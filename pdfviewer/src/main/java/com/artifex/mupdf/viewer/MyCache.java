package com.artifex.mupdf.viewer;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import java.util.ArrayList;

public class MyCache {
    private static SharedPreferences.Editor editor;
    private static SharedPreferences preferences;

    public static String getStringMetadata(Context context, String str) {
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
            return applicationInfo.metaData != null ? applicationInfo.metaData.getString(str) : "";
        } catch (PackageManager.NameNotFoundException unused) {
            return "";
        }
    }

    public static void setArrayStrings(String str, ArrayList<String> arrayList, Context context) {
        SharedPreferences.Editor edit = context.getSharedPreferences("Cache_Array", 0).edit();
        edit.putInt(str + "_size", arrayList.size());
        for (int i = 0; i < arrayList.size(); i++) {
            edit.putString(str + "_" + i, arrayList.get(i));
        }
        edit.apply();
    }

    public static ArrayList<String> getArrayStrings(String str, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Cache_Array", 0);
        int i = sharedPreferences.getInt(str + "_size", 0);
        ArrayList<String> arrayList = new ArrayList<>(i);
        for (int i2 = 0; i2 < i; i2++) {
            arrayList.add(sharedPreferences.getString(str + "_" + i2, (String) null));
        }
        return arrayList;
    }

    public static void setPrefs(String str, String str2, Context context) {
        SharedPreferences.Editor edit = context.getSharedPreferences("Cache_Array", 0).edit();
        edit.putString(str, str2);
        edit.apply();
    }

    public static String getPrefs(String str, Context context) {
        return context.getSharedPreferences("Cache_Array", 0).getString(str, "notfound");
    }

    private static void openLog(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getPackageName(), 0);
        preferences = sharedPreferences;
        editor = sharedPreferences.edit();
    }

    public static void putStringValueByName(Context context, String str, String str2) {
        openLog(context);
        editor.putString(str, str2);
        editor.commit();
    }

    public static String getStringValueByName(Context context, String str) {
        openLog(context);
        return preferences.getString(str, "");
    }

    public static void putIntValueByName(Context context, String str, int i) {
        openLog(context);
        editor.putInt(str, i);
        editor.commit();
    }

    public static int getIntValueByName(Context context, String str, int i) {
        openLog(context);
        return preferences.getInt(str, i);
    }

    public static int getIntValueByName(Context context, String str) {
        openLog(context);
        return preferences.getInt(str, 0);
    }

    public static void putBooleanValueByName(Context context, String str, boolean z) {
        openLog(context);
        editor.putBoolean(str, z);
        editor.commit();
    }

    public static boolean getBooleanValueByName(Context context, String str) {
        openLog(context);
        return preferences.getBoolean(str, false);
    }

    public static void removeAll() {
        editor.clear();
        editor.commit();
    }

    public static boolean getBooleanValueByName(Context context, String str, boolean z) {
        openLog(context);
        return preferences.getBoolean(str, z);
    }
}
