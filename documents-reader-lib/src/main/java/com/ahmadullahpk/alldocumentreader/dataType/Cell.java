package com.ahmadullahpk.alldocumentreader.dataType;



import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ahmadullahpk.alldocumentreader.widgets.tableview.filter.IFilterableModel;
import com.ahmadullahpk.alldocumentreader.widgets.tableview.sort.ISortableModel;


public class Cell implements ISortableModel, IFilterableModel {
    @NonNull
    private final String mId;
    @Nullable
    private final Object mData;
    @NonNull
    private final String mFilterKeyword;

    public Cell(@NonNull String id, @Nullable Object data) {
        this.mId = id;
        this.mData = data;
        this.mFilterKeyword = String.valueOf(data);
    }

    /**
     * This is necessary for sorting process.
     * See ISortableModel
     */
    @NonNull
    @Override
    public String getId() {
        return mId;
    }

    /**
     * This is necessary for sorting process.
     * See ISortableModel
     */
    @Nullable
    @Override
    public Object getContent() {
        return mData;
    }

    @Nullable
    public Object getData() {
        return mData;
    }

    @NonNull
    @Override
    public String getFilterableKeyword() {
        return mFilterKeyword;
    }
}
