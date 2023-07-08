package com.ahmadullahpk.alldocumentreader.util;

import java.util.ArrayList;
import java.util.List;

public class RtfElementGroup extends RtfElement {
    public RtfElementGroup parent;
    public List<RtfElement> children;

    public RtfElementGroup() {
        parent = null;
        children = new ArrayList<>();
    }

    public String getType() {
        // No children?
        if (children.isEmpty()) {
            return "";
        }

        // First child not a control word?
        RtfElement child = children.get(0);
        if (!(child instanceof RtfWordControl)) {
            return "";
        }

        return ((RtfWordControl) child).word;
    }

    public boolean isDestination() {
        // No children?
        if (children.isEmpty()) {
            return false;
        }

        // First child not a control symbol?
        RtfElement child = children.get(0);
        if (!(child instanceof RtfElementSymbol)) {
            return false;
        }

        return ((RtfElementSymbol) child).symbol == '*';
    }

    public void dump() {
        dump(0);
    }

    @Override
    public void dump(int level) {
        System.out.println("<div>");
        indent(level);
        System.out.println("{");
        System.out.println("</div>");

        for (RtfElement child : children) {
            if (child instanceof RtfElementGroup) {
                RtfElementGroup group = (RtfElementGroup) child;

                // Can we ignore this group?
                if (group.getType().equals("fonttbl")) {
                    continue;
                }
                if (group.getType().equals("colortbl")) {
                    continue;
                }
                if (group.getType().equals("stylesheet")) {
                    continue;
                }
                if (group.getType().equals("info")) {
                    continue;
                }

                // Skip any pictures and destinations.
                if (group.getType().length() >= 4 && group.getType().substring(0, 4).equals("pict")) {
                    continue;
                }
                if (group.isDestination()) {
                    continue;
                }
            }

            child.dump(level + 2);
        }

        System.out.println("<div>");
        indent(level);
        System.out.println("}");
        System.out.println("</div>");
    }
}