package com.bendani.bibliomania.bookinfo;


import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.bendani.bibliomania.R;
import com.bendani.bibliomania.books.domain.Book;
import com.bendani.bibliomania.generic.infrastructure.BeanProvider;
import com.bendani.bibliomania.generic.infrastructure.ConnectionService;
import com.bendani.bibliomania.generic.presentation.customview.FloatingLabelTextview;
import com.bendani.bibliomania.image.domain.ImageService;

import rx.Subscriber;

import static rx.android.schedulers.AndroidSchedulers.mainThread;


public class GeneralBookInfoFragment extends Fragment {


    private Book book;

    private FloatingLabelTextview titleTextView;
    private FloatingLabelTextview subtitleTextView;
    private FloatingLabelTextview authorTextView;
    private FloatingLabelTextview isbnTextView;
    private FloatingLabelTextview publisherTextView;
    private ImageView coverImageView;
    private ImageView readImageView;

    public GeneralBookInfoFragment() {
        // Required empty public constructor
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_general_book_info, container, false);
        titleTextView = (FloatingLabelTextview) view.findViewById(R.id.book_info_title_textview);
        subtitleTextView = (FloatingLabelTextview) view.findViewById(R.id.book_info_subtitle_textview);
        authorTextView = (FloatingLabelTextview) view.findViewById(R.id.book_info_author_textview);
        isbnTextView = (FloatingLabelTextview) view.findViewById(R.id.book_info_isbn_textview);
        publisherTextView = (FloatingLabelTextview) view.findViewById(R.id.book_info_publisher_textview);
        coverImageView = (ImageView) view.findViewById(R.id.cover_imageview);
        readImageView = (ImageView) view.findViewById(R.id.read_imageview);
        fillInElements();
        return view;
    }

    public void onResume() {
        super.onResume();
        fillInElements();
    }

    private void fillInElements() {
        titleTextView.setText(book.getTitle());
        subtitleTextView.setText(book.getSubtitle());
        authorTextView.setText(book.getPreferredAuthor().getFullName());
        publisherTextView.setText(book.getPublisher());
        isbnTextView.setText(book.getISBN());
        isbnTextView.clearAnimation();
        isbnTextView.setVisibility(View.GONE);
        Bitmap image = BeanProvider.imageService().getBookImageFromStorage(book);
        if (image != null) {
            coverImageView.setImageBitmap(image);
        } else if (BeanProvider.connectionService().isDeviceConnectedToInternet()) {
            retrieveImage();
        }

        if(book.getPersonalBookInfo().getReadingDates().isEmpty()){
            readImageView.setVisibility(View.GONE);
        }
    }

    private void retrieveImage() {
        BeanProvider.imageService().getBookImage(book)
                .observeOn(mainThread())
                .subscribe(new Subscriber<Void>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(Void aVoid) {
                        Bitmap image = BeanProvider.imageService().getBookImageFromStorage(book);
                        if (image != null) {
                            coverImageView.setImageBitmap(image);
                        }
                    }
                });
    }

    public void updateRead() {
        if(book.getPersonalBookInfo().getReadingDates().isEmpty()) {
            readImageView.setVisibility(View.GONE);
        } else {
            readImageView.setVisibility(View.VISIBLE);
        }
    }
}
