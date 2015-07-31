package com.bendani.bibliomania.generic.infrastructure;

import android.content.Context;

import com.bendani.bibliomania.books.domain.BookService;
import com.bendani.bibliomania.books.infrastructure.BooksRepository;
import com.bendani.bibliomania.books.infrastructure.BooksResource;
import com.bendani.bibliomania.generic.presentation.error.ErrorParser;
import com.bendani.bibliomania.image.domain.ImageService;
import com.bendani.bibliomania.image.infrastructure.GetImageResource;
import com.bendani.bibliomania.login.domain.LoginResource;
import com.bendani.bibliomania.login.domain.LoginService;
import com.bendani.bibliomania.login.domain.UserRepository;

import retrofit.RestAdapter;

public class BeanProvider {

    private static Context context;
    private static LoginService loginService;
    private static UserRepository userRepository;
    private static ErrorParser errorParser;
    private static BookService bookService;
    private static ConnectionService connectionService;
    private static BooksRepository booksRepository;
    private static ImageService imageService;

    public BeanProvider(Context context) {
        this.context = context;
    }

    public static LoginService loginService(){
        if(loginService == null){
            loginService = new LoginService(loginResource(), userRepository());
        }
        return loginService;
    }

    public static ConnectionService connectionService(){
        if(connectionService == null){
            connectionService = new ConnectionService(context);
        }
        return connectionService;
    }

    public static ImageService imageService(){
        if(imageService == null){
            imageService = new ImageService(context, getImageResource());
        }
        return imageService;
    }

    public static BookService bookService(){
        if(bookService == null){
            bookService = new BookService(booksResource(), userRepository(), connectionService(), booksRepository(), loginService(), imageService());
        }
        return bookService;
    }

    public static UserRepository userRepository() {
        if(userRepository == null){
            userRepository = new UserRepository(context);
        }
        return userRepository;
    }

    public static ErrorParser errorParser() {
        if(errorParser == null){
            errorParser = new ErrorParser();
        }
        return errorParser;
    }

    public static BooksRepository booksRepository() {
        if(booksRepository == null){
            booksRepository = new BooksRepository(context);
        }
        return booksRepository;
    }

    public static LoginResource loginResource(){
        return getRestAdapter()
                .create(LoginResource.class);
    }

    public static BooksResource booksResource(){
        return getRestAdapter()
                .create(BooksResource.class);
    }

    public static GetImageResource getImageResource(){
        return getRestAdapter()
                .create(GetImageResource.class);
    }

    private static RestAdapter getRestAdapter(){
        return new RestAdapter.Builder()
                .setEndpoint("http://bendani-cooperation.com/BiblioMania/api")
                .setLogLevel(RestAdapter.LogLevel.FULL).build();
    }
}
