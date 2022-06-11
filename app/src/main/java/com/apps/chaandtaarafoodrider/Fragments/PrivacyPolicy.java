package com.apps.chaandtaarafoodrider.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.apps.chaandtaarafoodrider.R;

public class PrivacyPolicy extends Fragment {

    public PrivacyPolicy() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_privacy_policy, container, false);
        ImageButton topBack=view.findViewById(R.id.imageButton2);
        topBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getActivity()!=null)getActivity().onBackPressed();
            }
        });
        return view;
    }
}