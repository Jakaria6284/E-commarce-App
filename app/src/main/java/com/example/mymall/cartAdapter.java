package com.example.mymall;

import static com.example.mymall.fragment.DBquires.firebaseFirestore;

import android.app.Dialog;
import android.content.res.ColorStateList;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mymall.fragment.DBquires;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class cartAdapter extends RecyclerView.Adapter{

    private List<CartitemModel> cartitemModelList;
    private TextView total_cart_amount_below;
    private TextView total_amount_delivery;
    private boolean showDeletebtn;

    public cartAdapter(List<CartitemModel> cartitemModelList,TextView total_cart_amount_below,boolean showDeletebtn) {
        this.cartitemModelList = cartitemModelList;
        this.total_cart_amount_below=total_cart_amount_below;
        this.showDeletebtn=showDeletebtn;
    }

    @Override
    public int getItemViewType(int position) {
       switch (cartitemModelList.get(position).getType())
       {
           case 0:
           return  CartitemModel.CART_ITEM;

           case 1:
               return CartitemModel.TOTAL_AMOUNT;
           default:
               return -1;
       }

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case CartitemModel.CART_ITEM:
            View cartitem= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_layout,parent,false);
            return new CartItemViewholder(cartitem);
            case CartitemModel.TOTAL_AMOUNT:
                View carttotal= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_total_amount_layout,parent,false);
                return new cartTotalAmountViewHolder(carttotal);
            default:
                return null;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
      switch (cartitemModelList.get(position).getType())
      {
          case CartitemModel.CART_ITEM:
              String product_id=cartitemModelList.get(position).getProduct_id();
              String resource=cartitemModelList.get(position).getProductImage();
              String title=cartitemModelList.get(position).getProductTitle();
              long freecoupens=cartitemModelList.get(position).getFreeCoupens();
              String productPrice=cartitemModelList.get(position).getProductPrice();
              String cuttedPrice=cartitemModelList.get(position).getCuttedPrice();
              long offerAplied=cartitemModelList.get(position).getOfferApplied();
              boolean in_stock=cartitemModelList.get(position).isIn_stock();
              long maxqty=cartitemModelList.get(position).getMaxQty();
              long productQty=cartitemModelList.get(position).getProductQuantity();
              boolean qty_eroor=cartitemModelList.get(position).isQty_error();
              List<String>qtyids=cartitemModelList.get(position).getQtyids();
              long stock_qty=cartitemModelList.get(position).getStock_qty();
              Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(),R.anim.left);
              holder.itemView.startAnimation(animation);
              ((CartItemViewholder)holder).setItemDetails(product_id,resource,title,freecoupens,productPrice,cuttedPrice,offerAplied,position,in_stock,String.valueOf(productQty),maxqty,qty_eroor,qtyids,stock_qty);
              break;
          case CartitemModel.TOTAL_AMOUNT:

              int totalitem=0;
              int totalitemprice=0;
              String deliverPrice;
              int totaAmount;
              int saveAmount=0;


              for(int x=0;x<cartitemModelList.size();x++)
              {
                  if(cartitemModelList.get(x).getType()==CartitemModel.CART_ITEM && cartitemModelList.get(x).isIn_stock())
                  {

                      int quantity=Integer.parseInt(String.valueOf(cartitemModelList.get(x).getProductQuantity()));
                     totalitem=   totalitem+Integer.parseInt(String.valueOf(cartitemModelList.get(x).getProductQuantity()));
                     if(TextUtils.isEmpty(cartitemModelList.get(x).getSelectedCoupenID())) {
                         totalitemprice = totalitemprice + Integer.parseInt(cartitemModelList.get(x).getProductPrice())*quantity;
                     }else
                     {
                         totalitemprice = totalitemprice + Integer.parseInt(cartitemModelList.get(x).getDiscountedpricerewardmodel())*quantity;
                     }

                     if(!TextUtils.isEmpty(cartitemModelList.get(x).getCuttedPrice()))
                     {
                         saveAmount=saveAmount+(Integer.parseInt(cartitemModelList.get(x).getCuttedPrice())-Integer.parseInt(cartitemModelList.get(x).getProductPrice()))*quantity;
                         if(!TextUtils.isEmpty(cartitemModelList.get(x).getSelectedCoupenID()))
                         {
                             saveAmount=saveAmount+(Integer.parseInt(cartitemModelList.get(x).getProductPrice())-Integer.parseInt(cartitemModelList.get(x).getDiscountedpricerewardmodel()))*quantity;
                         }
                     }else
                     {
                         if(!TextUtils.isEmpty(cartitemModelList.get(x).getSelectedCoupenID()))
                         {
                             saveAmount=saveAmount+(Integer.parseInt(cartitemModelList.get(x).getProductPrice())-Integer.parseInt(cartitemModelList.get(x).getDiscountedpricerewardmodel()))*quantity;
                         }
                     }
                     // String productiddd=cartitemModelList.get(x).getProduct_id();

                  }
              }
              if(totalitemprice>500)
              {
                  deliverPrice="Free";
                  totaAmount= totalitemprice;
              }else
              {
                  deliverPrice="60";
                  totaAmount= totalitemprice+60;

              }

              cartitemModelList.get(position).setTotal_item(totalitem);
              cartitemModelList.get(position).setTotal_item_price(totalitemprice);
              cartitemModelList.get(position).setDelivery_price(deliverPrice);
              cartitemModelList.get(position).setTotal_amount(totaAmount);
              cartitemModelList.get(position).setSaved_amount(saveAmount);
              //cartitemModelList.get(position).setProductId(product_idd);
              Animation animation2 = AnimationUtils.loadAnimation(holder.itemView.getContext(),R.anim.left);
              holder.itemView.startAnimation(animation2);

              ((cartTotalAmountViewHolder)holder).setTotalAmountprice(totalitem,totalitemprice,deliverPrice,totaAmount,saveAmount);
              break;
          default:
              return;

      }
    }

    @Override
    public int getItemCount() {

        if(cartitemModelList == null) {
            cartitemModelList = new ArrayList<>();
        }
        return cartitemModelList.size();


    }
   public class CartItemViewholder extends RecyclerView.ViewHolder{
      private ImageView productImage;
      private TextView productTitle;
        private TextView freeCoupens;
        private TextView productPrice;
        private TextView cuttedPrice;
        private TextView offersApplied;
        private TextView coupensApplied;
        private TextView productQuantity;
        private ImageView freeCoupensIcon;
        private ImageView imageviewDeletebtn;
        private TextView textviewDeletebtn;
        private View divider;
        private LinearLayout cheackAfterCoupenRedemption;
        private Button cartredempbtn;
       public  TextView coupen_title;
       public  TextView coupen_body;
       public  TextView coupen_validity,cartCoupenbodytxt;
       public   RecyclerView coupenrecyclerview;
       public   LinearLayout selectedcoupen;
       public  rewardAdapter RewardAdapter;
       private String productOrginalprice;
       TextView discountprice;
       TextView originalprice;

       private Button coupenRemovebtn;
       private Button coupenApplybtn;

        public CartItemViewholder(@NonNull View itemView) {
            super(itemView);
            productImage=itemView.findViewById(R.id.productImage);
            productTitle=itemView.findViewById(R.id.productTitle);
            freeCoupens=itemView.findViewById(R.id.tv_free_coupens);
            productPrice=itemView.findViewById(R.id.product_price);
            cuttedPrice=itemView.findViewById(R.id.cutted_priced);
            offersApplied=itemView.findViewById(R.id.offers_applies);
            coupensApplied=itemView.findViewById(R.id.coupens_applied);
            productQuantity=itemView.findViewById(R.id.product_quantity);
            imageviewDeletebtn=itemView.findViewById(R.id.imageViewDeletebtn);
            textviewDeletebtn=itemView.findViewById(R.id.textviewdetetebtn);
            freeCoupensIcon=itemView.findViewById(R.id.free_coupens_icon);
            divider=itemView.findViewById(R.id.divider4);
            cheackAfterCoupenRedemption=itemView.findViewById(R.id.linearLayout4);
            cartredempbtn=itemView.findViewById(R.id.coupen_redempbtncart);
            cartCoupenbodytxt=itemView.findViewById(R.id.cartcoupenbodytext);


        }
        private void setItemDetails(String product_id,String resource,String title,long freeCoupensNo,String productpriceText,String cuttedPriceText,long offerAplliedno,int position,boolean in_stock,String quantity,long maxQuantity,boolean qtyerror,List<String>qtyids,long stock_qty)
        {
            //productImage.setImageResource(resource);

            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.drawable.strip)).into(productImage);

            productTitle.setText(title);
            if(in_stock) {
                if (freeCoupensNo > 0) {
                    // freeCoupensIcon.setVisibility(View.VISIBLE);
                    freeCoupens.setVisibility(View.VISIBLE);
                    if (freeCoupensNo == 1) {
                        freeCoupens.setText("free" + freeCoupensNo + "Coupen");
                    } else {
                        freeCoupens.setText("free" + freeCoupensNo + "Coupens");
                    }

                } else {
                    // freeCoupensIcon.setVisibility(View.INVISIBLE);


                }
            }else
            {
                freeCoupensIcon.setVisibility(View.GONE);
                freeCoupens.setVisibility(View.GONE);
                freeCoupens.setVisibility(View.GONE);
            }


            if(in_stock) {
                productPrice.setText(productpriceText + "TK");
                cuttedPrice.setText(cuttedPriceText+"TK");
                if(!TextUtils.isEmpty(cartitemModelList.get(position).getSelectedCoupenID())) {
                    for (rewardModel rewardModel : DBquires.rewardModelList) {
                        if (rewardModel.getCoupenId().equals(cartitemModelList.get(position).getSelectedCoupenID())) {
                            cartCoupenbodytxt.setText(rewardModel.getBody());
                            cartCoupenbodytxt.setTextColor(itemView.getContext().getResources().getColor(R.color.md_red_500));
                        }

                    }
                    coupensApplied.setVisibility(View.VISIBLE);

                   // DBquires.cartitemModelList.get(position).setDiscountedpricerewardmodel(discountprice.getText().toString());
                    if(discountprice != null) {
                        String discountPriceStr = discountprice.getText().toString();
                        cartitemModelList.get(position).setDiscountedpricerewardmodel(discountPriceStr);
                    }

                    productPrice.setText(cartitemModelList.get(position).getDiscountedpricerewardmodel()+"TK");
                   // String offerpricededuction = String.valueOf(Long.valueOf(productpriceText) - Long.valueOf(discountprice.getText().toString()));
                    String offerpricededuction = null;
                    if(discountprice != null){
                        offerpricededuction = String.valueOf(Long.valueOf(productpriceText) - Long.valueOf(discountprice.getText().toString()));
                    }
                    coupensApplied.setText("applied:"+offerpricededuction+"TK");
                }else
                {

                }


            }else
            {
                productPrice.setText("OUT OF STOCK");
               // productPrice.setTextColor(itemView.getContext().getResources().getColor(R.color.md_red_900));
                cuttedPrice.setVisibility(View.GONE);
                divider.setVisibility(View.GONE);
            }
             if(in_stock) {
                 if (offerAplliedno > 0) {
                     offersApplied.setVisibility(View.VISIBLE);
                     String offerpricededuction = String.valueOf(Long.valueOf(cuttedPriceText) - Long.valueOf(productpriceText));
                     offersApplied.setText("offer: " + offerpricededuction+"tk");
                 } else {
                     offersApplied.setVisibility(View.INVISIBLE);

                 }
             }else
             {
                 offersApplied.setText("");
             }

            if(showDeletebtn)
            {
                imageviewDeletebtn.setVisibility(View.VISIBLE);
                textviewDeletebtn.setVisibility(View.VISIBLE);
            }else
            {
                imageviewDeletebtn.setVisibility(View.GONE);
                textviewDeletebtn.setVisibility(View.GONE);
            }

                imageviewDeletebtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(!TextUtils.isEmpty(cartitemModelList.get(position).getSelectedCoupenID())) {
                            for (rewardModel rewardModel:DBquires.rewardModelList) {
                                if(rewardModel.getCoupenId().equals(cartitemModelList.get(position).getSelectedCoupenID()))
                                {
                                    rewardModel.setAlreadyusedCoupen(false);

                                }

                            }
                            cartitemModelList.get(position).setSelectedCoupenID(null);
                        }

                        DBquires.removefromcartlist(position, itemView.getContext(),total_cart_amount_below);

                    }
                });


                textviewDeletebtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(!TextUtils.isEmpty(cartitemModelList.get(position).getSelectedCoupenID())) {
                            for (rewardModel rewardModel:DBquires.rewardModelList) {
                                if(rewardModel.getCoupenId().equals(cartitemModelList.get(position).getSelectedCoupenID()))
                                {
                                    rewardModel.setAlreadyusedCoupen(false);

                                }

                            }
                            cartitemModelList.get(position).setSelectedCoupenID(null);
                        }
                        DBquires.removefromcartlist(position, itemView.getContext(),total_cart_amount_below);
                    }
                });
                if(in_stock)
                {
                    cheackAfterCoupenRedemption.setVisibility(View.VISIBLE);
                }else
                {
                    cheackAfterCoupenRedemption.setVisibility(View.GONE);
                }
                productQuantity.setText("QTY: "+quantity);
             if(!showDeletebtn) {
                 if (qtyerror) {
                     productQuantity.setTextColor(itemView.getContext().getResources().getColor(R.color.md_deep_orange_A700));
                     productQuantity.setBackgroundTintList(ColorStateList.valueOf(itemView.getContext().getResources().getColor(R.color.md_deep_orange_A700)));

                 } else {
                     productQuantity.setTextColor(itemView.getContext().getResources().getColor(R.color.black));
                     productQuantity.setBackgroundTintList(ColorStateList.valueOf(itemView.getContext().getResources().getColor(R.color.black)));
                 }
             }

                productQuantity.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final Dialog quantityDilog=new Dialog(itemView.getContext());
                        quantityDilog.setContentView(R.layout.quantity);
                        quantityDilog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                        quantityDilog.setCancelable(false);
                        EditText quantityNumber=quantityDilog.findViewById(R.id.quantity_number);
                        Button ok=quantityDilog.findViewById(R.id.ok);
                        Button cancel=quantityDilog.findViewById(R.id.cancel);

                        ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(!TextUtils.isEmpty(quantityNumber.getText()))
                                if(Long.valueOf(quantityNumber.getText().toString())<=maxQuantity && Long.valueOf(quantityNumber.getText().toString())!=0) {

                                    if(showDeletebtn) {

                                        cartitemModelList.get(position).setProductQuantity(Long.valueOf(quantityNumber.getText().toString()));
                                    }else
                                    {
                                        DeliveryActivity.cartitemModelList.get(position).setProductQuantity(Long.valueOf(quantityNumber.getText().toString()));
                                    }
                                    productQuantity.setText("QTY: " + quantityNumber.getText().toString());
                                    notifyItemChanged(cartitemModelList.size()-1);
                                    if(!showDeletebtn) {
                                        DeliveryActivity.cartitemModelList.get(position).setQty_error(false);
                                        int intitialqty = Integer.parseInt(quantity);
                                        int finalqty = Integer.parseInt(quantityNumber.getText().toString());
                                        if (finalqty > intitialqty) {


                                            for (int y = 0; y <finalqty-intitialqty; y++) {
                                                String qtydocumentName = UUID.randomUUID().toString().substring(0, 15);
                                                Map<String, Object> timestamp = new HashMap<>();
                                                timestamp.put("time", FieldValue.serverTimestamp());

                                                int finalY = y;
                                                firebaseFirestore.collection("PRODUCTS").document(product_id)
                                                        .collection("QUANTITY")
                                                        .document(qtydocumentName).set(timestamp)
                                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void unused) {
                                                                qtyids.add(qtydocumentName);

                                                                if (finalY + 1 == finalqty-intitialqty) {
                                                                    firebaseFirestore.collection("PRODUCTS").document(product_id)
                                                                            .collection("QUANTITY").orderBy("time", Query.Direction.ASCENDING)
                                                                            .limit(stock_qty)
                                                                            .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                                @Override
                                                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                                    if (task.isSuccessful()) {
                                                                                        List<String> serverquantity = new ArrayList<>();
                                                                                        for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                                                                                            serverquantity.add(queryDocumentSnapshot.getId());
                                                                                        }
                                                                                        long Availableqty = 0;
                                                                                        //  boolean notavailable = true;
                                                                                        for (String quantityID : qtyids) {

                                                                                            if (!serverquantity.contains(quantityID)) {
                                                                                                DeliveryActivity.cartitemModelList.get(position).setQty_error(true);
                                                                                                DeliveryActivity.cartitemModelList.get(position).setMaxQty(Availableqty);

                                                                                                Toast.makeText(itemView.getContext(), "You Will Buy "+maxQuantity+" or less than "+maxQuantity, Toast.LENGTH_SHORT).show();

                                                                                                DeliveryActivity.allproductAvailable = false;

                                                                                            } else {

                                                                                                Availableqty++;
                                                                                            }
                                                                                        }


                                                                                      DeliveryActivity.cartadapter.notifyDataSetChanged();



                                                                                    } else {
                                                                                        String error = task.getException().getMessage();
                                                                                        Toast.makeText(itemView.getContext(), "" + error, Toast.LENGTH_SHORT).show();
                                                                                    }
                                                                                }
                                                                            });
                                                                }
                                                            }
                                                        });
                                            }
                                        }else if(intitialqty>finalqty)
                                        {


                                                    for (int x=0;x<intitialqty-finalqty;x++) {
                                                        String qtyID= qtyids.get(qtyids.size() - 1 - x);

                                                        firebaseFirestore.collection("PRODUCTS").document(product_id).collection("QUANTITY")
                                                                .document(qtyID)
                                                                .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                    @Override
                                                                    public void onSuccess(Void unused) {

                                                                            qtyids.remove(qtyID);
                                                                            DeliveryActivity.cartadapter.notifyDataSetChanged();


                                                                    }
                                                                });


                                                    }

                                        }

                                    }
                                    }
                                else
                                {

                                    Toast.makeText(itemView.getContext(), "You Will Buy "+maxQuantity+" or less than "+maxQuantity, Toast.LENGTH_SHORT).show();
                                }
                                quantityDilog.dismiss();
                            }
                        });

                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                              quantityDilog.dismiss();
                            }
                        });
                        quantityDilog.show();

                    }
                });
             cartredempbtn.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {

                     Dialog coupenredemdialog=new Dialog(itemView.getContext());
                     coupenredemdialog.setContentView(R.layout.coupen_redemp_dialog);
                     coupenredemdialog.setCancelable(true);
                     coupenredemdialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                     ImageView toggglerecyclerview=coupenredemdialog.findViewById(R.id.togglerecyclerview);
                     coupenrecyclerview=coupenredemdialog.findViewById(R.id.coupen_recyclerview);
                     selectedcoupen=coupenredemdialog.findViewById(R.id.selected_coupen);

                     coupen_title=coupenredemdialog.findViewById(R.id.reward_product_title);
                     coupen_body=coupenredemdialog.findViewById(R.id.reward_product_body);
                     coupen_validity=coupenredemdialog.findViewById(R.id.reward_due_date);
                     coupenRemovebtn=coupenredemdialog.findViewById(R.id.Coupen_cancel);
                     coupenApplybtn=coupenredemdialog.findViewById(R.id.coupen_apply);
                    if(showDeletebtn)
                    {
                        coupenApplybtn.setVisibility(View.VISIBLE);
                        coupenRemovebtn.setVisibility(View.VISIBLE);
                    }

                     originalprice=coupenredemdialog.findViewById(R.id.orginal_price);
                     discountprice=coupenredemdialog.findViewById(R.id.discount_price);
                     LinearLayoutManager layoutManager=new LinearLayoutManager(itemView.getContext());
                     layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                     coupenrecyclerview.setLayoutManager(layoutManager);
                     originalprice.setText("TK. "+productPrice.getText());
                     productOrginalprice=productpriceText;

                     coupenApplybtn.setOnClickListener(new View.OnClickListener() {
                         @Override
                         public void onClick(View view) {
                             if(!TextUtils.isEmpty(cartitemModelList.get(position).getSelectedCoupenID())) {
                                 for (rewardModel rewardModel : DBquires.rewardModelList) {
                                     if (rewardModel.getCoupenId().equals(cartitemModelList.get(position).getSelectedCoupenID())) {
                                         rewardModel.setAlreadyusedCoupen(false);
                                         cartCoupenbodytxt.setText("Congratulation ! Coupen apply successfully");
                                         cartCoupenbodytxt.setTextColor(itemView.getContext().getResources().getColor(R.color.white));
                                     }

                                 }
                             }
                             //exception handiling
                             String discountPriceText = discountprice.getText().toString();
                             discountPriceText = discountPriceText.replaceAll("[^\\d.]", ""); // Remove non-numeric characters
                             if (discountPriceText.startsWith(".")) {
                                 discountPriceText = discountPriceText.substring(1); // Remove '.' at the beginning
                             }
                             long discountPrice = Long.valueOf(discountPriceText);
                             //exception handiling

                             coupensApplied.setVisibility(View.VISIBLE);
                             cartitemModelList.get(position).setDiscountedpricerewardmodel(discountPriceText);
                             productPrice.setText(discountPriceText);
                             if (discountPriceText.startsWith("0000")) {
                                 productPrice.setText(productpriceText+"TK");
                             }else
                             {
                                 productPrice.setText(discountPriceText);
                             }
                             String offerpricededuction = String.valueOf(Long.valueOf(productpriceText) - Long.valueOf(discountPrice));
                             coupensApplied.setText("applied:"+offerpricededuction+"TK");
                             notifyItemChanged(cartitemModelList.size()-1);
                             coupenredemdialog.dismiss();
                         }
                     });

                     coupenRemovebtn.setOnClickListener(new View.OnClickListener() {
                         @Override
                         public void onClick(View view) {
                             for (rewardModel rewardModel:DBquires.rewardModelList) {
                                 if(rewardModel.getCoupenId().equals(cartitemModelList.get(position).getSelectedCoupenID()))
                                 {
                                     rewardModel.setAlreadyusedCoupen(false);

                                 }

                             }
                             cartitemModelList.get(position).setSelectedCoupenID(null);
                             coupensApplied.setVisibility(View.INVISIBLE);
                             productPrice.setText(productpriceText);
                             notifyItemChanged(cartitemModelList.size()-1);
                             coupenredemdialog.dismiss();
                         }
                     });

                     RewardAdapter=new rewardAdapter(position,DBquires.rewardModelList,true,coupenrecyclerview,selectedcoupen,productOrginalprice,coupen_title,coupen_body,coupen_validity,discountprice,cartitemModelList);
                     coupenrecyclerview.setAdapter(RewardAdapter);
                     RewardAdapter.notifyDataSetChanged();

                     toggglerecyclerview.setOnClickListener(new View.OnClickListener() {
                         @Override
                         public void onClick(View view) {
                             togglerecyclerview();
                         }
                     });
                     for (rewardModel rewardModel:DBquires.rewardModelList) {
                         if(rewardModel.getCoupenId().equals(cartitemModelList.get(position).getSelectedCoupenID()))
                         {
                             rewardModel.setAlreadyusedCoupen(false);

                         }

                     }
                     coupenredemdialog.show();

                 }
             });



        }
       private  void togglerecyclerview()
       {
           if(coupenrecyclerview.getVisibility()==View.GONE)
           {
               coupenrecyclerview.setVisibility(View.VISIBLE);
               selectedcoupen.setVisibility(View.GONE);
           }else
           {
               coupenrecyclerview.setVisibility(View.GONE);
               selectedcoupen.setVisibility(View.VISIBLE);
           }
       }
    }
  public  class cartTotalAmountViewHolder extends RecyclerView.ViewHolder{
    private TextView totalItem;
        private TextView totalItemPrice;
        private TextView deliveryPrice;
        private TextView totalAmount;
        private TextView saveAmount;

        public cartTotalAmountViewHolder(@NonNull View itemView) {
            super(itemView);
            totalItem=itemView.findViewById(R.id.orderdate);
            totalItemPrice=itemView.findViewById(R.id.total_item_price);
            deliveryPrice=itemView.findViewById(R.id.delivery_fee);
            totalAmount=itemView.findViewById(R.id.total_price);
            saveAmount=itemView.findViewById(R.id.save_ammount);

        }
        private void setTotalAmountprice(int totalItemText,int totalItemPriceText,String deliverPriceText,int totalAmountText,int saveAmountText)
        {
            String totalItemString = String.valueOf(totalItemText);
            String totalItemPriceString = String.valueOf(totalItemPriceText);
            String totalAmountString = String.valueOf(totalAmountText);
            String saveAmountString = String.valueOf(saveAmountText);
            String  total_cart_amount_belowString=String.valueOf(totalAmountText);

            // Set the text of the TextViews using the string values
            totalItem.setText("product( "+totalItemString+" )TK");
            totalItemPrice.setText(totalItemPriceString);
            deliveryPrice.setText(deliverPriceText);
            totalAmount.setText(totalAmountString+" TK");
            saveAmount.setText("You save your money "+saveAmountString+" TK");
            total_cart_amount_below.setText("TK."+total_cart_amount_belowString);
            LinearLayout parent=(LinearLayout) total_cart_amount_below.getParent().getParent();
            if(Integer.parseInt(String.valueOf(totalItemPriceString))==0)
            {
                if (cartitemModelList.size() > 0) {
                    cartitemModelList.remove(cartitemModelList.size()-1);
                }
                parent.setVisibility(View.GONE);

            }else
            {
                parent.setVisibility(View.VISIBLE);
            }





        }
    }

}
