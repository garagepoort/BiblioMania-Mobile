package com.bendani.bibliomania.generic.presentation;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class ConfirmationDialog {

    public static void create(Context context, String title, String message, DialogInterface.OnClickListener yesClickListener){
        new AlertDialog.Builder(context)
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton("Yes", yesClickListener)
        .setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }
}