package com.babcock.umislite.Discover;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.babcock.umislite.R;
import com.bumptech.glide.Glide;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

public class FacilityAdapter extends RecyclerView.Adapter<FacilityAdapter.FacilityViewHolder> {

    private static final String TAG = "FacilityAdapter";
    private List<Facility> facilityList;
    private Context context;
    private static final String IP_ADDRESS = "192.168.43.93:8080";

    FacilityAdapter(ArrayList<Facility> facilityArrayList, FragmentActivity activity) {
        this.facilityList = facilityArrayList;
        this.context = activity;
    }

    @NonNull
    @Override
    public FacilityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.facility_item, parent, false);
        return new FacilityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FacilityViewHolder holder, int position) {
        Facility facility = facilityList.get(position);

        holder.bindFacilityItems(facility);
    }

    @Override
    public int getItemCount() {
        return facilityList.size();
    }


    class FacilityViewHolder extends RecyclerView.ViewHolder {

        private AppCompatImageView facilityImage;
        private TextView facilityName;
        private TextView facilityAbout;

        FacilityViewHolder(@NonNull View itemView) {
            super(itemView);
            facilityImage = itemView.findViewById(R.id.facility_layout);
            facilityName = itemView.findViewById(R.id.tv_facility_name);
            facilityAbout = itemView.findViewById(R.id.facility_about);
        }

        void bindFacilityItems(Facility facility) {

            String imageUrl = "http://" + IP_ADDRESS + "/umislite/facilityimages/" + facility.getFacilityImage();
            facilityName.setText(facility.getFacilityName());
            facilityAbout.setText(facility.getFacilityAbout());

            Glide.with(context)
                    .load(imageUrl)
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.loading)
                    .into(facilityImage);
        }
    }
}

