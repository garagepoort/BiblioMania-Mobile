package com.bendani.bibliomania.image.domain;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.bendani.bibliomania.books.domain.Book;
import com.bendani.bibliomania.books.infrastructure.BooksRepository;
import com.bendani.bibliomania.generic.infrastructure.RXJavaExtension.JustOnCompleted;
import com.bendani.bibliomania.image.infrastructure.GetImageResource;
import com.bendani.bibliomania.login.domain.UserRepository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import retrofit.client.Response;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class ImageService {

    private Context context;
    private GetImageResource getImageResource;
    private UserRepository userRepository;

    public ImageService(Context context, GetImageResource getImageResource, UserRepository userRepository) {
        this.context = context;
        this.getImageResource = getImageResource;
        this.userRepository = userRepository;
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

    public void deleteAllImages(List<Book> books) {
        for (Book book : books) {
            File dir = context.getFilesDir();
            File file = new File(dir, book.getCoverImage());
            if(file != null){
                boolean deleted = file.delete();
            }
        }
    }

    public Observable<Void> getBookImage(final Book book) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(final Subscriber<? super Void> subscriber) {
                getImageResource.getBookImage(book.getId(), userRepository.retrieveUser().getToken()).subscribe(new Subscriber<Response>() {
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
        if(!filename.isEmpty()){
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
}
