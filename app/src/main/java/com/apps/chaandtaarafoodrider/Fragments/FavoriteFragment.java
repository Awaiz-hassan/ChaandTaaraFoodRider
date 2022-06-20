package com.apps.chaandtaarafoodrider.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.chaandtaarafoodrider.Adapters.FavAdapter;
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


public class FavoriteFragment extends Fragment {


    RecyclerView favRecycler;
    List<FoodItemModel> favFoodList;
    FavAdapter favAdapter;
    SharedPreference sharedPreference;
    ShimmerFrameLayout favLoadingShimmer;

    public FavoriteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        favRecycler = view.findViewById(R.id.favouriteRecycler);
        favLoadingShimmer=view.findViewById(R.id.shimmer_view_container);
        favFoodList = new ArrayList<>();
        sharedPreference = new SharedPreference(getActivity());
        favAdapter = new FavAdapter(getActivity(), favFoodList, sharedPreference.getUserId());
        favRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        favRecycler.setAdapter(favAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(favRecycler);
        getFavFoodList();
        return view;
    }

    void getFavFoodList() {
        favLoadingShimmer.startShimmer();
        favLoadingShimmer.setVisibility(View.VISIBLE);
        FirebaseDatabase.getInstance().getReference("Likes").child(sharedPreference.getUserId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                favLoadingShimmer.stopShimmer();
                favLoadingShimmer.setVisibility(View.GONE);
                if (dataSnapshot.exists()) {
                    favFoodList.clear();

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        FoodItemModel foodItemModel = snapshot.getValue(FoodItemModel.class);

                        favFoodList.add(foodItemModel);
                    }
                    favAdapter.notifyDataSetChanged();

                } else {
                    if (getActivity() != null){
                        Toast.makeText(getActivity(), "NO Favourites Found", Toast.LENGTH_SHORT).show();
                        favFoodList.clear();
                        favAdapter.notifyDataSetChanged();

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                favLoadingShimmer.stopShimmer();
                favLoadingShimmer.setVisibility(View.GONE);
            }
        });
    }

    ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
            if (favAdapter != null&sharedPreference!=null){
                FirebaseDatabase.getInstance().getReference().child("Likes")
                        .child(sharedPreference.getUserId())
                        .child(favFoodList.get(viewHolder.getAdapterPosition()).getId()).removeValue();
                favAdapter.notifyItemRemoved(viewHolder.getLayoutPosition());}
        }
    };

}