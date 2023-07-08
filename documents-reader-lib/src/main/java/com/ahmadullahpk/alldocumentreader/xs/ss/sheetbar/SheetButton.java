/*
 * 文件名称:          SheetButton.java
 *  
 * 编译器:            android2.2
 * 时间:              下午6:06:50
 */
package com.ahmadullahpk.alldocumentreader.xs.ss.sheetbar;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.ahmadullahpk.alldocumentreader.R;


/**
 * sheet表名称按钮
 * <p>
 * <p>
 * Read版本:        Read V1.0
 * <p>
 * 作者:            ljj8494
 * <p>
 * 日期:            2011-12-6
 * <p>
 * 负责人:          ljj8494
 * <p>
 * 负责小组:         
 * <p>
 * <p>
 */
public class SheetButton extends LinearLayout
{

    private static final int FONT_SIZE = 16;
    private static final int SHEET_BUTTON_MIN_WIDTH = 120;
    private boolean active;
    Context context;
    private View left;
    private View right;
    private int sheetIndex;
    private TextView textView;

    /**
     * 
     * @param context
     */
    public SheetButton(Context context, String sheetName, int sheetIndex)
    {
        super(context);
        this.context = context;
        setOrientation(HORIZONTAL);
        this.sheetIndex = sheetIndex;

        init(context, sheetName);
    }
    
    /**
     * 
     */
    private void init(Context context, String sheetName)
    {
        View view = new View(context);
        this.left = view;
        addView(view);
        TextView textView2 = new TextView(context);
        this.textView = textView2;
        textView2.setBackground(ContextCompat.getDrawable(context, R.drawable.sheet_bg));
        this.textView.setText(sheetName);
        this.textView.setTextSize(16.0f);
        this.textView.setGravity(17);
        this.textView.setTextColor(Color.parseColor("#444444"));
        this.textView.setPadding(5, 0, 5, 0);
        addView(this.textView, new LinearLayout.LayoutParams(Math.max((int) this.textView.getPaint().measureText(sheetName), 120), -1));
        View view2 = new View(context);
        this.right = view2;
        view2.setBackgroundColor(-16776961);
        addView(this.right);
    }

    

    /**
     * 选中或取消选中用到
     */
    public void changeFocus(boolean z) {
        this.active = z;
        this.textView.setBackground(ContextCompat.getDrawable(this.context, z ? R.drawable.sheet_bg_pressed : R.drawable.sheet_bg));
        this.textView.setTextColor(Color.parseColor(z ? "#217346" : "#444444"));
    }


    /**
     * 
     */
    public int getSheetIndex()
    {
        return this.sheetIndex;
    }
    
    /**
     * 
     */
    public void dispose()
    {

        left = null;
        textView = null;
        right = null;
    }
    


}
