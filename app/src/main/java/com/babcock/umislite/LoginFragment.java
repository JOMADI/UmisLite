package com.babcock.umislite;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;

public class LoginFragment extends Fragment {

    private static final String TAG = "LoginFragment";
    private static final String IP_ADDRESS = "192.168.43.93:8080";
    private static String URL = "http://" + IP_ADDRESS + "/umislite/login.php?";


    public static LoginFragment newInstance(){

        return new LoginFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        AppCompatEditText userName = v.findViewById(R.id.username);
        AppCompatEditText password = v.findViewById(R.id.password);

        AppCompatButton login = v.findViewById(R.id.login);

        final Context context = getContext().getApplicationContext();


        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();


        login.setOnClickListener(v1 -> {
           String user = Objects.requireNonNull(userName.getText()).toString();
           String pass = Objects.requireNonNull(password.getText()).toString();

           Log.d(TAG, user + " " + pass);

           if(!user.isEmpty() && !pass.isEmpty()){
               if(networkInfo != null && networkInfo.isConnectedOrConnecting()){
                   Log.d(TAG, "In Fetch User");
                   fetchUser(context, user.trim(), pass.trim());
               }else{
                   Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
               }
           }

        });
        return v;
    }

    private void fetchUser(Context context, String user, String pass) {
        URL += "u=" + user + "&p=" + pass;
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL,
                this::responseImpl,
                error -> {
                    Log.d(TAG, error.toString());
                    Toast.makeText(getActivity(), "Check Internet Connection and try Again", Toast.LENGTH_SHORT).show();
                }
        );
        MySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }

    private void responseImpl(String s) {
        Log.d(TAG, s);
        if(s.equals("null") || s.isEmpty()){
            Toast.makeText(getActivity(), "Invalid Details", Toast.LENGTH_LONG).show();
            return;
        }
        List<Student> student =  new Gson().fromJson(s, new TypeToken<List<Student>>(){}.getType());
        Log.d(TAG, student.toString());
        Intent intent = MainActivity.newIntent(getActivity(), student.get(0).getId());
        startActivity(intent);
        getActivity().finish();
    }
}
