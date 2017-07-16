package com.bendani.bibliomania;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.bendani.bibliomania.bookinfo.BookInfoActivity;
import com.bendani.bibliomania.books.domain.Book;
import com.bendani.bibliomania.books.presentation.BooksFragment;
import com.bendani.bibliomania.books.presentation.ProfileFragment;
import com.bendani.bibliomania.generic.application.BiblioManiaApp;
import com.bendani.bibliomania.generic.exception.NoInternetConnectionError;
import com.bendani.bibliomania.generic.infrastructure.BeanProvider;
import com.bendani.bibliomania.generic.infrastructure.RXJavaExtension.JustOnCompleteOrOnError;
import com.bendani.bibliomania.generic.presentation.ConfirmationDialog;
import com.bendani.bibliomania.login.LoginFragment;

import static com.bendani.bibliomania.generic.infrastructure.BeanProvider.bookService;
import static com.bendani.bibliomania.generic.infrastructure.BeanProvider.connectionService;
import static com.bendani.bibliomania.generic.infrastructure.BeanProvider.errorParser;
import static com.bendani.bibliomania.generic.infrastructure.BeanProvider.userRepository;
import static rx.android.schedulers.AndroidSchedulers.mainThread;


public class MainActivity extends AppCompatActivity {

    private final BeanProvider beanProvider;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private ListView drawerList;

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
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.activity_main);
        hideAllToolBars();

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar_actionbar_default);
        drawerList = (ListView) findViewById(R.id.left_drawer);

        drawerList.setAdapter(new ArrayAdapter<>(this, R.layout.drawer_list_item, new String[]{"Boeken", "Profiel", "Synchroniseer", "Log out"}));
        drawerList.setOnItemClickListener(new DrawerItemClickListener());
//        View header=getLayoutInflater().inflate(R.layout.drawer_header, null);
//        drawerList.addHeaderView(header);

        drawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                R.string.drawer_open,
                R.string.drawer_close
        ) {

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
                syncState();
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
                syncState();
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);

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
        hideAllToolBars();
        replaceFragment(new LoginFragment(), false);
    }

    public void goToBooksOverview() {
        replaceFragment(new BooksFragment(), true);
    }

    public void goToProfileFragment() {
        replaceFragment(new ProfileFragment(), true);
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
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() == 1) {
            goToBooksOverview();
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        drawerToggle.syncState();
    }

    public void setDefaultToolbar(String title) {
        toolbar.setVisibility(View.GONE);
        toolbar = (Toolbar) findViewById(R.id.toolbar_actionbar_default);
        toolbar.setVisibility(View.VISIBLE);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        drawerToggle.syncState();
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
            goToBooksOverview();
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

    private class DrawerItemClickListener implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            selectItem(position);
        }

        private void selectItem(int position) {
            if (position == 0) {
                goToBooksOverview();
            } else if (position == 1) {
                goToProfileFragment();
            } else if (position == 2) {
                downloadBooks();
            } else if (position == 3) {
                new ConfirmationDialog(MainActivity.this, getString(R.string.logout_confirmation_title), getString(R.string.logout_confirmation_message), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        userRepository().deleteUser();
                        goToLogin();
                    }
                }).show();
            }
            drawerLayout.closeDrawer(drawerList);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }
}
