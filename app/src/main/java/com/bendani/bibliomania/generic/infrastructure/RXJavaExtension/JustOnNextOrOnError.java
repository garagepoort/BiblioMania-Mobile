package com.bendani.bibliomania.generic.infrastructure.RXJavaExtension;

import rx.Observer;

public abstract class JustOnNextOrOnError<T> implements Observer<T> {
    @Override
    public void onCompleted() {

    }

    @Override
    public abstract void onNext(T t);

    @Override
    public abstract void onError(Throwable e);
}
