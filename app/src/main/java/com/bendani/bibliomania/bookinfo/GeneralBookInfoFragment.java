package com.bendani.bibliomania.bookinfo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bendani.bibliomania.R;
import com.bendani.bibliomania.books.domain.Book;


public class GeneralBookInfoFragment extends Fragment {


    private Book book;

    private TextView titleTextView;

    public GeneralBookInfoFragment() {
        // Required empty public constructor
    }

    public void setBook(Book book){
        this.book = book;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_general_book_info, container, false);
        titleTextView = (TextView) view.findViewById(R.id.book_info_title_textview);
        fillInElements();
        return view;
    }

    private void fillInElements(){
        titleTextView.setText(book.getTitle());
    }
}
