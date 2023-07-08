/*
 * 文件名称:          WPPageListItem.java
 *  
 * 编译器:            android2.2
 * 时间:              上午10:24:57
 */
package   com.ahmadullahpk.alldocumentreader.xs.wp.control;

import   com.ahmadullahpk.alldocumentreader.xs.system.IControl;
import   com.ahmadullahpk.alldocumentreader.xs.system.beans.pagelist.APageListItem;
import   com.ahmadullahpk.alldocumentreader.xs.system.beans.pagelist.APageListView;
import   com.ahmadullahpk.alldocumentreader.xs.wp.view.PageRoot;
import   com.ahmadullahpk.alldocumentreader.xs.wp.view.PageView;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * word engine "PageListView" component item
 * <p>
 * <p>
 * Read版本:        Read V1.0
 * <p>
 * 作者:            ljj8494
 * <p>
 * 日期:            2013-1-8
 * <p>
 * 负责人:          ljj8494
 * <p>
 * 负责小组:         
 * <p>
 * <p>
 */
public class WPPageListItem extends APageListItem
{
    private static final int BACKGROUND_COLOR = 0xFFFFFFFF;

    /**
     * 
      */
    public WPPageListItem(APageListView listView, IControl control, int pageWidth, int pageHeight)
    {
        super(listView, pageWidth, pageHeight);
        this.control = control;
        this.pageRoot= (PageRoot)listView.getModel();
        this.setBackgroundColor(BACKGROUND_COLOR);
    }
    
    /**
     * 
     */
    public void onDraw(Canvas canvas)
    {
        PageView pv = pageRoot.getPageView(pageIndex);
        if (pv != null)
        {
            float zoom = listView.getZoom();
            canvas.save();
            canvas.translate(-pv.getX() * zoom, -pv.getY() * zoom);
            pv.drawForPrintMode(canvas, 0, 0, zoom);
            canvas.restore();
        }
    }
    
    /**
     * 
     * @param pIndex     page index (base 0)
     * @param pageWidth     page width of after scaled
     * @param pageHeight    page height of after scaled
     */
    public void setPageItemRawData(final int pIndex, int pageWidth, int pageHeight)
    {
        super.setPageItemRawData(pIndex, pageWidth, pageHeight);
        //final APageListItem own = this;
        if ((int)(listView.getZoom() * 100) == 100
            || (isInit && pIndex == 0))
        {
            listView.exportImage(this, null);
        }
        isInit = false;
    }
    
    /**
     * added reapint image view
     */
    protected void addRepaintImageView(Bitmap bmp)
    {
        postInvalidate();
        listView.exportImage(this, null);
    }

    /**
     * remove reapint image view
     */
    protected void removeRepaintImageView()
    {
        
    }
    

    /**
     * 
     */
    public void dispose()
    {
        super.dispose();
        control = null;
        pageRoot = null;
    }
    //
    private boolean isInit = true;
    //
    private PageRoot pageRoot;
    
}
