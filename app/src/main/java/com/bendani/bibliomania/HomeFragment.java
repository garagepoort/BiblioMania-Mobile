package com.bendani.bibliomania;


import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bendani.bibliomania.books.domain.BookService;
import com.bendani.bibliomania.generic.exception.NoInternetConnectionError;
import com.bendani.bibliomania.generic.infrastructure.RXJavaExtension.JustOnCompleteOrOnError;

import static com.bendani.bibliomania.generic.infrastructure.BeanProvider.bookService;
import static com.bendani.bibliomania.generic.infrastructure.BeanProvider.connectionService;
import static com.bendani.bibliomania.generic.infrastructure.BeanProvider.errorParser;
import static rx.android.schedulers.AndroidSchedulers.mainThread;

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
            downloadBooks();
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

    private void downloadBooks() {
        final ProgressDialog progress = new ProgressDialog(getActivity());
        progress.setTitle(getString(R.string.downloading));
        progress.setCancelable(false);
        progress.show();

        bookService.downloadBooks()
                .observeOn(mainThread())
                .subscribe(new JustOnCompleteOrOnError<Void>() {
                    @Override
                    public void onCompleted() {
                        progress.dismiss();
                        Toast.makeText(getActivity(), getString(R.string.books_downloaded), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        progress.dismiss();
                        errorParser().createErrorDialogFromError(getActivity(), e).show();
                    }
                });
    }


    public View.OnClickListener getDownloadBooksButtonOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!connectionService().isDeviceConnectedToInternet()){
                    errorParser().createErrorDialogFromError(getActivity(), new NoInternetConnectionError()).show();
                }else{
                    downloadBooks();
                }
            }
        };
    }
}
