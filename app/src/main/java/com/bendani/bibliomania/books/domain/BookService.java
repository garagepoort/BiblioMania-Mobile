package com.bendani.bibliomania.books.domain;

import com.bendani.bibliomania.books.infrastructure.BooksRepository;
import com.bendani.bibliomania.books.infrastructure.BooksResource;
import com.bendani.bibliomania.generic.exception.NoInternetConnectionError;
import com.bendani.bibliomania.generic.infrastructure.ConnectionService;
import com.bendani.bibliomania.login.domain.UserRepository;
import com.bendani.bibliomania.login.exception.UserNotLoggedInException;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class BookService {

    private BooksResource booksResource;
    private UserRepository userRepository;
    private ConnectionService connectionService;
    private BooksRepository booksRepository;

    public BookService(BooksResource booksResource, UserRepository userRepository, ConnectionService connectionService, BooksRepository booksRepository) {
        this.booksResource = booksResource;
        this.userRepository = userRepository;
        this.connectionService = connectionService;
        this.booksRepository = booksRepository;
    }

    public Observable<Void> downloadBooks(){
        if(!userRepository.isUserLoggedIn()){
            return Observable.error(new UserNotLoggedInException());
        }
        if(!connectionService.isDeviceConnectedToInternet()){
            return Observable.error(new NoInternetConnectionError());
        }
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(final Subscriber<? super Void> subscriber) {
                booksResource.getBooks(userRepository.retrieveUser().getToken())
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Subscriber<List<Book>>() {
                            @Override
                            public void onCompleted() {
                                subscriber.onCompleted();
                            }

                            @Override
                            public void onError(Throwable e) {
                                subscriber.onError(e);
                            }

                            @Override
                            public void onNext(List<Book> books) {
                                booksRepository.store(new BookList(books));
                            }
                        });

            }
        });
    }

    public boolean areBooksInitialized() {
        return booksRepository.hasBooksList();
    }

    public List<Book> getBooks() {
        return booksRepository.retrieve().getBooks();
    }
}
