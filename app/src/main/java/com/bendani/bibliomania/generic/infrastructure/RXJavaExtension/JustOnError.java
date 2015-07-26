package com.bendani.bibliomania.generic.infrastructure.RXJavaExtension;

import rx.Observer;

public abstract class JustOnError<T> implements Observer<T> {

    @Override
    public void onCompleted() {

    }

    @Override
    public abstract void onError(Throwable e);

    @Override
    public void onNext(T t) {

    }
}
