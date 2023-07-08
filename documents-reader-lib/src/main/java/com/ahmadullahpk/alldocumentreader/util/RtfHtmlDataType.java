package com.ahmadullahpk.alldocumentreader.util;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;


public class RtfHtmlDataType {
    private String output;
    private Stack<RtfState> states;
    private RtfState state;
    private RtfState previousState;
    private Map<String, Boolean> openedTags;
    private List<String> fonttbl;
    private List<String> colortbl;
    private boolean newRootPar;


    public String format(RtfElementGroup root) {
        return format(root, false);
    }

    public String format(RtfElementGroup root, boolean page) {
        // Keeping track of style modifications.
        previousState = null;
        openedTags = new LinkedHashMap<>();
        openedTags.put("span", false);
        openedTags.put("p", true);

        // Create a stack of states and put an initial standard state onto the
        // stack.
        states = new Stack<>();
        state = new RtfState();
        states.push(state);

        // Do the job.
        output = "<p>";
        newRootPar = true;
        formatGroup(root);
        if (page) {
            wrapTags();
        }

        return output;
    }


    protected void extractFontTable(List<RtfElement> fontTblGrp) {

        List<String> fonttbl = new ArrayList<>();

        int c = fontTblGrp.size();

        for (int i = 1; i < c; i++) {
            // assume that font table entries are present in order of their index, i. e. f0, f1, f2...
            if (fontTblGrp.get(i) instanceof RtfElementGroup) {
                RtfElementGroup fontDesc = (RtfElementGroup) fontTblGrp.get(i);
                String fontFamily = "";
                // process font description group
                List<RtfElement> fontAttrs = fontDesc.children;
                // assume that the font index is the first (at least) RtfElement in the font descriptor RtfGroup.
                // Only RtfControlWord and RtfText elements are processed here. RtfGroups are not processed.
                for (int fa = 1; fa < fontAttrs.size(); fa++) {
                    RtfElement faElem = fontAttrs.get(fa);
                    if (faElem instanceof RtfWordControl) {
                        // font attribute
                        RtfWordControl fontAttr = (RtfWordControl) faElem;
                        // font family (has only one of):
                        if (fontAttr.word.equals("fnil")) {
                            // font family Unknown/Default -> no font name applicable so far
                        } else
                        if (fontAttr.word.equals("froman")) {
                            // font family Roman (proportionally spaced, serif)
                            fontFamily = "Times,serif";
                        } else
                        if (fontAttr.word.equals("fswiss")) {
                            // font family Swiss (proportionally spaced, sans-serif)
                            fontFamily = "Helvetica,Swiss,sans-serif";
                        } else
                        if (fontAttr.word.equals("fmodern")) {
                            // font family Fixed-pitch (typewriter)
                            fontFamily = "Courier,monospace";
                        } else
                        if (fontAttr.word.equals("fscript")) {
                            // font family Script (like handwritten)
                            fontFamily = "Cursive";
                        } else
                        if (fontAttr.word.equals("fdecor")) {
                            // font family Decorative
                            fontFamily = "'ITC Zapf Chancery'";
                        } else
                        if (fontAttr.word.equals("ftech")) {
                            // font family Non-Unicode, technical, symbol
                            fontFamily = "Symbol,Wingdings";
                        } else
                        if (fontAttr.word.equals("fbidi")) {
                            // font family bi-directional
                            fontFamily = "Miriam";
                        } else
                            // charset (after font family setting):
                            if (fontAttr.word.equals("fcharset")) {
                                // font charset reference (with parameter)
                                // 0 = default charset as defined in RTF header (assume ANSI, CP1252)
                                // 2 = SYMBOL_CHARSET (CP42)
                                if (fontAttr.parameter == 2) {
                                    // supersede font family by forcing "Symbol" font
                                    fontFamily = "Symbol";
                                }
                            }
                        // /cpgN (code page) is ignored. 42 however would equal /fcharset2 (Symbol)
                    }
                    if (faElem instanceof RtfText) {
                        // font name
                        RtfText fontName = (RtfText) faElem;
                        String fontNameText = fontName.text;
                        if (!";".equals(fontNameText)) {
                            if (fontNameText.endsWith(";")) {
                                fontNameText = fontNameText.substring(0, fontNameText.length() - 1);
                            }
                            if (!fontFamily.contains(fontNameText)) {
                                // DRY...
                                if (fontFamily.length() > 0) {
                                    fontFamily = "," + fontFamily;
                                }
                                fontFamily = "'" + fontNameText + "'" + fontFamily;
                            }
                        }
                    }
                }
                fonttbl.add(fontFamily);
            }
        }

        this.fonttbl = fonttbl;
    }

    protected void extractColorTable(List<RtfElement> colorTblGrp) {
        // {\colortbl;\red0\green0\blue0;}
        // index 0 is the "auto" color
        // force list to begin at index 1
        List<String> colortbl = new ArrayList<>();
        colortbl.add(null);

        int c = colorTblGrp.size();
        String color = "";

        for (int i = 2; i < c; i++) {
            if (colorTblGrp.get(i) instanceof RtfWordControl) {
                // Extract RGB color and convert it to hex string.
                int red = ((RtfWordControl) colorTblGrp.get(i)).parameter;
                int green = ((RtfWordControl) colorTblGrp.get(i + 1)).parameter;
                int blue = ((RtfWordControl) colorTblGrp.get(i + 2)).parameter;

                color = String.format("#%02x%02x%02x", red, green, blue);
                i += 2;
            } else if (colorTblGrp.get(i) instanceof RtfText) {
                // This a delimiter ";" so store the already extracted color.
                colortbl.add(color);
            }
        }

        this.colortbl = colortbl;
    }


    protected void formatGroup(RtfElementGroup group) {
        // Can we ignore this group?
        // Font table extraction.
        if (group.getType().equals("fonttbl")) {
            extractFontTable(group.children);
            return;
        }
        // Extract color table.
        if (group.getType().equals("colortbl")) {
            extractColorTable(group.children);
            return;
        }
        // Stylesheet extraction not yet supported.
        if (group.getType().equals("stylesheet")) {
            return;
        }
        // Info extraction not yet supported.
        if (group.getType().equals("info")) {
            return;
        }
        // Picture extraction not yet supported.
        if (group.getType().length() >= 4 && group.getType().substring(0, 4).equals("pict")) {
            return;
        }
        // Ignore destinations.
        if (group.isDestination()) {
            return;
        }

        // Push a new state onto the stack.
        state = (RtfState) state.clone();
        states.push(state);

        // Format all group children.
        for (RtfElement child : group.children) {
            if (child instanceof RtfElementGroup) {
                formatGroup((RtfElementGroup) child);
            } else if (child instanceof RtfWordControl) {
                formatControlWord((RtfWordControl) child);
            } else if (child instanceof RtfElementSymbol) {
                formatControlSymbol((RtfElementSymbol) child);
            } else if (child instanceof RtfText) {
                formatText((RtfText) child);
            }
        }

        // Pop state from stack.
        states.pop();
        state = states.peek();
    }

    protected void formatControlWord(RtfWordControl rtfWord) {
        if (rtfWord.word.equals("plain") || rtfWord.word.equals("pard")) {
            state.reset();
        } else
            // state changers, not printed immediately:
            if (rtfWord.word.equals("f")) {
                state.font = rtfWord.parameter;
            } else if (rtfWord.word.equals("b")) {
                state.bold = rtfWord.parameter > 0;
            } else if (rtfWord.word.equals("i")) {
                state.italic = rtfWord.parameter > 0;
            } else if (rtfWord.word.equals("ul")) {
                state.underline = rtfWord.parameter > 0;
            } else if (rtfWord.word.equals("ulnone")) {
                state.underline = false;
            } else if (rtfWord.word.equals("strike")) {
                state.strike = rtfWord.parameter > 0;
            } else if (rtfWord.word.equals("v")) {
                state.hidden = rtfWord.parameter > 0;
            } else if (rtfWord.word.equals("fs")) {
                state.fontSize = (int) Math.ceil((rtfWord.parameter / 24.0) * 16.0);
            } else if (rtfWord.word.equals("dn")) {
                state.dnup = (int) Math.ceil((rtfWord.parameter / 24.0) * 16.0) * -1;
            } else if (rtfWord.word.equals("up")) {
                state.dnup = (int) Math.ceil((rtfWord.parameter / 24.0) * 16.0);
            } else if (rtfWord.word.equals("sub")) {
                state.subscript = true;
                state.superscript = false;
            } else if (rtfWord.word.equals("super")) {
                state.subscript = false;
                state.superscript = true;
            } else if (rtfWord.word.equals("nosupersub")) {
                state.subscript = false;
                state.superscript = false;
            } else if (rtfWord.word.equals("cf")) {
                state.textColor = rtfWord.parameter;
            } else if (rtfWord.word.equals("cb") || rtfWord.word.equals("chcbpat") || rtfWord.word.equals("highlight")) {
                state.background = rtfWord.parameter;
            } else
                // special characters, printed immediately:
                if (rtfWord.word.equals("lquote")) {
                    applyStyle("&lsquo;");
                } else if (rtfWord.word.equals("rquote")) {
                    applyStyle("&rsquo;");
                } else if (rtfWord.word.equals("ldblquote")) {
                    applyStyle("&ldquo;");
                } else if (rtfWord.word.equals("rdblquote")) {
                    applyStyle("&rdquo;");
                } else if (rtfWord.word.equals("emdash")) {
                    applyStyle("&mdash;");
                } else if (rtfWord.word.equals("endash")) {
                    applyStyle("&ndash;");
                } else if (rtfWord.word.equals("emspace")) {
                    applyStyle("&emsp;");
                } else if (rtfWord.word.equals("enspace")) {
                    applyStyle("&ensp;");
                } else if (rtfWord.word.equals("tab")) {
                    applyStyle("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                } else if (rtfWord.word.equals("line")) {
                    applyStyle("<br>");
                } else if (rtfWord.word.equals("bullet")) {
                    applyStyle("&bull;");
                } else if (rtfWord.word.equals("u")) {
                    applyStyle("&#" + rtfWord.parameter + ";");
                } else if (rtfWord.word.equals("par") || rtfWord.word.equals("row")) {
                    // Close previously opened tags.
                    closeTags();

                    output += "<p>";
                    openedTags.put("p", true);
                    newRootPar = true;
                }
    }

    protected void applyStyle(String txt) {
        // Create span only when a style change occurs or a root paragraph start was just inserted.
        if (!state.equals(previousState) || newRootPar) {
            String span = "";

            if (state.font >= 0) {
                span += "font-family:" + printFontFamily(state.font) + ";";
            }
            if (state.bold) {
                span += "font-weight:bold;";
            }
            if (state.italic) {
                span += "font-style:italic;";
            }
            if (state.underline) {
                span += "text-decoration:underline;";
            }
            if (state.strike) {
                span += "text-decoration:strikethrough;";
            }
            if (state.hidden) {
                span += "display:none;";
            }
            if (state.fontSize != 0) {
                span += "font-size:" + state.fontSize + "px;";
            }
            // RTF dn/up:
            // By spec, RTF fs and RTF dn/up are independent of each other;
            // there is no documented "auto-reducing" for the font size.
            // In the wild, RTF dn/up often is given together with a "full" RTF fs but rendered with reduced font size.
            // Thus, RTF dn/up is rendered with implicit font size reduction.
            // This font-size setting supersedes the explicit "fs" font-size setting.
            if (state.dnup != 0) {
                span += calculateReducedFontSize() + "vertical-align:" + state.dnup + "px;";
            }
            // RTF sub/super:
            // Reduced font-size and vertical-align supersede settings from fs,dn,up.
            if (state.subscript) {
                span += calculateReducedFontSize() + "vertical-align:sub;";
            }
            if (state.superscript) {
                span += calculateReducedFontSize() + "vertical-align:super;";
            }
            if (state.textColor != 0) {
                span += "color:" + printColor(state.textColor) + ";";
            }
            if (state.background != 0) {
                span += "background-color:" + printColor(state.background) + ";";
            }

            // Keep track of preceding style.
            previousState = (RtfState) state.clone();

            // Close previously opened "span" tag.
            closeTag("span");

            output += "<span style=\"" + span + "\">" + txt;
            openedTags.put("span", true);
        } else {
            output += txt;
        }
        newRootPar = false;
    }


    protected String calculateReducedFontSize() {
        String css;
        if (state.fontSize != 0) {
            int reducedFontSize = (int) Math.ceil((state.fontSize / 3.0) * 2.0);
            css = "font-size:" + reducedFontSize + "px;";
        } else {
            css = "font-size:smaller;";
        }
        return css;
    }

    protected String printFontFamily(int index) {
        // index is 0-based
        if (index >= 0 && index < fonttbl.size()) {
            return fonttbl.get(index);
        } else {
            return "";
        }
    }


    protected String printColor(int index) {
        if (index >= 1 && index < colortbl.size()) {
            return colortbl.get(index);
        } else {
            return "";
        }
    }


    protected void closeTag(String tag) {
        if (openedTags.get(tag)) {
            output += "</" + tag + ">";
            openedTags.put(tag, false);
        }
    }


    protected void closeTags() {
        for (String tag : openedTags.keySet()) {
            closeTag(tag);
        }
    }


    protected void wrapTags() {
        StringBuilder source = new StringBuilder();
        source.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n");
        source.append("<html>\n");
        source.append("  <head>\n");
        source.append("    <meta content=\"text/html;charset=UTF-8\" http-equiv=\"content-type\"/>\n");
        source.append("  </head>\n");
        source.append("  <body>\n");
        source.append(output + "\n");
        source.append("  </body>\n");
        source.append("</html>\n");
        output = source.toString();
    }


    protected void formatControlSymbol(RtfElementSymbol rtfSymbol) {
        if (rtfSymbol.symbol == '\'') {
            applyStyle("&#" + rtfSymbol.parameter + ";");
        }
        if (rtfSymbol.symbol == '~') {
            output += "&nbsp;";
        }
    }

    protected void formatText(RtfText rtfText) {
        applyStyle(rtfText.text);
    }
}