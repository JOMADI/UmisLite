package com.babcock.umislite.Courses;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.babcock.umislite.R;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class SelectedFragment extends Fragment {

    private static int layout;

    public SelectedFragment() {
        // Required empty public constructor
    }

    public static SelectedFragment newInstance(int fragment_course_profile){
        layout = fragment_course_profile;
        return new SelectedFragment();
    }

    private int getLayout(){
        return layout;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(getLayout(), container, false);
    }

}
