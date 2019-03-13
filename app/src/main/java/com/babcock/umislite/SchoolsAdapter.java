package com.babcock.umislite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.babcock.umislite.Discover.Facility;
import com.bumptech.glide.Glide;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

public class SchoolsAdapter extends RecyclerView.Adapter<SchoolsAdapter.SchoolsViewHolder> {
    private static final String TAG = "SchoolsAdapter";
    private List<Schools> schoolsList;
    private Context context;
    private static final String IP_ADDRESS = "192.168.43.93:8080";

    public SchoolsAdapter(List<Schools> schoolsList, Context context) {
        this.schoolsList = schoolsList;
        this.context = context;
    }

    @NonNull
    @Override
    public SchoolsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.schools_item, parent, false);
        return new SchoolsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SchoolsViewHolder holder, int position) {
        
        Schools schools = schoolsList.get(position);
        
        holder.bindSchools(schools);

    }

    @Override
    public int getItemCount() {
        return schoolsList != null ? schoolsList.size() : 0;
    }

    public class SchoolsViewHolder extends RecyclerView.ViewHolder{
        private AppCompatImageView schoolImage;
        private TextView schoolName;
        private TextView schoolAbout;
        public SchoolsViewHolder(@NonNull View itemView) {
            super(itemView);
            schoolImage = itemView.findViewById(R.id.school_layout);
            schoolName = itemView.findViewById(R.id.tv_school_name);
            schoolAbout = itemView.findViewById(R.id.school_about);
        }

        public void bindSchools(Schools schools) {
            String imageUrl = "http://" + IP_ADDRESS + "/umislite/schoolimages/" + schools.getSchoolImage();
            schoolName.setText(MessageFormat.format("{0} ({1})", schools.getSchoolName(), schools.getSchoolCode()));
            schoolAbout.setText(schools.getSchoolAbout());

            Glide.with(context)
                    .load(imageUrl)
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.loading)
                    .into(schoolImage);


//            ImageLoader imageLoader = MySingleton.getInstance(context).getImageLoader();
//
//            imageLoader.get(imageUrl, new ImageLoader.ImageListener() {
//                @Override
//                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
//                    if(response.getBitmap() != null){
//                        Drawable drawable = new BitmapDrawable(response.getBitmap());
////                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
////                            schoolImage.setBackground(drawable);
////                        }else{
////                            schoolImage.setBackgroundDrawable(drawable);
////                        }
////
//
//                    }
//                }
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    Log.e(TAG, error.getMessage());
//                }
//
//
//            });

        }
    }
}
