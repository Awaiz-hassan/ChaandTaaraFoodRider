package com.apps.chaandtaarafoodrider.Activities;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.apps.chaandtaarafoodrider.R;
import com.apps.chaandtaarafoodrider.Utils.SharedPreference;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BottomNavigationActivity extends AppCompatActivity {

    SharedPreference sharedPreference;
    BadgeDrawable badge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);
        sharedPreference = new SharedPreference(BottomNavigationActivity.this);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_bottom_navigation);
        NavController navController = navHostFragment.getNavController();
        BottomNavigationView navView = findViewById(R.id.bottomNavigationView);
        NavigationUI.setupWithNavController(navView, navController);;


        badge = navView.getOrCreateBadge(R.id.basketFragment);
        badge.setBackgroundColor(getResources().getColor(R.color.purple_700));
        badge.setVisible(false);


        FirebaseDatabase.getInstance().getReference("Basket").child(sharedPreference.getUserId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    long l = snapshot.getChildrenCount();
                    int i = (int) l;
                    if (badge != null) {
                        badge.setNumber(i);
                        badge.setVisible(true);
                    }
                } else {
                    if (badge != null) {
                        badge.setVisible(false);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                if (badge != null) badge.setVisible(false);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}