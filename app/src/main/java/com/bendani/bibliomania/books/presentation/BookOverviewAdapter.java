package com.bendani.bibliomania.books.presentation;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bendani.bibliomania.R;
import com.bendani.bibliomania.books.domain.Book;

import java.util.ArrayList;
import java.util.List;

public class BookOverviewAdapter extends RecyclerView.Adapter {

    private List<Book> books;
    private List<Book> filteredBooks;
    private String constraint;

    public List<Book> getItems() {
        return filteredBooks;
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        public Book book;
        public TextView title;
        public TextView author;
        public TextView publisher;

        public BookViewHolder(View v) {
            super(v);
        }
    }

    public BookOverviewAdapter(List<Book> books) {
        this.books = books;
        this.filteredBooks = books;
    }

    public void flushFilter() {
        filteredBooks = new ArrayList<>();
        filteredBooks.addAll(books);
        notifyDataSetChanged();
    }

    public void setFilter(String queryText) {

        filteredBooks = new ArrayList<>();
        constraint = queryText.toString().toLowerCase();
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(queryText) ||
                    book.getSubtitle().toLowerCase().contains(queryText) ||
                    book.getPreferredAuthor().getFullName().toLowerCase().contains(queryText)) {
                filteredBooks.add(book);
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_overview_listitem, parent, false);
        BookViewHolder bookViewHolder = new BookViewHolder(v);
        bookViewHolder.title = (TextView) v.findViewById(R.id.title_textview);
        bookViewHolder.author = (TextView) v.findViewById(R.id.author_textview);
        bookViewHolder.publisher = (TextView) v.findViewById(R.id.publisher_textview);
        return bookViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final BookViewHolder bookViewHolder = (BookViewHolder) holder;
        Book book = filteredBooks.get(position);
        bookViewHolder.book = book;
        bookViewHolder.title.setText(book.getTitle());
        bookViewHolder.author.setText(book.getPreferredAuthor().getFullName());
        bookViewHolder.publisher.setText(book.getPublisher().getName());
    }

    @Override
    public int getItemCount() {
        return filteredBooks.size();
    }
}
