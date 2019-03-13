package com.babcock.umislite.Discover;


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

import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.babcock.umislite.DatabaseClient;
import com.babcock.umislite.MySingleton;
import com.babcock.umislite.R;
import com.babcock.umislite.SchoolRepo;
import com.babcock.umislite.Schools;
import com.babcock.umislite.SchoolsAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.UnsupportedEncodingException;
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
public class SchoolsFragment extends Fragment {
    private static final String TAG = "SchoolsFragment";
    private static final String IP_ADDRESS = "192.168.43.93:8080";
    private static final String URL = "http://" + IP_ADDRESS + "/umislite/school.php";

    private List<Schools> schoolsList;
    private ArrayList<Schools> schoolsArrayList;
    private SchoolsAdapter schoolsAdapter;

    public SchoolsFragment() {
        // Required empty public constructor
    }

    public static SchoolsFragment newInstance(){
        return new SchoolsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_schools, container, false);
        RecyclerView rv_schools = view.findViewById(R.id.rv_schools);
        schoolsArrayList = new ArrayList<>();
        rv_schools.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_schools.setItemAnimator(new DefaultItemAnimator());
        rv_schools.setHasFixedSize(true);
        schoolsAdapter = new SchoolsAdapter(schoolsArrayList, getActivity());
        rv_schools.setAdapter(schoolsAdapter);

        final Context context = container.getContext();


        ConnectivityManager connectivityManager = (ConnectivityManager) Objects.requireNonNull(getActivity())
                .getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnectedOrConnecting() && schoolsArrayList != null){
            Log.d(TAG, "In Fetch from server");
            fetchFromServer(context);
        }else{
            Log.d(TAG, "In Fetch from Room");
            fetchFromRoom();
        }
        return view;
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
        ){
            @Override
            protected void deliverResponse(String response) {
                super.deliverResponse(response);
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                try{
                    Cache.Entry entry = HttpHeaderParser.parseCacheHeaders(response);
                    if(entry == null){
                        entry = new Cache.Entry();
                    }

                    final long cacheHitButRefused = 3 * 60 * 100;
                    final long cacheExpired = 24 * 60 * 60 * 1000;
                    long now = System.currentTimeMillis();
                    final long softExpire = now + cacheHitButRefused;
                    final long ttl = now + cacheExpired;

                    entry.data = response.data;
                    entry.softTtl = softExpire;
                    entry.ttl = ttl;

                    String headerValue;
                    headerValue = response.headers.get("Date");
                    if(headerValue != null){
                        entry.serverDate = HttpHeaderParser.parseDateAsEpoch(headerValue);
                    }
                    headerValue = response.headers.get("Last-Modified");
                    if(headerValue !=  null){
                        entry.lastModified = HttpHeaderParser.parseDateAsEpoch(headerValue);
                    }

                    entry.responseHeaders = response.headers;
                    final String responseString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                    return Response.success(responseString, entry);
                }catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                }
            }

            @Override
            protected VolleyError parseNetworkError(VolleyError volleyError) {
                return super.parseNetworkError(volleyError);
            }

        };
        stringRequest.setShouldCache(false);
        MySingleton.getInstance(context).addToRequestQueue(stringRequest);

    }

    private void fetchFromRoom() {

        Thread thread = new Thread(() -> {
            List<SchoolRepo> schoolRepos = DatabaseClient.getInstance(getActivity())
                    .getAppDatabase().repoDao().getAll();
           schoolsArrayList.clear();
            for(SchoolRepo schoolRepo : schoolRepos){
                Schools schools = new Schools(schoolRepo.getSchoolId(),
                        schoolRepo.getSchoolCode(),
                        schoolRepo.getSchoolName(),
                        schoolRepo.getSchoolAbout(),
                        schoolRepo.getSchoolImaage());
                Log.d(TAG, schools.toString());
                schoolsArrayList.add(schools);
            }

            Objects.requireNonNull(getActivity()).runOnUiThread(() -> {
                Log.d(TAG, "Loading Room Data");
                schoolsAdapter.notifyDataSetChanged();
            });
        });

        thread.start();
    }


    private void responseImpl(String s) {
        Log.d(TAG, s);

        schoolsList = new Gson().fromJson(s, new TypeToken<List<Schools>>(){}.getType());
        Log.d(TAG, schoolsList.toString());

        schoolsArrayList.clear();
        schoolsArrayList.addAll(schoolsList);

        schoolsAdapter.notifyDataSetChanged();


        saveTask();
    }

    private void saveTask() {
        @SuppressLint("StaticFieldLeak")
        class SaveTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                //creating a task
                List<SchoolRepo> schoolRepos = DatabaseClient.getInstance(getActivity())
                        .getAppDatabase().repoDao().getAll();
                Log.d(TAG, schoolRepos.size() + "");

                if(schoolRepos.size() == 0){
                    for (int i = 0; i < schoolsList.size(); i++) {
                        SchoolRepo schoolRepo = new SchoolRepo();

                        schoolRepo.setSchoolId(schoolsList.get(i).getSchoolId());
                        schoolRepo.setSchoolCode(schoolsList.get(i).getSchoolCode());
                        schoolRepo.setSchoolName(schoolsList.get(i).getSchoolName());
                        schoolRepo.setSchoolAbout(schoolsList.get(i).getSchoolAbout());
                        schoolRepo.setSchoolImaage(schoolsList.get(i).getSchoolImage());

                        DatabaseClient.getInstance(Objects.requireNonNull(getContext()).getApplicationContext()).getAppDatabase().repoDao().insert(schoolRepo);
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
