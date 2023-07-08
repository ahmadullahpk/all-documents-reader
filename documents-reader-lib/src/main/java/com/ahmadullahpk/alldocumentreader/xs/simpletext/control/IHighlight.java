/*
 * 文件名称:          IHighlight.java
 *  
 * 编译器:            android2.2
 * 时间:              上午9:57:12
 */

package com.ahmadullahpk.alldocumentreader.xs.simpletext.control;

import com.ahmadullahpk.alldocumentreader.xs.simpletext.view.IView;

import android.graphics.Canvas;


public interface IHighlight
{

    /**
     * draw highlight
     */
    void draw(Canvas canvas, IView line, int originX, int originY, long start, long end, float zoom);

    /**
     * 
     */
    String getSelectText();

    /**
     * 
     */
    boolean isSelectText();

    /**
     * remove all selection
     */
    void removeHighlight();

    /**
     * 
     */
    void addHighlight(long start, long end);

    /**
     * @return Returns the selectStart.
     */
    long getSelectStart();

    /**
     * @param selectStart The selectStart to set.
     */
    void setSelectStart(long selectStart);

    /**
     * @return Returns the selectEnd.
     */
    long getSelectEnd();

    /**
     * @param selectEnd The selectEnd to set.
     */
    void setSelectEnd(long selectEnd);
    
    /**
     * @param isPaintHighlight The isPaintHighlight to set.
     */
    void setPaintHighlight(boolean isPaintHighlight);

    /**
     * 
     */
    void dispose();

}
