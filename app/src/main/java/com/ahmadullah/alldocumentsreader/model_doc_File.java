package com.ahmadullah.alldocumentsreader;

public class model_doc_File {
    String path;
    String name;

    public model_doc_File(String path, String name) {
        this.path = path;
        this.name = name;
    }

    public model_doc_File() {

    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
