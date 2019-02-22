package com.babcock.umislite.Discover;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.babcock.umislite.R;

import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class FacilitiesFragment extends Fragment {


    public FacilitiesFragment() {
        // Required empty public constructor
    }
    public static FacilitiesFragment newInstance(){
        return new FacilitiesFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_facilities, container, false);
    }

}
