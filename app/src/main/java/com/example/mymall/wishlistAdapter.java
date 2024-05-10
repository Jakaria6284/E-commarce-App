package com.example.mymall;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.mymall.fragment.DBquires;

import java.util.List;

public class wishlistAdapter extends RecyclerView.Adapter<wishlistAdapter.viewholder> {
    List<wishlistModel>wishlistModelList;
    private Boolean wishlistboolean;
    private boolean fromsearch;

    public boolean isFromsearch() {
        return fromsearch;
    }

    public void setFromsearch(boolean fromsearch) {
        this.fromsearch = fromsearch;
    }

    public List<wishlistModel> getWishlistModelList() {
        return wishlistModelList;
    }

    public void setWishlistModelList(List<wishlistModel> wishlistModelList) {
        this.wishlistModelList = wishlistModelList;
    }

    public wishlistAdapter(List<wishlistModel> wishlistModelList, Boolean wishlistboolean) {
        this.wishlistModelList = wishlistModelList;
        this.wishlistboolean=wishlistboolean;

    }

    @NonNull
    @Override
    public wishlistAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.whislist_item_layout,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull wishlistAdapter.viewholder holder, int position) {
        String productID=wishlistModelList.get(position).getProductID();
    String wish_product_img=wishlistModelList.get(position).getWish_product_image();
    String wish_product_title=wishlistModelList.get(position).getWish_product_title();
    long cupon=wishlistModelList.get(position).getWish_cupon();
    String rating=wishlistModelList.get(position).getRating();
    long totalrating=wishlistModelList.get(position).getTotalRating();
    String productPrice=wishlistModelList.get(position).getProductPrice();
    String cuttedPrice=wishlistModelList.get(position).getCuttedprice();
    boolean payment=wishlistModelList.get(position).isPaymentMethod();
    boolean in_stock=wishlistModelList.get(position).isIn_stock();
        Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(),R.anim.left);
        holder.itemView.startAnimation(animation);

    holder.setData(productID,wish_product_img,wish_product_title,cupon,rating,totalrating,productPrice,cuttedPrice,payment,position,in_stock);
    }

    @Override
    public int getItemCount() {
        return wishlistModelList.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{
        private ImageView wish_product_image;
        private ImageView wish_free_cupon_icon;
        private TextView wish_product_title;
        private TextView wish_cupon;
        private TextView tv_wish_rating;
        private TextView tv_wish_total_rating;
        private TextView productPrice;
        private TextView cuttedPrice;
        private TextView wish_payment_method;
        private ImageView wishlist_delete_btn;
        private LinearLayout ratingcontainer;
        private ImageView wishlist_coupen_icon;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            wish_product_image=itemView.findViewById(R.id.wishlist_product_image);
            wish_product_title=itemView.findViewById(R.id.whishlist_product_title);
           // wish_free_cupon_icon=itemView.findViewById(R.id.whislist_cupon_icon);
            wish_cupon=itemView.findViewById(R.id.whishlist_free_cupon);
            tv_wish_rating=itemView.findViewById(R.id.tv_product_rating_miview);
            tv_wish_total_rating=itemView.findViewById(R.id.whislist_totalrating);
            productPrice=itemView.findViewById(R.id.wish_list_product_price);
            cuttedPrice=itemView.findViewById(R.id.wishlist_cutted_price);
            wish_payment_method=itemView.findViewById(R.id.wishlist_payment_method);
            wishlist_delete_btn=itemView.findViewById(R.id.wishlist_delete_btn);
           // ratingcontainer=itemView.findViewById(R.id.linear_layout);
           // wish_free_cupon_icon=itemView.findViewById(R.id.whislist_cupon_icon);

        }
        private void setData(String productID,String w_product_image, String w_product_title, long freecuponNo, String rating, long totalrating, String product_price, String cutted_price, Boolean paymentMethod,int index,boolean in_stock)
        {
            Glide.with(itemView.getContext()).load(w_product_image).override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).apply(new RequestOptions().placeholder(R.drawable.strip)).into(wish_product_image);
            wish_product_title.setText(w_product_title);
          //  wish_cupon.setText(cupon);
            if(in_stock) {
                if (freecuponNo != 0) {

                    wish_cupon.setVisibility(View.VISIBLE);
                    if (freecuponNo == 1) {
                        wish_cupon.setText("free" + freecuponNo + "Coupen");
                    } else {
                        wish_cupon.setText("free" + freecuponNo + "Coupens");
                    }
                } else {
                    wish_cupon.setVisibility(View.INVISIBLE);
                }
            }else
            {
                wish_cupon.setVisibility(View.INVISIBLE);

            }
            if(in_stock) {
                tv_wish_rating.setText(rating);
                tv_wish_total_rating.setText("Total" + totalrating + "(Rating)");
                productPrice.setText(product_price);
                cuttedPrice.setText(cutted_price);
            }else
            {
                tv_wish_rating.setVisibility(View.GONE);
                tv_wish_total_rating.setVisibility(View.GONE);
                productPrice.setVisibility(View.GONE);
                cuttedPrice.setVisibility(View.GONE);
//                ratingcontainer.setVisibility(View.GONE);
               // wish_free_cupon_icon.setVisibility(View.GONE);
            }
            if(in_stock) {
                if (paymentMethod) {
                    wish_payment_method.setVisibility(View.VISIBLE);
                } else {
                    wish_payment_method.setVisibility(View.INVISIBLE);
                }
            }else
            {
                wish_payment_method.setText("OUT OF STOCK");
            }


            if(wishlistboolean)
            {
                wishlist_delete_btn.setVisibility(View.VISIBLE);
            }else
            {
                wishlist_delete_btn.setVisibility(View.GONE);
            }
            wishlist_delete_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    wishlist_delete_btn.setEnabled(false);
                    DBquires.removefromwishlist(index, itemView.getContext());
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(fromsearch)
                    {
                        ProductDetailsActivity.fromsearch=true;
                    }
                    Intent intent=new Intent(itemView.getContext(),ProductDetailsActivity.class);
                    intent.putExtra("product_id",productID);
                    itemView.getContext().startActivity(intent);
                }
            });



        }
    }
}
