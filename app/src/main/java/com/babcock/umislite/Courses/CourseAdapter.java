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

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    private static final String TAG = "CourseAdapter";
    private List<Courses> coursesList;
    private Context context;
    private static final String IP_ADDRESS = "192.168.43.93:8080";

    private List<Courses> selectedList;

    CourseAdapter(Context activity, ArrayList<Courses> coursesArrayList) {
        this.context = activity;
        this.coursesList = coursesArrayList;
        selectedList = new ArrayList<>();
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.course_item, parent, false);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        Courses courses = coursesList.get(position);
        if(!courses.isSelected())
            holder.bindCourses(courses, position);
        else{
            Log.d(TAG, "this is selected " + courses.toString());
        }
    }

    @Override
    public int getItemCount() {
        return coursesList != null ? coursesList.size() : 0;
    }


    class CourseViewHolder extends RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener{

        private TextView courseName;
        private TextView creditUnit;
        private TextView courseType;
        private AppCompatCheckBox courseSelected;
        private CircleImageView tutorImage;
        private AppCompatButton viewCourse;
        private Courses courses;
        CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(v -> ToDo.showBottomSheet());
            courseName = itemView.findViewById(R.id.tv_course_name);
            creditUnit = itemView.findViewById(R.id.credit_unit);
            tutorImage = itemView.findViewById(R.id.tutor_image);
            courseType = itemView.findViewById(R.id.course_type);
            courseSelected = itemView.findViewById(R.id.course_selected);
            courseSelected.setOnCheckedChangeListener(this);
            viewCourse = itemView.findViewById(R.id.view_course);
            viewCourse.setOnClickListener(v -> ToDo.showBottomSheet());
        }

        void bindCourses(Courses courses, int position) {
            this.courses = courses;
            courseName.setText(MessageFormat.format("{0} ({1})", courses.getCourseTitle(), courses.getCourseCode()));
            creditUnit.setText(MessageFormat.format("{0}Units", courses.getCreditUnit()));
            courseSelected.setChecked(false);

            if(courses.getCourseType().equals("Core")){
                courseType.setTextColor(Color.RED);
            }else{
                courseType.setTextColor(Color.GREEN);
            }
            courseType.setText(courses.getCourseType());

        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked){
                selectedList.add(courses);
                Log.d(TAG, selectedList.toString());
                SelectedFragment.addCourse(selectedList);
                courses.setSelected(true);
                selectedList.clear();
                removeAt(getAdapterPosition(), this);
            }
        }

        private void removeAt(int adapterPosition, CourseViewHolder courseViewHolder) {
            coursesList.remove(adapterPosition);
            notifyDataSetChanged();
        }
    }
}
