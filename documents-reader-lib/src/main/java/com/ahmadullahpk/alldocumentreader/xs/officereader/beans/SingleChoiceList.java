/*
 * 文件名称:           SingleChoiceDialog.java
 *  
 * 编译器:             android2.2
 * 时间:               上午11:13:35
 */
package com.ahmadullahpk.alldocumentreader.xs.officereader.beans;


import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ahmadullahpk.alldocumentreader.R;


/**
 * single choice list
 * <p>
 * <p>
 * Read版本:       Read V1.0
 * <p>
 * 作者:           jhy1790
 * <p>
 * 日期:           2011-12-16
 * <p>
 * 负责人:         jhy1790
 * <p>
 * 负责小组:         
 * <p>
 * <p>
 */
public class SingleChoiceList extends ListView
{
    /**
     * 
     * @param context
     * @param itemsResID
     */
    public SingleChoiceList(Context context, int itemsResID)
    {
        super(context);
        setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        String[] items = context.getResources().getStringArray(itemsResID);
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(
            context, R.layout.dialog_select_single_choice, android.R.id.text1, items);
        setAdapter(adapter);
    }
    /**
     * 
     */
    public void dispose()
    {
    }
}
