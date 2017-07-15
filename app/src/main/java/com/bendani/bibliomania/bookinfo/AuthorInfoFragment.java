package com.bendani.bibliomania.bookinfo;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bendani.bibliomania.R;
import com.bendani.bibliomania.books.domain.Author;
import com.bendani.bibliomania.generic.infrastructure.BeanProvider;
import com.bendani.bibliomania.generic.presentation.customview.FloatingLabelTextview;

import rx.Subscriber;

import static rx.android.schedulers.AndroidSchedulers.mainThread;

public class AuthorInfoFragment extends Fragment {

    private Author author;

    private FloatingLabelTextview nameTextView;
    private FloatingLabelTextview dateOfBirthTextView;
    private FloatingLabelTextview dateOfDeathTextView;
    private ImageView authorImageView;

    public AuthorInfoFragment() {
        // Required empty public constructor
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_author_info, container, false);
        nameTextView = (FloatingLabelTextview) view.findViewById(R.id.author_info_name_textview);
        dateOfBirthTextView = (FloatingLabelTextview) view.findViewById(R.id.author_info_date_of_birth_textview);
        dateOfDeathTextView = (FloatingLabelTextview) view.findViewById(R.id.author_info_date_of_death_textview);
        authorImageView = (ImageView) view.findViewById(R.id.author_imageview);
        fillInElements();
        return view;
    }

    public void onResume() {
        super.onResume();
        fillInElements();
    }

    private void fillInElements() {
        nameTextView.setText(author.getFullName());
        if (author.getDateOfBirth() != null) {
            dateOfBirthTextView.setText(author.getDateOfBirth().getFullDate());
        }
        if (author.getDateOfDeath() != null) {
            dateOfDeathTextView.setText(author.getDateOfDeath().getFullDate());
        }

        Bitmap image = BeanProvider.imageService().getAuthorImageFromStorage(author);
        if (image != null) {
            authorImageView.setImageBitmap(image);
        } else if (BeanProvider.connectionService().isDeviceConnectedToInternet()) {
            retrieveImage();
        }
    }

    private void retrieveImage() {
        BeanProvider.imageService().getAuthorImage(author)
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
                        Bitmap image = BeanProvider.imageService().getAuthorImageFromStorage(author);
                        if (image != null) {
                            authorImageView.setImageBitmap(image);
                        }
                    }
                });
    }
}
