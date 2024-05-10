package com.example.mymall;


import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class GridProductViewAdaptar extends BaseAdapter {
    public GridProductViewAdaptar(List<horizental_scroll_product_model> horizentalScrollProductModelList) {
        this.horizental_scroll_product_modelList = horizentalScrollProductModelList;
    }

    List<horizental_scroll_product_model> horizental_scroll_product_modelList;
    @Override
    public int getCount() {
        return horizental_scroll_product_modelList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View Covertview, ViewGroup parent) {
        View view ;
        if(Covertview==null)
        {
           view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizenta_scroll_item_layout,null);
           view.setElevation(0);
            ImageView productImage=view.findViewById(R.id.hhs_image);
            TextView productTitle=view.findViewById(R.id.hhs_product_title);
            TextView productDescription=view.findViewById(R.id.hhs_product_description);
            TextView productPrice=view.findViewById(R.id.hhs_product_price);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent productdetailIntent=new Intent(parent.getContext(),ProductDetailsActivity.class);
                    productdetailIntent.putExtra("product_id",horizental_scroll_product_modelList.get(position).getProduct_id());

                    parent.getContext().startActivity(productdetailIntent);
                    ((Activity) view.getContext()).overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ((Activity) view.getContext()).overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
                        }
                    }, 2000);

                }
            });
            //productImage.setImageResource(horizental_scroll_product_modelList.get(position).getProductImage());
            Glide.with(parent.getContext()).load(horizental_scroll_product_modelList.get(position).getProductImage()).apply(new RequestOptions().placeholder(R.drawable.image)).into(productImage);
            productTitle.setText(horizental_scroll_product_modelList.get(position).getProductTitle());
            productDescription.setText(horizental_scroll_product_modelList.get(position).getProductDescription());
            productPrice.setText(horizental_scroll_product_modelList.get(position).getProductPrice());

        }else
        {
            view=Covertview;
        }


        return view;

    }
}
