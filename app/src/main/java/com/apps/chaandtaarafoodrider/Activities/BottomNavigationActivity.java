package com.apps.chaandtaarafoodrider.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.apps.chaandtaarafoodrider.Fragments.BasketFragment;
import com.apps.chaandtaarafoodrider.Fragments.FavoriteFragment;
import com.apps.chaandtaarafoodrider.Fragments.HomeFragment;
import com.apps.chaandtaarafoodrider.Fragments.SearchFragment;
import com.apps.chaandtaarafoodrider.Fragments.UserSettingsFragment;
import com.apps.chaandtaarafoodrider.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomNavigationActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);
        loadFragment(new HomeFragment());
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        fragment = new HomeFragment();
                        loadFragment(fragment);
                        return true;
                    case R.id.navigation_search:
                        fragment = new SearchFragment();
                        loadFragment(fragment);
                        return true;
                    case R.id.navigation_basket:
                        fragment = new BasketFragment();
                        loadFragment(fragment);
                        return true;
                    case R.id.navigation_favourite:
                        fragment = new FavoriteFragment();
                        loadFragment(fragment);
                    case R.id.navigation_uer:
                        fragment = new UserSettingsFragment();
                        loadFragment(fragment);
                        return true;
                }

                return false;
            };

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.commit();
    }
}