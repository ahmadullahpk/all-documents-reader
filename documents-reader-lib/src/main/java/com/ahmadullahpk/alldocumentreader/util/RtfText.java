package com.ahmadullahpk.alldocumentreader.util;


public class RtfText extends RtfElement {
    public String text;

    @Override
    public void dump(int level) {
        System.out.println("<div style='color:red'>");
        indent(level);
        System.out.println("TEXT " + text);
        System.out.println("</div>");
    }
}