package com.ahmadullahpk.alldocumentreader.util;


public class RtfElementSymbol extends RtfElement {
    public char symbol;
    public int parameter = 0;

    @Override
    public void dump(int level) {
        System.out.println("<div style='color:blue'>");
        indent(level);
        System.out.println("SYMBOL " + symbol + " (" + parameter + ")");
        System.out.println("</div>");
    }
}