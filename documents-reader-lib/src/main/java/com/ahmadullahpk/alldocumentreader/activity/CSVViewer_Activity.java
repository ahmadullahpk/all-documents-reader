package com.ahmadullahpk.alldocumentreader.activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatDelegate;

import com.ahmadullahpk.alldocumentreader.adapters_All.TableEventListener;
import com.ahmadullahpk.alldocumentreader.adapters_All.TablePreviewwAdp;
import com.ahmadullahpk.alldocumentreader.R;
import com.ahmadullahpk.alldocumentreader.dataType.Cell;
import com.ahmadullahpk.alldocumentreader.dataType.ColumnHeader;
import com.ahmadullahpk.alldocumentreader.dataType.RowHeader;
import com.ahmadullahpk.alldocumentreader.databinding.ActivityCsvViewerBinding;
import com.ahmadullahpk.alldocumentreader.manageui.CustomFrameLayout;
import com.ahmadullahpk.alldocumentreader.util.CSVReader;
import com.ahmadullahpk.alldocumentreader.util.Utility;
import com.ahmadullahpk.alldocumentreader.widgets.tableview.TableView;
import com.ahmadullahpk.alldocumentreader.widgets.tableview.filter.Filter;
import com.ahmadullahpk.alldocumentreader.widgets.tableview.pagination.Pagination;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class CSVViewer_Activity extends BaseActivity {

    ActivityCsvViewerBinding binding;
    private CustomFrameLayout.Builder toolbarBuilder;
    List<List<Cell>> cellDataList = new ArrayList<>();
    List<ColumnHeader> columnHeaderList = new ArrayList<>();
    ArrayList<List<String>> csv_data = new ArrayList<>();
    Boolean fromConverterApp = false;
    private Pagination mPagination;
    private final boolean mPaginationEnabled = false;
    private Filter mTableFilter;
    private TableView mTableView;
    List<RowHeader> rowHeaderList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } catch (Exception e) {
            e.printStackTrace();
        }

        binding = ActivityCsvViewerBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.headerTitleText.setTextAppearance(this, R.style.PageTitleBold);

        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });




        this.mTableView = findViewById(R.id.tableview);
        if (getIntent() != null) {
            String stringExtra = getIntent().getStringExtra("path");
            String stringExtra2 = getIntent().getStringExtra("name");
            this.fromConverterApp = getIntent().getBooleanExtra("fromConverterApp", false);
            Integer.parseInt(getIntent().getStringExtra("fileType"));
            //  changeBackGroundColor(100);
            //   textView.setText(stringExtra2);
            binding.headerTitleText.setMaxLines(1);
            binding.headerTitleText.setText(stringExtra2);
            new LoadCVSDataFromPath(stringExtra).execute();
        }
        this.mTableFilter = new Filter(this.mTableView);
        if (this.mPaginationEnabled) {
            this.mPagination = new Pagination(this.mTableView);
        }
    }


    public void loadCVSDate(String str) {
        try {
            List read = new CSVReader(this).read(new FileInputStream(str));
            if (read.size() > 0) {
                for (int i = 0; i < read.size(); i++) {
                    String[] strArr = (String[]) read.get(i);
                    ArrayList<Cell> arrayList = new ArrayList<>();
                    ArrayList<String> arrayList2 = new ArrayList<>();
                    if (strArr.length > 1) {
                        String str2 = "";
                        for (int i2 = 0; i2 < strArr.length; i2++) {
                            if (i == 0) {
                                this.columnHeaderList.add(new ColumnHeader(String.valueOf(i), strArr[i2]));
                            } else {
                                arrayList.add(new Cell(i2 + "-" + i, strArr[i2]));
                            }
                            arrayList2.add(strArr[i2] + "");
                            str2 = str2 + " -  " + strArr[i2];
                        }
                        this.rowHeaderList.add(new RowHeader(String.valueOf(i), String.valueOf(i)));
                    }
                    this.cellDataList.add(arrayList);
                    this.csv_data.add(arrayList2);
                }
                Utility.logCatMsg("size " + read.size());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void filterTable(String str) {
        this.mTableFilter.set(str);
    }

    public void filterTableForMood(String str) {
        this.mTableFilter.set(2, str);
    }

    public void filterTableForGender(String str) {
        this.mTableFilter.set(4, str);
    }

    public void nextTablePage() {
        this.mPagination.nextPage();
    }

    public void previousTablePage() {
        this.mPagination.previousPage();
    }

    public void goToTablePage(int i) {
        this.mPagination.goToPage(i);
    }

    public void setTableItemsPerPage(int i) {
        this.mPagination.setItemsPerPage(i);
    }



    class LoadCVSDataFromPath extends AsyncTask<Void, Void, Void> {
        String file_path;
        ProgressDialog progressDialog;

        public LoadCVSDataFromPath(String str) {
            this.file_path = str;
        }

        @Override
        protected void onPreExecute() {
            ProgressDialog progressDialog2 = new ProgressDialog(CSVViewer_Activity.this);
            this.progressDialog = progressDialog2;
            progressDialog2.setMessage(CSVViewer_Activity.this.getResources().getString(R.string.loadingFiles));
            this.progressDialog.setProgressStyle(1);
            this.progressDialog.setMax(100);
            this.progressDialog.setProgress(0);
            this.progressDialog.setCancelable(false);
            this.progressDialog.setButton(-1, CSVViewer_Activity.this.getResources().getString(R.string.cancel), (dialogInterface, i) -> {
                dialogInterface.dismiss();
                LoadCVSDataFromPath.this.cancel(true);
            });
            this.progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voidArr) {
            CSVViewer_Activity.this.loadCVSDate(this.file_path);
            return null;
        }

        @Override
        protected void onPostExecute(Void voidR) {
            super.onPostExecute(voidR);
            ProgressDialog progressDialog2 = this.progressDialog;
            if (progressDialog2 != null && progressDialog2.isShowing()) {
                this.progressDialog.dismiss();
            }
            TablePreviewwAdp tablePreviewwAdp = new TablePreviewwAdp();
            CSVViewer_Activity.this.mTableView.setAdapter(tablePreviewwAdp);
            CSVViewer_Activity.this.mTableView.setTableViewListener(new TableEventListener(CSVViewer_Activity.this.mTableView));
            tablePreviewwAdp.setAllItems(CSVViewer_Activity.this.columnHeaderList, CSVViewer_Activity.this.rowHeaderList, CSVViewer_Activity.this.cellDataList);
        }
    }





}