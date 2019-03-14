package com.babcock.umislite.Courses;


import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.babcock.umislite.CoursesRepo;
import com.babcock.umislite.DatabaseClient;
import com.babcock.umislite.MySingleton;
import com.babcock.umislite.R;
import com.babcock.umislite.SchoolRepo;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ToDo extends Fragment {

    private List<Courses> serverList;
    private static final String TAG = "TODOFragment";
    private ArrayList<Courses> coursesArrayList;
    private CourseAdapter courseAdapter;
    private static final String IP_ADDRESS = "192.168.43.93:8080";
    private static String URL_COURSES = "http://" + IP_ADDRESS + "/umislite/course_info.php?type=";

    static BottomSheetDialog bottomSheetDialog;
    private int deptId;
    private String studentLevel;

    public ToDo() {
        // Required empty public constructor
    }

    public static ToDo newInstance(int deptId, String level){
        ToDo toDo = new ToDo();
        Bundle args = new Bundle();
        args.putInt("DeptId", deptId);
        args.putString("Level", level);
        toDo.setArguments(args);

        return toDo;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            deptId = getArguments().getInt("DeptId");
            studentLevel = getArguments().getString("Level");
        }
    }

    public static void showBottomSheet(){
        bottomSheetDialog.show();
    }



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_to_do, container, false);
        RecyclerView recyclerView = v.findViewById(R.id.rv_courses_todo);
        coursesArrayList = new ArrayList<>();

        bottomSheetDialog = new BottomSheetDialog(Objects.requireNonNull(getActivity()));
        View bottomSheet = inflater.inflate(R.layout.view_course_item, null);
        bottomSheetDialog.setContentView(bottomSheet);


        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from((View) bottomSheet.getParent());
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        //bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        AppCompatTextView courseName = bottomSheet.findViewById(R.id.course_name);
        AppCompatTextView courseDesc = bottomSheet.findViewById(R.id.course_desc_content);
        AppCompatButton joinCourse = bottomSheet.findViewById(R.id.join_course);
        joinCourse.setOnClickListener(v1 -> {
            Toast.makeText(getActivity(), "COURSE SELECTED", Toast.LENGTH_LONG).show();
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_SETTLING);
        });
        bottomSheetBehavior.setPeekHeight(1000);

        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {

            }

            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });


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
            fetchFromRoom();
        }

        return v;
    }

    private void fetchFromServer(Context context) {
        URL_COURSES += String.valueOf(deptId);
        String level = studentLevel;
        if(level.equals("100")){
            URL_COURSES += "&level=onelevel_2";
        }
        if(level.equals("200")){
            URL_COURSES += "&level=twolevel_2";
        }
        if(level.equals("300")){
            URL_COURSES += "&level=threelevel_2";
        }
        if(level.equals("400")){
            URL_COURSES += "&level=fourlevel_2";
        }
        Log.d(TAG, URL_COURSES);

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL_COURSES,
                this::responseCourses,
                error -> {
                    Log.d(TAG, error.toString());
                    fetchFromRoom();
                }
        );
        MySingleton.getInstance(Objects.requireNonNull(getContext()).getApplicationContext()).addToRequestQueue(stringRequest);
    }

    private void fetchFromRoom() {
        Thread thread = new Thread(() -> {
            List<CoursesRepo> coursesRepos = DatabaseClient.getInstance(getActivity())
                    .getAppDatabase().repoDao().getAllCourses();
            coursesArrayList.clear();
            for(CoursesRepo coursesRepo : coursesRepos){
                Courses courses = new Courses(coursesRepo.getCourseCode(), coursesRepo.getCourseTitle(), coursesRepo.getCourseType(), coursesRepo.getCreditUnit());
                Log.d(TAG, courses.toString());
                coursesArrayList.add(courses);
            }

            Objects.requireNonNull(getActivity()).runOnUiThread(() -> {
                Log.d(TAG, "Loading Room Data");
                courseAdapter.notifyDataSetChanged();
            });
        });

        thread.start();
    }

    private void responseCourses(String s) {
        Log.d(TAG, s);
        serverList = new Gson().fromJson(s, new TypeToken<List<Courses>>(){}.getType());

        coursesArrayList.clear();
        coursesArrayList.addAll(serverList);
        courseAdapter.notifyDataSetChanged();

        saveTask();
    }

    private void saveTask() {
        @SuppressLint("StaticFieldLeak")
        class SaveTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                //creating a task
                List<CoursesRepo> allCourses = DatabaseClient.getInstance(getActivity())
                        .getAppDatabase().repoDao().getAllCourses();
                Log.d(TAG, allCourses.size() + "");

                if(allCourses.size() == 0){
                    for (int i = 0; i < serverList.size(); i++) {
                        CoursesRepo coursesRepo = new CoursesRepo();
                        coursesRepo.setCourseCode(serverList.get(i).getCourseCode());
                        coursesRepo.setCourseTitle(serverList.get(i).getCourseTitle());
                        coursesRepo.setCourseType(serverList.get(i).getCourseType());
                        coursesRepo.setCreditUnit(serverList.get(i).getCreditUnit());

                        DatabaseClient.getInstance(Objects.requireNonNull(getContext()).getApplicationContext()).getAppDatabase().repoDao().insert(coursesRepo);
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getActivity(), "Saved", Toast.LENGTH_LONG).show();
            }
        }

        SaveTask st = new SaveTask();
        st.execute();
    }

}
