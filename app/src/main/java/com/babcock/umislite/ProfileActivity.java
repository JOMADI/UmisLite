package com.babcock.umislite;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.babcock.umislite.Courses.SelectedFragment;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.tabs.TabLayout;

import java.text.MessageFormat;
import java.util.Objects;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    private static final String PARENT_TAG = "PARENT";
    private static final String TAG = "ProfileActivity";
    private static final String IP_ADDRESS = "192.168.43.93:8080";
    private static final String URL = "http://" + IP_ADDRESS + "/umislite/student_info.php?id=1";

    private CircleImageView profileImage;
    private TextView tvName;
    private TextView tvEmail;
    private TextView tvCourse;
    private TextView tvMatric;
    private TextView tvLevel;

    private Profile studentInfo;

    public static Intent newIntent(Context context, Profile studentInfo){
        Intent i = new Intent(context, ProfileActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("StudentInfo", studentInfo);
        i.putExtras(bundle);

        return i;
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

        profileImage = findViewById(R.id.profile_image);
        tvName = findViewById(R.id.tv_name);
        tvEmail = findViewById(R.id.tv_email);
        tvCourse = findViewById(R.id.tv_course);
        tvMatric = findViewById(R.id.tv_matric);
        tvLevel = findViewById(R.id.tv_level);

        studentInfo = (Profile) Objects.requireNonNull(getIntent().getExtras()).getSerializable("StudentInfo");
        assert studentInfo != null;
        initializeProfile(studentInfo);

        TabAdapter tabAdapter = new TabAdapter(getSupportFragmentManager());
        tabAdapter.addFragment(SelectedFragment.newInstance(), "COURSES SELECTED");
        tabAdapter.addFragment(BookmarkFragment.newInstance(), "BOOKMARKS");

        viewPager.setAdapter(tabAdapter);
        tabLayout.setupWithViewPager(viewPager);


    }

    private  void initializeProfile(Profile studentInfo) {
        String imageUrl = "http://" + IP_ADDRESS + "/umislite/studentimages/" + studentInfo.getProfilePicture();

        Glide.with(this)
                .load(imageUrl)
                .asBitmap()
                .dontAnimate()
                .placeholder(R.drawable.loading)
                .listener(new RequestListener<String, Bitmap>() {
                    @Override
                    public boolean onException(Exception e, String s, Target<Bitmap> target, boolean b) {
                        return false;
                    }
                    @Override
                    public boolean onResourceReady(Bitmap bitmap, String s, Target<Bitmap> target, boolean b, boolean b1) {
                        profileImage.setImageBitmap(bitmap);
                        return false;
                    }
                }).preload();

        tvName.setText(MessageFormat.format("{0} {1} {2}", studentInfo.getLastName(), studentInfo.getFirstName(), studentInfo.getMiddleName()));
        tvEmail.setText(studentInfo.getEmail());
        tvCourse.setText(MessageFormat.format("{0}", studentInfo.getProgramName()));
        tvMatric.setText(studentInfo.getMatricNumber());
        tvLevel.setText(MessageFormat.format("{0}Level", studentInfo.getLevel()));

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
                Intent intent = EditProfile.newIntent(this, studentInfo);
                intent.putExtra(PARENT_TAG, true);
                startActivity(intent);
                return true;

            case R.id.signout:
                Intent i = WelcomeActivity.newIntent(this);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    finishAffinity();
                }
                startActivity(i);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
