package com.bendani.bibliomania.generic.presentation.error;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

public class ErrorDialog extends AlertDialog{

    public ErrorDialog(Context context, String message, String title) {
        super(context);
        setTitle(title);
        setMessage(message);
        setButton(Dialog.BUTTON_POSITIVE, "Ok", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dismiss();
            }
        });
    }
}
