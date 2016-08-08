package com.bendani.bibliomania.generic.presentation;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class ConfirmationDialog extends AlertDialog {

    public ConfirmationDialog(Context context, String title, String message, OnClickListener yesClickListener) {
        super(context);
        setTitle(title);
        setMessage(message);
        setButton(DialogInterface.BUTTON_POSITIVE, "Yes", yesClickListener);
        setButton(DialogInterface.BUTTON_NEGATIVE, "No", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
    }
}