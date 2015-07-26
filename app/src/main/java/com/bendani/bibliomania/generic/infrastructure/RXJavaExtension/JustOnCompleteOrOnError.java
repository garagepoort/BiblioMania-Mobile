package com.bendani.bibliomania.generic.infrastructure.RXJavaExtension;

import rx.Observer;

public abstract class JustOnCompleteOrOnError<T> implements Observer<T> {
    @Override
    public abstract void onCompleted();

    @Override
    public void onNext(T t) {

    }

    @Override
    public abstract void onError(Throwable e);
}
