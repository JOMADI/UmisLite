package com.babcock.umislite.Courses;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.babcock.umislite.R;
import com.babcock.umislite.TabAdapter;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.tabs.TabLayout;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CoursesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CoursesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private int deptId;
    private String level;


    public CoursesFragment() {
        // Required empty public constructor
    }

//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment CoursesFragment.
//     */
    public static CoursesFragment newInstance(int deptId, String level) {
        CoursesFragment coursesFragment = new CoursesFragment();
        Bundle args = new Bundle();
        args.putInt("DeptId", deptId);
        args.putString("Level", level);
        coursesFragment.setArguments(args);
        return coursesFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            deptId = getArguments().getInt("DeptId");
            level = getArguments().getString("Level");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_courses, container, false);
        ViewPager viewPager = v.findViewById(R.id.viewpager_courses);
        TabLayout tabLayout = v.findViewById(R.id.tabs_courses);

        TabAdapter tabAdapter = new TabAdapter(getChildFragmentManager());
        tabAdapter.addFragment(ToDo.newInstance(deptId, level), "TO-DO");
        tabAdapter.addFragment(SelectedFragment.newInstance(), "SELECTED");
        tabAdapter.addFragment(SpecialFragment.newInstance(), "SPECIAL");

        viewPager.setAdapter(tabAdapter);
        tabLayout.setupWithViewPager(viewPager);

        return v;
    }

}
