package com.ahmadullahpk.alldocumentreader.activity;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintJob;
import android.print.PrintManager;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;


import com.ahmadullahpk.alldocumentreader.R;
import com.ahmadullahpk.alldocumentreader.databinding.ActivityViewRtfBinding;
import com.ahmadullahpk.alldocumentreader.util.RtfHtmlDataType;
import com.ahmadullahpk.alldocumentreader.util.RtfParseException;
import com.ahmadullahpk.alldocumentreader.util.RtfReader;
import com.ahmadullahpk.alldocumentreader.util.Utility;

import java.io.File;

public class ViewRtf_Activity extends AppCompatActivity {

    ActivityViewRtfBinding binding;
    private String fileName;
    private String filePath;
    Boolean fromConverterApp = false;
    boolean isExit = false;
    boolean isFromAppActivity = false;
    PrintDocumentAdapter printAdapter;
    PrintJob printJob;
    WebView webview;
    private boolean back = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } catch (Exception e) {
            e.printStackTrace();
        }

        binding = ActivityViewRtfBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);



        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareFile();
            }
        });
        binding.imgPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                printAndShare();
            }
        });


        if (getIntent() != null) {
            this.filePath = getIntent().getStringExtra("path");
            this.fileName = getIntent().getStringExtra("name");
            this.isFromAppActivity = getIntent().getBooleanExtra("fromAppActivity", false);
            this.fromConverterApp = getIntent().getBooleanExtra("fromConverterApp", false);
            binding.headerTitleText.setMaxLines(1);
            binding.headerTitleText.setText(fileName);

        }
        WebView webView = (WebView) findViewById(R.id.webView);
        this.webview = webView;
        webView.setWebViewClient(new WebViewClient());
        this.webview.getSettings().setBuiltInZoomControls(true);
        this.webview.getSettings().setDisplayZoomControls(false);
        this.webview.getSettings().setAllowFileAccess(true);
        new loadTextFromRtfFile().execute();
       // Utility.Toast(this, "Please wait...");
    }



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void createWebPrintJob(WebView webView) {
        PrintManager printManager = (PrintManager) getSystemService(PRINT_SERVICE);
        this.printAdapter = webView.createPrintDocumentAdapter("New_RTF_File.pdf");
        this.printJob = printManager.print(getString(R.string.app_name)
                + " Document", this.printAdapter, new PrintAttributes.Builder()
                .setMediaSize(PrintAttributes.MediaSize.ISO_A4).setResolution(new PrintAttributes
                        .Resolution("id", "print", 200, 200))
                .setColorMode(PrintAttributes.COLOR_MODE_COLOR).setMinMargins(PrintAttributes
                        .Margins.NO_MARGINS).build());
    }


    class loadTextFromRtfFile extends AsyncTask<Void, Void, Void> {
        String html;

        loadTextFromRtfFile() {
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @RequiresApi(api = Build.VERSION_CODES.N)
        protected Void doInBackground(Void... voidArr) {
            back = false;
            File file = new File(ViewRtf_Activity.this.filePath);
            RtfReader rtfReader = new RtfReader();
            RtfHtmlDataType rtfHtmlDataType = new RtfHtmlDataType();
            try {
                rtfReader.parse(file);
                this.html = rtfHtmlDataType.format(rtfReader.root, true);
                System.out.println();
                return null;
            } catch (RtfParseException e) {
                Utility.logCatMsg("RtfParseException " + e.getMessage());
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Void voidR) {
            super.onPostExecute(voidR);
            back = true;
            ViewRtf_Activity.this.webview.loadDataWithBaseURL("", this.html, "text/html", "UTF-8", "");
        }
    }


    public class WebViewClient extends android.webkit.WebViewClient {
        public WebViewClient() {
        }

        @Override
        public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
            super.onPageStarted(webView, str, bitmap);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView webView, String str) {
            webView.loadUrl(str);
            return true;
        }

        @Override
        public void onPageFinished(WebView webView, String str) {
            super.onPageFinished(webView, str);
            ViewRtf_Activity.this.findViewById(R.id.progressBar).setVisibility(View.GONE);
        }
    }

    private void shareFile() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        Uri parse = Uri.parse(this.filePath);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_STREAM, parse);
        startActivity(Intent.createChooser(intent, "Share File"));

    }


    private void printAndShare() {
        createWebPrintJob(this.webview);

    }


    @Override
    public void onBackPressed() {
        if(back){
            finish();
        }else {
            Toast.makeText(this, "Please wait while task complete", Toast.LENGTH_SHORT).show();
        }
    }


}