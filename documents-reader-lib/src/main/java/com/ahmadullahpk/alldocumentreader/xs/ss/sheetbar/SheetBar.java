/*
 * 文件名称:          SheetList.java
 *  
 * 编译器:            android2.2
 * 时间:              下午4:13:50
 */
package   com.ahmadullahpk.alldocumentreader.xs.ss.sheetbar;

import java.util.Vector;

import   com.ahmadullahpk.alldocumentreader.xs.constant.EventConstant;
import   com.ahmadullahpk.alldocumentreader.xs.system.IControl;

import android.content.Context;
import android.content.res.Configuration;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

/**
 * 
 * <p>
 * <p>
 * Read版本:        Read V1.0
 * <p>
 * 作者:            ljj8494
 * <p>
 * 日期:            2011-11-15
 * <p>
 * 负责人:          ljj8494
 * <p>
 * 负责小组:         
 * <p>
 * <p>
 */
public class SheetBar extends HorizontalScrollView implements OnClickListener
{
    /**
     * 
     * @param context
     */
    public SheetBar(Context context)
    {
        super(context);
    }    
    
    /**
     * 
     * @param context
     */
    public SheetBar(Context context, IControl control, int minimumWidth)
    {
        super(context);
        this.control = control;
        this.setVerticalFadingEdgeEnabled(false);
        this.setFadingEdgeLength(0);
        if (minimumWidth == getResources().getDisplayMetrics().widthPixels)
        {
            this.minimumWidth = -1;
        }
        else
        {
            this.minimumWidth = minimumWidth;
        }
        init();
    }
    
    /**
     * 
     *
     */
    public void onConfigurationChanged(Configuration newConfig)
    {
        sheetbarFrame.setMinimumWidth(minimumWidth == -1 ? getResources().getDisplayMetrics().widthPixels
            : minimumWidth);
    }
    
    /**
     * 
     */
    private void init() {
        Context context = getContext();
        LinearLayout linearLayout = new LinearLayout(context);
        this.sheetbarFrame = linearLayout;
        linearLayout.setGravity(80);
        this.sheetbarFrame.setBackgroundColor(-7829368);
        this.sheetbarFrame.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout linearLayout2 = this.sheetbarFrame;
        int i = this.minimumWidth;
        if (i == -1) {
            i = getResources().getDisplayMetrics().widthPixels;
        }
        linearLayout2.setMinimumWidth(i);
        this.sheetbarHeight = 100;
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-2, 100);
        this.sheetbarFrame.addView(new View(context), layoutParams);
        Vector vector = (Vector) this.control.getActionValue(EventConstant.SS_GET_ALL_SHEET_NAME, (Object) null);
        FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(-2, 100);
        int size = vector.size();
        for (int i2 = 0; i2 < size; i2++) {
            SheetButton sheetButton = new SheetButton(context, (String) vector.get(i2), i2);
            if (this.currentSheet == null) {
                this.currentSheet = sheetButton;
                sheetButton.changeFocus(true);
            }
            sheetButton.setOnClickListener(this);
            this.sheetbarFrame.addView(sheetButton, layoutParams2);
            if (i2 < size - 1) {
                this.sheetbarFrame.addView(new View(context), layoutParams2);
            }
        }
        this.sheetbarFrame.addView(new View(context), layoutParams);
        addView(this.sheetbarFrame, new FrameLayout.LayoutParams(-2, this.sheetbarHeight));
    }
    /**
     * 
     *
     */
    public void onClick(View v)
    {
        currentSheet.changeFocus(false);
        
        SheetButton sb = (SheetButton)v;
        sb.changeFocus(true);
        currentSheet = sb;
        
        control.actionEvent(EventConstant.SS_SHOW_SHEET, currentSheet.getSheetIndex());
    }

    /**
     * set focus sheet button(called when clicked document hyperlink)
     * @param index
     */
    public void setFocusSheetButton(int index)
    {
        if(currentSheet.getSheetIndex() == index)
        {
            return;
        }
        
        int count = sheetbarFrame.getChildCount();
        View view = null;
        for(int i = 0; i < count; i++)
        {
            view = sheetbarFrame.getChildAt(i);
            if (view instanceof SheetButton && ((SheetButton)view).getSheetIndex() == index)
            {
                currentSheet.changeFocus(false);
                
                currentSheet = (SheetButton)view;
                currentSheet.changeFocus(true);
                break;
            }
        }
        
        //sheetbar scrolled
        int screenWidth = control.getActivity().getWindowManager().getDefaultDisplay().getWidth();
        int barWidth = sheetbarFrame.getWidth();
        if(barWidth > screenWidth)
        {
            int left = view.getLeft();
            int right = view.getRight();
            int off = (screenWidth - (right - left)) / 2;
            
            off = left - off;
            if(off < 0)
            {
                off = 0;
            }
            else if(off + screenWidth > barWidth)
            {
                off = barWidth - screenWidth;
            }
           
            scrollTo(off, 0);
        }
    }
    
    /**
     * @return Returns the sheetbarHeight.
     */
    public int getSheetbarHeight()
    {
        return sheetbarHeight;
    }
    
    /**
     * 
     */
    public void dispose()
    {

        
        currentSheet = null;
        if(sheetbarFrame != null)
        {
            int count = sheetbarFrame.getChildCount();
            View v;
            for(int i = 0; i < count; i++)
            {
                v = sheetbarFrame.getChildAt(i);
                if(v instanceof SheetButton)
                {
                    ((SheetButton)v).dispose();
                }
            }
            sheetbarFrame = null;
        }
    }

    //
    private int minimumWidth;
    //
    //
    private int sheetbarHeight;
    //
    private SheetButton currentSheet;
    //
    private IControl control;
    //
    private LinearLayout sheetbarFrame;

}
