package com.apps.chaandtaarafoodrider.Adapters;

import android.content.Context;
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
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.chaandtaarafoodrider.Model.FoodItemModel;
import com.apps.chaandtaarafoodrider.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.FoodViewHolder> {

    Context context;
    List<FoodItemModel> favFoodList;
    String userId;
    private int lastPosition = -1;


    public FavAdapter(Context context, List<FoodItemModel> favFoodList, String userId) {
        this.context = context;
        this.favFoodList = favFoodList;
        this.userId = userId;
    }

    @NonNull
    @Override
    public FavAdapter.FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fav_item, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavAdapter.FoodViewHolder holder, int position) {
        FoodItemModel favFoodItem = favFoodList.get(holder.getAdapterPosition());

        holder.addToCart.setOnClickListener(view -> {

        });

        if (favFoodItem != null) {

            if (favFoodItem.getImage() != null) {
                RequestOptions options = new RequestOptions()
                        .centerCrop()
                        .placeholder(R.drawable.image_place_holder)
                        .error(R.drawable.image_place_holder)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .priority(Priority.HIGH);
                if (context != null) {
                    if (favFoodItem.getImage() != null)
                        Glide.with(context).load(favFoodItem.getImage())
                                .apply(options)
                                .into(holder.foodImage);
                    if (favFoodItem.getName() != null) holder.name.setText(favFoodItem.getName());
                    if (favFoodItem.getPrice() != null)
                        holder.price.setText("PKR " + favFoodItem.getPrice());
                }
            }
        }

        if (context != null)
            if (favFoodItem != null)
                if (favFoodItem.getId() != null && userId != null) {
                    holder.addToCart.setOnClickListener(view -> {


                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Basket").child(userId);
                        String cartItemId = reference.push().getKey();

                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("name", favFoodItem.getName());
                        hashMap.put("searchName", favFoodItem.getSearchName());
                        hashMap.put("image", favFoodItem.getImage());
                        hashMap.put("price", favFoodItem.getPrice());
                        hashMap.put("quantity", "1");
                        hashMap.put("category", favFoodItem.getCategory());
                        hashMap.put("id", cartItemId);
                        hashMap.put("description", favFoodItem.getDescription());
                        hashMap.put("metric", favFoodItem.getMetric());
                        if(cartItemId!=null)
                        reference.child(cartItemId).setValue(hashMap);
                        if (context != null)
                            Toast.makeText(context, "Added to cart", Toast.LENGTH_SHORT).show();


                    });
                }


        if (context != null) {
            setAnimation(holder.itemView, position);
        }

        holder.itemView.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.action_favoriteFragment_to_foodItemDetails);

        });

    }

    @Override
    public int getItemCount() {
        return favFoodList.size();
    }

    class FoodViewHolder extends RecyclerView.ViewHolder {
        CardView addToCart;
        ImageView foodImage;
        TextView name, price;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            addToCart = itemView.findViewById(R.id.cartButton);
            foodImage = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
        }
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
