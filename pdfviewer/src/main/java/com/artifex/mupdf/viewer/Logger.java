package com.artifex.mupdf.viewer;

import android.os.Environment;
import android.util.Log;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Logger {
    private static boolean mLogWrite = false;
    private static boolean mLogWriteOther = false;
    private static String mPath = (Environment.getExternalStorageDirectory().getPath() + "/");
    private static boolean mSeeLog = false;
    private static String mTag = "MuPDFLog";

    public static void init(String str, String str2, boolean z, boolean z2, boolean z3) {
        mPath = str;
        mTag = str2;
        mLogWrite = z;
        mLogWriteOther = z2;
        mSeeLog = z3;
    }

    public static String getTag() {
        return mTag;
    }

    public static void d(String str, String str2) {
        if (mSeeLog) {
            Log.d(str, str2);
        }
    }

    public static void write(String str, Exception exc, String str2) {
        write(str, str2);
        write(str, getStackElement(exc));
    }

    public static void write(String str, Throwable th, String str2) {
        write(str, str2);
        write(str, getStackElement(th));
    }

    private static String getStackElement(Throwable th) {
        try {
            StringWriter stringWriter = new StringWriter();
            th.printStackTrace(new PrintWriter(stringWriter));
            return stringWriter.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void write(String str, String str2) {
        if (mSeeLog) {
            Log.i(str, str2);
        }
        if (mTag.equals(str)) {
            if (mLogWrite) {
                writeToFile(str, str2);
            }
        } else if (mLogWriteOther) {
            writeToFile(str, str2);
        }
    }

    private static void writeToFile(String str, String str2) {
        if (Environment.getExternalStorageState().equals("mounted")) {
            try {
                String str3 = mPath + str + "/";
                Date date = new Date();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
                SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyyMMdd", Locale.CHINA);
                String format = simpleDateFormat.format(date);
                String format2 = simpleDateFormat2.format(date);
                File file = new File(str3);
                if (!file.exists()) {
                    file.mkdirs();
                }
                File file2 = new File(str3 + format2 + ".txt");
                if (!file2.exists()) {
                    file2.createNewFile();
                }
                FileWriter fileWriter = new FileWriter(file2, true);
                fileWriter.append("\r\n=============" + format + "=====================\r\n");
                fileWriter.append(str2);
                fileWriter.flush();
                fileWriter.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
