package com.apps.chaandtaarafoodrider.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.chaandtaarafoodrider.Model.CartItem;
import com.apps.chaandtaarafoodrider.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class BasketAdapter extends RecyclerView.Adapter<BasketAdapter.BasketViewHolder> {

    Context context;
    List<CartItem> cartItemList;
    String userId;
    private int lastPosition = -1;

    public BasketAdapter(Context context, List<CartItem> cartItemList, String userId) {
        this.context = context;
        this.cartItemList = cartItemList;
        this.userId = userId;
    }

    @NonNull
    @Override
    public BasketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.basket_item, parent, false);
        return new BasketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BasketViewHolder holder, int position) {

        CartItem cartItem = cartItemList.get(holder.getAdapterPosition());


        if (cartItem != null) {

            if (cartItem.getImage() != null) {
                RequestOptions options = new RequestOptions()
                        .centerCrop()
                        .placeholder(R.drawable.image_place_holder)
                        .error(R.drawable.image_place_holder)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .priority(Priority.HIGH);
                if (context != null) {
                    if (cartItem.getImage() != null)
                        Glide.with(context).load(cartItem.getImage())
                                .apply(options)
                                .into(holder.foodImage);
                    if (cartItem.getName() != null) holder.name.setText(cartItem.getName());
                    if (cartItem.getPrice() != null)
                        holder.price.setText("PKR " + cartItem.getPrice());
                }
            }
        }


        if (context != null) {
            setAnimation(holder.itemView, position);
        }

    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }

    public class BasketViewHolder extends RecyclerView.ViewHolder {
        CardView addToCart;
        ImageView foodImage;
        TextView name, price;

        public BasketViewHolder(@NonNull View itemView) {
            super(itemView);
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
