package com.ahmadullah.alldocumentsreader;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;



import com.ahmadullahpk.alldocumentreader.activity.All_Document_Reader_Activity;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class Adapter_Doc_Files extends RecyclerView.Adapter<Adapter_Doc_Files.ViewHolder> {
    ArrayList<model_doc_File> mylist;
    Context context;
    String file_path;


    public Adapter_Doc_Files(Context context , ArrayList<model_doc_File> mylist) {
        this.mylist = mylist;
        this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(context).inflate(R.layout.row_files, parent, false);
        return new ViewHolder(row);

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {


        file_path = mylist.get(position).getPath();
        String file_name = file_path.substring(file_path.lastIndexOf("/") + 1);
        holder.file_name.setText(file_name);

        File file = new File(file_path);
        long file_size = Integer.parseInt(String.valueOf(file.length() / 1024));
        holder.file_size.setText(fileSize(file_size));

        String dateFormat = new SimpleDateFormat("dd-MMM-yyyy").format(
                new Date(new File(String.valueOf(file)).lastModified()));
        holder.txt_date.setText(dateFormat);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readFile( mylist.get(position).getPath());
            }
        });

        holder.optionMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Open_menu(holder, position);
            }
        });

    }

    private void Open_menu(ViewHolder holder, int posi) {
        PopupMenu popupMenu = new PopupMenu(context, holder.optionMenuBtn);
        popupMenu.inflate(R.menu.menu_item);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if(R.id.open == id ){
                    readFile(mylist.get(posi).getPath());
                }
                else if(R.id.rename == id ){
                    dialog_name_update(mylist.get(posi).getPath(), holder);
                }
                else if(R.id.delete == id ){
                    dialog_delete(mylist.get(posi).getPath(), posi, holder);
                }
                else if(R.id.share == id ){
                    share_pdf(mylist.get(posi).getPath());
                }

                return false;
            }
        });
        popupMenu.show();
    }


    public static String fileSize(long size) {
        if (size <= 0) return "0";
        final String[] units = new String[]{"B", "kB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#.00").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

    @Override
    public int getItemCount() {
        return mylist.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView file_name, file_size, txt_date;
        ImageView img_icon, optionMenuBtn;
        LinearLayout ll_root;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_date = itemView.findViewById(R.id.txt_date);
            file_name = itemView.findViewById(R.id.txt_name);
            file_size = itemView.findViewById(R.id.txt_size);
            img_icon = itemView.findViewById(R.id.img_icon);
            optionMenuBtn = itemView.findViewById(R.id.optionMenuBtn);
            ll_root = itemView.findViewById(R.id.ll_root);
        }
    }



    private void dialog_delete(String path, int posi, ViewHolder holder) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_deleting);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);

        TextView txt_delete = dialog.findViewById(R.id.txt_delete);
        TextView txt_cancel = dialog.findViewById(R.id.txt_cancel);
        txt_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new File(path).delete();
                Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                mylist.remove(posi);
                notifyDataSetChanged();
            }

        });
        txt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        if (dialog != null) {
            dialog.show();
        }
    }

    private void dialog_name_update(String path, ViewHolder holder) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_edite);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);

        TextView txt_update = dialog.findViewById(R.id.txt_update);
        TextView txt_cancel = dialog.findViewById(R.id.txt_cancel);
        EditText edt_name = dialog.findViewById(R.id.edt_name);

        String name = new File(path).getName();
        name = name.substring(0, name.lastIndexOf("."));
        edt_name.setText(name);
        txt_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edt_name.getText().toString().equals("")) {
                    edt_name.setError("Please enter name");
                    return;
                } else {
                    holder.file_name.setText(edt_name.getText().toString());
                    File oldfolder = new File(path);
                    String ext = new File(path).getName();
                    ext = ext.substring(ext.lastIndexOf("."));
                    String des = new File(path).getParent();
                    File newfile = new File(des + "/" + edt_name.getText().toString() + ext);
                    oldfolder.renameTo(newfile);
                    holder.file_name.setText(edt_name.getText().toString()+ext);
                    dialog.dismiss();
                }
            }

        });
        txt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        if (dialog != null) {
            dialog.show();
        }
    }



    private void share_pdf(String filename) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            Uri uri = Uri.parse(filename);
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.putExtra(Intent.EXTRA_STREAM, uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setType("application/pdf");
            context.startActivity(Intent.createChooser(intent, "Share with..."));
        } else {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("application/pdf");
            intent.putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(context, context.getPackageName() + ".provider", new File(filename)));
            context.startActivity(Intent.createChooser(intent, "Share with..."));
        }

    }




    private void readFile( String path) {
        Intent intent = new Intent(context, All_Document_Reader_Activity.class);
        intent.putExtra("path", path);
        intent.putExtra("fromAppActivity", true);
        context.startActivity(intent);
    }


}


