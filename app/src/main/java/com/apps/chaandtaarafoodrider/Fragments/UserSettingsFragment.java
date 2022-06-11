package com.apps.chaandtaarafoodrider.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.apps.chaandtaarafoodrider.Activities.EmailVerification;
import com.apps.chaandtaarafoodrider.Activities.LoginActivity;
import com.apps.chaandtaarafoodrider.R;
import com.apps.chaandtaarafoodrider.Utils.SharedPreference;
import com.google.firebase.auth.FirebaseAuth;


public class UserSettingsFragment extends Fragment {


    ConstraintLayout profileSettings,manageAddresses,ordersHistory,termsAndConditions,privacyPolicy,logoutSettings;

    public UserSettingsFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_user_settings, container, false);
        profileSettings=view.findViewById(R.id.profileSettings);
        manageAddresses=view.findViewById(R.id.ManageAddressSettings);
        ordersHistory=view.findViewById(R.id.OrderHistorySettings);
        termsAndConditions=view.findViewById(R.id.termsAndConditionSettings);
        privacyPolicy=view.findViewById(R.id.privacyPolicySettings);
        logoutSettings=view.findViewById(R.id.LogoutSettings);


        profileSettings.setOnClickListener(view16 -> {
            Navigation.findNavController(view16).navigate(R.id.action_userSettingsFragment_to_accountAndProfile);

        });

        manageAddresses.setOnClickListener(view15 -> {
            Navigation.findNavController(view15).navigate(R.id.action_userSettingsFragment_to_manageAddresses);

        });
        ordersHistory.setOnClickListener(view14 -> {
            Navigation.findNavController(view14).navigate(R.id.action_userSettingsFragment_to_ordersHistory);

        });
        termsAndConditions.setOnClickListener(view13 -> {
            Navigation.findNavController(view13).navigate(R.id.action_userSettingsFragment_to_termsAndConditions);

        });
        privacyPolicy.setOnClickListener(view12 -> {
            Navigation.findNavController(view12).navigate(R.id.action_userSettingsFragment_to_privacyPolicy);

        });

        logoutSettings.setOnClickListener(view1 -> {
            if(getActivity()!=null){
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(getActivity(), "Logout Successful", Toast.LENGTH_SHORT).show();
                SharedPreference sharedPreference=new SharedPreference(getActivity());
                sharedPreference.clearSharedPrefs();
               Intent intent= new Intent(getActivity(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
                getActivity().startActivity(intent);
            }
        });

        return view;
    }
}