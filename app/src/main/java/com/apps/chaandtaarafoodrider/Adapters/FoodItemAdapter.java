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
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

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
        View view = LayoutInflater.from(context).inflate(R.layout.food_item, parent, false);
        return new FoodItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodItemAdapter.FoodItemViewHolder holder, int position) {
        FoodItemModel foodItemModel = foodItemList.get(holder.getAdapterPosition());
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.image_place_holder)
                .error(R.drawable.image_place_holder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH);
        if (context != null) {
            if (foodItemModel.getImage() != null)
                Glide.with(context).load(foodItemModel.getImage())
                        .apply(options)
                        .into(holder.foodImage);
            if (foodItemModel.getName() != null) holder.foodName.setText(foodItemModel.getName());
            if (foodItemModel.getPrice() != null)
                holder.foodPrice.setText("PKR "+foodItemModel.getPrice());
        }
    }

    @Override
    public int getItemCount() {
        return foodItemList.size();
    }

    class FoodItemViewHolder extends RecyclerView.ViewHolder {
        ImageView foodImage;
        TextView foodName;
        TextView foodPrice;

        public FoodItemViewHolder(@NonNull View itemView) {
            super(itemView);
            foodImage = itemView.findViewById(R.id.imageView13);
            foodName = itemView.findViewById(R.id.textView8);
            foodPrice = itemView.findViewById(R.id.textView9);

        }
    }
}
