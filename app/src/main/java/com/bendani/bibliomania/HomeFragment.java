package com.bendani.bibliomania;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bendani.bibliomania.books.domain.BookService;

import static com.bendani.bibliomania.generic.infrastructure.BeanProvider.bookService;

public class HomeFragment extends Fragment {

    private BookService bookService;
    private LinearLayout booksButton;
    private LinearLayout downloadBooksButton;

    public HomeFragment() {
        bookService = bookService();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.setDefaultToolbar(getString(R.string.title));

        booksButton = (LinearLayout) view.findViewById(R.id.books_button);
        downloadBooksButton = (LinearLayout) view.findViewById(R.id.download_books_button);

        booksButton.setOnClickListener(getBooksButtonOnClickListener());
        downloadBooksButton.setOnClickListener(getDownloadBooksButtonOnClickListener());

        if (!bookService().areBooksInitialized()) {
            mainActivity.downloadBooksWithoutConfirmation();
        }

        return view;
    }

    private View.OnClickListener getBooksButtonOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.goToBooksOverview();
            }
        };
    }


    public View.OnClickListener getDownloadBooksButtonOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.downloadBooks();
            }
        };
    }
}
