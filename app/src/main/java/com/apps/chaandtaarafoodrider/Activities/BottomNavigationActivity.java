package com.apps.chaandtaarafoodrider.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.apps.chaandtaarafoodrider.Fragments.BasketFragment;
import com.apps.chaandtaarafoodrider.Fragments.FavoriteFragment;
import com.apps.chaandtaarafoodrider.Fragments.HomeFragment;
import com.apps.chaandtaarafoodrider.Fragments.SearchFragment;
import com.apps.chaandtaarafoodrider.Fragments.UserSettingsFragment;
import com.apps.chaandtaarafoodrider.R;
import com.apps.chaandtaarafoodrider.Utils.SharedPreference;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class BottomNavigationActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottomNavigationView);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_bottom_navigation);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }
}