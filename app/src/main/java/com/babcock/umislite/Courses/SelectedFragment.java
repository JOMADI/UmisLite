package com.babcock.umislite.Courses;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.babcock.umislite.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class SelectedFragment extends Fragment {
    private static final String TAG = "SelectedFragment";
    private static ArrayList<Courses> coursesArrayList;
    private static SelectedCourseAdapter courseAdapter;

    public SelectedFragment() {
        // Required empty public constructor
    }

    public static SelectedFragment newInstance(){
        return new SelectedFragment();
    }



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_selected, container, false);
        RecyclerView  recyclerView = view.findViewById(R.id.rv_courses_selected);

        coursesArrayList = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        courseAdapter = new SelectedCourseAdapter(getActivity(), coursesArrayList);
        recyclerView.setAdapter(courseAdapter);
       return view;
    }

    public static void addCourse(List<Courses> courses){
        courseAdapter.addSelectedCourse(courses);
    }
}
