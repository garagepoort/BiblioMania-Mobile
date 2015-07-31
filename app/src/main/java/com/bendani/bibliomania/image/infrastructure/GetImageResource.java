package com.bendani.bibliomania.image.infrastructure;

import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

public interface GetImageResource {

    @GET("/image/book/{id}")
    public Observable<Response> getBookImage(@Path("id") int bookid);
}
