package com.babcock.umislite;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;




/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MoreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MoreFragment extends Fragment {
    private static final String TAG = "MoreFragment";
    private Profile profile;

    public MoreFragment() {
        // Required empty public constructor
    }

//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment MoreFragment.
//     */
    public static MoreFragment newInstance(Profile studentInfo) {
        MoreFragment moreFragment = new MoreFragment();
        Bundle args = new Bundle();
        args.putSerializable("StudentInfo", studentInfo);
        moreFragment.setArguments(args);
        return moreFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            profile = (Profile) getArguments().getSerializable("StudentInfo");
            assert profile != null;
            Log.d(TAG, getArguments().toString());
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_more, container, false);
        ConstraintLayout settings = v.findViewById(R.id.settings);
        settings.setOnClickListener(view -> {
            Intent intent = SettingsActivity.newIntent(getActivity(), profile);
            startActivity(intent);
        });
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
