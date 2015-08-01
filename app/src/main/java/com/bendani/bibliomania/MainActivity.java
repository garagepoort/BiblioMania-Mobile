package com.bendani.bibliomania;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.bendani.bibliomania.bookinfo.BookInfoActivity;
import com.bendani.bibliomania.books.domain.Book;
import com.bendani.bibliomania.books.presentation.BooksFragment;
import com.bendani.bibliomania.generic.application.BiblioManiaApp;
import com.bendani.bibliomania.generic.exception.NoInternetConnectionError;
import com.bendani.bibliomania.generic.infrastructure.BeanProvider;
import com.bendani.bibliomania.generic.infrastructure.RXJavaExtension.JustOnCompleteOrOnError;
import com.bendani.bibliomania.generic.presentation.ConfirmationDialog;
import com.bendani.bibliomania.login.LoginFragment;

import static com.bendani.bibliomania.generic.infrastructure.BeanProvider.bookService;
import static com.bendani.bibliomania.generic.infrastructure.BeanProvider.connectionService;
import static com.bendani.bibliomania.generic.infrastructure.BeanProvider.errorParser;
import static rx.android.schedulers.AndroidSchedulers.mainThread;


public class MainActivity extends AppCompatActivity {

    private final BeanProvider beanProvider;
    private Toolbar toolbar;

    public MainActivity() {
        if (getApplication() == null) {
            beanProvider = new BeanProvider(this);
        } else {
            beanProvider = ((BiblioManiaApp) getApplication()).getBeanProvider();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hideAllToolBars();

        toolbar = (Toolbar) findViewById(R.id.toolbar_actionbar_default);

        if (beanProvider.userRepository().isUserLoggedIn()) {
            goToCorrectFragment(getIntent());
        } else {
            goToLogin();
        }
    }

    public void goToBookInfo(Book book) {
        Intent intent = new Intent(this, BookInfoActivity.class);
        intent.putExtra("BOOK", book);
        startActivity(intent);
    }

    public void goToLogin() {
        replaceFragment(new LoginFragment(), false);
    }

    public void goToHome() {
        replaceFragment(new HomeFragment(), false);
    }

    public void goToBooksOverview() {
        replaceFragment(new BooksFragment(), false);
    }


    private void replaceFragment(Fragment nextFragment, boolean addToBackStack) {
        closeVirtualKeyboard();
        if (!addToBackStack) {
            getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        Fragment currentFragment = getCurrentFragment();
        if (currentFragment != null && currentFragment.getClass().equals(nextFragment.getClass())) {
            getFragmentManager().popBackStack();
        }
        FragmentTransaction transaction = getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_placeholder, nextFragment);
        if (addToBackStack) {
            transaction.addToBackStack("");
        }
        transaction.commit();
    }


    private Fragment getCurrentFragment() {
        return getFragmentManager().findFragmentById(R.id.fragment_placeholder);
    }

    public void closeVirtualKeyboard() {
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        View currentFocus = getCurrentFocus();
        if (currentFocus != null) {
            inputManager.hideSoftInputFromWindow(currentFocus.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public void showKeyboard(View view) {
        InputMethodManager keyboard = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        keyboard.showSoftInput(view, 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem settingsItem = menu.findItem(R.id.action_settings);
        settingsItem.setVisible(false);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() == 1) {
            if(bookService().areBooksInitialized()){
                goToBooksOverview();
            }else{
                goToHome();
            }
        } else if (getFragmentManager().getBackStackEntryCount() > 1) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    public void setSearchBooksToolbar() {
        replaceToolbar(R.id.toolbar_actionbar_search_books);
    }

    private void replaceToolbar(int toolbar_actionbar_damage_fragment) {
        toolbar.setVisibility(View.GONE);
        toolbar = (Toolbar) findViewById(toolbar_actionbar_damage_fragment);
        toolbar.setVisibility(View.VISIBLE);
        setSupportActionBar(toolbar);
    }

    public void setDefaultToolbar(String title) {
        toolbar.setVisibility(View.GONE);
        toolbar = (Toolbar) findViewById(R.id.toolbar_actionbar_default);
        toolbar.setVisibility(View.VISIBLE);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
    }

    public void hideAllToolBars() {
        findViewById(R.id.toolbar_actionbar_default).setVisibility(View.GONE);
        findViewById(R.id.toolbar_actionbar_search_books).setVisibility(View.GONE);
    }

    private void goToCorrectFragment(Intent intent) {
        String fragment = intent.getStringExtra("FRAGMENT");
        if (fragment != null && fragment.equals("BOOKS")) {
            goToBooksOverview();
        } else {
            if (!bookService().areBooksInitialized()) {
                goToHome();
            } else {
                goToBooksOverview();
            }
        }
    }

    public void downloadBooks() {
        if (!connectionService().isDeviceConnectedToInternet()) {
            errorParser().createErrorDialogFromError(this, new NoInternetConnectionError()).show();
        } else {
            new ConfirmationDialog(this, getString(R.string.download_confirmation_title), getString(R.string.download_confirmation_message), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    downloadBooksWithoutConfirmation();
                }
            }).show();
        }
    }

    public void downloadBooksWithoutConfirmation() {
        final ProgressDialog progress = new ProgressDialog(this);
        progress.setTitle(getString(R.string.downloading));
        progress.setMessage(getString(R.string.download_books_progress_message));
        progress.setCancelable(false);
        progress.show();

        beanProvider.bookService().downloadBooks()
                .observeOn(mainThread())
                .subscribe(new JustOnCompleteOrOnError<Void>() {
                    @Override
                    public void onCompleted() {
                        progress.dismiss();
                        Toast.makeText(MainActivity.this, getString(R.string.books_downloaded), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        progress.dismiss();
                        errorParser().createErrorDialogFromError(MainActivity.this, e).show();
                    }
                });
    }
}
