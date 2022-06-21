package com.apps.chaandtaarafoodrider.Adapters;

import android.content.Context;
import android.content.pm.LabeledIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
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

import java.util.HashMap;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    private final Context context;
    private final List<FoodItemModel> foodItemList;
    private final String userId;
    private int lastPosition = -1;

    public SearchAdapter(Context context, List<FoodItemModel> foodItemList, String userId) {
        this.context = context;
        this.foodItemList = foodItemList;
        this.userId = userId;
    }

    @NonNull
    @Override
    public SearchAdapter.SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.search_item,parent,false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.SearchViewHolder holder, int position) {
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
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Likes").child(userId).child(foodItemModel.getId());
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("name", foodItemModel.getName());
                        hashMap.put("searchName", foodItemModel.getSearchName());
                        hashMap.put("image", foodItemModel.getImage());
                        hashMap.put("price", foodItemModel.getPrice());
                        hashMap.put("category", foodItemModel.getCategory());
                        hashMap.put("id", foodItemModel.getId());
                        hashMap.put("description", foodItemModel.getDescription());
                        reference.setValue(hashMap);

                    } else {
                        FirebaseDatabase.getInstance().getReference().child("Likes")
                                .child(userId)
                                .child(foodItemModel.getId()).removeValue();
                    }

                });

                holder.addToCart.setOnClickListener(view -> {
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Basket").child(userId).child(foodItemModel.getId());
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("name", foodItemModel.getName());
                    hashMap.put("searchName", foodItemModel.getSearchName());
                    hashMap.put("image", foodItemModel.getImage());
                    hashMap.put("price", foodItemModel.getPrice());
                    hashMap.put("category", foodItemModel.getCategory());
                    hashMap.put("id", foodItemModel.getId());
                    hashMap.put("description", foodItemModel.getDescription());
                    reference.setValue(hashMap);
                    Toast.makeText(context, "Added to Basket", Toast.LENGTH_SHORT).show();

                });
            }
        if (context != null) {
            setAnimation(holder.itemView, position);
        }
    }

    @Override
    public int getItemCount() {
        return foodItemList.size();
    }
    public class SearchViewHolder extends RecyclerView.ViewHolder{
        ImageView foodImage;
        ImageView like;
        TextView foodName;
        TextView foodPrice;
        CardView addToCart;
        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            foodImage = itemView.findViewById(R.id.imageView13);
            foodName = itemView.findViewById(R.id.textView8);
            foodPrice = itemView.findViewById(R.id.textView9);
            like = itemView.findViewById(R.id.like);
            addToCart = itemView.findViewById(R.id.cardView2);
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

    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.bottom_up_zoom_out);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }
}
