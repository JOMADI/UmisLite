package com.babcock.umislite;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import de.hdodenhof.circleimageview.CircleImageView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.text.MessageFormat;
import java.util.Objects;

public class EditProfile extends AppCompatActivity {
    private static final String TAG = "EditProfileActivity";
    private static final String PARENT_TAG = "PARENT";
    private static final String IP_ADDRESS = "192.168.43.93:8080";

    private boolean parentIsProfile;

    private CircleImageView profileImage;
    private TextView name;
    private TextView program;
    private TextView matric;
    private TextView level;
    private TextView department;
    private TextView school;
    private EditText email;
    private EditText mobileNo;
    private EditText homeAddress;

    private AppCompatButton updateProfile;

    public static Intent newIntent(Context context, Profile studentInfo){
        Intent intent = new Intent(context, EditProfile.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("StudentInfo", studentInfo);
        intent.putExtras(bundle);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        parentIsProfile = getIntent().getBooleanExtra(PARENT_TAG, false);

        Profile profile = (Profile) Objects.requireNonNull(getIntent().getExtras()).getSerializable("StudentInfo");
        assert profile != null;
        Log.d(TAG, profile.toString());
        Toolbar toolbar = findViewById(R.id.toolbar_profile);
        TextView textView = toolbar.findViewById(R.id.toolbar_title);
        textView.setText(R.string.edit_profile);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back_arrow);
        actionBar.setDisplayShowTitleEnabled(false);

        profileImage = findViewById(R.id.profile_pic);
        name = findViewById(R.id.name);
        program = findViewById(R.id.program);
        matric = findViewById(R.id.matric);
        level = findViewById(R.id.level);
        department = findViewById(R.id.department);
        school = findViewById(R.id.school);

        email = findViewById(R.id.email);
        mobileNo = findViewById(R.id.mobile_no);
        homeAddress = findViewById(R.id.home_address);

        updateProfile = findViewById(R.id.update_profile);

        assert profile != null;
        initializeEditProfile(profile);

    }

    private void initializeEditProfile(Profile studentInfo) {
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

        name.setText(MessageFormat.format("{0} {1} {2}", studentInfo.getLastName(), studentInfo.getMiddleName(), studentInfo.getFirstName()));
        program.setText(MessageFormat.format("{0} ({1})", studentInfo.getProgramName(), studentInfo.getProgramCode()));
        matric.setText(studentInfo.getMatricNumber());
        level.setText(studentInfo.getLevel());
        department.setText(MessageFormat.format("{0} ({1})", studentInfo.getDeptName(), studentInfo.getDeptCode()));
        school.setText(MessageFormat.format("{0} ({1})", studentInfo.getSchoolName(), studentInfo.getSchoolCode()));

        email.setText(studentInfo.getEmail());
        mobileNo.setText(studentInfo.getMobileNumber());
        homeAddress.setText(studentInfo.getAddress());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_profile_menu, menu);
        return true;
    }

    @Override
    public Intent getSupportParentActivityIntent() {
        return getParentActivityIntentImpl();
    }

    @Override
    public Intent getParentActivityIntent() {
        return getParentActivityIntentImpl();
    }

    private Intent getParentActivityIntentImpl() {
        Intent i = null;

        if (parentIsProfile) {
            i = new Intent(this, ProfileActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        } else {
            i = new Intent(this, SettingsActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        }

        return i;
    }
}
