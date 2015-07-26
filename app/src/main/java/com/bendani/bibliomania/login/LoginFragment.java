package com.bendani.bibliomania.login;


import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.bendani.bibliomania.MainActivity;
import com.bendani.bibliomania.R;
import com.bendani.bibliomania.generic.infrastructure.BeanProvider;
import com.bendani.bibliomania.generic.infrastructure.RXJavaExtension.JustOnCompleteOrOnError;
import com.bendani.bibliomania.generic.presentation.customview.FloatingLabelEditText;
import com.bendani.bibliomania.login.domain.LoginService;

import static com.bendani.bibliomania.generic.infrastructure.BeanProvider.errorParser;
import static rx.android.schedulers.AndroidSchedulers.mainThread;

public class LoginFragment extends Fragment {

    private LoginService loginService;
    private Button loginButton;
    private FloatingLabelEditText usernameEditText;
    private FloatingLabelEditText passwordEditText;

    public LoginFragment() {
        loginService = BeanProvider.loginService();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        loginButton = (Button) view.findViewById(R.id.login_button);
        usernameEditText = (FloatingLabelEditText) view.findViewById(R.id.username_input);
        passwordEditText = (FloatingLabelEditText) view.findViewById(R.id.password_input);

        loginButton.setOnClickListener(getLoginButtonClickListener());
        return view;
    }

    private View.OnClickListener getLoginButtonClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progress = new ProgressDialog(getActivity());
                progress.setTitle(getString(R.string.logging_in));
                progress.setCancelable(false);
                progress.show();
                loginService.login(usernameEditText.getTextEditText(), passwordEditText.getTextEditText())
                        .observeOn(mainThread())
                        .subscribe(new JustOnCompleteOrOnError<Void>() {
                            @Override
                            public void onCompleted() {
                                progress.dismiss();
                                MainActivity mainActivity = (MainActivity) getActivity();
                                mainActivity.goToHome();
                            }

                            @Override
                            public void onError(Throwable e) {
                                progress.dismiss();
                                errorParser().createErrorDialogFromError(getActivity(), e);
                            }
                        });
            }
        };
    }


}
