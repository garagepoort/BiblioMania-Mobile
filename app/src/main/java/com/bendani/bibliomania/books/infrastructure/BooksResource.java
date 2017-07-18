package com.bendani.bibliomania.books.infrastructure;

import com.bendani.bibliomania.books.domain.Book;
import com.bendani.bibliomania.books.domain.ReadingDate;

import java.util.List;

import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;
import rx.Observable;

public interface BooksResource {

    @GET("/books")
    public Observable<List<Book>> getBooks(@Query("token") String token);

    @POST("/reading-dates")
    public Observable<Void> createReadingDate(@Query("token") String token, @Body ReadingDate readingDate);
}
