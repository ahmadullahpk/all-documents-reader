package com.ahmadullahpk.alldocumentreader.util;

public class RtfState implements Cloneable {

    public boolean bold;
    public boolean italic;
    public boolean underline;
    public boolean strike;
    public boolean hidden;
    public int dnup;
    public boolean subscript;
    public boolean superscript;
    public int fontSize;
    public int font;
    public int textColor;
    public int background;
    public RtfState() {
        reset();
    }


    @Override
    public Object clone() {
        RtfState newState = new RtfState();
        newState.bold = this.bold;
        newState.italic = this.italic;
        newState.underline = this.underline;
        newState.strike = this.strike;
        newState.hidden = this.hidden;
        newState.dnup = this.dnup;
        newState.subscript = this.subscript;
        newState.superscript = this.superscript;
        newState.fontSize = this.fontSize;
        newState.font = this.font;
        newState.textColor = this.textColor;
        newState.background = this.background;
        return newState;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof RtfState)) {
            return false;
        }

        RtfState anotherState = (RtfState) obj;
        return this.bold == anotherState.bold && this.italic == anotherState.italic
                && this.underline == anotherState.underline && this.strike == anotherState.strike
                && this.dnup == anotherState.dnup
                && this.subscript == anotherState.subscript && this.superscript == anotherState.superscript
                && this.hidden == anotherState.hidden && this.fontSize == anotherState.fontSize
                && this.font == anotherState.font
                && this.textColor == anotherState.textColor && this.background == anotherState.background;
    }


    public void reset() {
        bold = false;
        italic = false;
        underline = false;
        strike = false;
        hidden = false;
        dnup = 0;
        subscript = false;
        superscript = false;
        fontSize = 0;
        font = 0;
        textColor = 0;
        background = 0;
    }
}
