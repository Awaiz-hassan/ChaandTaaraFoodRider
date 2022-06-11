package com.apps.chaandtaarafoodrider.Fragments;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.apps.chaandtaarafoodrider.R;

public class AccountAndProfile extends Fragment {


    public AccountAndProfile() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_account_and_profile, container, false);
        ImageButton topBack=view.findViewById(R.id.imageButton2);

        ConstraintLayout changePassword=view.findViewById(R.id.changePasswordSettings);
        changePassword.setOnClickListener(view1 -> Navigation.findNavController(view1).navigate(R.id.action_accountAndProfile_to_changePassword));
        topBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getActivity()!=null)getActivity().onBackPressed();
            }
        });
        return view;
    }
}