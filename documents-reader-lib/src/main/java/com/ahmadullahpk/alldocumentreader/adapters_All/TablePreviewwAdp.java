package com.ahmadullahpk.alldocumentreader.adapters_All;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import   com.ahmadullahpk.alldocumentreader.R;
import com.ahmadullahpk.alldocumentreader.adapters_All.viewholder.TableCellViewHolder;
import com.ahmadullahpk.alldocumentreader.adapters_All.viewholder.ColumnViewHolder;
import com.ahmadullahpk.alldocumentreader.adapters_All.viewholder.HeaderViewHolder;
import com.ahmadullahpk.alldocumentreader.dataType.Cell;
import com.ahmadullahpk.alldocumentreader.dataType.ColumnHeader;
import com.ahmadullahpk.alldocumentreader.dataType.RowHeader;
import com.ahmadullahpk.alldocumentreader.viewmodel.TableViewModel;
import   com.ahmadullahpk.alldocumentreader.widgets.tableview.adapter.AbstractTableAdapter;
import   com.ahmadullahpk.alldocumentreader.widgets.tableview.adapter.recyclerview.holder.AbstractViewHolder;
import   com.ahmadullahpk.alldocumentreader.widgets.tableview.sort.SortState;



public class TablePreviewwAdp extends AbstractTableAdapter<ColumnHeader, RowHeader, Cell> {

    private static final int MOOD_CELL_TYPE = 1;
    private static final int GENDER_CELL_TYPE = 2;

    @NonNull
    @Override
    public AbstractViewHolder onCreateCellViewHolder(ViewGroup viewGroup, int i) {
        return new TableCellViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adp_table_cell, viewGroup, false));
    }

    @Override
    public void onBindCellViewHolder(@NonNull AbstractViewHolder abstractViewHolder, Cell cell, int i, int i2) {
        ((TableCellViewHolder) abstractViewHolder).setCell(cell);
    }

    @NonNull
    @Override
    public AbstractViewHolder onCreateColumnHeaderViewHolder(@NonNull ViewGroup parent, int viewType) {
        // TODO: check
        //Log.e(LOG_TAG, " onCreateColumnHeaderViewHolder has been called");
        // Get Column Header xml Layout
        View layout = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adp_table_view_column, parent, false);

        // Create a ColumnHeader ViewHolder
        return new ColumnViewHolder(layout, getTableView());
    }

    @Override
    public void onBindColumnHeaderViewHolder(@NonNull AbstractViewHolder holder, @Nullable ColumnHeader
            columnHeaderItemModel, int columnPosition) {

        // Get the holder to update cell item text
        ColumnViewHolder columnViewHolder = (ColumnViewHolder) holder;
        columnViewHolder.setColumnHeader(columnHeaderItemModel);
    }

    @NonNull
    @Override
    public AbstractViewHolder onCreateRowHeaderViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Get Row Header xml Layout
        View layout = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adp_table_view_row, parent, false);

        // Create a Row Header ViewHolder
        return new HeaderViewHolder(layout);
    }

    @Override
    public void onBindRowHeaderViewHolder(@NonNull AbstractViewHolder holder, @Nullable RowHeader rowHeaderItemModel,
                                          int rowPosition) {

        // Get the holder to update row header item text
        HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
        headerViewHolder.row_header_textview.setText(String.valueOf(rowHeaderItemModel.getData()));
    }

    @NonNull
    @Override
    public View onCreateCornerView(@NonNull ViewGroup parent) {
        // Get Corner xml layout
        View corner = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adp_table_view_corne, parent, false);
        corner.setOnClickListener(view -> {
            SortState sortState = TablePreviewwAdp.this.getTableView()
                    .getRowHeaderSortingStatus();
            if (sortState != SortState.ASCENDING) {
                Log.d("TableViewAdapter", "Order Ascending");
                TablePreviewwAdp.this.getTableView().sortRowHeader(SortState.ASCENDING);
            } else {
                Log.d("TableViewAdapter", "Order Descending");
                TablePreviewwAdp.this.getTableView().sortRowHeader(SortState.DESCENDING);
            }
        });
        return corner;
    }

    @Override
    public int getColumnHeaderItemViewType(int position) {
        return 0;
    }

    @Override
    public int getRowHeaderItemViewType(int position) {
        return 0;
    }

    @Override
    public int getCellItemViewType(int column) {
        switch (column) {
            case TableViewModel.MOOD_COLUMN_INDEX:
                return MOOD_CELL_TYPE;
            case TableViewModel.GENDER_COLUMN_INDEX:
                return GENDER_CELL_TYPE;
            default:
                // Default view type
                return 0;
        }
    }
}