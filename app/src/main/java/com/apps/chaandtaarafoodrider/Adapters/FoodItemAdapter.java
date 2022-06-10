package com.apps.chaandtaarafoodrider.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.chaandtaarafoodrider.Model.FoodItemModel;
import com.apps.chaandtaarafoodrider.R;

import java.util.List;

public class FoodItemAdapter extends RecyclerView.Adapter<FoodItemAdapter.FoodItemViewHolder> {

    private Context context;
    private List<FoodItemModel> foodItemList;


    public FoodItemAdapter(Context context, List<FoodItemModel> foodItemList) {
        this.context = context;
        this.foodItemList = foodItemList;
    }

    @NonNull
    @Override
    public FoodItemAdapter.FoodItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.food_item,parent,false);
        return new FoodItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodItemAdapter.FoodItemViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class FoodItemViewHolder extends RecyclerView.ViewHolder{
         ImageView foodImage;
         TextView foodName;
         TextView foodPrice;
        public FoodItemViewHolder(@NonNull View itemView) {
            super(itemView);
            foodImage=itemView.findViewById(R.id.imageView13);
            foodName=itemView.findViewById(R.id.textView8);
            foodPrice=itemView.findViewById(R.id.textView9);

        }
    }
}
