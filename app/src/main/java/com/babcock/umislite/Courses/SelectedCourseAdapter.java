package com.babcock.umislite.Courses;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.babcock.umislite.R;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.core.widget.CompoundButtonCompat;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class SelectedCourseAdapter extends RecyclerView.Adapter<SelectedCourseAdapter.SelectCourseViewHolder> {

    private static final String TAG = "SelectedCourseAdapter";
    private List<Courses> coursesList;
    private Context context;
    private static final String IP_ADDRESS = "192.168.43.93:8080";

    private SelectCourseViewHolder viewHolder;


    SelectedCourseAdapter(Context activity, ArrayList<Courses> coursesArrayList) {
        Log.d(TAG, coursesArrayList.toString());
        this.context = activity;
        this.coursesList = coursesArrayList;
    }

    @NonNull
    @Override
    public SelectCourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.course_item, parent, false);
        return new SelectCourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectCourseViewHolder holder, int position) {
        this.viewHolder = holder;
        Courses courses = coursesList.get(position);
        holder.bindCourses(courses, position);
    }

    @Override
    public int getItemCount() {
        return coursesList != null ? coursesList.size() : 0;
    }

    void addSelectedCourse(List<Courses> courses) {
        coursesList.addAll(courses);
        notifyDataSetChanged();
    }


    class SelectCourseViewHolder extends RecyclerView.ViewHolder{

        private TextView courseName;
        private TextView creditUnit;
        private TextView courseType;
        private AppCompatCheckBox courseSelected;
        private CircleImageView tutorImage;
        private AppCompatButton viewCourse;
        private Courses courses;
        SelectCourseViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(v -> ToDo.showBottomSheet());
            courseName = itemView.findViewById(R.id.tv_course_name);
            creditUnit = itemView.findViewById(R.id.credit_unit);
            tutorImage = itemView.findViewById(R.id.tutor_image);
            courseType = itemView.findViewById(R.id.course_type);
            courseSelected = itemView.findViewById(R.id.course_selected);
            viewCourse = itemView.findViewById(R.id.view_course);
            viewCourse.setOnClickListener(v -> ToDo.showBottomSheet());
        }

        void bindCourses(Courses courses, int position) {
            Log.d(TAG, courses.toString());
            courseName.setText(MessageFormat.format("{0} ({1})", courses.getCourseTitle(), courses.getCourseCode()));
            creditUnit.setText(MessageFormat.format("{0}Units", courses.getCreditUnit()));
            courseSelected.setChecked(courses.isSelected());
            if(courses.getCourseType().equals("Core")){
                courseType.setTextColor(Color.RED);
            }else{
                courseType.setTextColor(Color.GREEN);
            }
            courseType.setText(courses.getCourseType());

        }


//        @Override
//        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//            if(isChecked){
//                selectedList.add(courses);
//                courses.setSelected(true);
//                removeAt(getAdapterPosition(), this);
//            }
//        }

//        private void removeAt(int adapterPosition, SelectCourseViewHolder courseViewHolder) {
//
//            coursesList.remove(adapterPosition);
//            SelectedFragment.addCourse(courses);
//            notifyDataSetChanged();
//        }
    }
}
