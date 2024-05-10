package com.example.mymall;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.Date;
import java.util.List;

public class myorderAdapter extends RecyclerView.Adapter<myorderAdapter.Viewholder> {
  private List<my_order_item_model>myOrderItemModels;

    public myorderAdapter(List<my_order_item_model> myOrderItemModels) {
        this.myOrderItemModels = myOrderItemModels;
    }

    @NonNull
    @Override
    public myorderAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_order_item, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myorderAdapter.Viewholder holder, int position) {

        String productImage=myOrderItemModels.get(position).getProducts_images();
        String product_title=myOrderItemModels.get(position).getProducts_titles();
        String orderDeliveryDate=myOrderItemModels.get(position).getOrder_delivery_date();
        String ordered=myOrderItemModels.get(position).getOrder();
        String packed=myOrderItemModels.get(position).getPacked();
        String shipped=myOrderItemModels.get(position).getShipped();
        String delivery=myOrderItemModels.get(position).getDelivery();
        String fulladress=myOrderItemModels.get(position).getFullAdress();
        String fullName=myOrderItemModels.get(position).getFullName();
        String pincode=myOrderItemModels.get(position).getPincode();
        String orderid=myOrderItemModels.get(position).getOrderid();
        String productid=myOrderItemModels.get(position).getProductid();
        String userid=myOrderItemModels.get(position).getUserid();
        Date orderdate=myOrderItemModels.get(position).getOrderdate();

        Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(),R.anim.left);
        holder.itemView.startAnimation(animation);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(holder.itemView.getContext(),OrderDetailActivity.class);
                intent.putExtra("productimage",productImage);
                intent.putExtra("producttitle",product_title);
                intent.putExtra("price",orderDeliveryDate);
                intent.putExtra("Ordered",ordered);
                intent.putExtra("Packed",packed);
                intent.putExtra("Shipped",shipped);
                intent.putExtra("Delivery",delivery);
                intent.putExtra("fullName",fullName);
                intent.putExtra("fullAddress",fulladress);
                intent.putExtra("Pincode",pincode);
                intent.putExtra("userID",userid);
                intent.putExtra("productID",productid);
                intent.putExtra("orderID",orderid);
                intent.putExtra("orderDate",orderdate);


                holder.itemView.getContext().startActivity(intent);
            }
        });


        holder.setData(productImage,product_title,orderDeliveryDate,fullName,fulladress,pincode,userid,productid,orderid,orderdate);
    }

    @Override
    public int getItemCount() {
        return myOrderItemModels.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        private ImageView product_image;
        private TextView product_title;
        private TextView order_delivery_date,fullName,fullAdress,pincode;
       

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            product_image = itemView.findViewById(R.id.products_images);
            product_title = itemView.findViewById(R.id.products_titles);
            order_delivery_date = itemView.findViewById(R.id.order_delivery_date);







        }
        public void setData(String productImage, String productTitle, String orderDeliveryDate,String FullName,String FullAdress,String Pincode,String userid,String productid,String orderid,Date orderdate) {
            Glide.with(itemView.getContext()).load(productImage).apply(new RequestOptions().placeholder(R.drawable.strip)).into(product_image);
            product_title.setText(productTitle);
            order_delivery_date.setText("TK. "+orderDeliveryDate+"/-");


        }

        }


}
