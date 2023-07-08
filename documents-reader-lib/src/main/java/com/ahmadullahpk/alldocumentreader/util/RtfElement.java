package com.ahmadullahpk.alldocumentreader.util;

public abstract class RtfElement {
    protected abstract void dump(int level);

    protected void indent(int level) {
        for (int i = 0; i < level; i++) {
            System.out.println("&nbsp;");
        }
    }
}