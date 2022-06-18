package com.apps.chaandtaarafoodrider.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.chaandtaarafoodrider.Adapters.FoodItemAdapter;
import com.apps.chaandtaarafoodrider.Model.FoodItemModel;
import com.apps.chaandtaarafoodrider.R;
import com.apps.chaandtaarafoodrider.Utils.SharedPreference;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {


    SharedPreference sharedPreference;
    TextView name;
    RecyclerView popularProducts;
    FoodItemAdapter popularFoodsAdapter;
    List<FoodItemModel> popularFoodList;
    ShimmerFrameLayout popularFoodShimmer;
    DatabaseReference popularFoodRef;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        name = view.findViewById(R.id.name);
        popularFoodList = new ArrayList<>();
        sharedPreference = new SharedPreference(getActivity());


        // Set popular food adapter
        popularFoodRef = FirebaseDatabase.getInstance().getReference("PopularFoodItems");
        popularProducts = view.findViewById(R.id.popularProducts);
        popularFoodShimmer = view.findViewById(R.id.shimmer_view_container);
        popularFoodsAdapter = new FoodItemAdapter(getActivity(), popularFoodList);
        popularProducts.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        popularProducts.setAdapter(popularFoodsAdapter);


        if (sharedPreference.getUserName() != null) {
            name.setText(sharedPreference.getUserName());
        } else {
            getUsername();
        }
        getPopularProducts();

        return view;
    }


    void getUsername() {
        FirebaseDatabase.getInstance().getReference("Users").child(sharedPreference.getUserId()).child("username").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String userName = snapshot.getValue(String.class);
                if (sharedPreference != null) {
                    sharedPreference.setUserName(userName);
                    if (name != null) name.setText(userName);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    void getPopularProducts() {
        Log.d("TAG12", "snapshot error: ");

        popularFoodShimmer.startShimmer();
        popularFoodShimmer.setVisibility(View.VISIBLE);
        FirebaseDatabase.getInstance().getReference("PopularFoodItems").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                popularFoodList.clear();
                popularFoodShimmer.stopShimmer();
                popularFoodShimmer.setVisibility(View.GONE);
                Log.d("TAG12", "snapshot error: ");

                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        FoodItemModel foodItemModel = snapshot.getValue(FoodItemModel.class);

                        popularFoodList.add(foodItemModel);
                    }
                    popularFoodsAdapter.notifyDataSetChanged();
                } else {
                    popularFoodShimmer.stopShimmer();
                    popularFoodShimmer.setVisibility(View.GONE);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                popularFoodShimmer.stopShimmer();
                popularFoodShimmer.setVisibility(View.GONE);
                Log.d("TAG12", "onCancelled: ");
            }

        });


    }
}