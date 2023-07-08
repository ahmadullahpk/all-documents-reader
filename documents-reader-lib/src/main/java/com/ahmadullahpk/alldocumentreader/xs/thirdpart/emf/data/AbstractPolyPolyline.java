// Copyright 2007, FreeHEP.
package   com.ahmadullahpk.alldocumentreader.xs.thirdpart.emf.data;

import   com.ahmadullahpk.alldocumentreader.xs.java.awt.Rectangle;
import   com.ahmadullahpk.alldocumentreader.xs.thirdpart.emf.EMFRenderer;

import android.graphics.Point;

/**
 * Parent class for a group of PolyLines. Childs are
 * rendered as not closed polygons.
 *
 * @author Steffen Greiffenberg
 * @version $Id$? */
public abstract class AbstractPolyPolyline extends AbstractPolyPolygon {

    protected AbstractPolyPolyline(
        int id,
        int version,
        Rectangle bounds,
        int[] numberOfPoints,
        Point[][] points) {

        super(id, version, bounds, numberOfPoints, points);
    }

    /**
     * displays the tag using the renderer. The default behavior
     * is not to close the polygons and not to fill them.
     *
     * @param renderer EMFRenderer storing the drawing session data
     */
    public void render(EMFRenderer renderer) {
        render(renderer, false);
    }
}
