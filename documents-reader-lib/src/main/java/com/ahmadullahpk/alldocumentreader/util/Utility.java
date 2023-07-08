package com.ahmadullahpk.alldocumentreader.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

import androidx.documentfile.provider.DocumentFile;

import   com.ahmadullahpk.alldocumentreader.R;
import com.ahmadullahpk.alldocumentreader.dataType.Model_Main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class  Utility {

    private static final String TAG = Utility.class.getSimpleName();

    public static void logCatMsg(String str) {
        Log.d(TAG, "logCatMsg: " + str);
    }


    public static void Toast(Context context, String str) {
        Toast.makeText(context, str, Toast.LENGTH_LONG).show();
    }


    public static String getLastModifyDate(Long l) {
        return new SimpleDateFormat("MMM dd | hh:mm a", Locale.US).format(new Date(l));
    }


    public static boolean isPackageInstalled(Context context, String str) {
        PackageManager packageManager = context.getPackageManager();
        Intent launchIntentForPackage = packageManager.getLaunchIntentForPackage(str);
        if (launchIntentForPackage == null) {
            return false;
        }
        return !packageManager.queryIntentActivities(launchIntentForPackage, PackageManager.MATCH_DEFAULT_ONLY).isEmpty();
    }

    public static void deleteTempFolder(Context context) {
        File file = new File(Environment.getExternalStorageDirectory().getPath() + context.getResources().getString(R.string.app_folder_name) + "/.temp");
        if (file.exists()) {
            file.delete();
        }
    }


    public static String getDateTime() {
        String format = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
        return "PDF_" + format;
    }


    public static String getFileSize(File file) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        if (file.isFile()) {
            double length = (double) file.length();
            if (length > 1048576.0d) {
                StringBuilder sb = new StringBuilder();
                Double.isNaN(length);
                sb.append(decimalFormat.format(length / 1048576.0d));
                sb.append(" MB");
                return sb.toString();
            } else if (length > 1024.0d) {
                StringBuilder sb2 = new StringBuilder();
                Double.isNaN(length);
                sb2.append(decimalFormat.format(length / 1024.0d));
                sb2.append(" KB");
                return sb2.toString();
            } else {
                return decimalFormat.format(length) + " B";
            }
        } else {
            throw new IllegalArgumentException("Expected a file");
        }
    }

    public static String getFileDateTime(Long l) {
        return new SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.US).format(new Date(l));
    }

    public static boolean deleteDir(File file) {
        if (file.isDirectory()) {
            String[] list = file.list();
            assert list != null;
            for (String file2 : list) {
                if (!deleteDir(new File(file, file2))) {
                    return false;
                }
            }
        }
        return file.delete();
    }


    public static void vibratePhone(Context context) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= 26) {
            vibrator.vibrate(VibrationEffect.createOneShot(50, -1));
        } else {
            vibrator.vibrate(50);
        }
    }


    public static String writeStringAsFile(Context context, String str, String str2) {
        String str3 = Environment.getExternalStorageDirectory().getPath() + context.getResources().getString(R.string.app_folder_name);
        File file = new File(str3);
        if (!file.exists()) {
            file.mkdirs();
            logCatMsg("created");
        }
        File file2 = new File(str3, str2);
        try {
            FileWriter fileWriter = new FileWriter(file2);
            fileWriter.write(str);
            fileWriter.close();
            return file2.getPath();
        } catch (IOException e) {
            logCatMsg("IOException " + e.getMessage());
            return null;
        }
    }



    public static String getCurrentDateTime() {
        return System.currentTimeMillis() + "";

    }


    public static boolean deleteFileOrUri(Context context, Model_Main modelMain) {
        boolean delete;
        logCatMsg("path " + modelMain.getPath() + " :: Uri Path " + modelMain.getUriPath());
        try {
            if (!modelMain.getPath().isEmpty()) {
                delete = DocumentFile.fromFile(new File(modelMain.getPath())).delete();
                logCatMsg("file Deleted by file method.. " + delete);
            } else if (modelMain.getUriPath().isEmpty()) {
                return false;
            } else {
                delete = DocumentFile.fromSingleUri(context, Uri.parse(modelMain.getUriPath())).delete();
                logCatMsg("file Deleted by Uri method.. " + delete);
            }
            return delete;
        } catch (Exception e) {
            e.printStackTrace();
            logCatMsg("Exception in Utility.deleteFileOrUri " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }



    public static File getTempFolderDirectory(Context context) {
        File file = new File(context.getFilesDir() + "/temp");
        if (!file.exists()) {
            file.mkdir();
        }
        return file;
    }


}
