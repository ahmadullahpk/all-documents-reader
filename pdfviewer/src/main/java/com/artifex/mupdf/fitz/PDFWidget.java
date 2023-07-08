package com.artifex.mupdf.fitz;

public class PDFWidget extends PDFAnnotation {
   public static final int TYPE_UNKNOWN = 0;
   public static final int TYPE_BUTTON = 1;
   public static final int TYPE_CHECKBOX = 2;
   public static final int TYPE_COMBOBOX = 3;
   public static final int TYPE_LISTBOX = 4;
   public static final int TYPE_RADIOBUTTON = 5;
   public static final int TYPE_SIGNATURE = 6;
   public static final int TYPE_TEXT = 7;
   public static final int TX_FORMAT_NONE = 0;
   public static final int TX_FORMAT_NUMBER = 1;
   public static final int TX_FORMAT_SPECIAL = 2;
   public static final int TX_FORMAT_DATE = 3;
   public static final int TX_FORMAT_TIME = 4;
   public static final int PDF_FIELD_IS_READ_ONLY = 1;
   public static final int PDF_FIELD_IS_REQUIRED = 2;
   public static final int PDF_FIELD_IS_NO_EXPORT = 4;
   public static final int PDF_TX_FIELD_IS_MULTILINE = 4096;
   public static final int PDF_TX_FIELD_IS_PASSWORD = 8192;
   public static final int PDF_TX_FIELD_IS_COMB = 16777216;
   public static final int PDF_BTN_FIELD_IS_NO_TOGGLE_TO_OFF = 16384;
   public static final int PDF_BTN_FIELD_IS_RADIO = 32768;
   public static final int PDF_BTN_FIELD_IS_PUSHBUTTON = 65536;
   public static final int PDF_CH_FIELD_IS_COMBO = 131072;
   public static final int PDF_CH_FIELD_IS_EDIT = 262144;
   public static final int PDF_CH_FIELD_IS_SORT = 524288;
   public static final int PDF_CH_FIELD_IS_MULTI_SELECT = 2097152;
   private int fieldType;
   private int fieldFlags;
   private int textFormat;
   private int maxLen;
   private String[] options;
   private String originalValue;

   protected PDFWidget(long p) {
      super(p);
   }

   public int getFieldType() {
      return this.fieldType;
   }

   public int getFieldFlags() {
      return this.fieldFlags;
   }

   public boolean isReadOnly() {
      return (this.getFieldFlags() & 1) != 0;
   }

   public native String getValue();

   public native boolean setValue(String var1);

   public boolean isButton() {
      int ft = this.getFieldType();
      return ft == 1 || ft == 2 || ft == 5;
   }

   public boolean isPushButton() {
      return this.getFieldType() == 1;
   }

   public boolean isCheckbox() {
      return this.getFieldType() == 2;
   }

   public boolean isRadioButton() {
      return this.getFieldType() == 5;
   }

   public native boolean toggle();

   public boolean isText() {
      return this.getFieldType() == 7;
   }

   public boolean isMultiline() {
      return (this.getFieldFlags() & 4096) != 0;
   }

   public boolean isPassword() {
      return (this.getFieldFlags() & 8192) != 0;
   }

   public boolean isComb() {
      return (this.getFieldFlags() & 16777216) != 0;
   }

   public int getMaxLen() {
      return this.maxLen;
   }

   public int getTextFormat() {
      return this.textFormat;
   }

   public native boolean setTextValue(String var1);

   public native Quad[] textQuads();

   public native void setEditing(boolean var1);

   public native boolean isEditing();

   public void startEditing() {
      this.setEditing(true);
      this.originalValue = this.getValue();
   }

   public void cancelEditing() {
      this.setValue(this.originalValue);
      this.setEditing(false);
   }

   public boolean commitEditing(String newValue) {
      this.setValue(this.originalValue);
      this.setEditing(false);
      if (this.setTextValue(newValue)) {
         return true;
      } else {
         this.setEditing(true);
         return false;
      }
   }

   public boolean isChoice() {
      int ft = this.getFieldType();
      return ft == 3 || ft == 4;
   }

   public boolean isComboBox() {
      return this.getFieldType() == 3;
   }

   public boolean isListBox() {
      return this.getFieldType() == 4;
   }

   public String[] getOptions() {
      return this.options;
   }

   public native boolean setChoiceValue(String var1);

   static {
      Context.init();
   }
}
