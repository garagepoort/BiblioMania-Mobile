package com.bendani.bibliomania;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.bendani.bibliomania.bookinfo.BookInfoActivity;
import com.bendani.bibliomania.books.domain.Book;
import com.bendani.bibliomania.books.presentation.BooksFragment;
import com.bendani.bibliomania.generic.application.BiblioManiaApp;
import com.bendani.bibliomania.generic.infrastructure.BeanProvider;
import com.bendani.bibliomania.login.LoginFragment;


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
            goToHome();
        } else {
            goToLogin();
        }
    }

    public void goToBookInfo(Book book) {
        Intent intent = new Intent(this, BookInfoActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
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
        replaceFragment(new BooksFragment(), true);
    }


    private void replaceFragment(Fragment nextFragment, boolean addToBackStack) {
        closeVirtualKeyboard();
        if (!addToBackStack) {
            getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        Fragment currentFragment = getCurrentFragment();

        if (currentFragment == null || !(currentFragment.getClass().equals(nextFragment.getClass()))) {
            FragmentTransaction transaction = getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_placeholder, nextFragment);
            if (addToBackStack) {
                transaction.addToBackStack("");
            }
            transaction.commit();
        }
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
            goToHome();
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
}
