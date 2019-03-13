package com.babcock.umislite.Discover;


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
import com.babcock.umislite.SchoolsAdapter;
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
public class FacilitiesFragment extends Fragment {

    private static final String TAG = "FacilitiesFragment";
    private static final String IP_ADDRESS = "192.168.43.93:8080";
    private static final String URL = "http://" + IP_ADDRESS + "/umislite/facility.php";

    private ArrayList<Facility> facilityArrayList;
    private FacilityAdapter facilityAdapter;
    private List<Facility> facilityList;

    public FacilitiesFragment() {
        // Required empty public constructor
    }
    public static FacilitiesFragment newInstance(){
        return new FacilitiesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_facilities, container, false);
        RecyclerView rv_facility = v.findViewById(R.id.rv_facility);
        facilityArrayList = new ArrayList<>();
        rv_facility.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_facility.setItemAnimator(new DefaultItemAnimator());
        rv_facility.setHasFixedSize(true);
        facilityAdapter = new FacilityAdapter(facilityArrayList, getActivity());
        rv_facility.setAdapter(facilityAdapter);

        final Context context = container.getContext();


        ConnectivityManager connectivityManager = (ConnectivityManager) Objects.requireNonNull(getActivity())
                .getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnectedOrConnecting() && facilityArrayList != null){
            Log.d(TAG, "In Fetch from server");
            fetchFromServer(context);
        }else{
            Log.d(TAG, "In Fetch from Room");
            fetchFromRoom();
        }
        return v;
    }

    private void fetchFromRoom() {
    }

    private void fetchFromServer(Context context) {

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL,
                this::responseImpl,
                error -> {
                    Log.e(TAG, error.toString());
                    Log.d(TAG, "Loading from Room After Server failure");
                    fetchFromRoom();
                }
        );

        MySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }

    private void responseImpl(String s) {
        Log.d(TAG, s);

        facilityList = new Gson().fromJson(s, new TypeToken<List<Facility>>(){}.getType());
        Log.d(TAG, facilityList.toString());

        facilityArrayList.clear();
        facilityArrayList.addAll(facilityList);

        facilityAdapter.notifyDataSetChanged();

    }

}
