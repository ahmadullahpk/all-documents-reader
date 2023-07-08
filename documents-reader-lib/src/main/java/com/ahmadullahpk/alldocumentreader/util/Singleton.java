package com.ahmadullahpk.alldocumentreader.util;

import com.ahmadullahpk.alldocumentreader.dataType.ImageParcelable;
import com.ahmadullahpk.alldocumentreader.dataType.FavoriteFilesParcel;
import   com.ahmadullahpk.alldocumentreader.dataType.Model_Main;
import   com.ahmadullahpk.alldocumentreader.dataType.FolderDataType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class
Singleton {
    private static Singleton singleton = new Singleton();
    int adCounter = 0;
    ArrayList<Model_Main> allDocumentsList = new ArrayList<>();
    ArrayList<Model_Main> allEBookFilesList = new ArrayList<>();
    FavoriteFilesParcel favoriteFilesParcel;
    ArrayList<ImageParcelable> fileList = new ArrayList<>();
    List<FolderDataType> folderDatumTypes = new ArrayList<>();
    boolean isCodeFileDeleted = false;
    private boolean isCodeFileFolderView = false;
    boolean isFileDeleted = false;

    public void setCodeFileDeleted(boolean z) {
    }


    private Singleton() {
    }

    public static Singleton getInstance() {
        if (singleton == null) {
            singleton = new Singleton();
        }
        return singleton;
    }

    public ArrayList<Model_Main> getAllDocumentFileList() {
        if (this.allDocumentsList == null) {
            this.allDocumentsList = new ArrayList<>();
        }
        return this.allDocumentsList;
    }

    public ArrayList<Model_Main> getAllEbookFileList() {
        if (this.allEBookFilesList == null) {
            this.allEBookFilesList = new ArrayList<>();
        }
        return this.allEBookFilesList;
    }




    public boolean isFileDeleted() {
        return this.isFileDeleted;
    }

    public void setFileDeleted(boolean z) {
        this.isFileDeleted = z;
    }

    public boolean isCodeFileDeleted() {
        return this.isCodeFileDeleted;
    }

    public boolean isCodeFileFolderView() {
        return this.isCodeFileFolderView;
    }

    public void setCodeFileFolderView(boolean z) {
        this.isCodeFileFolderView = z;
    }

    public int getAdCounter() {
        return this.adCounter;
    }

    public void setAdCounter() {
        int i = this.adCounter + 1;
        this.adCounter = i;
        if (i == 4) {
            this.adCounter = 0;
        }
        int i2 = this.adCounter;
    }



    public void setFileList(ArrayList<ImageParcelable> arrayList) {
        this.fileList = arrayList;
    }

    public ArrayList<ImageParcelable> getFileList() {
        if (this.fileList == null) {
            this.fileList = new ArrayList<>();
        }
        return this.fileList;
    }

    public ArrayList<ImageParcelable> getFileList(int i) {
        ArrayList<ImageParcelable> imageParcelables = getFolderList().get(i).getImages();
        this.fileList = imageParcelables;
        return imageParcelables;
    }

    public List<FolderDataType> getFolderList() {
        if (this.folderDatumTypes == null) {
            this.folderDatumTypes = new ArrayList<>();
        }
        return this.folderDatumTypes;
    }

    public List<FolderDataType> getFolderList(Collection<FolderDataType> collection) {
        ArrayList<FolderDataType> arrayList = new ArrayList(collection);
        this.folderDatumTypes = arrayList;
        return arrayList;
    }

    public void destroy() {

        ArrayList<Model_Main> arrayList = this.allDocumentsList;
        if (arrayList != null) {
            arrayList.clear();
        }
        this.allDocumentsList = null;

        singleton = null;
    }




}
