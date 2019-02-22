package com.babcock.umislite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.TypefaceCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.babcock.umislite.Courses.CoursesFragment;

import com.babcock.umislite.Discover.DiscoverFragment;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private final HomeFragment homeFragment = HomeFragment.newInstance();
    private final DiscoverFragment discoverFragment = DiscoverFragment.newInstance();
    private final CoursesFragment coursesFragment = CoursesFragment.newInstance();
   // private final ProfileFragment profileFragment = ProfileFragment.newInstance();
    private final MoreFragment moreFragment = MoreFragment.newInstance();
    private final FragmentManager fragmentManager = getSupportFragmentManager();

    Fragment active = homeFragment;
    private TextView toolbarTitle;

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            ActionBar actionBar = getSupportActionBar();
            switch (menuItem.getItemId()){
                case R.id.home:
                    fragmentManager.beginTransaction().hide(active).show(homeFragment).commit();
                    active = homeFragment;
                   // actionBar.setTitle("UmisLite Pro");
                    toolbarTitle.setText("UmisLite Pro");
                    return true;

                case R.id.discover:
                    fragmentManager.beginTransaction().hide(active).show(discoverFragment).commit();
                    active = discoverFragment;
                  //  actionBar.setTitle("Discover");
                    toolbarTitle.setText("Discover");
                    return true;

                case R.id.courses:
                    fragmentManager.beginTransaction().hide(active).show(coursesFragment).commit();
                    active = coursesFragment;
                   // actionBar.setTitle("Courses");
                    toolbarTitle.setText("Courses");
                    return true;

//                case R.id.profile:
//                    fragmentManager.beginTransaction().hide(active).show(profileFragment).commit();
//                    active = profileFragment;
//                    //actionBar.setTitle("Profile");
//                    toolbarTitle.setText("Profile");
//                    return true;

                case R.id.more:
                    fragmentManager.beginTransaction().hide(active).show(moreFragment).commit();
                    active = moreFragment;
                    //actionBar.setTitle("More");
                    toolbarTitle.setText("More");
                    return true;

            }

            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppBarLayout appBarLayout = findViewById(R.id.appbar_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbarTitle = toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        toolbarTitle.setText(toolbar.getTitle());


        getSupportActionBar().setDisplayShowTitleEnabled(false);

        setUpBottomViewFragments();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

       AppCompatImageButton cardView = toolbar.findViewById(R.id.profile_image);
        cardView.setOnClickListener(v -> {
            Intent intent = Profile.newIntent(this);
            startActivity(intent);
        });

    }

    private void setUpBottomViewFragments(){

        fragmentManager.beginTransaction().add(R.id.main_container, moreFragment, "moreFragment").hide(moreFragment).commit();
     //   fragmentManager.beginTransaction().add(R.id.main_container, profileFragment, "profileFragment").hide(profileFragment).commit();
        fragmentManager.beginTransaction().add(R.id.main_container, coursesFragment, "coursesFragment").hide(coursesFragment).commit();
        fragmentManager.beginTransaction().add(R.id.main_container, discoverFragment, "discoverFragment").hide(discoverFragment).commit();
        fragmentManager.beginTransaction().add(R.id.main_container, homeFragment, "homeFragment").commit();

    }
}
