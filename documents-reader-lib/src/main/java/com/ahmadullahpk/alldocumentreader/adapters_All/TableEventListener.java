package com.ahmadullahpk.alldocumentreader.adapters_All;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmadullahpk.alldocumentreader.adapters_All.viewholder.ColumnViewHolder;
import com.ahmadullahpk.alldocumentreader.dialog.DialogColumnLongPress;
import com.ahmadullahpk.alldocumentreader.dialog.DialogRowLongPress;
import com.ahmadullahpk.alldocumentreader.widgets.tableview.TableView;
import com.ahmadullahpk.alldocumentreader.widgets.tableview.listener.ITableViewListener;

public class TableEventListener implements ITableViewListener {
    private Context mContext;
    private TableView mTableView;

    public TableEventListener(TableView tableView) {
        this.mContext = tableView.getContext();
        this.mTableView = tableView;
    }

    public void onCellClicked(@NonNull RecyclerView.ViewHolder viewHolder, int i, int i2) {
        showToast("Cell " + i + " " + i2 + " has been clicked.");
    }

    public void onCellDoubleClicked(@NonNull RecyclerView.ViewHolder viewHolder, int i, int i2) {
        showToast("Cell " + i + " " + i2 + " has been double clicked.");
    }

    public void onCellLongPressed(@NonNull RecyclerView.ViewHolder viewHolder, int i, int i2) {
        showToast("Cell " + i + " " + i2 + " has been long pressed.");
    }

    public void onColumnHeaderClicked(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        showToast("Column header  " + i + " has been clicked.");
    }

    public void onColumnHeaderDoubleClicked(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        showToast("Column header  " + i + " has been double clicked.");
    }

    public void onColumnHeaderLongPressed(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof ColumnViewHolder) {
            new DialogColumnLongPress((ColumnViewHolder) viewHolder, this.mTableView).show();
        }
    }

    public void onRowHeaderClicked(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        showToast("Row header " + i + " has been clicked.");
    }

    public void onRowHeaderDoubleClicked(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        showToast("Row header " + i + " has been double clicked.");
    }

    public void onRowHeaderLongPressed(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        new DialogRowLongPress(viewHolder, this.mTableView).show();
    }

    private void showToast(String str) {
        Toast.makeText(this.mContext, str, Toast.LENGTH_SHORT).show();
    }
}
