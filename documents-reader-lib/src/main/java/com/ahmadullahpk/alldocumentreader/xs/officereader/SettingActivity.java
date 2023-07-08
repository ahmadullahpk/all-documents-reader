/*
 * 文件名称:           SettingActivity.java
 *
 * 编译器:             android2.2
 * 时间:               下午2:56:13
 */
package com.ahmadullahpk.alldocumentreader.xs.officereader;

import java.util.Vector;

import com.ahmadullahpk.alldocumentreader.R;
import com.ahmadullahpk.alldocumentreader.xs.constant.DialogConstant;
import com.ahmadullahpk.alldocumentreader.xs.officereader.settings.SetRecentCountDialog;
import com.ahmadullahpk.alldocumentreader.xs.officereader.settings.SettingDialogAction;
import com.ahmadullahpk.alldocumentreader.xs.system.IControl;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;


public class SettingActivity extends Activity {
    // 设置最近打开的文档数
    public static final int SET_RECENT_FILE_COUNT = 0;

    /**
     * 构造器
     */
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        control = new SettingControl(this);
        settingFrame = new SettingFrame(this);
        settingFrame.post(new Runnable() {
            /**
             *
             *
             */
            public void run() {
                initListener();
                init();
            }
        });

        setContentView(settingFrame);
        dialogAction = new SettingDialogAction(control);
    }

    /**
     *
     */
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mHeight = getResources().getDisplayMetrics().heightPixels;
        mHeight -= getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
        listView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, mHeight));
    }

    /**
     *
     */
    protected void onDestroy() {
        super.onDestroy();
        dispose();
    }

    /**
     *
     */
    public void onBackPressed() {
        super.onBackPressed();
    }

    /**
     * List 单击事件
     */
    private void initListener() {
        onItemClickListener = new AdapterView.OnItemClickListener() {
            /**
             *
             */
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showSettings(position);
            }
        };
    }

    /**
     *
     */
    public void init() {
        mHeight = getResources().getDisplayMetrics().heightPixels;
        mHeight -= getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
        listView = new ListView(this);
        listView.setOnItemClickListener(onItemClickListener);
        String[] items = getResources().getStringArray(R.array.setting_items);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, R.layout.dialog_setting, android.R.id.text1, items);
        listView.setAdapter(adapter);
        settingFrame.addView(listView, new LayoutParams(LayoutParams.MATCH_PARENT, mHeight));
    }

    /**
     *
     */
    public void dispose() {
        settingFrame = null;
        listView = null;
        onItemClickListener = null;
        if (dialogAction != null) {
            dialogAction.dispose();
            dialogAction = null;
        }
        if (control != null) {
            control.dispose();
            control = null;
        }

    }

    /**
     * 显示某一具体的设置
     *
     * @param id
     */
    public void showSettings(int id) {
        if (id == SET_RECENT_FILE_COUNT) {
            int count = ((SettingControl) control).getRecentMax();
            String[] items = getResources().getStringArray(R.array.setting_items);
            Vector<Object> vector = new Vector<Object>();
            vector.add(items[id]);
            vector.add(String.valueOf(count));
            new SetRecentCountDialog(this.control, this, dialogAction, vector,
                    DialogConstant.SET_MAX_RECENT_NUMBER,
                    R.string.sys_menu_settings).show();
        }
    }

    //
    private int mHeight;
    //
    private SettingFrame settingFrame;
    //
    public ListView listView;
    //
    private AdapterView.OnItemClickListener onItemClickListener;
    //
    private SettingDialogAction dialogAction;
    //
    private IControl control;
}
