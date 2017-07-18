package com.bendani.bibliomania.generic.presentation;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RatingBar;
import android.widget.Toast;

import com.bendani.bibliomania.R;
import com.bendani.bibliomania.books.domain.Book;
import com.bendani.bibliomania.books.domain.Date;
import com.bendani.bibliomania.books.domain.ReadingDate;
import com.bendani.bibliomania.generic.infrastructure.BeanProvider;
import com.bendani.bibliomania.generic.infrastructure.RXJavaExtension.JustOnCompleteOrOnError;
import com.bendani.bibliomania.generic.infrastructure.RXJavaExtension.JustOnCompleted;
import com.bendani.bibliomania.generic.presentation.customview.FloatingLabelEditText;

import static com.bendani.bibliomania.generic.infrastructure.BeanProvider.errorParser;

public class ReadingDateDialog {

    public static void create(final Activity context, final Book book, final JustOnCompleted justOnCompleted) {

        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.reading_dialog, null);

        final RatingBar ratingBar = (RatingBar) promptsView.findViewById(R.id.reading_date_rating);
        final DatePicker datePicker = (DatePicker) promptsView.findViewById(R.id.reading_date_date_picker);
        final FloatingLabelEditText reviewTextField = (FloatingLabelEditText) promptsView.findViewById(R.id.reading_date_review_input);

        new AlertDialog.Builder(context)
                .setTitle("Leesdatum toevoegen")
                .setView(promptsView)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Date date = new Date(datePicker.getDayOfMonth(), datePicker.getMonth(), datePicker.getYear());
                        int rating = (int) (ratingBar.getRating()*2);
                        final ReadingDate readingDate = new ReadingDate(date, reviewTextField.getTextEditText(), rating, book.getPersonalBookInfo().getId());
                        BeanProvider.bookService().addReadingDate(readingDate).subscribe(new JustOnCompleteOrOnError<Void>() {
                            @Override
                            public void onCompleted() {
                                context.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        book.getPersonalBookInfo().getReadingDates().add(readingDate);
                                        Toast.makeText(context, "Leesdatum toegevoegd", Toast.LENGTH_LONG).show();
                                        justOnCompleted.onCompleted();
                                    }
                                });
                            }

                            @Override
                            public void onError(final Throwable e) {
                                context.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        errorParser().createErrorDialogFromError(context, e).show();
                                    }
                                });
                            }

                        });
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

}
