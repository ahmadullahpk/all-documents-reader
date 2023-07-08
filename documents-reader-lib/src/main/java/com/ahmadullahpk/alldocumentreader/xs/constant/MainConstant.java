/*
 * 文件名称:          MainConstant.java
 *
 * 编译器:            android2.2
 * 时间:              下午2:29:36
 */
package   com.ahmadullahpk.alldocumentreader.xs.constant;


import   com.ahmadullahpk.alldocumentreader.xs.fc.openxml4j.opc.ContentTypes;

import java.util.regex.Pattern;


public final class MainConstant {
    public static final byte APPLICATION_TYPE_ALL = 100;
    public static final byte APPLICATION_TYPE_CODE = 6;
    public static final byte APPLICATION_TYPE_CODE_FOLDER_VIEW = 7;
    public static final byte APPLICATION_TYPE_COMPRESS = 15;
    public static final byte APPLICATION_TYPE_CSV = 10;
    public static final byte APPLICATION_TYPE_DIR = 5;
    public static final byte APPLICATION_TYPE_EPUB = 14;
    public static final byte APPLICATION_TYPE_FAVORITE = 12;
    public static final byte APPLICATION_TYPE_FOLDER_VIEW = 8;
    public static final byte APPLICATION_TYPE_HTML = 11;
    public static final byte APPLICATION_TYPE_XML = 112;
    public static final byte APPLICATION_TYPE_PDF = 3;
    public static final byte APPLICATION_TYPE_PPT = 2;
    public static final byte APPLICATION_TYPE_RTF = 13;
    public static final byte APPLICATION_TYPE_SS = 1;
    public static final byte APPLICATION_TYPE_TRANSPARENT = 9;
    public static final byte APPLICATION_TYPE_TXT = 4;
    public static final byte APPLICATION_TYPE_WP = 0;
    public static final float DEFAULT_TAB_WIDTH_PIXEL = 28.0f;
    public static final float DEFAULT_TAB_WIDTH_POINT = 21.0f;
    public static final int DRAWMODE_CALLOUTDRAW = 1;
    public static final int DRAWMODE_CALLOUTERASE = 2;
    public static final int DRAWMODE_NORMAL = 0;
    public static final int EMU_PER_INCH = 914400;
    public static final String FILE_TYPE_CSV = "csv";
    public static final String FILE_TYPE_DJVU = "djvu";
    public static final String FILE_TYPE_DOC = "doc";
    public static final String FILE_TYPE_DOCX = "docx";
    public static final String FILE_TYPE_DOT = "dot";
    public static final String FILE_TYPE_DOTM = "dotm";
    public static final String FILE_TYPE_DOTX = "dotx";
    public static final String FILE_TYPE_EPUB = "epub";
    public static final String FILE_TYPE_HTML = "html";
    public static final String FILE_TYPE_MOBI = "mobi";
    public static final String FILE_TYPE_PDF = "pdf";
    public static final String FILE_TYPE_POT = "pot";
    public static final String FILE_TYPE_POTM = "potm";
    public static final String FILE_TYPE_POTX = "potx";
    public static final String FILE_TYPE_PPT = "ppt";
    public static final String FILE_TYPE_PPTM = "pptm";
    public static final String FILE_TYPE_PPTX = "pptx";
    public static final String FILE_TYPE_RTF = "rtf";
    public static final String FILE_TYPE_TXT = "txt";
    public static final String FILE_TYPE_XLS = "xls";
    public static final String FILE_TYPE_XLSM = "xlsm";
    public static final String FILE_TYPE_XLSX = "xlsx";
    public static final String FILE_TYPE_XLT = "xlt";
    public static final String FILE_TYPE_XLTM = "xltm";
    public static final String FILE_TYPE_XLTX = "xltx";
    public static final String FILE_TYPE_ZIP = "zip";
    public static final int GAP = 5;
    public static final int HANDLER_MESSAGE_DISMISS_PROGRESS = 3;
    public static final int HANDLER_MESSAGE_DISPOSE = 4;
    public static final int HANDLER_MESSAGE_ERROR = 1;
    public static final int HANDLER_MESSAGE_SEND_READER_INSTANCE = 4;
    public static final int HANDLER_MESSAGE_SHOW_PROGRESS = 2;
    public static final int HANDLER_MESSAGE_SUCCESS = 0;
    public static final String INTENT_FILED_FILE_LIST_TYPE = "fileListType";
    public static final String INTENT_FILED_FILE_PATH = "filePath";
    public static final String INTENT_FILED_MARK_FILES = "markFiles";
    public static final String INTENT_FILED_MARK_STATUS = "markFileStatus";
    public static final String INTENT_FILED_RECENT_FILES = "recentFiles";
    public static final String INTENT_FILED_SDCARD_FILES = "sdcard";
    public static final int MAXZOOM = 30000;
    public static final int MAXZOOM_THUMBNAIL = 5000;
    public static final float MM_TO_POINT = 2.835f;
    public static final int NOTEPAD_COLOR_THEM1 = 101;
    public static final int NOTEPAD_COLOR_THEM10 = 1010;
    public static final int NOTEPAD_COLOR_THEM2 = 102;
    public static final int NOTEPAD_COLOR_THEM3 = 103;
    public static final int NOTEPAD_COLOR_THEM4 = 104;
    public static final int NOTEPAD_COLOR_THEM5 = 105;
    public static final int NOTEPAD_COLOR_THEM6 = 106;
    public static final int NOTEPAD_COLOR_THEM7 = 107;
    public static final int NOTEPAD_COLOR_THEM8 = 108;
    public static final int NOTEPAD_COLOR_THEM9 = 109;
    public static final float PIXEL_DPI = 96.0f;
    public static final float PIXEL_TO_POINT = 0.75f;
    public static final float PIXEL_TO_TWIPS = 15.0f;
    public static final float POINT_DPI = 72.0f;
    public static final float POINT_TO_PIXEL = 1.3333334f;
    public static final float POINT_TO_TWIPS = 20.0f;
    public static final int STANDARD_RATE = 10000;
    public static final String STORAGE_LOCATION = "storage_location";
    public static final String TABLE_RECENT = "openedfiles";
    public static final String TABLE_SETTING = "settings";
    public static final String TABLE_STAR = "starredfiles";
    public static final float TWIPS_TO_PIXEL = 0.06666667f;
    public static final float TWIPS_TO_POINT = 0.05f;
    public static final int ZOOM_ROUND = 10000000;

    public static Pattern getPattern() {
        return Pattern.compile(".*\\.(doc|doc|docx|xls|xlsx|ppt|pptx|txt|pdf|dotx|dotm|xlt|xltx|xlsm|xltm|pot|pptm|potx|potm|epub|mobi|csv|rtf)$");
    }

    public static int getFileType(String str) {
        String lowerCase = str.toLowerCase();
        if (lowerCase.endsWith(FILE_TYPE_DOC) || lowerCase.endsWith(FILE_TYPE_DOCX) || lowerCase.endsWith(FILE_TYPE_DOT) || lowerCase.endsWith(FILE_TYPE_DOTX) || lowerCase.endsWith(FILE_TYPE_DOTM)) {
            return APPLICATION_TYPE_WP;
        }
        if (lowerCase.endsWith(FILE_TYPE_XLS) || lowerCase.endsWith(FILE_TYPE_XLSX) || lowerCase.endsWith(FILE_TYPE_XLT) || lowerCase.endsWith(FILE_TYPE_XLTX) || lowerCase.endsWith(FILE_TYPE_XLTM) || lowerCase.endsWith(FILE_TYPE_XLSM)) {
            return APPLICATION_TYPE_SS;
        }
        if (lowerCase.endsWith(FILE_TYPE_PPT) || lowerCase.endsWith(FILE_TYPE_PPTX) || lowerCase.endsWith(FILE_TYPE_POT) || lowerCase.endsWith(FILE_TYPE_PPTM) || lowerCase.endsWith(FILE_TYPE_POTX) || lowerCase.endsWith(FILE_TYPE_POTM)) {
            return APPLICATION_TYPE_PPT;
        }
        if (lowerCase.endsWith("pdf")) {
            return APPLICATION_TYPE_PDF;
        }
        if (lowerCase.endsWith(FILE_TYPE_TXT)) {
            return APPLICATION_TYPE_TXT;
        }
        if (lowerCase.endsWith(FILE_TYPE_RTF)) {
            return APPLICATION_TYPE_RTF;
        }
        if (lowerCase.endsWith(FILE_TYPE_EPUB) || lowerCase.endsWith(FILE_TYPE_MOBI)) {
            return APPLICATION_TYPE_EPUB;
        }
        return (lowerCase.endsWith(FILE_TYPE_HTML) || lowerCase.endsWith("ascii") || lowerCase.endsWith("asm") || lowerCase.endsWith("awk") || lowerCase.endsWith("bash") || lowerCase.endsWith("bat") || lowerCase.endsWith("bf") || lowerCase.endsWith("bsh") || lowerCase.endsWith("c") || lowerCase.endsWith("cert") || lowerCase.endsWith("cgi") || lowerCase.endsWith("clj") || lowerCase.endsWith("conf") || lowerCase.endsWith("cpp") || lowerCase.endsWith("cs") || lowerCase.endsWith("css") || lowerCase.endsWith(FILE_TYPE_CSV) || lowerCase.endsWith("elr") || lowerCase.endsWith("go") || lowerCase.endsWith("h") || lowerCase.endsWith("hs") || lowerCase.endsWith("htaccess") || lowerCase.endsWith("htm") || lowerCase.endsWith(FILE_TYPE_HTML) || lowerCase.endsWith("ini") || lowerCase.endsWith("java") || lowerCase.endsWith("js") || lowerCase.endsWith("json") || lowerCase.endsWith("key") || lowerCase.endsWith("lisp") || lowerCase.endsWith(".log") || lowerCase.endsWith(".lua") || lowerCase.endsWith("md") || lowerCase.endsWith("mkdn") || lowerCase.endsWith("pem") || lowerCase.endsWith("php") || lowerCase.endsWith("pl") || lowerCase.endsWith("py") || lowerCase.endsWith("rb") || lowerCase.endsWith("readme") || lowerCase.endsWith("scala") || lowerCase.endsWith("sh") || lowerCase.endsWith("sql") || lowerCase.endsWith("srt") || lowerCase.endsWith("sub") || lowerCase.endsWith("tex") || lowerCase.endsWith("vb") || lowerCase.endsWith("vbs") || lowerCase.endsWith("vhdl") || lowerCase.endsWith("wollok") || lowerCase.endsWith(ContentTypes.EXTENSION_XML) || lowerCase.endsWith("xsd") || lowerCase.endsWith("xsl") || lowerCase.endsWith("yaml") || lowerCase.endsWith("iml") || lowerCase.endsWith("gitignore") || lowerCase.endsWith("gradle")) ? 6 : 5;
    }

    public static Pattern getCodeFilePattern() {
        return Pattern.compile(".*\\.(html|ascii|asm|awk|bash|bat|bf|bsh|c|cert|cgi|clj|conf|cpp|cs|css|csv|elr|go|h|hs|htaccess|htm|html|ini|java|js|json|key|lisp|.log|.lua|md|mkdn|pem|php|pl|py|rb|readme|scala|sh|sql|srt|sub|tex|vb|vbs|vhdl|wollok|xml|xsd|xsl|yaml|iml|gitignore|gradle)$");
    }
}