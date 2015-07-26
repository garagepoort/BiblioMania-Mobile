package com.bendani.bibliomania.generic.infrastructure.RXJavaExtension;

import rx.Observer;

public abstract class JustOnCompleted<T> implements Observer<T> {

    @Override
    public abstract void onCompleted();

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onNext(T t) {

    }
}