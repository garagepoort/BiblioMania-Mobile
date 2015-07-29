package com.bendani.bibliomania.bookinfo;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.bendani.bibliomania.R;
import com.bendani.bibliomania.books.domain.Book;

import java.util.ArrayList;
import java.util.List;


public class BookInfoActivity extends AppCompatActivity {

    private BookInfoPagerAdapter mBookInfoPagerAdapter;
    private ViewPager mViewPager;
    private ActionBar actionBar;
    private Toolbar toolbar;
    private Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_info);
        Intent intent = getIntent();
        book = (Book) intent.getSerializableExtra("BOOK");

        mBookInfoPagerAdapter = new BookInfoPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mBookInfoPagerAdapter);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(book.getTitle());

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs_layout);
        tabLayout.setupWithViewPager(mViewPager);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_book_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class BookInfoPagerAdapter extends FragmentStatePagerAdapter {
        private List<Fragment> fragments = new ArrayList<>();
        private List<String> titles = new ArrayList<>();

        public BookInfoPagerAdapter(FragmentManager fm) {
            super(fm);
            GeneralBookInfoFragment generalBookInfoFragment = new GeneralBookInfoFragment();
            generalBookInfoFragment.setBook(book);
            fragments.add(generalBookInfoFragment);
            titles.add(getString(R.string.general_book_info_title));
            fragments.add(new AuthorInfoFragment());
            titles.add(getString(R.string.author_info_title));
        }

        @Override
        public Fragment getItem(int i) {
            return fragments.get(i);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }

}