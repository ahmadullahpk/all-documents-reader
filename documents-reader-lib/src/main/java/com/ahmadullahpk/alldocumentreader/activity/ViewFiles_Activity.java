package com.ahmadullahpk.alldocumentreader.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;

import com.ahmadullahpk.alldocumentreader.databinding.ActivityViewFilesBinding;
import com.ahmadullahpk.alldocumentreader.R;
import com.ahmadullahpk.alldocumentreader.xs.common.IOfficeToPicture;
import com.ahmadullahpk.alldocumentreader.xs.constant.EventConstant;
import com.ahmadullahpk.alldocumentreader.xs.constant.MainConstant;
import com.ahmadullahpk.alldocumentreader.xs.macro.DialogListener;
import com.ahmadullahpk.alldocumentreader.xs.res.ResKit;
import com.ahmadullahpk.alldocumentreader.xs.ss.sheetbar.SheetBar;
import com.ahmadullahpk.alldocumentreader.xs.system.IControl;
import com.ahmadullahpk.alldocumentreader.xs.system.IMainFrame;
import com.ahmadullahpk.alldocumentreader.xs.system.MainControl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ViewFiles_Activity extends BaseActivity implements IMainFrame {

    ActivityViewFilesBinding binding;
    private static final String TAG = ViewFiles_Activity.class.getSimpleName();
    private LinearLayout appFrame;
    private int applicationType = -1;
    private final Object bg = -7829368;
    private SheetBar bottomBar;
    private MainControl control;
    private String fileName;
    private String filePath;
    private View gapView;
    private boolean isDispose;
    boolean isFromAppActivity = false;
    private boolean isThumbnail;
    private String tempFilePath;
    private Toast toast;
    private boolean writeLog = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } catch (Exception e) {
            e.printStackTrace();
        }

        binding = ActivityViewFilesBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        binding.headerTitleText.setTextAppearance(this, R.style.PageTitleBold);
        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareFile();
            }
        });

        this.control = new MainControl(this);
        this.appFrame = findViewById(R.id.appFrame);
        if (getIntent() != null) {
            this.filePath = getIntent().getStringExtra("path");
            this.fileName = getIntent().getStringExtra("name");
            this.isFromAppActivity = getIntent().getBooleanExtra("fromAppActivity", false);
            binding.headerTitleText.setMaxLines(1);
            binding.headerTitleText.setText(this.fileName);
        }
        createView();
        this.control.openFile(this.filePath);
        this.control.setOffictToPicture(new IOfficeToPicture() {
            private Bitmap bitmap;

            @Override
            public void dispose() {
            }

            @Override
            public byte getModeType() {
                return 1;
            }

            @Override
            public boolean isZoom() {
                return false;
            }

            @Override
            public void setModeType(byte b) {
            }

            @Override
            public Bitmap getBitmap(int i, int i2) {
                if (i == 0 || i2 == 0) {
                    return null;
                }
                Bitmap bitmap = this.bitmap;
                if (!(bitmap != null && bitmap.getWidth() == i && this.bitmap.getHeight() == i2)) {
                    Bitmap bitmap3 = this.bitmap;
                    if (bitmap3 != null) {
                        bitmap3.recycle();
                    }
                    this.bitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
                }
                return this.bitmap;
            }

            public void callBack(Bitmap bitmap) {
                ViewFiles_Activity.this.saveBitmapToFile(bitmap);
            }
        });
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private void saveBitmapToFile(Bitmap bitmap) {
        if (bitmap == null) {
            return;
        }
        if (this.tempFilePath == null) {
            if ("mounted".equals(Environment.getExternalStorageState())) {
                this.tempFilePath = Environment.getExternalStorageDirectory().getAbsolutePath();
            }
            String stringBuilder = this.tempFilePath +
                    File.separatorChar +
                    "tempPic";
            File file = new File(stringBuilder);
            if (!file.exists()) {
                file.mkdir();
            }
            this.tempFilePath = file.getAbsolutePath();
        }
        String stringBuilder = this.tempFilePath +
                File.separatorChar +
                "export_image.jpg";
        File file = new File(stringBuilder);
        try {
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();

        } catch (IOException ex) {
            Log.e(TAG, "saveBitmapToFile: ", ex);

        }
    }


    @Override
    public Activity getActivity() {
        return this;
    }

    public DialogListener getDialogListener() {
        return null;
    }

    @Override
    public void setFindBackForwardState(boolean state) {

    }


    @Override
    public int getTopBarHeight() {
        return 0;
    }


    @Override
    public boolean onEventMethod(View v, MotionEvent e1, MotionEvent e2, float xValue, float yValue, byte eventMethodType) {
        return false;
    }

    @Override
    public boolean isDrawPageNumber() {
        return true;
    }

    @Override
    public boolean isShowZoomingMsg() {
        return true;
    }

    @Override
    public boolean isPopUpErrorDlg() {
        return true;
    }

    @Override
    public boolean isShowPasswordDlg() {
        return true;
    }

    @Override
    public boolean isShowProgressBar() {
        return true;
    }

    @Override
    public boolean isShowFindDlg() {
        return true;
    }

    @Override
    public boolean isShowTXTEncodeDlg() {
        return true;
    }

    @Override
    public String getTXTDefaultEncode() {
        return "GBK";
    }

    @Override
    public boolean isTouchZoom() {
        return true;
    }

    @Override
    public boolean isZoomAfterLayoutForWord() {
        return true;
    }

    @Override
    public byte getWordDefaultView() {
        return 0;
    }


    @Override
    public void changeZoom() {

    }

    @Override
    public void changePage() {

    }

    @Override
    public void completeLayout() {

    }

    @Override
    public void error(int errorCode) {

    }

    @Override
    public void fullScreen(boolean fullscreen) {

    }

    @Override
    public void showProgressBar(boolean visible) {

    }

    @Override
    public void updateViewImages(List<Integer> viewList) {

    }

    @Override
    public boolean isChangePage() {
        return true;
    }


    @Override
    public void setIgnoreOriginalSize(boolean ignoreOriginalSize) {

    }

    @Override
    public boolean isIgnoreOriginalSize() {
        return false;
    }

    @Override
    public byte getPageListViewMovingPosition() {
        return 0;
    }


    @Override
    protected void onDestroy() {
        dispose();
        super.onDestroy();
    }


    private void createView() {
        String lowerCase = this.filePath.toLowerCase();
        if (lowerCase.endsWith(MainConstant.FILE_TYPE_DOC) ||
                lowerCase.endsWith(MainConstant.FILE_TYPE_DOCX) ||
                lowerCase.endsWith(MainConstant.FILE_TYPE_TXT) ||
                lowerCase.endsWith(MainConstant.FILE_TYPE_DOT) ||
                lowerCase.endsWith(MainConstant.FILE_TYPE_DOTX) ||
                lowerCase.endsWith(MainConstant.FILE_TYPE_DOTM)) {
            this.applicationType = 0;
        } else if (lowerCase.endsWith(MainConstant.FILE_TYPE_XLS) ||
                lowerCase.endsWith(MainConstant.FILE_TYPE_XLSX) ||
                lowerCase.endsWith(MainConstant.FILE_TYPE_XLT) ||
                lowerCase.endsWith(MainConstant.FILE_TYPE_XLTX) ||
                lowerCase.endsWith(MainConstant.FILE_TYPE_XLTM) ||
                lowerCase.endsWith(MainConstant.FILE_TYPE_XLSM)) {
            this.applicationType = 1;
        } else if (lowerCase.endsWith(MainConstant.FILE_TYPE_PPT) ||
                lowerCase.endsWith(MainConstant.FILE_TYPE_PPTX) ||
                lowerCase.endsWith(MainConstant.FILE_TYPE_POT) ||
                lowerCase.endsWith(MainConstant.FILE_TYPE_PPTM) ||
                lowerCase.endsWith(MainConstant.FILE_TYPE_POTX) ||
                lowerCase.endsWith(MainConstant.FILE_TYPE_POTM)) {
            this.applicationType = 2;
        } else if (lowerCase.endsWith("pdf")) {
            this.applicationType = 3;
        } else {
            this.applicationType = 0;
        }
    }


    @Override
    public void onBackPressed() {
        Object actionValue = this.control.getActionValue(EventConstant.PG_SLIDESHOW, null);
        if (actionValue == null || !(Boolean) actionValue) {
            if (this.control.getReader() != null) {
                this.control.getReader().abortReader();
            }
            MainControl mainControl = this.control;
            if (mainControl == null || !mainControl.isAutoTest()) {
                if (this.isFromAppActivity) {
                    finish();
                   // startActivity(new Intent(this, Main_Home_Activity.class));
                }
                super.onBackPressed();
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                return;
            }
            System.exit(0);
            return;
        }
        fullScreen(false);
        this.control.actionEvent(EventConstant.PG_SLIDESHOW_END, null);
    }


    private void maximiseScreen() {
        hideSystemUI();

    }

    private void shareFile() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        Uri parse = Uri.parse(this.filePath);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_STREAM, parse);
        startActivity(Intent.createChooser(intent, getResources().getString(R.string.shareUsing)));
    }

    public void fileShare() {
        ArrayList<Uri> arrayList = new ArrayList<>();
        arrayList.add(Uri.fromFile(new File(this.filePath)));
        Intent intent = new Intent(Intent.ACTION_SEND_MULTIPLE);
        intent.putExtra(Intent.EXTRA_STREAM, arrayList);
        intent.setType("application/octet-stream");
        startActivity(Intent.createChooser(intent, getResources().getText(R.string.sys_share_title)));
    }

    public Dialog onCreateDialog(int i) {
        return this.control.getDialog(this, i);
    }

    @Override
    public void updateToolsbarStatus() {
        LinearLayout linearLayout = this.appFrame;
        if (linearLayout != null && !this.isDispose) {
            int childCount = linearLayout.getChildCount();
            for (int i = 0; i < childCount; i++) {
                this.appFrame.getChildAt(i);
            }
        }
    }

    public IControl getControl() {
        return this.control;
    }

    public int getApplicationType() {
        return this.applicationType;
    }


    public String getFilePath() {
        return this.filePath;
    }

    public boolean doActionEvent(int i, Object obj) {
        if (i == 0) {
            onBackPressed();
        } else if (i != 15) {
            if (i == 20) {
                updateToolsbarStatus();
            } else if (i == 25) {
                setTitle((String) obj);
            } else if (i != 268435464) {
                if (i == 536870913) {
                    fileShare();
                } else if (i == 788529152) {
                    String trim = ((String) obj).trim();
                    if (trim.length() <= 0 || !this.control.getFind().find(trim)) {
                        setFindBackForwardState(false);
                        this.toast.setText(getLocalString("DIALOG_FIND_NOT_FOUND"));
                        this.toast.show();
                    } else {
                        setFindBackForwardState(true);
                    }
                } else if (i != 1073741828) {
                    switch (i) {
                        case EventConstant.APP_DRAW_ID:
                            this.control.getSysKit().getCalloutManager().setDrawingMode(1);
                            this.appFrame.post(() -> ViewFiles_Activity.this.control.actionEvent(EventConstant.APP_INIT_CALLOUTVIEW_ID, null));
                            break;
                        case EventConstant.APP_BACK_ID:
                            this.control.getSysKit().getCalloutManager().setDrawingMode(0);
                            break;
                        case EventConstant.APP_PEN_ID:
                            if (!(Boolean) obj) {
                                this.control.getSysKit().getCalloutManager().setDrawingMode(0);
                            } else {
                                this.control.getSysKit().getCalloutManager().setDrawingMode(1);
                                this.appFrame.post(() -> ViewFiles_Activity.this.control.actionEvent(EventConstant.APP_INIT_CALLOUTVIEW_ID, null));
                            }
                            break;
                        case EventConstant.APP_ERASER_ID:
                            try {
                                if (!(Boolean) obj) {
                                    this.control.getSysKit().getCalloutManager().setDrawingMode(0);
                                } else {
                                    this.control.getSysKit().getCalloutManager().setDrawingMode(2);
                                }
                                break;
                            } catch (Exception e) {
                                this.control.getSysKit().getErrorKit().writerLog(e);
                                break;
                            }
                        default:
                            return false;
                    }
                } else {
                    this.bottomBar.setFocusSheetButton((Integer) obj);
                }
            }
        }
        return true;
    }

    @Override
    public void openFileFinish() {
        View view = new View(getApplicationContext());
        this.gapView = view;
        view.setBackgroundColor(-7829368);
       // this.appFrame.addView(this.gapView, new LinearLayout.LayoutParams(-1, 1));
        this.appFrame.addView(this.control.getView(), new LinearLayout.LayoutParams(-1, -1));
    }

    @Override
    public int getBottomBarHeight() {
        SheetBar sheetBar = this.bottomBar;
        if (sheetBar != null) {
            return sheetBar.getSheetbarHeight();
        }
        return 0;
    }

    @Override
    public String getAppName() {
        return getString(R.string.sys_name);
    }

    public void destroyEngine() {
        super.onBackPressed();
    }

    @Override
    public String getLocalString(String str) {
        return ResKit.instance().getLocalString(str);
    }

    @Override
    public void setWriteLog(boolean z) {
        this.writeLog = z;
    }

    @Override
    public boolean isWriteLog() {
        return this.writeLog;
    }

    @Override
    public void setThumbnail(boolean z) {
        this.isThumbnail = z;
    }

    @Override
    public Object getViewBackground() {
        return this.bg;
    }

    @Override
    public boolean isThumbnail() {
        return this.isThumbnail;
    }

    @Override
    public File getTemporaryDirectory() {
        File externalFilesDir = getExternalFilesDir(null);
        if (externalFilesDir != null) {
            return externalFilesDir;
        }
        return getFilesDir();
    }

    @Override
    public void dispose() {
        this.isDispose = true;

        MainControl mainControl = this.control;
        if (mainControl != null) {
            mainControl.dispose();
            this.control = null;
        }
        this.bottomBar = null;
        LinearLayout linearLayout = this.appFrame;
        if (linearLayout != null) {
            int childCount = linearLayout.getChildCount();
            for (int i = 0; i < childCount; i++) {
                this.appFrame.getChildAt(i);
            }
            this.appFrame = null;
        }
    }


}