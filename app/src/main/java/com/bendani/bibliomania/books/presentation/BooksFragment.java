package com.bendani.bibliomania.books.presentation;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.bendani.bibliomania.MainActivity;
import com.bendani.bibliomania.R;

import static com.bendani.bibliomania.generic.infrastructure.BeanProvider.bookService;

/**
 * A simple {@link Fragment} subclass.
 */
public class BooksFragment extends Fragment {

    private RecyclerView recyclerView;
    private BookOverviewAdapter bookOverviewAdapter;
    private LinearLayoutManager linearLayoutManager;
    private EditText bookSearchEditText;
    private ImageButton clearText;

    public BooksFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_books, container, false);
        View actionbar = getActivity().findViewById(R.id.toolbar_actionbar_search_books);

        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.setSearchBooksToolbar();
        recyclerView = (RecyclerView) view.findViewById(R.id.book_recycler_view);

        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        bookOverviewAdapter = new BookOverviewAdapter(bookService().getBooks(), mainActivity);
        recyclerView.setAdapter(bookOverviewAdapter);

        bookSearchEditText = (EditText) actionbar.findViewById(R.id.search_books_edittext);
        bookSearchEditText.addTextChangedListener(getSearchEditTextTextWatcher());

        clearText = (ImageButton) actionbar.findViewById(R.id.clear_text);
        clearText.setOnClickListener(getClearTextClickListener());

        return view;
    }

    private TextWatcher getSearchEditTextTextWatcher() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                bookOverviewAdapter.setFilter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };
    }

    private View.OnClickListener getClearTextClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookSearchEditText.setText("");
            }
        };
    }


}
