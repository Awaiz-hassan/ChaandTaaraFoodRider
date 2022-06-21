package com.apps.chaandtaarafoodrider.Fragments;

import android.os.Bundle;
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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {


    SharedPreference sharedPreference;
    TextView name;
    RecyclerView popularProducts, fastFoodRecycler, desiFoodRecycler, bbqRecycler, meatRecycler;
    FoodItemAdapter popularFoodsAdapter, fastFoodAdapter, desiFoodAdapter, bbqAdapter, meatAdapter;
    List<FoodItemModel> popularFoodList, fastFoodList, desiFoodList, bbqList, meatList;
    ShimmerFrameLayout popularFoodShimmer, fastFoodShimmer, desiFoodShimmer, bbqShimmer, meatShimmer;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        name = view.findViewById(R.id.name);
        sharedPreference = new SharedPreference(getActivity());


        // Set PopularFood
        popularFoodList = new ArrayList<>();
        popularProducts = view.findViewById(R.id.popularProducts);
        popularFoodShimmer = view.findViewById(R.id.shimmer_view_container);
        popularFoodsAdapter = new FoodItemAdapter(getActivity(), popularFoodList, sharedPreference.getUserId());
        popularProducts.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        popularProducts.setAdapter(popularFoodsAdapter);


        // set FastFood
        fastFoodList = new ArrayList<>();
        fastFoodRecycler = view.findViewById(R.id.fastFoodRecycler);
        fastFoodShimmer = view.findViewById(R.id.shimmer_view_container2);
        fastFoodAdapter = new FoodItemAdapter(getActivity(), fastFoodList, sharedPreference.getUserId());
        fastFoodRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        fastFoodRecycler.setAdapter(fastFoodAdapter);


        // set DesiFood
        desiFoodList = new ArrayList<>();
        desiFoodRecycler = view.findViewById(R.id.desiFoodRecycler);
        desiFoodShimmer = view.findViewById(R.id.shimmer_view_container3);
        desiFoodAdapter = new FoodItemAdapter(getActivity(), desiFoodList, sharedPreference.getUserId());
        desiFoodRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        desiFoodRecycler.setAdapter(desiFoodAdapter);

        // setBBQ
        bbqList = new ArrayList<>();
        bbqRecycler = view.findViewById(R.id.bbqFoodRecycler);
        bbqShimmer = view.findViewById(R.id.shimmer_view_container4);
        bbqAdapter = new FoodItemAdapter(getActivity(), bbqList, sharedPreference.getUserId());
        bbqRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        bbqRecycler.setAdapter(bbqAdapter);


        // setMeat
        meatList = new ArrayList<>();
        meatRecycler = view.findViewById(R.id.meatRecycler);
        meatShimmer = view.findViewById(R.id.shimmer_view_container5);
        meatAdapter = new FoodItemAdapter(getActivity(), meatList, sharedPreference.getUserId());
        meatRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        meatRecycler.setAdapter(meatAdapter);


        if (sharedPreference.getUserName() != null) {
            name.setText(sharedPreference.getUserName());
        } else {
            getUsername();
        }
        getPopularProducts();
        getFastFood();
        getDesiFood();
        getBBQ();
        getMeat();

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

        popularFoodShimmer.startShimmer();
        popularFoodShimmer.setVisibility(View.VISIBLE);
        FirebaseDatabase.getInstance().getReference("PopularFoodItems").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                popularFoodList.clear();
                popularFoodShimmer.stopShimmer();
                popularFoodShimmer.setVisibility(View.GONE);

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
            }

        });


    }


    void getFastFood() {

        fastFoodShimmer.startShimmer();
        fastFoodShimmer.setVisibility(View.VISIBLE);
        FirebaseDatabase.getInstance().getReference("FoodItems").orderByChild("category").equalTo("fastfood").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                fastFoodList.clear();
                fastFoodShimmer.stopShimmer();
                fastFoodShimmer.setVisibility(View.GONE);

                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        FoodItemModel foodItemModel = snapshot.getValue(FoodItemModel.class);

                        fastFoodList.add(foodItemModel);
                    }
                    fastFoodAdapter.notifyDataSetChanged();
                } else {
                    fastFoodShimmer.stopShimmer();
                    fastFoodShimmer.setVisibility(View.GONE);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                fastFoodShimmer.stopShimmer();
                fastFoodShimmer.setVisibility(View.GONE);
            }

        });


    }


    void getDesiFood() {

        desiFoodShimmer.startShimmer();
        desiFoodShimmer.setVisibility(View.VISIBLE);
        FirebaseDatabase.getInstance().getReference("FoodItems").orderByChild("category").equalTo("desifood").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                desiFoodList.clear();
                desiFoodShimmer.stopShimmer();
                desiFoodShimmer.setVisibility(View.GONE);

                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        FoodItemModel foodItemModel = snapshot.getValue(FoodItemModel.class);

                        desiFoodList.add(foodItemModel);
                    }
                    desiFoodAdapter.notifyDataSetChanged();
                } else {
                    desiFoodShimmer.stopShimmer();
                    desiFoodShimmer.setVisibility(View.GONE);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                popularFoodShimmer.stopShimmer();
                popularFoodShimmer.setVisibility(View.GONE);
            }

        });


    }


    void getBBQ() {

        bbqShimmer.startShimmer();
        bbqShimmer.setVisibility(View.VISIBLE);
        FirebaseDatabase.getInstance().getReference("FoodItems").orderByChild("category").equalTo("bbq").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                bbqList.clear();
                bbqShimmer.stopShimmer();
                bbqShimmer.setVisibility(View.GONE);

                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        FoodItemModel foodItemModel = snapshot.getValue(FoodItemModel.class);
                        bbqList.add(foodItemModel);
                    }
                    bbqAdapter.notifyDataSetChanged();
                } else {
                    bbqShimmer.stopShimmer();
                    bbqShimmer.setVisibility(View.GONE);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                bbqShimmer.stopShimmer();
                bbqShimmer.setVisibility(View.GONE);
            }

        });


    }

    void getMeat() {
        meatShimmer.startShimmer();
        meatShimmer.setVisibility(View.VISIBLE);
        FirebaseDatabase.getInstance().getReference("FoodItems").orderByChild("category").equalTo("meat").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                meatList.clear();
                meatShimmer.stopShimmer();
                meatShimmer.setVisibility(View.GONE);

                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        FoodItemModel foodItemModel = snapshot.getValue(FoodItemModel.class);

                        meatList.add(foodItemModel);
                    }
                    meatAdapter.notifyDataSetChanged();
                } else {
                    meatShimmer.stopShimmer();
                    meatShimmer.setVisibility(View.GONE);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                meatShimmer.stopShimmer();
                meatShimmer.setVisibility(View.GONE);
            }

        });


    }
}