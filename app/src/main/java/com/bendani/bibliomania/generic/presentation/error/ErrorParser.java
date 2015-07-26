package com.bendani.bibliomania.generic.presentation.error;

import android.content.Context;

import com.bendani.bibliomania.R;

public class ErrorParser {

    public ErrorParser() {
    }

    public ErrorDialog createErrorDialogFromError(Context context, Throwable throwable){
        return new ErrorDialog(context, throwable.getMessage(), context.getString(R.string.generic_error));
    }
}
