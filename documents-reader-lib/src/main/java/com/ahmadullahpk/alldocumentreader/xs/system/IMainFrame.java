
package com.ahmadullahpk.alldocumentreader.xs.system;

import java.io.File;
import java.util.List;

import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;


public interface IMainFrame
{
    // onTouch
    byte ON_TOUCH = 0;
    // onDown
    byte ON_DOWN = 1;
    // onShowPresso
    byte ON_SHOW_PRESS = 2;
    // onSingleTapUp
    byte ON_SINGLE_TAP_UP = 3;
    // onScroll
    byte ON_SCROLL = 4;
    // onLongPress
    byte ON_LONG_PRESS = 5;
    // onFling
    byte ON_FLING = 6;
    // onSingleTapConfirmed
    byte ON_SINGLE_TAP_CONFIRMED = 7;
    // onDoubleTap
    byte ON_DOUBLE_TAP = 8;
    // onDoubleTapEvent
    byte ON_DOUBLE_TAP_EVENT = 9;
    // onClick
    byte ON_CLICK = 10;

    /**
     * get activity instance
     * @return activity instance
     */
    Activity getActivity();
    
    /**
     * do action 
     *
     * @param actionID action ID 
     * 
     * @param obj acValue
     * 
     * @return  True if the listener has consumed the event, false otherwise. 
     */
    boolean doActionEvent(int actionID, Object obj);
    
    /**
     * reader file finish call this method
     */
    void openFileFinish();
    
    /**
     * update tool bar status
     */
    void updateToolsbarStatus();
    
    /**
     * set the find back button and find forward button state
     * 
     * @param state
     */
    void setFindBackForwardState(boolean state);
    
    /**
     * get bottom  bar height
     * @return bottom bar height
     */
    int getBottomBarHeight();
    
    /**
     * get top bar height
     * @return top bar height
     */
    int getTopBarHeight();
    
    /**
     * get application name;
     * @return application name
     */
    String getAppName();
    
    /**
     * 
     * @return
     */
    File getTemporaryDirectory();
    
    /**
     * event method, office engine dispatch 
     * 
     * @param       v             event source
     * @param       e1            MotionEvent instance
     * @param       e2            MotionEvent instance
     * @param       xValue        eventNethodType is ON_SCROLL, this is value distanceX
     *                             eventNethodType is ON_FLING, this is value velocityY
     *                             eventNethodType is other type, this is value -1   
     * 
     * @param       yValue        eventNethodType is ON_SCROLL, this is value distanceY
     *                             eventNethodType is ON_FLING, this is value velocityY
     *                             eventNethodType is other type, this is value -1  
     * @param       eventMethodType  event method
     *              @see IMainFrame#ON_CLICK
     *              @see IMainFrame#ON_DOUBLE_TAP
     *              @see IMainFrame#ON_DOUBLE_TAP_EVENT
     *              @see IMainFrame#ON_DOWN
     *              @see IMainFrame#ON_FLING
     *              @see IMainFrame#ON_LONG_PRESS
     *              @see IMainFrame#ON_SCROLL
     *              @see IMainFrame#ON_SHOW_PRESS
     *              @see IMainFrame#ON_SINGLE_TAP_CONFIRMED
     *              @see IMainFrame#ON_SINGLE_TAP_UP
     *              @see IMainFrame#ON_TOUCH
     */
    boolean onEventMethod(View v, MotionEvent e1, MotionEvent e2, float xValue, float yValue, byte eventMethodType);
    
    /**
     * is support draw page number
     * @return  true  draw page number
     *           false don’t draw page number
     */

    boolean isDrawPageNumber();
    
    /**
     * true: show message when zooming
     * false: not show message when zooming
     * @return
     */
    boolean isShowZoomingMsg();
    
    /**
     * true: pop up dialog when throw err
     * false: not pop up dialog when throw err
     * @return
     */
    boolean isPopUpErrorDlg();
    
    /**
     * show password dialog when parse file with password
     * @return
     */
    boolean isShowPasswordDlg();
    
    /**
     * show progress bar or not when parsing document
     * @return
     */
    boolean isShowProgressBar();
    
    /**
     * 
     */
    boolean isShowFindDlg();
    
    /**
     * show txt encode dialog when parse txt file
     * @return
     */
    boolean isShowTXTEncodeDlg();
    
    /**
     * get txt default encode when not showing txt encode dialog
     * @return null if showing txt encode dialog
     */
    String getTXTDefaultEncode();
    
    /**
     * is support zoom in / zoom out
     * 
     * @return  true  touch zoom
     *           false don’t touch zoom
     */

    boolean isTouchZoom();
    
    /**
     * normal view, changed after zoom bend, you need to re-layout
     * 
     *  @return  true   re-layout
     *            false  don't re-layout   
     */
    boolean isZoomAfterLayoutForWord();
    
    /**
     * Word application default view (Normal or Page)
     * 
     * @return 0, page view
     *          1，normal view;
     *           
     */
    byte getWordDefaultView();
    
    /**
     * get Internationalization resource
     * 
     * @param resName Internationalization resource name
     */
    String getLocalString(String resName);
    
    /**
     * callback this method after zoom change
     */
    void changeZoom();
    
    /**
     * 
     */
    void changePage();
    
    /**
     * 
     */
    void completeLayout();
    
    /**
     * when engine error occurred callback this method
     */
    void error(int errorCode);
    
    /**
     * full screen, not show top tool bar
     */
    void fullScreen(boolean fullscreen);
    
    /**
     * 
     * @param visible
     */
    void showProgressBar(boolean visible);
    
    /**
     * 
     * @param viewList
     */
    void updateViewImages(List<Integer> viewList);

    
    /**
     *  set change page flag, Only when effectively the PageSize greater than ViewSize.
     *  (for PPT, word print mode, PDF)
     *  
     *  @param b    = true, change page
     *              = false, don't change page
     */
    boolean isChangePage();
    
    /**
     * when need destroy office engine instance callback this method
     */
    //public void destroyEngine();
    
    /**
     * 
     * @param saveLog
     */
    void setWriteLog(boolean saveLog);
    
    /**
     * 
     * @return
     */
    boolean isWriteLog();
    
    /**
     * 
     * @param isThumbnail
     */
    void setThumbnail(boolean isThumbnail);
    
    /**
     * 
     * @return
     */
    boolean isThumbnail();
    
    /**
     * get view backgrouond
     * @return
     */
    Object getViewBackground();
    
    /**
     * set flag whether fitzoom can be larger than 100% but smaller than the max zoom
     * @param ignoreOriginalSize
     */
    void setIgnoreOriginalSize(boolean ignoreOriginalSize);
    
    /**
     * 
     * @return
     * true fitzoom may be larger than 100% but smaller than the max zoom
     * false fitzoom can not larger than 100%
     */
    boolean isIgnoreOriginalSize();
    
    /**
     * page list view moving position
     * @param position horizontal or vertical
     */
    byte getPageListViewMovingPosition();
    
    /**
     * 
     */
    void dispose();
}
