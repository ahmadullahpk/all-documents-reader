package com.ahmadullahpk.alldocumentreader.listeners;

import   com.ahmadullahpk.alldocumentreader.dataType.FolderDataType;

import java.util.HashMap;

 public interface OnPhotosLoadListener {
    void onPhotoDataLoadedCompleted(HashMap<String, FolderDataType> hashMap);
}