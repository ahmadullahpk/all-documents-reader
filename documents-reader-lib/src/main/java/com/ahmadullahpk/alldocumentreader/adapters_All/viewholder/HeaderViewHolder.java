package com.ahmadullahpk.alldocumentreader.adapters_All.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.ahmadullahpk.alldocumentreader.R;
import com.ahmadullahpk.alldocumentreader.widgets.tableview.adapter.recyclerview.holder.AbstractViewHolder;


public class HeaderViewHolder extends AbstractViewHolder {
    @NonNull
    public final TextView row_header_textview;

    public HeaderViewHolder(@NonNull View itemView) {
        super(itemView);
        row_header_textview = itemView.findViewById(R.id.row_header_textview);
    }
}
