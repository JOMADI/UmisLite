package com.babcock.umislite;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.babcock.umislite.Courses.SelectedFragment;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

public class Profile extends AppCompatActivity {

    private static final String PARENT_TAG = "PARENT";

    public static Intent newIntent(Context context){
        return new Intent(context, Profile.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_profile);
        Toolbar toolbar = findViewById(R.id.toolbar_profile);
        TextView textView = toolbar.findViewById(R.id.toolbar_title);
        textView.setText(R.string.profile);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back_arrow);
        actionBar.setDisplayShowTitleEnabled(false);

        ViewPager viewPager = findViewById(R.id.viewpager_courses);
        TabLayout tabLayout = findViewById(R.id.tabs_courses);

        TabAdapter tabAdapter = new TabAdapter(getSupportFragmentManager());
        tabAdapter.addFragment(SelectedFragment.newInstance(R.layout.fragment_course_profile), "COURSES SELECTED");
        tabAdapter.addFragment(BookmarkFragment.newInstance(), "BOOKMARKS");

        viewPager.setAdapter(tabAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

       getMenuInflater().inflate(R.menu.profile_menu, menu);
       return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.edit_profile:
                Intent intent = EditProfile.newIntent(this);
                intent.putExtra(PARENT_TAG, true);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
