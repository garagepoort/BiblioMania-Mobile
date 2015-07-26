package com.bendani.bibliomania.books.infrastructure;

import com.bendani.bibliomania.books.domain.Book;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

public interface BooksResource {

    @GET("/books")
    public Observable<List<Book>> getBooks(@Query("token") String token);
}
