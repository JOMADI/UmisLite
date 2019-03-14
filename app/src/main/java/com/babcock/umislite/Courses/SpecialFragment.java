package com.babcock.umislite.Courses;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.babcock.umislite.MySingleton;
import com.babcock.umislite.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class SpecialFragment extends Fragment {

    private static final String TAG = "FragmentFragment";
    private List<Courses> serverList;
    private ArrayList<Courses> coursesArrayList;
    private CourseAdapter courseAdapter;
    private static final String IP_ADDRESS = "192.168.43.93:8080";
    private static String URL_COURSES = "http://" + IP_ADDRESS + "/umislite/allcourses.php";


    public SpecialFragment() {
        // Required empty public constructor
    }

    public static SpecialFragment newInstance(){
        return new SpecialFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View  v = inflater.inflate(R.layout.fragment_special, container, false);

        RecyclerView recyclerView = v.findViewById(R.id.rv_courses_special);
        coursesArrayList = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        courseAdapter = new CourseAdapter(getActivity(), coursesArrayList);
        recyclerView.setAdapter(courseAdapter);

        //recyclerView.setOnClickListener(v1 -> bottomSheetDialog.show());

        final Context context = container.getContext();


        ConnectivityManager connectivityManager = (ConnectivityManager) Objects.requireNonNull(getActivity())
                .getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnectedOrConnecting() && coursesArrayList != null){
            Log.d(TAG, "In Fetch from server");
            fetchFromServer(context);
        }else{
            Log.d(TAG, "In Fetch from Room");
          //  fetchFromRoom();
        }

        return v;
    }

    private void fetchFromServer(Context context) {
        Log.d(TAG, URL_COURSES);

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL_COURSES,
                this::responseCourses,
                error -> {
                    Log.d(TAG, error.toString());
                   // fetchFromRoom();
                }
        );
        MySingleton.getInstance(Objects.requireNonNull(getContext()).getApplicationContext()).addToRequestQueue(stringRequest);
    }


    private void responseCourses(String s) {
        Log.d(TAG, s);
        serverList = new Gson().fromJson(s, new TypeToken<List<Courses>>(){}.getType());

        coursesArrayList.clear();
        coursesArrayList.addAll(serverList);
        courseAdapter.notifyDataSetChanged();

       // saveTask();
    }

}
