package com.ahmadullahpk.alldocumentreader.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.view.View;
import android.widget.TextView;

import com.ahmadullahpk.alldocumentreader.R;
import com.ahmadullahpk.alldocumentreader.adapters_All.Adapter_Print_PdfDocument;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.util.FitPolicy;

import java.io.File;

public class PDF_Reader_Activity extends AppCompatActivity {

    PDFView pdfView;
    TextView btnPrint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_reader);

        pdfView = findViewById(R.id.pdfView);
        btnPrint = findViewById(R.id.btnPrint);

        String path = getIntent().getStringExtra("path");
        File filepdf = new File(path);

        btnPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                printFile(path);
            }
        });


        pdfView.fromFile(filepdf)
                //.pages(0) // all pages are displayed by default
                .enableSwipe(true) // allows to block changing pages using swipe
                .swipeHorizontal(true)
                .enableDoubletap(true)
              //  .defaultPage(0)

                .enableAnnotationRendering(false) // render annotations (such as comments, colors or forms)
                .password(null)
                .scrollHandle(null)
                .enableAntialiasing(true) // improve rendering a little bit on low-res screens
                // spacing between pages in dp. To define spacing color, set view background
                .spacing(0)
                .autoSpacing(false) // add dynamic spacing to fit each page on its own on the screen
                .pageFitPolicy(FitPolicy.WIDTH) // mode to fit pages in the view
                .fitEachPage(false) // fit each page to the view, else smaller pages are scaled relative to largest page.
                .pageSnap(false) // snap pages to screen boundaries
                .pageFling(true) // make a fling change only a single page like ViewPager
                .nightMode(false) // toggle night mode
                .load();


    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void printFile(String pdffilepath) {
        PrintManager printManager = (PrintManager) getSystemService(Context.PRINT_SERVICE);
        PrintDocumentAdapter printAdapter = null;
        try {
            printAdapter = new Adapter_Print_PdfDocument(PDF_Reader_Activity.this, pdffilepath);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        printManager.print("Document", printAdapter, new PrintAttributes.Builder().build());
    }
}