package com.example.mymall;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class rewardAdapter extends RecyclerView.Adapter<rewardAdapter.viewholder> {

   List<rewardModel>rewardModelList;
   private boolean useminilayout=false;
   private  LinearLayout selectedcoupen;
   private RecyclerView coupenrecyclerview;
   private TextView coupen_title,coupen_body, coupen_validity,discountedPrice;
   private  String productOrginal_price;
   private int cartItemposition=-1;
   private List<CartitemModel>cartitemModelList;

    public rewardAdapter(List<rewardModel> rewardModelList, boolean useminilayout) {
        this.rewardModelList = rewardModelList;
        this.useminilayout=useminilayout;

    }

    public rewardAdapter(List<rewardModel> rewardModelList, boolean useminilayout, RecyclerView coupenrecyclerview, LinearLayout selectedcoupen, String productOrginal_price, TextView coupen_title, TextView coupen_body, TextView coupen_validity,TextView discountedPrice) {
        this.rewardModelList = rewardModelList;
        this.useminilayout=useminilayout;
        this.coupenrecyclerview=coupenrecyclerview;
        this.selectedcoupen=selectedcoupen;
        this.productOrginal_price=productOrginal_price;
        this.coupen_title=coupen_title;
        this.coupen_body=coupen_body;
        this.coupen_validity=coupen_validity;
        this.discountedPrice=discountedPrice;

    }
    public rewardAdapter(int cartItemposition,List<rewardModel> rewardModelList, boolean useminilayout, RecyclerView coupenrecyclerview, LinearLayout selectedcoupen, String productOrginal_price, TextView coupen_title, TextView coupen_body, TextView coupen_validity,TextView discountedPrice,List<CartitemModel>cartitemModelList) {
        this.rewardModelList = rewardModelList;
        this.useminilayout=useminilayout;
        this.coupenrecyclerview=coupenrecyclerview;
        this.selectedcoupen=selectedcoupen;
        this.productOrginal_price=productOrginal_price;
        this.coupen_title=coupen_title;
        this.coupen_body=coupen_body;
        this.coupen_validity=coupen_validity;
        this.discountedPrice=discountedPrice;
        this.cartItemposition=cartItemposition;
        this.cartitemModelList=cartitemModelList;

    }

    @NonNull
    @Override
    public rewardAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if(useminilayout)
        {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mini_reward_layout, parent, false);
        }else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reward_item_layout, parent, false);
        }
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull rewardAdapter.viewholder holder, int position) {
      String type=rewardModelList.get(position).getType();
      String body=rewardModelList.get(position).getBody();
      String lower_limit=rewardModelList.get(position).getLower_limit();String uper_limit=rewardModelList.get(position).getUpper_limit();
      String percentage=rewardModelList.get(position).getPercentage();
      Date validity=rewardModelList.get(position).getValidity();
      boolean already_used=rewardModelList.get(position).isAlreadyusedCoupen();
      String coupenID=rewardModelList.get(position).getCoupenId();

        Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(),R.anim.left);
        holder.itemView.startAnimation(animation);



      holder.setData(coupenID,type,body,lower_limit,uper_limit,percentage,validity,already_used);
     // holder.rewardanimation.startAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(),R.anim.animation3));

    }

    @Override
    public int getItemCount() {
        return rewardModelList.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        private TextView reward_product_title;
        private TextView reward_product_due_date;
        private TextView reward_product_body;
        private ConstraintLayout rewardanimation;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            reward_product_title=itemView.findViewById(R.id.reward_product_title);
            reward_product_due_date=itemView.findViewById(R.id.reward_due_date);
            reward_product_body=itemView.findViewById(R.id.reward_product_body);
            rewardanimation=itemView.findViewById(R.id.reward_item);
        }
        private void setData(String coupenID,String type,String body,String lower_limit,String uper_limit,String percentage,Date validity,boolean already_used)
        {
           if(type.equals("Discount"))
           {
               reward_product_title.setText(type);

           }else
           {
               reward_product_title.setText("Flat TK."+percentage +" OFF");
           }
           if(!already_used) {
               if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                   SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MMM/YYYY");
                   reward_product_due_date.setText(simpleDateFormat.format(validity));

               }
           }else
           {
               reward_product_due_date.setText("Already used");
               reward_product_due_date.setTextColor(itemView.getContext().getResources().getColor(R.color.md_red_500));
           }

            reward_product_body.setText(body);

            if(useminilayout)
            {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!already_used) {

                            coupen_title.setText(type);
                            coupen_body.setText(body);

                            if (Long.valueOf(productOrginal_price) >= Long.valueOf(lower_limit) && Long.valueOf(productOrginal_price) <= Long.valueOf(uper_limit)) {
                                if (type.equals("Discount")) {
                                    long discountAmount = Long.valueOf(productOrginal_price) * Long.valueOf(percentage) / 100;
                                    discountedPrice.setText(String.valueOf(Long.valueOf(productOrginal_price) - discountAmount));
                                } else {
                                    discountedPrice.setText(String.valueOf(Long.valueOf(productOrginal_price) - Long.valueOf(percentage)));
                                }
                                if(cartItemposition!=-1) {
                                    cartitemModelList.get(cartItemposition).setSelectedCoupenID(coupenID);
                                }
                            } else {
                                if(cartItemposition!=-1) {
                                    cartitemModelList.get(cartItemposition).setSelectedCoupenID(null);
                                }
                                Toast.makeText(itemView.getContext(), "Sorry ! Product does not matches the coupen terms", Toast.LENGTH_SHORT).show();
                            }

                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MMM/YYYY");
                                coupen_validity.setText(simpleDateFormat.format(validity));
                            }
                            if (coupenrecyclerview.getVisibility() == View.GONE) {
                                coupenrecyclerview.setVisibility(View.VISIBLE);
                                selectedcoupen.setVisibility(View.GONE);
                            } else {
                                coupenrecyclerview.setVisibility(View.GONE);
                                selectedcoupen.setVisibility(View.VISIBLE);
                            }

                        }
                    }
                });
            }

        }
    }
}

