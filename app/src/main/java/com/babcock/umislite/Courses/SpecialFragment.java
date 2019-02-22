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
public class SpecialFragment extends Fragment {


    public SpecialFragment() {
        // Required empty public constructor
    }

    public static SpecialFragment newInstance(){
        return new SpecialFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_special, container, false);
    }

}
