package com.ahmadullahpk.alldocumentreader.activity;

import android.os.Bundle;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintJob;
import android.print.PrintManager;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatDelegate;

import com.ahmadullahpk.alldocumentreader.R;
import com.ahmadullahpk.alldocumentreader.databinding.ActivityPrintPdfBinding;
import com.ahmadullahpk.alldocumentreader.manageui.CustomFrameLayout;
import com.ahmadullahpk.alldocumentreader.util.Singleton;
import com.ahmadullahpk.alldocumentreader.util.Utility;

import java.io.IOException;
import java.io.InputStream;

public class PrintPdfActivity extends BaseActivity {
    ActivityPrintPdfBinding binding;
    private CustomFrameLayout.Builder toolbarBuilder;

    PrintDocumentAdapter printAdapter;
    PrintJob printJob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } catch (Exception e) {
            e.printStackTrace();
        }

        binding = ActivityPrintPdfBinding.inflate(getLayoutInflater());
        View layout = binding.getRoot();
        setContentView(layout);
        setStatusBar();
        adaptFitsSystemWindows(getWindow().getDecorView());
        configHeader();

        WebView webView = binding.webView;
        webView.setInitialScale(100);
        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView webView, int i) {
                if (i < 100) {
                    binding.progressBar.setVisibility(View.VISIBLE);
                }
                if (i == 100) {
                    binding.progressBar.setVisibility(View.GONE);
                }
            }
        });
        if (getIntent() != null) {
           // webView.loadDataWithBaseURL(null, getHtmlContent(PdfCreateActivity.data), "text/html", "utf-8", null);
        }
        binding.btnSaveAsPDF.setOnClickListener(view -> PrintPdfActivity.this.createWebPrintJob(webView));
    }


    private void createWebPrintJob(WebView webView) {
        PrintManager printManager = (PrintManager) getSystemService(PRINT_SERVICE);
        this.printAdapter = webView.createPrintDocumentAdapter("New PDF File.pdf");
        this.printJob = printManager.print(getString(R.string.app_name) + " Document", this.printAdapter, new PrintAttributes.Builder().setMediaSize(PrintAttributes.MediaSize.ISO_A4).setColorMode(PrintAttributes.COLOR_MODE_COLOR).setMinMargins(PrintAttributes.Margins.NO_MARGINS).build());
    }

    protected String getHtmlContent(String str) {
        String LoadData = LoadData("bootstrap/bootstrap.css");
        String LoadData2 = LoadData("dist/summernote.css");
        return "<html><head> <style>" + LoadData + LoadData2 + "</style>" + "</head>" + "<body>" + str + "</body>" + "</html>";
    }

    @Override
    public void onResume() {
        super.onResume();
        if (this.printJob != null && this.printJob.isCompleted()) {
            Utility.Toast(this, getResources().getString(R.string.pdfFileCreatedSuccessfully));
            Singleton.getInstance().setFileDeleted(true);
        }
    }

    public String LoadData(String str) {
        try {
            InputStream open = getAssets().open(str);
            byte[] bArr = new byte[open.available()];
            open.read(bArr);
            open.close();
            return new String(bArr);
        } catch (IOException unused) {
            return "";
        }
    }


    private void configHeader() {
        binding.header.headerTitleText.setTextAppearance(this, R.style.PageTitleBold);
        binding.header.headerTitleText.setFont(this, 1);
        binding.appToolbar.setToolbarTitle("Preview");
        binding.header.headerTitleText.setText("Preview");
        this.toolbarBuilder = new CustomFrameLayout.Builder(binding.appToolbar, this);
        this.toolbarBuilder.setBackArrow(R.drawable.back_arrow, getResources().getColor(R.color.blue_start), getResources().getColor(R.color.blue_end), view -> this.onBackPressed());


    }

}