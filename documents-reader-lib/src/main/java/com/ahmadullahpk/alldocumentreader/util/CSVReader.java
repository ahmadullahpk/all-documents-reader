package com.ahmadullahpk.alldocumentreader.util;

import android.content.Context;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {
    private Context context;

    private static class StringDArray {
        private String[] data;
        private int used;

        private StringDArray() {
            this.data = new String[0];
            this.used = 0;
        }

        public void add(String str) {
            int i = this.used;
            String[] strArr = this.data;
            if (i >= strArr.length) {
                String[] strArr2 = new String[(i + 1)];
                System.arraycopy(strArr, 0, strArr2, 0, i);
                this.data = strArr2;
            }
            String[] strArr3 = this.data;
            int i2 = this.used;
            this.used = i2 + 1;
            strArr3[i2] = str;
        }

        public int length() {
            return this.used;
        }

        public String[] getArray() {
            return this.data;
        }
    }

    public CSVReader(Context context2) {
        this.context = context2;
    }

    public List read(InputStream inputStream) {
        ArrayList arrayList = new ArrayList();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            boolean z = false;
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    break;
                }
                StringDArray stringDArray = new StringDArray();
                String str = "";
                for (char c : (readLine + ',').toCharArray()) {
                    if (c == 10 || c == 13) {
                        stringDArray.add(str);
                        str = "";
                        z = false;
                    } else if (c == '\"') {
                        z = !z;
                    } else if (c != ',') {
                        str = str + c;
                    } else if (!z) {
                        stringDArray.add(str);
                        str = "";
                    } else {
                        str = str + c;
                    }
                }
                if (stringDArray.length() > 0) {
                    if (arrayList.size() <= 0) {
                        arrayList.add(stringDArray.getArray());
                    } else if (stringDArray.length() >= ((String[]) arrayList.get(0)).length) {
                        arrayList.add(stringDArray.getArray());
                    }
                }
            }
            inputStream.close();
        } catch (Exception e) {
            Toast.makeText(this.context, "Error : " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return arrayList;
    }
}
