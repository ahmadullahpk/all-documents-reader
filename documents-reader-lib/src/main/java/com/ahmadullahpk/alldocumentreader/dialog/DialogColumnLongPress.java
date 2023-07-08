package com.ahmadullahpk.alldocumentreader.dialog;

import android.view.MenuItem;
import android.widget.PopupMenu;

import com.ahmadullahpk.alldocumentreader.adapters_All.viewholder.ColumnViewHolder;
import com.ahmadullahpk.alldocumentreader.widgets.tableview.TableView;
import com.ahmadullahpk.alldocumentreader.widgets.tableview.sort.SortState;


public class DialogColumnLongPress extends PopupMenu implements PopupMenu.OnMenuItemClickListener {
    private static final int ASCENDING = 1;
    private static final int DESCENDING = 2;
    private static final int HIDE_ROW = 3;
    private static final int SCROLL_ROW = 5;
    private static final int SHOW_ROW = 4;
    private final TableView mTableView;
    private final int mXPosition;

    public DialogColumnLongPress(ColumnViewHolder columnViewHolder, TableView tableView) {
        super(columnViewHolder.itemView.getContext(), columnViewHolder.itemView);
        this.mTableView = tableView;
        this.mXPosition = columnViewHolder.getAdapterPosition();
        initialize();
    }

    private void initialize() {
        createMenuItem();
        changeMenuItemVisibility();
        setOnMenuItemClickListener(this);
    }

    private void createMenuItem() {
        this.mTableView.getContext();
    }

    private void changeMenuItemVisibility() {
        SortState sortingStatus = this.mTableView.getSortingStatus(this.mXPosition);
        if (sortingStatus != SortState.UNSORTED) {
            if (sortingStatus == SortState.DESCENDING) {
                getMenu().getItem(ASCENDING
                ).setVisible(false);
            } else if (sortingStatus == SortState.ASCENDING) {
                getMenu().getItem(0).setVisible(false);
            }
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == 1) {
            this.mTableView.sortColumn(this.mXPosition, SortState.ASCENDING);
        } else if (itemId == DESCENDING) {
            this.mTableView.sortColumn(this.mXPosition, SortState.DESCENDING);
        } else if (itemId == HIDE_ROW) {
            this.mTableView.hideRow(SCROLL_ROW);
        } else if (itemId == SHOW_ROW) {
            this.mTableView.showRow(SCROLL_ROW);
        } else if (itemId == SCROLL_ROW) {
            this.mTableView.scrollToRowPosition(SCROLL_ROW);
        }
        return true;
    }
}
