package com.bendani.bibliomania.books.domain;

import com.bendani.bibliomania.books.infrastructure.BooksRepository;
import com.bendani.bibliomania.books.infrastructure.BooksResource;
import com.bendani.bibliomania.generic.exception.NoInternetConnectionError;
import com.bendani.bibliomania.generic.infrastructure.BeanProvider;
import com.bendani.bibliomania.generic.infrastructure.ConnectionService;
import com.bendani.bibliomania.generic.infrastructure.RXJavaExtension.JustOnCompleteOrOnError;
import com.bendani.bibliomania.generic.infrastructure.RXJavaExtension.JustOnError;
import com.bendani.bibliomania.image.domain.ImageService;
import com.bendani.bibliomania.login.domain.LoginService;
import com.bendani.bibliomania.login.domain.User;
import com.bendani.bibliomania.login.domain.UserRepository;
import com.bendani.bibliomania.login.exception.UserNotLoggedInException;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

import static java.lang.Thread.sleep;

public class BookService {

    private BooksResource booksResource;
    private UserRepository userRepository;
    private ConnectionService connectionService;
    private BooksRepository booksRepository;
    private LoginService loginService;
    private ImageService imageService;

    public BookService(BooksResource booksResource, UserRepository userRepository, ConnectionService connectionService, BooksRepository booksRepository, LoginService loginService, ImageService imageService) {
        this.booksResource = booksResource;
        this.userRepository = userRepository;
        this.connectionService = connectionService;
        this.booksRepository = booksRepository;
        this.loginService = loginService;
        this.imageService = imageService;
    }

    public Observable<Void> downloadBooks() {
        if (!userRepository.isUserLoggedIn()) {
            return Observable.error(new UserNotLoggedInException());
        }
        if (!connectionService.isDeviceConnectedToInternet()) {
            return Observable.error(new NoInternetConnectionError());
        }
        final User user = userRepository.retrieveUser();
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(final Subscriber<? super Void> subscriber) {
                loginService.login(user.getUsername(), user.getPassword())
                        .flatMap(new Func1<Void, Observable<List<Book>>>() {
                            @Override
                            public Observable<List<Book>> call(Void aVoid) {
                                return booksResource.getBooks(userRepository.retrieveUser().getToken());
                            }
                        })
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

    public Observable<Void> addReadingDate(final ReadingDate readingDate) {
        if (!userRepository.isUserLoggedIn()) {
            return Observable.error(new UserNotLoggedInException());
        }
        if (!connectionService.isDeviceConnectedToInternet()) {
            return Observable.error(new NoInternetConnectionError());
        }
        final User user = userRepository.retrieveUser();
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(final Subscriber<? super Void> subscriber) {
                loginService.login(user.getUsername(), user.getPassword())
                        .flatMap(new Func1<Void, Observable<Void>>() {
                            @Override
                            public Observable<Void> call(Void aVoid) {
                                return booksResource.createReadingDate(userRepository.retrieveUser().getToken(), readingDate);
                            }
                        })
                        .subscribe(new JustOnCompleteOrOnError<Void>() {
                            @Override
                            public void onCompleted() {
                                subscriber.onCompleted();
                            }

                            @Override
                            public void onError(Throwable e) {
                                subscriber.onError(e);
                            }

                        });
            }
        });

    }

    private void getBookImages(List<Book> books, final Subscriber<? super Void> subscriber) {
        Observable.from(books).flatMap(new Func1<Book, Observable<?>>() {
            @Override
            public Observable<Void> call(Book book) {
                try {
                    sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return imageService.getBookImage(book);
            }
        }).subscribe(new JustOnError<Object>() {
            @Override
            public void onError(Throwable e) {
                subscriber.onError(e);
            }
        });
    }

    private void getAuthorImages(List<Book> books, final Subscriber<? super Void> subscriber) {
        Observable.from(books).flatMap(new Func1<Book, Observable<?>>() {
            @Override
            public Observable<Void> call(Book book) {
                try {
                    sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return imageService.getAuthorImage(book.getPreferredAuthor());
            }
        }).subscribe(new JustOnError<Object>() {
            @Override
            public void onError(Throwable e) {
                subscriber.onError(e);
            }
        });
    }

    public boolean areBooksInitialized() {
        return booksRepository.hasBooksList();
    }

    public List<Book> getBooks() {
        if(areBooksInitialized()){
            return booksRepository.retrieve().getBooks();
        }
        return new ArrayList<>();
    }
}
