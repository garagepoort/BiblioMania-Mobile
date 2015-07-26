package com.bendani.bibliomania.generic.infrastructure.RXJavaExtension;

import rx.Observer;

public abstract class JustOnNext<T> implements Observer<T> {
    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public abstract void onNext(T t);
}
