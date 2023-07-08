
package com.ahmadullahpk.alldocumentreader.xs.system;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Toast;

import com.ahmadullahpk.alldocumentreader.xs.common.ICustomDialog;
import com.ahmadullahpk.alldocumentreader.xs.common.IOfficeToPicture;
import com.ahmadullahpk.alldocumentreader.xs.common.ISlideShow;
import com.ahmadullahpk.alldocumentreader.xs.common.picture.PictureKit;
import com.ahmadullahpk.alldocumentreader.xs.constant.MainConstant;
import com.ahmadullahpk.alldocumentreader.xs.fc.doc.TXTKit;
import com.ahmadullahpk.alldocumentreader.xs.fc.pdf.PDFLib;
import com.ahmadullahpk.alldocumentreader.xs.pg.control.PGControl;
import com.ahmadullahpk.alldocumentreader.xs.pg.model.PGModel;
import com.ahmadullahpk.alldocumentreader.xs.simpletext.model.IDocument;
import com.ahmadullahpk.alldocumentreader.xs.ss.control.SSControl;
import com.ahmadullahpk.alldocumentreader.xs.ss.model.baseModel.Workbook;
import com.ahmadullahpk.alldocumentreader.xs.wp.control.WPControl;

public class MainControl extends AbstractControl {
    private IControl appControl;
    private ICustomDialog customDialog;
    private String filePath;
    private IMainFrame frame;
    private Handler handler;
    private boolean isAutoTest;
    private boolean isCancel;
    private boolean isDispose;
    private IOfficeToPicture officeToPicture;
    private DialogInterface.OnKeyListener onKeyListener;
    private ProgressDialog progressDialog;
    private IReader reader;
    private ISlideShow slideShow;
    private Toast toast;
    private AUncaughtExceptionHandler uncaught;
    private byte applicationType = -1;
    public SysKit sysKit = new SysKit(this);

    @Override
    public Dialog getDialog(Activity activity, int id) {
        return null;
    }

    @Override
    public void layoutView(int x, int y, int w, int h) {
    }

    public MainControl(IMainFrame frame) {
        this.frame = frame;
        AUncaughtExceptionHandler aUncaughtExceptionHandler = new AUncaughtExceptionHandler(this);
        this.uncaught = aUncaughtExceptionHandler;
        Thread.setDefaultUncaughtExceptionHandler(aUncaughtExceptionHandler);
        init();
    }

    private void init() {
        initListener();
        boolean z = false;
        this.toast = Toast.makeText(getActivity().getApplicationContext(), "", Toast.LENGTH_SHORT);
        String stringExtra = getActivity().getIntent().getStringExtra("autoTest");
        if (stringExtra != null && stringExtra.equals("true")) {
            z = true;
        }
        this.isAutoTest = z;
    }

    @SuppressLint("HandlerLeak")
    private void initListener() {
        this.onKeyListener = (dialog, keyCode, event) -> {
            if (keyCode != 4) {
                return false;
            }
            dialog.dismiss();
            MainControl.this.isCancel = true;
            if (MainControl.this.reader != null) {
                MainControl.this.reader.abortReader();
                MainControl.this.reader.dispose();
            }
            MainControl.this.getActivity().onBackPressed();
            return true;
        };
        this.handler = new Handler() {
            @Override
            public void handleMessage(final Message msg) {
                if (!MainControl.this.isCancel) {
                    int i = msg.what;
                    if (i == 0) {
                        post(() -> {
                            try {
                                if (MainControl.this.getMainFrame().isShowProgressBar()) {
                                    MainControl.this.dismissProgressDialog();
                                } else if (MainControl.this.customDialog != null) {
                                    MainControl.this.customDialog.dismissDialog((byte) 2);
                                }
                                MainControl.this.createApplication(msg.obj);
                            } catch (Exception e) {
                                MainControl.this.sysKit.getErrorKit().writerLog(e, true);
                            }
                        });
                    } else if (i == 1) {
                        post(() -> {
                            MainControl.this.dismissProgressDialog();
                            if (msg.obj instanceof Throwable) {
                                MainControl.this.sysKit.getErrorKit().writerLog((Throwable) msg.obj, true);
                            }
                        });
                    } else if (i != 2) {
                        if (i == 3) {
                            post(MainControl.this::dismissProgressDialog);
                        } else if (i == 4) {
                            MainControl.this.reader = (IReader) msg.obj;
                        }
                    } else if (MainControl.this.getMainFrame().isShowProgressBar()) {
                        post(() -> {
                            MainControl.this.progressDialog = ProgressDialog.show(MainControl.this.getActivity(), MainControl.this.frame.getAppName(), MainControl.this.frame.getLocalString("DIALOG_LOADING"), false, false, null);
                            MainControl.this.progressDialog.setOnKeyListener(MainControl.this.onKeyListener);
                        });
                    } else if (MainControl.this.customDialog != null) {
                        MainControl.this.customDialog.showDialog((byte) 2);
                    }
                }
            }
        };
    }

    public void dismissProgressDialog() {
        ProgressDialog progressDialog = this.progressDialog;
        if (progressDialog != null) {
            progressDialog.dismiss();
            this.progressDialog = null;
        }
        Handler handler = this.handler;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }

    public void createApplication(Object obj) throws Exception {
        Object viewBackground;
        if (obj != null) {
            byte b = this.applicationType;
            if (b == 0) {
                this.appControl = new WPControl(this, (IDocument) obj, this.filePath);
            } else if (b == 1) {
                this.appControl = new SSControl(this, (Workbook) obj, this.filePath);
            } else if (b == 2) {
                this.appControl = new PGControl(this, (PGModel) obj, this.filePath);
            }
            View view = this.appControl.getView();
            if (!(view == null || (viewBackground = this.frame.getViewBackground()) == null)) {
                if (viewBackground instanceof Integer) {
                    view.setBackgroundColor((Integer) viewBackground);
                } else if (viewBackground instanceof Drawable) {
                    view.setBackgroundDrawable((Drawable) viewBackground);
                }
            }
            final boolean z = this.applicationType == 3 && ((PDFLib) obj).hasPasswordSync();
            if (this.applicationType != 3) {
                this.frame.openFileFinish();
            } else if (!z) {
                this.frame.openFileFinish();
            }
            PictureKit.instance().setDrawPictrue(true);
            this.handler.post(() -> {
                if (Build.VERSION.SDK_INT >= 11) {
                    try {
                        View view2 = MainControl.this.getView();
                        Object invoke = view2.getClass().getMethod("isHardwareAccelerated", null).invoke(view2, null);
                        if (invoke != null && (invoke instanceof Boolean) && (Boolean) invoke) {
                            view2.getClass().getMethod("setLayerType", Integer.TYPE, Paint.class).invoke(view2, view2.getClass().getField("LAYER_TYPE_SOFTWARE").getInt(null), null);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                MainControl.this.actionEvent(26, false);
                MainControl.this.actionEvent(19, null);
                if (MainControl.this.applicationType != 3) {
                    MainControl.this.frame.updateToolsbarStatus();
                } else if (!z) {
                    MainControl.this.frame.updateToolsbarStatus();
                }
                MainControl.this.getView().postInvalidate();
            });
            return;
        }
        throw new Exception("Document with password");
    }

    @Override
    public boolean openFile(String filePath) {
        this.filePath = filePath;
        String lowerCase = filePath.toLowerCase();
        if (lowerCase.endsWith(MainConstant.FILE_TYPE_DOC) || lowerCase.endsWith(MainConstant.FILE_TYPE_DOCX) || lowerCase.endsWith(MainConstant.FILE_TYPE_TXT) || lowerCase.endsWith(MainConstant.FILE_TYPE_DOT) || lowerCase.endsWith(MainConstant.FILE_TYPE_DOTX) || lowerCase.endsWith(MainConstant.FILE_TYPE_DOTM)) {
            this.applicationType = (byte) 0;
        } else if (lowerCase.endsWith(MainConstant.FILE_TYPE_XLS) || lowerCase.endsWith(MainConstant.FILE_TYPE_XLSX) || lowerCase.endsWith(MainConstant.FILE_TYPE_XLT) || lowerCase.endsWith(MainConstant.FILE_TYPE_XLTX) || lowerCase.endsWith(MainConstant.FILE_TYPE_XLTM) || lowerCase.endsWith(MainConstant.FILE_TYPE_XLSM)) {
            this.applicationType = (byte) 1;
        } else if (lowerCase.endsWith(MainConstant.FILE_TYPE_PPT) || lowerCase.endsWith(MainConstant.FILE_TYPE_PPTX) || lowerCase.endsWith(MainConstant.FILE_TYPE_POT) || lowerCase.endsWith(MainConstant.FILE_TYPE_PPTM) || lowerCase.endsWith(MainConstant.FILE_TYPE_POTX) || lowerCase.endsWith(MainConstant.FILE_TYPE_POTM)) {
            this.applicationType = (byte) 2;
        } else if (lowerCase.endsWith("pdf")) {
            this.applicationType = (byte) 3;
        } else {
            this.applicationType = (byte) 0;
        }
        boolean isSupport = FileKit.instance().isSupport(lowerCase);
        if (lowerCase.endsWith(MainConstant.FILE_TYPE_TXT) || !isSupport) {
            TXTKit.instance().readText(this, this.handler, filePath);
        } else {
            new FileReaderThread(this, this.handler, filePath, null).start();
        }
        return true;
    }

    @Override
    public void actionEvent(int actionID, final Object obj) {
        if (actionID == 23 && this.reader != null) {
            IControl iControl = this.appControl;
            if (iControl != null) {
                iControl.actionEvent(actionID, obj);
            }
            this.reader.dispose();
            this.reader = null;
        }
        IMainFrame iMainFrame = this.frame;
        if (iMainFrame != null && !iMainFrame.doActionEvent(actionID, obj)) {
            if (actionID == -268435456) {
                getView().postInvalidate();
            } else if (actionID == 0) {
                try {
                    Message message = new Message();
                    message.obj = obj;
                    this.reader.dispose();
                    message.what = 0;
                    this.handler.handleMessage(message);
                } catch (Throwable th) {
                    this.sysKit.getErrorKit().writerLog(th);
                }
            } else if (actionID == 26) {
                Handler handler = this.handler;
                if (handler != null) {
                    handler.post(() -> {
                        if (!MainControl.this.isDispose) {
                            MainControl.this.frame.showProgressBar((Boolean) obj);
                        }
                    });
                }
            } else if (actionID == 536870919) {
                this.appControl.actionEvent(actionID, obj);
                this.frame.updateToolsbarStatus();
            } else if (actionID == 536870921) {
                IReader iReader = this.reader;
                if (iReader != null) {
                    iReader.abortReader();
                }
            } else if (actionID != 17) {
                if (actionID == 18) {
                    this.toast.cancel();
                } else if (actionID == 23) {
                    Handler handler2 = this.handler;
                    if (handler2 != null) {
                        handler2.post(() -> {
                            if (!MainControl.this.isDispose) {
                                MainControl.this.frame.showProgressBar(false);
                            }
                        });
                    }
                } else if (actionID == 24) {
                    Handler handler3 = this.handler;
                    if (handler3 != null) {
                        handler3.post(() -> {
                            if (!MainControl.this.isDispose) {
                                MainControl.this.frame.showProgressBar(true);
                            }
                        });
                    }
                } else if (actionID == 117440512) {
                    TXTKit.instance().reopenFile(this, this.handler, this.filePath, (String) obj);
                } else if (actionID != 117440513) {
                    IControl iControl2 = this.appControl;
                    if (iControl2 != null) {
                        iControl2.actionEvent(actionID, obj);
                    }
                } else {
                    String[] strArr = (String[]) obj;
                    if (strArr.length == 2) {
                        this.filePath = strArr[0];
                        this.applicationType = (byte) 0;
                        TXTKit.instance().reopenFile(this, this.handler, this.filePath, strArr[1]);
                    }
                }
            } else if ((obj instanceof String)) {
                this.toast.setText((String) obj);
                this.toast.setGravity(17, 0, 0);
                this.toast.show();
            }
        }
    }

    @Override
    public IFind getFind() {
        return this.appControl.getFind();
    }

    @Override
    public Object getActionValue(int actionID, Object obj) {
        if (actionID == 1) {
            return this.filePath;
        }
        IControl iControl = this.appControl;
        if (iControl == null) {
            return null;
        }
        if (actionID != 536870928 && actionID != 805306371 && actionID != 536870931 && actionID != 1342177283 && actionID != 1358954506) {
            return iControl.getActionValue(actionID, obj);
        }
        boolean isDrawPictrue = PictureKit.instance().isDrawPictrue();
        boolean isThumbnail = this.frame.isThumbnail();
        PictureKit.instance().setDrawPictrue(true);
        if (actionID == 536870928) {
            this.frame.setThumbnail(true);
        }
        Object actionValue = this.appControl.getActionValue(actionID, obj);
        if (actionID == 536870928) {
            this.frame.setThumbnail(isThumbnail);
        }
        PictureKit.instance().setDrawPictrue(isDrawPictrue);
        return actionValue;
    }

    @Override
    public View getView() {
        IControl iControl = this.appControl;
        if (iControl == null) {
            return null;
        }
        return iControl.getView();
    }

    @Override
    public boolean isAutoTest() {
        return this.isAutoTest;
    }

    @Override
    public IMainFrame getMainFrame() {
        return this.frame;
    }

    @Override
    public Activity getActivity() {
        return this.frame.getActivity();
    }

    @Override
    public IOfficeToPicture getOfficeToPicture() {
        return this.officeToPicture;
    }

    @Override
    public ICustomDialog getCustomDialog() {
        return this.customDialog;
    }

    @Override
    public ISlideShow getSlideShow() {
        return this.slideShow;
    }

    @Override
    public IReader getReader() {
        return this.reader;
    }

    @Override
    public byte getApplicationType() {
        return this.applicationType;
    }

    public void setOffictToPicture(IOfficeToPicture opt) {
        this.officeToPicture = opt;
    }

    public void setCustomDialog(ICustomDialog dlg) {
        this.customDialog = dlg;
    }

    public void setSlideShow(ISlideShow slideshow) {
        this.slideShow = slideshow;
    }

    @Override
    public SysKit getSysKit() {
        return this.sysKit;
    }

    @Override
    public int getCurrentViewIndex() {
        return this.appControl.getCurrentViewIndex();
    }

    @Override
    public void dispose() {
        this.isDispose = true;
        IControl iControl = this.appControl;
        if (iControl != null) {
            iControl.dispose();
            this.appControl = null;
        }
        IReader iReader = this.reader;
        if (iReader != null) {
            iReader.dispose();
            this.reader = null;
        }
        IOfficeToPicture iOfficeToPicture = this.officeToPicture;
        if (iOfficeToPicture != null) {
            iOfficeToPicture.dispose();
            this.officeToPicture = null;
        }
        ProgressDialog progressDialog = this.progressDialog;
        if (progressDialog != null) {
            progressDialog.dismiss();
            this.progressDialog = null;
        }
        if (this.customDialog != null) {
            this.customDialog = null;
        }
        if (this.slideShow != null) {
            this.slideShow = null;
        }
        this.frame = null;
        Handler handler = this.handler;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            this.handler = null;
        }
        AUncaughtExceptionHandler aUncaughtExceptionHandler = this.uncaught;
        if (aUncaughtExceptionHandler != null) {
            aUncaughtExceptionHandler.dispose();
            this.uncaught = null;
        }
        this.onKeyListener = null;
        this.toast = null;
        this.filePath = null;
        System.gc();
        SysKit sysKit = this.sysKit;
        if (sysKit != null) {
            sysKit.dispose();
        }
    }
}