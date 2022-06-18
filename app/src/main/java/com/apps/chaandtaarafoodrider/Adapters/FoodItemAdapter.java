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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class FoodItemAdapter extends RecyclerView.Adapter<FoodItemAdapter.FoodItemViewHolder> {

    private Context context;
    private List<FoodItemModel> foodItemList;
    private String userId;

    public FoodItemAdapter(Context context, List<FoodItemModel> foodItemList, String userId) {
        this.context = context;
        this.foodItemList = foodItemList;
        this.userId = userId;
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
                holder.foodPrice.setText("PKR " + foodItemModel.getPrice());
        }
        if (context != null)
            if (foodItemModel.getId() != null && holder.like != null && userId != null) {
                isLikedOrNot(foodItemModel.getId(), holder.like, userId);
                holder.like.setOnClickListener(view -> {
                    if (holder.like.getTag().equals("like")) {
                        FirebaseDatabase.getInstance().getReference().child("Likes").child(userId).child(foodItemModel.getId()).setValue(true);

                    } else {
                        FirebaseDatabase.getInstance().getReference().child("Likes")
                                .child(userId)
                                .child(foodItemModel.getId()).removeValue();
                    }

                });
            }
    }

    @Override
    public int getItemCount() {
        return foodItemList.size();
    }

    class FoodItemViewHolder extends RecyclerView.ViewHolder {
        ImageView foodImage;
        ImageView like;
        TextView foodName;
        TextView foodPrice;

        public FoodItemViewHolder(@NonNull View itemView) {
            super(itemView);
            foodImage = itemView.findViewById(R.id.imageView13);
            foodName = itemView.findViewById(R.id.textView8);
            foodPrice = itemView.findViewById(R.id.textView9);
            like = itemView.findViewById(R.id.like);

        }
    }


    private void isLikedOrNot(String postId, final ImageView imageView, String userId) {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Likes").child(userId).child(postId);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    imageView.setImageResource(R.drawable.ic_heart_checked);
                    imageView.setTag("liked");
                } else {
                    imageView.setImageResource(R.drawable.ic_heart);
                    imageView.setTag("like");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                imageView.setImageResource(R.drawable.ic_heart);
                imageView.setTag("like");
            }
        });

    }
}
