package com.ahmadullahpk.alldocumentreader.widgets.dynamicgrid;


public interface DynamicGridAdapterInterface {

    void reorderItems(int originalPosition, int newPosition);

    int getColumnCount();

    boolean canReorder(int position);

}
