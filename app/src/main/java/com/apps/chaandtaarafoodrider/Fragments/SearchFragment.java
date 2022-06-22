package com.apps.chaandtaarafoodrider.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.apps.chaandtaarafoodrider.Adapters.SearchAdapter;
import com.apps.chaandtaarafoodrider.Model.FoodItemModel;
import com.apps.chaandtaarafoodrider.R;
import com.apps.chaandtaarafoodrider.Utils.SharedPreference;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class SearchFragment extends Fragment {


    List<FoodItemModel> searchList;
    SearchAdapter searchAdapter;
    RecyclerView searchRecycler;
    int scrollPosition=0;
    SharedPreference sharedPreference;
    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_search, container, false);
        searchRecycler=view.findViewById(R.id.searchRecycler);
        searchList=new ArrayList<>();
        sharedPreference=new SharedPreference(getActivity());
        searchAdapter=new SearchAdapter(getActivity(),searchList,sharedPreference.getUserId());
        searchRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        searchRecycler.setAdapter(searchAdapter);

        if(scrollPosition>0){
            searchRecycler.scrollTo(0,scrollPosition);
        }

        EditText searchText= view.findViewById(R.id.editText);
        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length()>0){
                    searchFood(charSequence.toString().toLowerCase());}
                else{
                    searchList.clear();
                    searchAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return view;
    }
    private void searchFood(String s) {
        Log.d("TAG", "searchFood: ");
        Query query = FirebaseDatabase.getInstance().getReference("FoodItems").orderByChild("searchName")
                .startAt(s)
                .endAt(s+"\uf8ff");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                searchList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    FoodItemModel foodItemModel = snapshot.getValue(FoodItemModel.class);
                    searchList.add(foodItemModel);
                }
                searchAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onDestroyView() {
        if(searchRecycler!=null){
            scrollPosition=searchRecycler.getScrollY();
        }
        super.onDestroyView();
    }
}