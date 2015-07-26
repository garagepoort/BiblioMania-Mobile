package com.bendani.bibliomania.books.infrastructure;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.bendani.bibliomania.books.domain.BookList;
import com.google.gson.Gson;

public class BooksRepository {

    private static final String BOOKS_LIST = "BOOKS_LIST";
    private static final String TAG = "BookRepository";
    private BookList bookList;
    private Context context;

    public BooksRepository(Context context) {
        this.context = context;
    }

    public void store(final BookList bookList) {
        this.bookList = bookList;
        Gson gson = new Gson();
        final String json = gson.toJson(bookList);
        storeToSharedPreferences(BOOKS_LIST, json);
    }

    public BookList retrieve() {
        if (bookList == null) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            Gson gson = new Gson();
            String json = preferences.getString(BOOKS_LIST, "");
            if (!json.equals("")) {
                bookList = gson.fromJson(json, BookList.class);
            }
        }
        return bookList;
    }

    public void removeBooksList(){
        storeToSharedPreferences(BOOKS_LIST, "");
    }

    public boolean hasBooksList(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return !preferences.getString(BOOKS_LIST, "").isEmpty();
    }

    private void storeToSharedPreferences(String key, String json) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = preferences.edit();
        prefsEditor.putString(key, json);
        prefsEditor.commit();
    }
}
