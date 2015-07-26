package com.bendani.bibliomania.login.domain;

import retrofit.http.POST;
import retrofit.http.Query;
import rx.Observable;

public interface LoginResource {

    @POST("/authenticate")
    public Observable<LoginAnswer> login(@Query("username") String username,@Query("password") String password);
}
