package com.ahmadullahpk.alldocumentreader.util;

public class RtfWordControl extends RtfElement {
    public String word;
    public int parameter;

    @Override
    public void dump(int level) {
        System.out.println("<div style='color:green'>");
        indent(level);
        System.out.println("WORD " + word + " (" + parameter + ")");
        System.out.println("</div>");
    }
}