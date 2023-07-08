package com.ahmadullahpk.alldocumentreader.dialog;


import android.view.MenuItem;
import android.widget.PopupMenu;

import androidx.recyclerview.widget.RecyclerView;

import   com.ahmadullahpk.alldocumentreader.widgets.tableview.TableView;


public class DialogRowLongPress extends PopupMenu implements PopupMenu.OnMenuItemClickListener {
    private static final int REMOVE_ROW = 3;
    private static final int SCROLL_COLUMN = 1;
    private static final int SHOW_HIDE_COLUMN = 2;
    private final int mRowPosition;
    private final TableView mTableView;

    private void createMenuItem() {
    }

    public DialogRowLongPress(RecyclerView.ViewHolder viewHolder, TableView tableView) {
        super(viewHolder.itemView.getContext(), viewHolder.itemView);
        this.mTableView = tableView;
        this.mRowPosition = viewHolder.getAdapterPosition();
        initialize();
    }

    private void initialize() {
        createMenuItem();
        setOnMenuItemClickListener(this);
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == SCROLL_COLUMN) {
            this.mTableView.scrollToColumnPosition(15);
        } else if (itemId != SHOW_HIDE_COLUMN) {
            if (itemId == REMOVE_ROW) {
                this.mTableView.getAdapter().removeRow(this.mRowPosition);
            }
        } else if (this.mTableView.isColumnVisible(SCROLL_COLUMN)) {
            this.mTableView.hideColumn(SCROLL_COLUMN);
        } else {
            this.mTableView.showColumn(SCROLL_COLUMN);
        }
        return true;
    }
}
