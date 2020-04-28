package com.example.sabrahamson.tabbedviewdemo;

import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

public class HelpActivity extends AppCompatActivity {

    /**
     * The {@link PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        setupActionBar();
        setupTabs();
        setupFAB();

    }

    private void setupActionBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getDelegate().getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
    }


    private void setupTabs() {
        // Get a reference to the TabLayout
        TabLayout tabLayout = findViewById(R.id.tabLayout);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), getApplicationContext());

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // Bind the ViewPager and its adapter to the tabLayout to add in the tabs
        tabLayout.setupWithViewPager(mViewPager);
    }

    private void setupFAB() {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_help, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        } else if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // The following two methods are useful to prevent a certain crash on certain old LG devices
    // LG work-around
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean isOldLG = ((keyCode == KeyEvent.KEYCODE_MENU) &&
                (Build.VERSION.SDK_INT <= 16) &&
                (Build.MANUFACTURER.compareTo("LGE") == 0));

        //noinspection SimplifiableConditionalExpression
        return isOldLG ? true : super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_MENU) &&
                (Build.VERSION.SDK_INT <= 16) &&
                (Build.MANUFACTURER.compareTo("LGE") == 0)) {
            openOptionsMenu();
            return true;
        } else {
            return super.onKeyUp(keyCode, event);
        }
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        // Used to keep a reference to the Application's Context
        private final Context mContext;

        // These will be the titles read in from XML
        private final String[] mArrTitles;

        // Change the constructor to accept the Context so we have it in this Adapter
        public SectionsPagerAdapter(FragmentManager fm, Context context) {
            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
            mContext = context;
            mArrTitles = mContext.getResources().getStringArray(R.array.helpSectionTitles);
        }

        @Override
        @NonNull
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a HelpTabFragment (defined as a static inner class below).
            return HelpTabFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show a page for each element in the mArrTitles array
            return mArrTitles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            // return the title of the current page based on the position in the array
            if (position >= 0 && position < mArrTitles.length)
                // In case it is text (as opposed to a number) then all of it will be capitalized
                return mArrTitles[position].toUpperCase();
            else
                return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     * We refactored the name to HelpTabFragment
     */
    public static class HelpTabFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        private TextView mTextView;
        private String[] mArrSections;
        private int mSectionNumber;

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static HelpTabFragment newInstance(int sectionNumber) {
            HelpTabFragment fragment = new HelpTabFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public HelpTabFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_help, container, false);
            // get a a handle to the current TextView
            mTextView = rootView.findViewById(R.id.section_label);

            // get the String array of content from XML
            mArrSections = getResources().getStringArray(R.array.helpSections);

            // get the section number
            assert getArguments() != null;
            mSectionNumber = getArguments().getInt(ARG_SECTION_NUMBER) - 1;
            return rootView;
        }

        /**
         * The setting text of each Fragment is done in onViewCreated.
         * Earlier prep is done in onCreateView, just above
         * <p>
         * From Fragment.java:
         * <p>
         * Called immediately after {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}
         * has returned, but before any saved state has been restored in to the view.
         * This gives subclasses a chance to initialize themselves once
         * they know their view hierarchy has been completely created.  The fragment's
         * view hierarchy is not however attached to its parent at this point.
         *
         * @param view               The View returned by {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
         * @param savedInstanceState If non-null, this fragment is being re-constructed
         *                           from a previous saved state as given here.
         */
        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            // set the current tabs text to the correct text (from the array)
            mTextView.setText(mArrSections[mSectionNumber]);
        }
    }
}
