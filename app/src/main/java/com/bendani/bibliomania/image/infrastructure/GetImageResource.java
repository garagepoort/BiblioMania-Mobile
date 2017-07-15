package com.bendani.bibliomania.image.infrastructure;

import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

public interface GetImageResource {

    @GET("/image/book/{id}")
    public Observable<Response> getBookImage(@Path("id") int bookid, @Query("token") String token);
    @GET("/image/author/{id}")
    public Observable<Response> getAuthorImage(@Path("id") int authorId, @Query("token") String token);
}
