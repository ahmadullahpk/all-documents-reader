package com.ahmadullahpk.alldocumentreader.util;


import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;


public class RtfReader {
    private String rtf;
    private int pos;
    private int len;
    private char tchar;
    private RtfElementGroup group;
    public RtfElementGroup root = null;


    protected void getChar() {
        if (pos < rtf.length()) {
            tchar = rtf.charAt(pos++);
        }
    }


    protected int hexdec(String s) {
        return Integer.parseInt(s, 16);
    }


    protected boolean isDigit() {
        if (tchar >= 48 && tchar <= 57) {
            return true;
        }
        return false;
    }


    protected boolean isLetter() {
        if (tchar >= 65 && tchar <= 90) {
            return true;
        }
        if (tchar >= 97 && tchar <= 122) {
            return true;
        }
        return false;
    }

    protected void parseStartGroup() {
        // Store state of document on stack.
        RtfElementGroup newGroup = new RtfElementGroup();
        if (group != null) {
            newGroup.parent = group;
        }
        if (root == null) {
            group = newGroup;
            root = newGroup;
        } else {
            group.children.add(newGroup);
            group = newGroup;
        }
    }


    protected void parseEndGroup() {
        // Retrieve state of document from stack.
        group = group.parent;
    }


    protected void parseControlWord() {
        getChar();
        String word = "";

        while (isLetter()) {
            word += tchar;
            getChar();
        }

        // Read parameter (if any) consisting of digits.
        // Paramater may be negative.
        int parameter = -1;
        boolean negative = false;
        if (tchar == '-') {
            getChar();
            negative = true;
        }

        while (isDigit()) {
            if (parameter == -1) {
                parameter = 0;
            }
            parameter = parameter * 10 + Integer.parseInt(tchar + "");
            getChar();
        }

        if (parameter == -1) {
            parameter = 1;
        }
        if (negative) {
            parameter = -parameter;
        }

        // If this is u, then the parameter will be followed by a character.
        if (word.equals("u")) {
            // Ignore space delimiter.
            if (tchar == ' ') {
                getChar();
            }

            // If the replacement character is encoded as hexadecimal value \'hh
            // then jump over it.
            if (tchar == '\\' && rtf.charAt(pos) == '\'') {
                pos += 3;
            }

            // Convert to UTF unsigned decimal code.
            if (negative) {
                parameter += 65536;
            }
        }
        // If the current character is a space, then it is a delimiter. It is
        // consumed.
        // If it's not a space, then it's part of the next item in the text, so
        // put the character back.
        else {
            if (tchar != ' ') {
                pos--;
            }
        }

        RtfWordControl rtfWord = new RtfWordControl();
        rtfWord.word = word;
        rtfWord.parameter = parameter;
        group.children.add(rtfWord);
    }

    protected void parseControlSymbol() {
        // Read symbol (one character only).
        getChar();
        char symbol = tchar;

        // Symbols ordinarily have no parameter. However, if this is \', then it
        // is followed by a 2-digit hex-code.
        int parameter = 0;
        if (symbol == '\'') {
            getChar();
            String firstChar = tchar + "";
            getChar();
            String secondChar = tchar + "";
            parameter = hexdec(firstChar + secondChar);
        }

        RtfElementSymbol rtfSymbol = new RtfElementSymbol();
        rtfSymbol.symbol = symbol;
        rtfSymbol.parameter = parameter;
        group.children.add(rtfSymbol);
    }


    protected void parseControl() {
        // Beginning of an RTF control word or control symbol.
        // Look ahead by one character to see if it starts with a letter
        // (control word) or another symbol (control symbol).
        getChar();
        pos--;
        if (isLetter()) {
            parseControlWord();
        } else {
            parseControlSymbol();
        }
    }


    protected void parseText() throws RtfParseException {
        // Parse plain text up to backslash or brace, unless escaped.
        String text = "";
        boolean terminate = false;

        do {
            terminate = false;

            // Is this an escape?
            if (tchar == '\\') {
                // Perform lookahead to see if this is really an escape
                // sequence.
                getChar();
                switch (tchar) {
                    case '\\':
                    case '{':
                    case '}':
                        break;
                    default:
                        // Not an escape. Roll back.
                        pos -= 2;
                        terminate = true;
                        break;
                }
            } else if (tchar == '{' || tchar == '}') {
                pos--;
                terminate = true;
            }

            if (!terminate) {
                text += tchar;
                getChar();
            }
        } while (!terminate && pos < len);

        RtfText rtfText = new RtfText();
        rtfText.text = text;

        // If group does not exist, then this is not a valid RTF file. Throw an
        // exception.
        if (group == null) {
            throw new RtfParseException("Invalid RTF file.");
        }

        group.children.add(rtfText);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void parse(File rtfFile) throws RtfParseException {
        try {
            try (FileInputStream fis = new FileInputStream(rtfFile)) {
                parse(fis);
            }
        } catch (IOException e) {
            throw new RtfParseException(e.getMessage());
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void parse(InputStream rtfStream) throws RtfParseException {
        String rtfSource = new BufferedReader(new InputStreamReader(rtfStream)).lines()
                .collect(Collectors.joining("\n"));
        parse(rtfSource);
    }


    public void parse(String rtfSource) throws RtfParseException {
        rtf = rtfSource;
        pos = 0;
        len = rtf.length();
        group = null;
        root = null;

        while (pos < len) {
            // Read next character.
            getChar();

            // Ignore \r and \n.
            if (tchar == '\n' || tchar == '\r') {
                continue;
            }

            // What type of character is this?
            switch (tchar) {
                case '{':
                    parseStartGroup();
                    break;
                case '}':
                    parseEndGroup();
                    break;
                case '\\':
                    parseControl();
                    break;
                default:
                    parseText();
                    break;
            }
        }
    }
}