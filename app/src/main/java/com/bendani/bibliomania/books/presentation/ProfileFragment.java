package com.bendani.bibliomania.books.presentation;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bendani.bibliomania.MainActivity;
import com.bendani.bibliomania.R;
import com.bendani.bibliomania.generic.infrastructure.BeanProvider;
import com.bendani.bibliomania.generic.presentation.customview.FloatingLabelTextview;

public class ProfileFragment extends Fragment {


    private FloatingLabelTextview usernameTextView;
    private FloatingLabelTextview emailTextView;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        usernameTextView = (FloatingLabelTextview) view.findViewById(R.id.profile_username_textview);
        emailTextView = (FloatingLabelTextview) view.findViewById(R.id.profile_email_textview);

        final MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.setDefaultToolbar(BeanProvider.userRepository().retrieveUser().getUsername());

        fillInElements();
        return view;
    }

    private void fillInElements() {
        usernameTextView.setText(BeanProvider.userRepository().retrieveUser().getUsername());
        emailTextView.setText(BeanProvider.userRepository().retrieveUser().getEmail());
    }

}
