
package   com.ahmadullahpk.alldocumentreader.xs.thirdpart.emf.data;

import java.io.IOException;

import   com.ahmadullahpk.alldocumentreader.xs.java.awt.Rectangle;
import   com.ahmadullahpk.alldocumentreader.xs.thirdpart.emf.EMFInputStream;

/**
 * 
 * @author tonyj
 */
public class Region
{
    private Rectangle bounds;

    private Rectangle region;

    public Region(Rectangle bounds, Rectangle region)
    {
        this.bounds = bounds;
        this.region = region;
    }

    public Region(EMFInputStream emf) throws IOException
    {
        /* int length = */emf.readDWORD();
        /* int mode = */emf.readDWORD();
        /* int nRect = */emf.readDWORD();
        int size = emf.readDWORD();
        bounds = emf.readRECTL();
        region = emf.readRECTL();
        for (int i = 16; i < size; i += 16)
            emf.readRECTL();
    }


    public int length()
    {
        return 48;
    }

    public String toString()
    {
        return "  Region\n" + "    bounds: " + bounds + "\n    region: " + region;
    }

    public Rectangle getBounds()
    {
        return bounds;
    }
}
