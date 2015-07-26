package com.bendani.bibliomania.login.domain;

import com.bendani.bibliomania.generic.infrastructure.RXJavaExtension.JustOnCompleteOrOnError;

import rx.Observable;
import rx.Subscriber;

public class LoginService {

    private LoginResource loginResource;
    private UserRepository userRepository;

    public LoginService(LoginResource loginResource, UserRepository userRepository) {
        this.loginResource = loginResource;
        this.userRepository = userRepository;
    }

    public Observable<Void> login(final String username, final String password){
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(final Subscriber<? super Void> subscriber) {
                loginResource.login(username, password)
                        .subscribe(new Subscriber<LoginAnswer>() {
                            @Override
                            public void onCompleted() {
                                subscriber.onCompleted();
                            }

                            @Override
                            public void onError(Throwable e) {
                                subscriber.onError(e);
                            }

                            @Override
                            public void onNext(LoginAnswer loginAnswer) {
                                User user = new User(username, password, loginAnswer.getToken());
                                userRepository.saveUser(user);
                            }
                        });
            }
        });
    }
}

