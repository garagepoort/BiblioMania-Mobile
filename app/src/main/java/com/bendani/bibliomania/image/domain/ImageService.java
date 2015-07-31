package com.bendani.bibliomania.image.domain;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.bendani.bibliomania.books.domain.Book;
import com.bendani.bibliomania.image.infrastructure.GetImageResource;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import retrofit.client.Response;
import rx.Observable;
import rx.Subscriber;

public class ImageService {

    private Context context;
    private GetImageResource getImageResource;

    public ImageService(Context context, GetImageResource getImageResource) {
        this.context = context;
        this.getImageResource = getImageResource;
    }

    public Bitmap getImage(Book book){
        InputStream is = null;
        try {
            is = context.openFileInput(book.getCoverImage());
            return BitmapFactory.decodeStream(is);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Observable<Void> getBookImage(final Book book) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(final Subscriber<? super Void> subscriber) {
                getImageResource.getBookImage(book.getId()).subscribe(new Subscriber<Response>() {
                    @Override
                    public void onCompleted() {
                        subscriber.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        subscriber.onError(e);
                    }

                    @Override
                    public void onNext(Response response) {
                        try {
                            writeToInternalStorage(book.getCoverImage(), response.getBody().in());
                            subscriber.onNext(null);
                        } catch (IOException e) {
                            subscriber.onError(e);
                        }
                    }
                });
            }
        });
    }

    private void writeToInternalStorage(String filename,InputStream in) throws IOException {
        FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
        int read = 0;
        byte[] bytes = new byte[1024];

        while ((read = in.read(bytes)) != -1) {
            fos.write(bytes, 0, read);
        }
        fos.close();
        in.close();
    }
}
