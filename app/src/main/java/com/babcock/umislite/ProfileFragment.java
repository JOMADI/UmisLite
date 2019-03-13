package com.babcock.umislite;


import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import de.hdodenhof.circleimageview.CircleImageView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.babcock.umislite.Courses.SelectedFragment;
import com.babcock.umislite.Courses.ToDo;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.tabs.TabLayout;

import java.text.MessageFormat;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    private static final String PARENT_TAG = "PARENT";
    private static final String TAG = "ProfileFragment";
    private static final String IP_ADDRESS = "192.168.43.93:8080";
    private static final String URL = "http://" + IP_ADDRESS + "/umislite/student_info.php?id=1";

    private CircleImageView profileImage;
    private TextView tvName;
    private TextView tvEmail;
    private TextView tvCourse;
    private TextView tvMatric;
    private TextView tvLevel;

    private Profile studentInfo;

    private int deptId;
    private String studentLevel;

    public ProfileFragment() {

    }

    public static ProfileFragment newInstance(Profile profile) {
        ProfileFragment profileFragment = new ProfileFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("StudentInfo", profile);
        profileFragment.setArguments(bundle);

        return profileFragment;
    }

    public static ProfileFragment newInstance(int deptId, String level, Profile profile){
        ProfileFragment profileFragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putSerializable("StudentInfo", profile);
        args.putInt("DeptId", deptId);
        args.putString("Level", level);
        profileFragment.setArguments(args);

        return profileFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            deptId = getArguments().getInt("DeptId");
            studentLevel = getArguments().getString("Level");
            studentInfo = (Profile) getArguments().getSerializable("StudentInfo");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);

//        Toolbar toolbar = view.findViewById(R.id.toolbar_profile);
//        TextView textView = toolbar.findViewById(R.id.toolbar_title);
//        textView.setText(R.string.profile);
//        getActivity().setSupportActionBar(toolbar);
//        ActionBar actionBar = getSupportActionBar();
//        assert actionBar != null;
//        actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.setHomeAsUpIndicator(R.drawable.ic_back_arrow);
//        actionBar.setDisplayShowTitleEnabled(false);

        ViewPager viewPager = view.findViewById(R.id.viewpager_courses);
        TabLayout tabLayout = view.findViewById(R.id.tabs_courses);

        profileImage = view.findViewById(R.id.profile_image);
        tvName = view.findViewById(R.id.tv_name);
        tvEmail = view.findViewById(R.id.tv_email);
        tvCourse = view.findViewById(R.id.tv_course);
        tvMatric = view.findViewById(R.id.tv_matric);
        tvLevel = view.findViewById(R.id.tv_level);

        initializeProfile(studentInfo);

        TabAdapter tabAdapter = new TabAdapter(getChildFragmentManager());
        tabAdapter.addFragment(ToDo.newInstance(deptId, studentLevel), "COURSES");
        tabAdapter.addFragment(BookmarkFragment.newInstance(), "FINANCES");

        viewPager.setAdapter(tabAdapter);
        tabLayout.setupWithViewPager(viewPager);



        return view;
    }


    private  void initializeProfile(Profile studentInfo) {
        String imageUrl = "http://" + IP_ADDRESS + "/umislite/studentimages/" + studentInfo.getProfilePicture();

        Glide.with(getActivity())
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

}
