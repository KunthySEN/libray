package com.canadia.library.helper;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.canadia.library.R;
import com.canadia.library.models.BookModel;

public class Tool {

    public Dialog dialog;

    public void dialogForm(Activity activity, BookModel book_detail, final OnButtonClick onButtonClick) {
       dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.edit);
        EditText main_title = (EditText) dialog.findViewById(R.id.edit_title);
        main_title.setText(book_detail.getTitle());
        EditText description = (EditText) dialog.findViewById(R.id.edit_desc);
        description.setText(book_detail.getBody());
        final Button closeDialog = (Button) dialog.findViewById(R.id.btn_back);
        closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        final Button callDialog = (Button) dialog.findViewById(R.id.btn_edit);
        callDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                book_detail.setBody(description.getText().toString());
                book_detail.setTitle(main_title.getText().toString());
                onButtonClick.buttonClick(book_detail);
//                dialog.dismiss();
            }
        });

        dialog.show();

    }



}

