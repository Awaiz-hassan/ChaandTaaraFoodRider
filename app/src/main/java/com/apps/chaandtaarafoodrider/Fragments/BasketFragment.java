package com.apps.chaandtaarafoodrider.Fragments;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.chaandtaarafoodrider.Adapters.BasketAdapter;
import com.apps.chaandtaarafoodrider.Model.CartItem;
import com.apps.chaandtaarafoodrider.R;
import com.apps.chaandtaarafoodrider.Utils.SharedPreference;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BasketFragment extends Fragment {


    private List<CartItem> cartItemList;
    private RecyclerView recyclerBasket;
    private BasketAdapter basketAdapter;
    private SharedPreference sharedPreference;
    private ShimmerFrameLayout basketShimmer;
    private TextView priceText;
    double price=0;

    public BasketFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_basket, container, false);
        cartItemList = new ArrayList<>();
        basketShimmer = view.findViewById(R.id.shimmer_view_container);
        sharedPreference = new SharedPreference(getActivity());
        recyclerBasket = view.findViewById(R.id.basketRecycler);
        priceText=view.findViewById(R.id.textView24);
        basketAdapter = new BasketAdapter(getActivity(), cartItemList, sharedPreference.getUserId());
        recyclerBasket.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerBasket.setAdapter(basketAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerBasket);
        getBasketList();

        return view;
    }

    void getBasketList() {
        basketShimmer.startShimmer();
        basketShimmer.setVisibility(View.VISIBLE);
        FirebaseDatabase.getInstance().getReference("Basket").child(sharedPreference.getUserId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                basketShimmer.stopShimmer();
                basketShimmer.setVisibility(View.GONE);
                if (dataSnapshot.exists()) {
                    cartItemList.clear();
                    price=0;
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        CartItem foodItemModel = snapshot.getValue(CartItem.class);
                        if(foodItemModel!=null)
                        price=price+Double.parseDouble(foodItemModel.getPrice());
                        cartItemList.add(foodItemModel);
                    }
                    basketAdapter.notifyDataSetChanged();

                } else {
                    if (getActivity() != null) {
                        Toast.makeText(getActivity(), "Basket Empty", Toast.LENGTH_SHORT).show();
                        price=0;
                        cartItemList.clear();
                        basketAdapter.notifyDataSetChanged();

                    }
                }
                if(priceText!=null){
                    if(price==0){
                        priceText.setText("PKR 0.00");
                    }
                    else{
                        priceText.setText("PKR " + price);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                basketShimmer.stopShimmer();
                basketShimmer.setVisibility(View.GONE);
            }
        });
    }

    ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
            if (basketAdapter != null & sharedPreference != null) {
                FirebaseDatabase.getInstance().getReference().child("Basket")
                        .child(sharedPreference.getUserId())
                        .child(cartItemList.get(viewHolder.getAdapterPosition()).getId()).removeValue();
                basketAdapter.notifyItemRemoved(viewHolder.getLayoutPosition());
            }
        }
    };


}