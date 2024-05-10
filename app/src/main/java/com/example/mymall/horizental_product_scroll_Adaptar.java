package com.example.mymall;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.util.List;

public class horizental_product_scroll_Adaptar extends RecyclerView.Adapter<horizental_product_scroll_Adaptar.ViewHolder> {
    private List<horizental_scroll_product_model> horizentalScrollProductModelList;

    public horizental_product_scroll_Adaptar(List<horizental_scroll_product_model> horizentalScrollProductModelList) {
        this.horizentalScrollProductModelList = horizentalScrollProductModelList;
    }


    @NonNull
    @Override
    public horizental_product_scroll_Adaptar.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.horizenta_scroll_item_layout,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull horizental_product_scroll_Adaptar.ViewHolder holder, int position) {
        String resource=horizentalScrollProductModelList.get(position).getProductImage();
        String title=horizentalScrollProductModelList.get(position).getProductTitle();
        String description=horizentalScrollProductModelList.get(position).getProductDescription();
        String price=horizentalScrollProductModelList.get(position).getProductPrice();
        String product_id=horizentalScrollProductModelList.get(position).getProduct_id();

            holder.horizental_animation.startAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(),R.anim.left));

           // holder.horizental_animation.startAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(),R.anim.animation3));


       // holder.setProductImage(resource);
       // holder.setProductTitle(title);
       // holder.setProductDescription(description);
       // holder.setProductPrice(price);
        //holder.setProductid(product_id);
       // holder.setData(product_id,resource,title,description,price);
        if (title != null && !title.equals("")) {
            holder.setData(product_id, resource, title, description, price);
        }



    }

    @Override
    public int getItemCount() {
       if( horizentalScrollProductModelList.size()>8)
       {
          return 8;
       }else
       {
           return horizentalScrollProductModelList.size();
       }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
      private  ImageView productImage;
        private  TextView productTitle;
        private  TextView productDescription;
        private TextView productPrice;
        private ConstraintLayout horizental_animation;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage=itemView.findViewById(R.id.hhs_image);
            productTitle=itemView.findViewById(R.id.hhs_product_title);
            productDescription=itemView.findViewById(R.id.hhs_product_description);
            productPrice=itemView.findViewById(R.id.hhs_product_price);
            horizental_animation=itemView.findViewById(R.id.horizental_item);




        }
        private void setData(String product_id,String resource,String title,String description,String price)
        {
            Glide.with(itemView.getContext()).load(resource).override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).apply(new RequestOptions().placeholder(R.drawable.image)).into(productImage);
            productTitle.setText(title);
            productDescription.setText(description);
            productPrice.setText(price);

            if(!title.equals(""))
            {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent productdetailsIntent=new Intent(itemView.getContext(),ProductDetailsActivity.class);
                        productdetailsIntent.putExtra("product_id",product_id);
                        itemView.getContext().startActivity(productdetailsIntent);
                        ((Activity) itemView.getContext()).overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                ((Activity) itemView.getContext()).overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_in_left);
                            }
                        }, 200); // Adjust the delay time as needed
                       // overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }
                });
            }

        }

    }
}
