package com.bendani.bibliomania.bookinfo;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bendani.bibliomania.R;
import com.bendani.bibliomania.books.domain.Book;
import com.bendani.bibliomania.generic.infrastructure.BeanProvider;
import com.bendani.bibliomania.generic.presentation.customview.FloatingLabelTextview;


public class GeneralBookInfoFragment extends Fragment {


    private Book book;

    private FloatingLabelTextview titleTextView;
    private FloatingLabelTextview subtitleTextView;
    private FloatingLabelTextview authorTextView;
    private FloatingLabelTextview isbnTextView;
    private ImageView coverImageView;
    private RatingBar ratingBar;

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
        titleTextView = (FloatingLabelTextview) view.findViewById(R.id.book_info_title_textview);
        subtitleTextView = (FloatingLabelTextview) view.findViewById(R.id.book_info_subtitle_textview);
        authorTextView = (FloatingLabelTextview) view.findViewById(R.id.book_info_author_textview);
        isbnTextView = (FloatingLabelTextview) view.findViewById(R.id.book_info_isbn_textview);
        ratingBar = (RatingBar) view.findViewById(R.id.rating);
        coverImageView = (ImageView) view.findViewById(R.id.cover_imageview);
        fillInElements();
        return view;
    }

    public void onResume(){
        super.onResume();
        fillInElements();
    }

    private void fillInElements(){
        titleTextView.setText(book.getTitle());
        subtitleTextView.setText(book.getSubtitle());
        authorTextView.setText(book.getPreferredAuthor().getFullName());
        isbnTextView.setText(book.getISBN());
        ratingBar.setRating(book.getPersonalBookInfo().getRating());
        Bitmap image = BeanProvider.imageService().getImage(book);
        if(image != null){
            coverImageView.setImageBitmap(image);
        }
    }
}
