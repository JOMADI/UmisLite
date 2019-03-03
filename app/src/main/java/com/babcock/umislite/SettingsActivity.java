package com.babcock.umislite;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class SettingsActivity extends AppCompatActivity {

    private static final String PARENT_TAG = "PARENT";

    public static Intent newIntent(Context context, Profile studentInfo){
        Intent intent = new Intent(context, SettingsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("StudentInfo", studentInfo);
        intent.putExtras(bundle);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);
        Toolbar toolbar = findViewById(R.id.toolbar_profile);
        TextView textView = toolbar.findViewById(R.id.toolbar_title);
        textView.setText(R.string.title_activity_settings);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back_arrow);
        actionBar.setDisplayShowTitleEnabled(false);

        TextView editProfile = findViewById(R.id.edit_profile);
        Profile profile = (Profile) getIntent().getExtras().getSerializable("StudentInfo");
        editProfile.setOnClickListener(v -> {
            Intent intent = EditProfile.newIntent(this, profile);
            intent.putExtra(PARENT_TAG, false);
            startActivity(intent);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings_menu, menu);
        return true;
    }
}
