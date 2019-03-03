package com.babcock.umislite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.babcock.umislite.Courses.CoursesFragment;

import com.babcock.umislite.Discover.DiscoverFragment;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String IP_ADDRESS = "192.168.43.93:8080";
    private static final String URL = "http://" + IP_ADDRESS + "/umislite/student_info.php?id=1";

    private TextView toolbarTitle;
    private Profile studentInfo;
    private AppCompatImageButton cardView;

    private final HomeFragment homeFragment = HomeFragment.newInstance();
    private final DiscoverFragment discoverFragment = DiscoverFragment.newInstance();
    private final CoursesFragment coursesFragment = CoursesFragment.newInstance();
    // private final ProfileFragment profileFragment = ProfileFragment.newInstance();
    private MoreFragment moreFragment;
    private final FragmentManager fragmentManager = getSupportFragmentManager();

    Fragment active = homeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //AppBarLayout appBarLayout = findViewById(R.id.appbar_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbarTitle = toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        toolbarTitle.setText(toolbar.getTitle());


        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        final Context context = getApplicationContext();


        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnectedOrConnecting()){
            Log.d(TAG, "In Fetch from server");
            fetchFromServer(context);
        }else{
            Log.d(TAG, "In Fetch from Room");
            fetchFromRoom();
        }
        cardView = toolbar.findViewById(R.id.profile_image);
        cardView.setOnClickListener(v -> {
            Intent intent = ProfileActivity.newIntent(this, studentInfo);
            startActivity(intent);
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
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
//                    //actionBar.setTitle("ProfileActivity");
//                    toolbarTitle.setText("ProfileActivity");
//                    return true;

                case R.id.more:
                    fragmentManager.beginTransaction().hide(active).show(moreFragment).commit();
                    active = moreFragment;
                    //actionBar.setTitle("More");
                    toolbarTitle.setText("More");
                    return true;

            }

            return false;
        });



    }

    private void setUpBottomViewFragments(){

        fragmentManager.beginTransaction().add(R.id.main_container, moreFragment, "moreFragment").hide(moreFragment).commit();
        //   fragmentManager.beginTransaction().add(R.id.main_container, profileFragment, "profileFragment").hide(profileFragment).commit();
        fragmentManager.beginTransaction().add(R.id.main_container, coursesFragment, "coursesFragment").hide(coursesFragment).commit();
        fragmentManager.beginTransaction().add(R.id.main_container, discoverFragment, "discoverFragment").hide(discoverFragment).commit();
        fragmentManager.beginTransaction().add(R.id.main_container, homeFragment, "homeFragment").commit();

    }

    private void fetchFromRoom() {

        Thread thread = new Thread(() -> {
            ProfileRepo profileRepo = DatabaseClient.getInstance(this)
                    .getAppDatabase().repoDao().getProfile().get(0);
            Profile profile = new Profile();
            profile.setStudentId(profileRepo.getStudentId());
            profile.setDepartmentId(profileRepo.getDepartmentId());
            profile.setFirstName(profileRepo.getFirstName());
            profile.setMiddleName(profileRepo.getMiddleName());
            profile.setLastName(profileRepo.getLastName());
            profile.setMatricNumber(profileRepo.getMatricNumber());
            profile.setLevel(profileRepo.getLevel());
            profile.setEmail(profileRepo.getEmail());
            profile.setAddress(profileRepo.getAddress());
            profile.setMobileNumber(profileRepo.getMobileNumber());
            profile.setProfilePicture(profileRepo.getProfilePicture());
            profile.setDeptCode(profileRepo.getDeptCode());
            profile.setDeptName(profileRepo.getDeptName());
            profile.setSchoolCode(profileRepo.getSchoolCode());
            profile.setSchoolName(profileRepo.getSchoolName());
            profile.setProgramCode(profileRepo.getProgramCode());
            profile.setProgramName(profileRepo.getProgramName());


            runOnUiThread(() -> {
                Log.d(TAG, "Loading Room Data");
                studentInfo = profile;
                setUpImage(profile);
            });

        });

        thread.start();
    }

    private void fetchFromServer(Context context) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL,
                this::responseImpl,
                error -> {
                Log.d(TAG, error.toString());
                fetchFromRoom();
                }
        );
        MySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }

    private void responseImpl(String s) {
        Log.d(TAG, s);
        List<Profile> studentList = new Gson().fromJson(s, new TypeToken<List<Profile>>(){}.getType());
        studentInfo = studentList.get(0);

        setUpImage(studentInfo);
        moreFragment = MoreFragment.newInstance(studentInfo);

        setUpBottomViewFragments();
        saveTask();
    }

    private void setUpImage(Profile studentInfo){
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
                        cardView.setImageBitmap(bitmap);
                        return false;
                    }
                }).preload();
    }

    private void saveTask() {
        @SuppressLint("StaticFieldLeak")
        class SaveTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                //creating a task
                List<ProfileRepo> profileRepos = DatabaseClient.getInstance(MainActivity.this)
                        .getAppDatabase().repoDao().getProfile();
                Log.d(TAG, profileRepos.size() + "");
                if(profileRepos.size() > 0)
                    DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().repoDao().delete(profileRepos.get(0));

                ProfileRepo profileRepo = new ProfileRepo();
                profileRepo.setStudentId(studentInfo.getStudentId());
                profileRepo.setDepartmentId(studentInfo.getDepartmentId());
                profileRepo.setFirstName(studentInfo.getFirstName());
                profileRepo.setMiddleName(studentInfo.getMiddleName());
                profileRepo.setLastName(studentInfo.getLastName());
                profileRepo.setMatricNumber(studentInfo.getMatricNumber());
                profileRepo.setLevel(studentInfo.getLevel());
                profileRepo.setEmail(studentInfo.getEmail());
                profileRepo.setAddress(studentInfo.getAddress());
                profileRepo.setMobileNumber(studentInfo.getMobileNumber());
                profileRepo.setProfilePicture(studentInfo.getProfilePicture());
                profileRepo.setDeptCode(studentInfo.getDeptCode());
                profileRepo.setDeptName(studentInfo.getDeptName());
                profileRepo.setSchoolCode(studentInfo.getSchoolCode());
                profileRepo.setSchoolName(studentInfo.getSchoolName());
                profileRepo.setProgramCode(studentInfo.getProgramCode());
                profileRepo.setProgramName(studentInfo.getProgramName());
                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().repoDao().insert(profileRepo);

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(MainActivity.this, "Saved", Toast.LENGTH_LONG).show();
            }
        }

        SaveTask st = new SaveTask();
        st.execute();
    }
}
