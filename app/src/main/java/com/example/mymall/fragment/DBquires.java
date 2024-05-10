package com.example.mymall.fragment;


import static com.example.mymall.ProductDetailsActivity.addToWhishListbtn;
import static com.example.mymall.ProductDetailsActivity.initialrating;
import static com.example.mymall.ProductDetailsActivity.productId;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymall.AddAddressActivity;
import com.example.mymall.CartitemModel;
import com.example.mymall.DeliveryActivity;
import com.example.mymall.HomePageAdaptar;
import com.example.mymall.HomepageModel;
import com.example.mymall.ProductDetailsActivity;
import com.example.mymall.R;
import com.example.mymall.categoryAdaptar;
import com.example.mymall.categorymodel;
import com.example.mymall.horizental_scroll_product_model;
import com.example.mymall.myAddressModel;
import com.example.mymall.my_order_item_model;
import com.example.mymall.notificationActivity;
import com.example.mymall.notificationModel;
import com.example.mymall.rewardModel;
import com.example.mymall.sliderModel;
import com.example.mymall.wishlistModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DBquires {
    public static FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
    public static  List<categorymodel> categorymodelList=new ArrayList<>();
   // public static  List<HomepageModel> homepageModelList=new ArrayList<>();
    public static List<List<HomepageModel>> list=new ArrayList<>();
    public static List<String> loadedcategoryName=new ArrayList<>();
    static DocumentSnapshot documentSnapshot;
    //for wishlist
    public static List<String>wishlist=new ArrayList<>();
    public static List<wishlistModel> wishlistModelList=new ArrayList<>();

    //for my rating
    public static List<String> myRatedId=new ArrayList<>();
    public static List<Long> myratings=new ArrayList<>();

    //for my  cart
    public static List<CartitemModel> cartitemModelList=new ArrayList<>();
    public static List<String>cartlist=new ArrayList<>();

    //myadess
    public static List<myAddressModel> myAddressModelList=new ArrayList<>();
    public static List<String>adresslist=new ArrayList<>();
    public static int selectedIndex=-1;

    //reward
    public static List<rewardModel>rewardModelList=new ArrayList<>();

   //for my order
    public static List<my_order_item_model>myOrderItemModelList=new ArrayList<>();

    public static int adressSelected;

    static boolean isTotalAmountAdded;

    public static List<notificationModel> notificationModelList=new ArrayList<>();
    public static ListenerRegistration listenerRegistration;




    public static void loadCategories(RecyclerView categoryrecyclerview,Context context)
    {
       // firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseFirestore.collection("CATEGORIES").orderBy("index").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful())
                        {

                            for(QueryDocumentSnapshot queryDocumentSnapshot:task.getResult())
                            {
                                categorymodelList.add(new categorymodel(queryDocumentSnapshot.getString("icon"),queryDocumentSnapshot.getString("categoryName")));
                            }


                            categoryAdaptar categoryAdaptar=new categoryAdaptar(categorymodelList);
                            categoryrecyclerview.setAdapter(categoryAdaptar);
                            categoryAdaptar.notifyDataSetChanged();

                        }else
                        {
                            String error=task.getException().getMessage();
                            Toast.makeText(context, ""+error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    public static void setFragmentData(RecyclerView homepagerecyclerview, Context context, final int index, String categoryNama)
    {
        firebaseFirestore.collection("CATEGORIES")
                .document(categoryNama.toUpperCase())
                .collection("TOP_DEALS").orderBy("index").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        {
                            if(task.isSuccessful())
                            {

                                for(QueryDocumentSnapshot queryDocumentSnapshot:task.getResult())
                                {
                                    if((long)queryDocumentSnapshot.get("view_type")==0)
                                    {
                                        List<sliderModel>sliderModelList=new ArrayList<sliderModel>();
                                        long no_of_banner=(long)queryDocumentSnapshot.get("no_of_banners");
                                        for(long x=1;x<no_of_banner +1;x++)
                                        {
                                            sliderModelList.add(new sliderModel(queryDocumentSnapshot.getString("banner_"+x),queryDocumentSnapshot.getString("banner_"+"_background")));
                                        }
                                        list.get(index).add(new HomepageModel(0,sliderModelList));
                                    }else if((long)queryDocumentSnapshot.get("view_type")==1)
                                    {
                                        list.get(index).add(new HomepageModel(1,queryDocumentSnapshot.getString("baner_slider")));
                                    }else if((long)queryDocumentSnapshot.get("view_type")==2)
                                    {
                                        List<horizental_scroll_product_model>horizental=new ArrayList<>();
                                        List<wishlistModel>viewallproduct=new ArrayList<>();

                                       // long no_of_product=(long)queryDocumentSnapshot.get("no_of_product");



                                        ArrayList<String> productIds = new ArrayList<>();


                                            ArrayList<String> products = (ArrayList<String>) queryDocumentSnapshot.get("products");
                                            if (products != null) {
                                                productIds.addAll(products);
                                            }



                                            for(String productIdss:productIds)
                                        {
                                            horizental.add(new horizental_scroll_product_model( productIdss,"","",""," "));
                                            viewallproduct.add(new wishlistModel(productIdss,""
                                            ,""
                                            ,0
                                            ,""
                                            ,0
                                            ,""
                                            ,""
                                            ,false
                                                    ,false));
                                        }
                                        list.get(index).add(new HomepageModel(2,queryDocumentSnapshot.getString("layout_title"),horizental,viewallproduct));

                                    }
                                    else if((long)queryDocumentSnapshot.get("view_type")==3)
                                    {
                                        List<horizental_scroll_product_model>gridelayout=new ArrayList<>();

                                       // long no_of_product=(long)queryDocumentSnapshot.get("no_of_product");
                                        ArrayList<String> productIds = new ArrayList<>();


                                            ArrayList<String> products = (ArrayList<String>) queryDocumentSnapshot.get("products");
                                            if (products != null) {
                                                productIds.addAll(products);
                                            }


                                        for(String productIdss:productIds) {
                                            gridelayout.add(new horizental_scroll_product_model(productIdss, "", "", "", " "));
                                        }

                                        list.get(index).add(new HomepageModel(3,queryDocumentSnapshot.getString("layout_title"),gridelayout));


                                    }

                                }


                                HomePageAdaptar homePageAdaptar=new HomePageAdaptar(list.get(index));
                                homepagerecyclerview.setAdapter(homePageAdaptar);
                                homePageAdaptar.notifyDataSetChanged();
                                HomeFragment.swipeRefreshLayout.setRefreshing(false);
                            }else
                            {
                                String error=task.getException().getMessage();
                                Toast.makeText(context,error, Toast.LENGTH_SHORT).show();
                            }

                        }
                    }

                });


    }
    //------------------------------

    //--------------------------------------------------------------------------------------------------------
    public static void loadwishlist(final Context context, final boolean loadproduct,Dialog loading_dialog)
    {


        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser currentuser=firebaseAuth.getCurrentUser();
        firebaseFirestore.collection("USERS").document(currentuser.getUid()).collection("USER_DATA").document("MY_WISHSLIST")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

                    @Override

                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            Long listSize = task.getResult().getLong("list_size");
                            if (listSize != null) {



                                for (long x = 0; x < (long) task.getResult().getLong("list_size"); x++) {
                                    // String productId = task.getResult().getString("product_id_" + x);


                                    if (wishlist.contains(productId)) {
                                        ProductDetailsActivity.AlreadyAddedToWhislist = true;
                                        addToWhishListbtn.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context.getApplicationContext(), R.color.md_red_500)));
                                    } else {
                                        ProductDetailsActivity.AlreadyAddedToWhislist = false;
                                        if (addToWhishListbtn != null) {
                                            addToWhishListbtn.setBackgroundColor(Color.parseColor("#999999"));
                                            addToWhishListbtn.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context.getApplicationContext(), R.color.md_blue_grey_200)));
                                        }
                                    }


                                    //  String productId = "lgrRS1HPRQyiGqkHHDhp";
                                    Object productId = task.getResult().get("product_id_" + x);
                                    if (productId != null) {
                                        wishlist.add(productId.toString());
                                    }



                                    if (loadproduct) {


                                       loading_dialog.show();
                                        // String productId = "lgrRS1HPRQyiGqkHHDhp";
                                      //  DocumentSnapshot documentSnapshot=task.getResult();
                                        Object productIdd = task.getResult().get("product_id_" + x);
                                        if (productIdd != null) {



                                            firebaseFirestore.collection("PRODUCTS").document(productIdd.toString())
                                                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

                                                        @Override
                                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                            if (task.isSuccessful()) {
                                                                DocumentSnapshot documentSnapshot=task.getResult();
                                                                firebaseFirestore.collection("PRODUCTS").document(productIdd.toString())
                                                                        .collection("QUANTITY").orderBy("time", Query.Direction.ASCENDING)
                                                                        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                            @Override
                                                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                                if(task.isSuccessful()) {


                                                                                    if (documentSnapshot != null && documentSnapshot.getLong("stock_qty") != null) {
                                                                                        if (task.getResult().getDocuments().size() < documentSnapshot.getLong("stock_qty")) {
                                                                                            wishlistModelList.add(new wishlistModel(productIdd.toString(), documentSnapshot.getString("product_img_1")
                                                                                                    , documentSnapshot.getString("product_title")
                                                                                                    , documentSnapshot.getLong("free_coupen")
                                                                                                    , documentSnapshot.getString("avg_rating")
                                                                                                    , documentSnapshot.getLong("total_rating")
                                                                                                    , documentSnapshot.getString("product_price")
                                                                                                    , documentSnapshot.getString("cutted_price")
                                                                                                    , documentSnapshot.getBoolean("COD")
                                                                                                    , true));
                                                                                        } else {
                                                                                            wishlistModelList.add(new wishlistModel(productIdd.toString(), documentSnapshot.getString("product_img_1")
                                                                                                    , documentSnapshot.getString("product_title")
                                                                                                    , documentSnapshot.getLong("free_coupen")
                                                                                                    , documentSnapshot.getString("avg_rating")
                                                                                                    , documentSnapshot.getLong("total_rating")
                                                                                                    , documentSnapshot.getString("product_price")
                                                                                                    , documentSnapshot.getString("cutted_price")
                                                                                                    , documentSnapshot.getBoolean("COD")
                                                                                                    , false));
                                                                                        }

                                                                                    }


                                                                                    MyWishListFragment.adapter.notifyDataSetChanged();
                                                                                    loading_dialog.dismiss();

                                                                                }else
                                                                                {
                                                                                    String error = task.getException().getMessage();
                                                                                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show();

                                                                                }
                                                                                }


                                                                        });







                                                            } else {
                                                                // String error = task.getException().getMessage();
                                                                // Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                                                            }


                                                        }


                                                    });

                                        }
                                    }
                                }
                            }

                            }else {
                            // String error = task.getException().getMessage();
                            // Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                        }














                    }
                });

}


   // -----------------------------------------------------------------------------------------------------
    public static void removefromwishlist(int index,Context context)
    {
       // Context context = null;
        final String removeProductID=wishlist.get(index);
        wishlist.remove(index);
        Map<String,Object> updatewishlist=new HashMap<>();
        for(int x=0;x<wishlist.size();x++)
        {
            updatewishlist.put("product_id_"+x,wishlist.get(x));

        }
        updatewishlist.put("list_size",(long)wishlist.size());
        firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA")
                .document("MY_WISHSLIST")
                .set(updatewishlist).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            if(wishlistModelList.size()!=0)
                            {
                                wishlistModelList.remove(index);
                                MyWishListFragment.adapter.notifyDataSetChanged();
                            }
                            ProductDetailsActivity.AlreadyAddedToWhislist=false;
                            Toast.makeText(context, "Remove succsessfully", Toast.LENGTH_SHORT).show();
                        }else
                        {
                            wishlist.add(index,removeProductID);
                            String error=task.getException().getMessage();
                            Toast.makeText(context,error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public static void loadRatingList(Context context)
    {

       myRatedId.clear();
       myratings.clear();
        firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA")
                .document("MY_RATINGLIST")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()) {
                            Long listSize = task.getResult().getLong("list_size");
                           if (listSize != null) {
                                for (long x = 0; x < (long) task.getResult().getLong("list_size"); x++) {
                                    myRatedId.add(task.getResult().getString("product_id_" + x));
                                    Long rating = (Long) task.getResult().get("rating_" + x);
                                    if (rating != null) {
                                        myratings.add(rating.longValue());
                                    }
                                    // myratings.add((long) task.getResult().get("rating_" + x));


                                    if (task.getResult() != null) {
                                        String productId = task.getResult().getString("product_id_" + x);

                                        if (productId != null && productId.equals(productId)) {
                                            ProductDetailsActivity.initialrating = Integer.parseInt(String.valueOf((long) task.getResult().get("rating_" + x))) - 1;
                                            if (ProductDetailsActivity.rateNowContainer != null) {
                                                ProductDetailsActivity.setReting(initialrating);
                                            }
                                        } else {
                                            if (task.getException() != null) {
                                                String error = task.getException().getMessage();
                                                Toast.makeText(context, "" + error, Toast.LENGTH_SHORT).show();
                                                // handle the error message
                                            } else {
                                                // handle the case where there is no exception
                                            }
                                        }

                                    }

                                }

                                }
                            }
                        }

                });


    }

    public static void cleardata()
    {
     categorymodelList.clear();
     list.clear();
     wishlistModelList.clear();
     loadedcategoryName.clear();
     wishlist.clear();
     cartlist.clear();


    }

    public static void loadcartlist(Context context, boolean loadproduct, final TextView badgeCount,TextView total_cart_amount_below,Dialog loading_dialog)
    {
    cartlist.clear();

        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();


        FirebaseUser currentuser=firebaseAuth.getCurrentUser();
        firebaseFirestore.collection("USERS").document(currentuser.getUid()).collection("USER_DATA").document("MY_CARTLIST")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if (task.isSuccessful()) {

                             Long listSize = task.getResult().getLong("list_size");
                              if (listSize != null) {
                            for (long x = 0; x < (long)task.getResult().getLong("list_size"); x++) {
                                // String productId = task.getResult().getString("product_id_" + x);


                                if (cartlist.contains(productId)) {
                                    ProductDetailsActivity.AlreadyAddedToCartlist = true;

                                } else {
                                    ProductDetailsActivity.AlreadyAddedToCartlist = false;
                                }



                                Object productId = task.getResult().get("product_id_" + x);
                                if (productId != null) {
                                    cartlist.add(productId.toString());
                                //cartlist.add(productID);
                                }

                                if (loadproduct) {
                                    Object productIdd = task.getResult().get("product_id_" + x);
                                    if (productIdd!= null) {

                                    loading_dialog.show();
                                        firebaseFirestore.collection("PRODUCTS").document(productIdd.toString())
                                                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                        loading_dialog.show();
                                                        if (task.isSuccessful()) {
                                                            DocumentSnapshot documentSnapshot=task.getResult();

                                                            firebaseFirestore.collection("PRODUCTS").document(productIdd.toString())
                                                                    .collection("QUANTITY").orderBy("time", Query.Direction.ASCENDING)
                                                                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                            if(task.isSuccessful())
                                                                            {
                                                                                int index=0;
                                                                                if(cartlist.size()>=2)
                                                                                {
                                                                                    index=cartlist.size()-2;
                                                                                }

                                                                                if(task.getResult().getDocuments().size()<documentSnapshot.getLong("stock_qty"))
                                                                                {
                                                                                    cartitemModelList.add(index,new CartitemModel(CartitemModel.CART_ITEM,productIdd.toString(), documentSnapshot.getString("product_img_1")
                                                                                            , documentSnapshot.getString("product_title")
                                                                                            , documentSnapshot.getLong("free_coupen")
                                                                                            , documentSnapshot.getString("product_price")
                                                                                            , documentSnapshot.getString("cutted_price")
                                                                                            , (long) 1
                                                                                            , (long)documentSnapshot.getLong("max_qty")
                                                                                            , (long) documentSnapshot.getLong("offer_aplied")
                                                                                            , (long) 0,
                                                                                            true,
                                                                                            documentSnapshot.getLong("stock_qty")));
                                                                                }else
                                                                                {
                                                                                    cartitemModelList.add(index,new CartitemModel(CartitemModel.CART_ITEM,productIdd.toString(), documentSnapshot.getString("product_img_1")
                                                                                            , documentSnapshot.getString("product_title")
                                                                                            , documentSnapshot.getLong("free_coupen")
                                                                                            , documentSnapshot.getString("product_price")
                                                                                            , documentSnapshot.getString("cutted_price")
                                                                                            , (long) 1
                                                                                            , (long)documentSnapshot.getLong("max_qty")
                                                                                            , (long) documentSnapshot.getLong("offer_aplied")
                                                                                            , (long) 0,
                                                                                            false,
                                                                                            documentSnapshot.getLong("stock_qty")));
                                                                                }
                                                                                boolean isTotalAmountAdded = false;
                                                                                for (CartitemModel cartItem : cartitemModelList) {
                                                                                    if (cartItem.getType() == CartitemModel.TOTAL_AMOUNT) {
                                                                                        isTotalAmountAdded = true;
                                                                                        break;
                                                                                    }
                                                                                }
                                                                                if (!isTotalAmountAdded && cartlist.size() > 0) {
                                                                                    cartitemModelList.add(new CartitemModel(CartitemModel.TOTAL_AMOUNT));
                                                                                    LinearLayout parent=(LinearLayout) total_cart_amount_below.getParent().getParent();
                                                                                    parent.setVisibility(View.VISIBLE);
                                                                                }




                                                                                if(cartlist.size()==0)
                                                                                {
                                                                                    cartitemModelList.clear();

                                                                                }

                                                                                MyCartFragment.cartAdapter.notifyDataSetChanged();
                                                                                loading_dialog.dismiss();
                                                                            }else
                                                                            {

                                                                            }
                                                                        }
                                                                    });
                                                            loading_dialog.dismiss();
                                                        } else {

                                                        }

                                                    }

                                                });
                                    }
                                }
                                }
                                  if(cartlist.size()!=0)
                                  {
                                     if(badgeCount!=null) {
                                         badgeCount.setVisibility(View.VISIBLE);
                                     }
                                  }else
                                  {
                                      if(badgeCount!=null) {
                                          badgeCount.setVisibility(View.INVISIBLE);
                                      }
                                  }
                                  if(DBquires.cartlist.size()<99)
                                  {
                                      if(badgeCount!=null) {

                                          badgeCount.setText(String.valueOf(DBquires.cartlist.size()));
                                      }
                                  }else
                                  {
                                       if(badgeCount!=null) {
                                           badgeCount.setText(String.valueOf(99));
                                       }
                                  }//h
                            }


                        }














                    }
                });


    }

    public static void removefromcartlist(int index,Context context,TextView total_cart_amount_below)
    {
       // final String removeProductID=cartlist.get(index);
        if (cartlist.size() > index) {
            cartlist.remove(index);
        }

        Map<String,Object> updatecartlist=new HashMap<>();
        for(int x=0;x<cartlist.size();x++)
        {
            updatecartlist.put("product_id_"+x,cartlist.get(x));

        }
        updatecartlist.put("list_size",(long)cartlist.size());
        firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA")
                .document("MY_CARTLIST")
                .set(updatecartlist).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            if(cartitemModelList.size()!=0)
                            {
                                cartitemModelList.remove(index);
                                MyCartFragment.cartAdapter.notifyDataSetChanged();
                            }
                            if(cartlist.size()==0)
                            {
                                cartitemModelList.clear();
                                LinearLayout parent=(LinearLayout) total_cart_amount_below.getParent().getParent();
                                parent.setVisibility(View.GONE);

                            }
                            if(ProductDetailsActivity.cartItem!=null) {
                                ProductDetailsActivity.cartItem.setActionView(null);
                            }
                            ProductDetailsActivity.AlreadyAddedToCartlist=false;
                            Toast.makeText(context, "Remove succsessfully from Cart", Toast.LENGTH_SHORT).show();
                        }else
                        {
                           // cartlist.add(index,removeProductID);
                            String error=task.getException().getMessage();
                            Toast.makeText(context,error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    public static void loadAdresses(final Context context, Dialog paymentmethod)
    {
        myAddressModelList.clear();
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();

        FirebaseUser currentuser=firebaseAuth.getCurrentUser();
        firebaseFirestore.collection("USERS").document(currentuser.getUid()).collection("USER_DATA").document("MY_ADRESS")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            Intent intent;
                            if (task.getResult().get("list_size") == null) {
                                intent = new Intent(context, AddAddressActivity.class);
                               // intent.putExtra("INTENT",intent);
                            } else {


                                //start
                                Object result = task.getResult();
                                if (result != null) {
                                    Long listSizeLong = (Long) ((DocumentSnapshot) result).get("list_size");
                                    if (listSizeLong != null) {
                                        String listSizeStr = Long.toString(listSizeLong);
                                        for (long x = 1; x <=listSizeLong; x++) {
                                            myAddressModelList.add(new myAddressModel(
                                                    task.getResult().getString("fullname_" + x),
                                                    task.getResult().getString("Mobile_"+x),
                                                    task.getResult().getString("Alternative_Mobile_"+x),
                                                    task.getResult().getString("address_" + x),
                                                    task.getResult().getString("pincode_" + x),
                                                    task.getResult().getBoolean("selected_" + x)


                                            ));
                                            if (task.getResult().getBoolean("selected_" + x)) {
                                                selectedIndex = Integer.parseInt(String.valueOf(x-1));
                                            }
                                        }
                                    }
                                }

                                //end


                                    intent = new Intent(context, DeliveryActivity.class);

                                }
                                context.startActivity(intent);

                            }else
                            {
                                String error = task.getException().getMessage();
                                Toast.makeText(context, "" + error, Toast.LENGTH_SHORT).show();

                            }
                        }
                    });

    }

    public static void loadrewards(Context context,boolean onRewardfragment,Dialog loading_dialog)
    {
        rewardModelList.clear();
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser currentuser=firebaseAuth.getCurrentUser();
        loading_dialog.show();
        firebaseFirestore.collection("USERS").document(currentuser.getUid())
                        .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful())
                        {
                           final Date last_seen=task.getResult().getDate("last_seen");

                            firebaseFirestore.collection("USER_REWARDS")
                                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if(task.isSuccessful())
                                            {
                                                for(QueryDocumentSnapshot documentSnapshot:task.getResult()) {
                                                  if (documentSnapshot.getString("type").equals("Discount") && last_seen.before(documentSnapshot.getDate("validity"))) {
                                                        rewardModelList.add(new rewardModel(documentSnapshot.getId(),documentSnapshot.getString("type"),
                                                                documentSnapshot.getString("body"), documentSnapshot.getString("lower_limit")
                                                                , documentSnapshot.getString("upper_limit"), documentSnapshot.getString("percentage")
                                                                , (Date) documentSnapshot.getDate("validity"),
                                                                documentSnapshot.getBoolean("already_used")));


                                                    } else if(documentSnapshot.getString("type").equals("Discount") && last_seen.before(documentSnapshot.getDate("validity"))) {
                                                        rewardModelList.add(new rewardModel(documentSnapshot.getId(), documentSnapshot.getString("type"),
                                                                documentSnapshot.getString("body"), documentSnapshot.getString("lower_limit")
                                                                , documentSnapshot.getString("upper_limit"), documentSnapshot.getString("Amount")
                                                                , (Date) documentSnapshot.getDate("validity"),
                                                                documentSnapshot.getBoolean("already_used")));

                                                    }
                                                }
                                                if(onRewardfragment) {
                                                    MyRewardFragment.RewardAdapter.notifyDataSetChanged();
                                                }
                                                loading_dialog.dismiss();

                                            }else
                                            {
                                                String error=task.getException().getMessage();
                                                Toast.makeText(context, ""+error, Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                            loading_dialog.dismiss();
                        }else
                        {
                            String error=task.getException().getMessage();
                            Toast.makeText(context, ""+error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });



    }
    public static void loadOrder(Context context, Dialog loadingDialog) {
        myOrderItemModelList.clear();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        loadingDialog.show();

        if (currentUser != null) {
            firebaseFirestore.collection("USERS").document(currentUser.getUid())
                    .collection("USER_ORDER")
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (DocumentSnapshot orderDocument : task.getResult().getDocuments()) {
                                    if (orderDocument.contains("ORDER_ID")) {
                                        String orderId = orderDocument.getString("ORDER_ID");
                                        String ordered=orderDocument.getString("Ordered");
                                        String packed=orderDocument.getString("Packed");
                                        String shipped=orderDocument.getString("Shift");
                                        String delivery=orderDocument.getString("Delivery");


                                        Log.d("Order", "Order ID: " + orderId); // Logging order ID for debugging purposes
                                        Log.d("Order", "Ordered: " + ordered);
                                        Log.d("Order", "Packed: " + packed);
                                        Log.d("Order", "shipped: " + shipped);
                                        Log.d("Order", "Delivery: " + delivery);

                                        firebaseFirestore.collection("ORDERS").document(orderId)
                                                .collection("Order_items").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                        if (task.isSuccessful()) {
                                                            for (DocumentSnapshot orderItemDocument : task.getResult().getDocuments()) {
                                                                if (orderItemDocument != null && orderItemDocument.contains("product_id")) {
                                                                    String productId = orderItemDocument.getString("product_id");
                                                                    String FullName=orderItemDocument.getString("Full_Name");
                                                                    String FullAdress=orderItemDocument.getString("Address");
                                                                    String pincode=orderItemDocument.getString("pincode");
                                                                    String userid=orderItemDocument.getString("user_id");
                                                                    String productid=orderItemDocument.getString("product_id");
                                                                    String orderid=orderItemDocument.getString("ORDER_ID");
                                                                    Date orderdate=orderItemDocument.getDate("Date");

                                                                    Log.d("Order", "Product ID: " + productId); // Logging product ID for debugging purposes

                                                                    firebaseFirestore.collection("PRODUCTS").document(productId).get()
                                                                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                                @Override
                                                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                                    if (task.isSuccessful()) {
                                                                                        DocumentSnapshot productDocument = task.getResult();
                                                                                        if (productDocument != null && productDocument.exists()) {
                                                                                            String productImg = productDocument.getString("product_img_1");
                                                                                            String productTitle = productDocument.getString("product_title");
                                                                                            String price = productDocument.getString("product_price");
                                                                                            Log.d("Ordeer", "productimage:"+productImg + "Product: " + productTitle + ", Price: " + price); // Logging product details for debugging purposes

                                                                                            my_order_item_model myOrderItemModel = new my_order_item_model(productImg, productTitle, price,ordered,packed,shipped,delivery,FullName,FullAdress,pincode,userid,productid,orderid,orderdate);
                                                                                            myOrderItemModelList.add(myOrderItemModel);
                                                                                        } else {
                                                                                            String error = "Product document is null";
                                                                                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                                                                                        }
                                                                                        MyOrderFragment.orderAdapter.notifyDataSetChanged();
                                                                                        loadingDialog.dismiss();
                                                                                    } else {
                                                                                        String error = task.getException().getMessage();
                                                                                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                                                                                    }
                                                                                }
                                                                            });
                                                                }
                                                            }


                                                            // Set visibility of no data view to gone (e.g., noDataTextView.setVisibility(View.GONE);)
                                                        } else {
                                                            String error = task.getException().getMessage();
                                                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                                                            loadingDialog.dismiss();
                                                            // Set visibility of no data view here (e.g., noDataTextView.setVisibility(View.VISIBLE);)
                                                        }
                                                    }
                                                });
                                    }
                                }
                                MyOrderFragment.orderAdapter.notifyDataSetChanged();
                                loadingDialog.dismiss();

                                // Set visibility of no data view here (e.g., noDataTextView.setVisibility(View.VISIBLE);
                            }
                        }
                    });
        } else {
            // Handle the case when the currentUser is null
            String error = "User is not authenticated";
            Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
            loadingDialog.dismiss();
            // Set visibility of no data view here (e.g., noDataTextView.setVisibility(View.VISIBLE);
        }
    }

    public static void loadOrderforAcount(Context context, Dialog loadingDialog) {
        myOrderItemModelList.clear();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        loadingDialog.show();

        if (currentUser != null) {
            firebaseFirestore.collection("USERS").document(currentUser.getUid())
                    .collection("USER_ORDER")
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (DocumentSnapshot orderDocument : task.getResult().getDocuments()) {
                                    if (orderDocument.contains("ORDER_ID")) {
                                        String orderId = orderDocument.getString("ORDER_ID");
                                        String ordered=orderDocument.getString("Ordered");
                                        String packed=orderDocument.getString("Packed");
                                        String shipped=orderDocument.getString("Shift");
                                        String delivery=orderDocument.getString("Delivery");


                                        Log.d("Order", "Order ID: " + orderId); // Logging order ID for debugging purposes
                                        Log.d("Order", "Ordered: " + ordered);
                                        Log.d("Order", "Packed: " + packed);
                                        Log.d("Order", "shipped: " + shipped);
                                        Log.d("Order", "Delivery: " + delivery);

                                        firebaseFirestore.collection("ORDERS").document(orderId)
                                                .collection("Order_items").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                        if (task.isSuccessful()) {
                                                            for (DocumentSnapshot orderItemDocument : task.getResult().getDocuments()) {
                                                                if (orderItemDocument != null && orderItemDocument.contains("product_id")) {
                                                                    String productId = orderItemDocument.getString("product_id");
                                                                    String FullName=orderItemDocument.getString("Full_Name");
                                                                    String FullAdress=orderItemDocument.getString("Address");
                                                                    String pincode=orderItemDocument.getString("pincode");
                                                                    String userid=orderItemDocument.getString("user_id");
                                                                    String productid=orderItemDocument.getString("product_id");
                                                                    String orderid=orderItemDocument.getString("ORDER_ID");
                                                                    Date orderdate=orderItemDocument.getDate("Date");

                                                                    Log.d("Order", "Product ID: " + productId); // Logging product ID for debugging purposes

                                                                    firebaseFirestore.collection("PRODUCTS").document(productId).get()
                                                                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                                @Override
                                                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                                    if (task.isSuccessful()) {
                                                                                        DocumentSnapshot productDocument = task.getResult();
                                                                                        if (productDocument != null && productDocument.exists()) {
                                                                                            String productImg = productDocument.getString("product_img_1");
                                                                                            String productTitle = productDocument.getString("product_title");
                                                                                            String price = productDocument.getString("product_price");
                                                                                            Log.d("Ordeer", "productimage:"+productImg + "Product: " + productTitle + ", Price: " + price); // Logging product details for debugging purposes

                                                                                            my_order_item_model myOrderItemModel = new my_order_item_model(productImg, productTitle, price,ordered,packed,shipped,delivery,FullName,FullAdress,pincode,userid,productid,orderid,orderdate);
                                                                                            myOrderItemModelList.add(myOrderItemModel);
                                                                                        } else {
                                                                                            String error = "Product document is null";
                                                                                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                                                                                        }
                                                                                        MyAccountFragment.orderAdapter.notifyDataSetChanged();
                                                                                        loadingDialog.dismiss();
                                                                                    } else {
                                                                                        String error = task.getException().getMessage();
                                                                                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                                                                                    }
                                                                                }
                                                                            });
                                                                }
                                                            }


                                                            // Set visibility of no data view to gone (e.g., noDataTextView.setVisibility(View.GONE);)
                                                        } else {
                                                            String error = task.getException().getMessage();
                                                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                                                            loadingDialog.dismiss();
                                                            // Set visibility of no data view here (e.g., noDataTextView.setVisibility(View.VISIBLE);)
                                                        }
                                                    }
                                                });
                                    }
                                }
                                MyAccountFragment.orderAdapter.notifyDataSetChanged();
                                loadingDialog.dismiss();

                                // Set visibility of no data view here (e.g., noDataTextView.setVisibility(View.VISIBLE);
                            }
                        }
                    });
        } else {
            // Handle the case when the currentUser is null
            String error = "User is not authenticated";
            Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
            loadingDialog.dismiss();
            // Set visibility of no data view here (e.g., noDataTextView.setVisibility(View.VISIBLE);
        }
    }

    public static void cheacknotification(Boolean remove,@Nullable TextView notifycount)
    {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if(remove)
        {
            listenerRegistration.remove();
        }else
        {
            listenerRegistration= firebaseFirestore.collection("USERS").document(currentUser.getUid()).collection("USER_DATA").document("MY_NOTIFICATION")
                    .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                            if(value!=null && value.exists())
                            {
                                notificationModelList.clear();
                                int unread=0;
                                for (long x = 0; x < (long) value.getLong("list_size"); x++)
                                {

                                    notificationModelList.add(0,new notificationModel(value.getString("nTitle_"+x),value.getString("nBody_"+x),value.getBoolean("readed_"+x)));
                                   if(!value.getBoolean("readed_"+x))
                                   {
                                       unread++;

                                           if(unread!=0)
                                           {
                                               if(notifycount!=null) {
                                                   notifycount.setVisibility(View.VISIBLE);
                                               }
                                           }else
                                           {
                                               if(notifycount!=null) {
                                                   notifycount.setVisibility(View.INVISIBLE);
                                               }
                                           }
                                           if(unread<99)
                                           {
                                               if(notifycount!=null) {

                                                   notifycount.setText(String.valueOf(unread));
                                               }
                                           }else
                                           {
                                               if(notifycount!=null) {
                                                   notifycount.setText(String.valueOf(99));
                                               }
                                           }


                                   }

                                }
                                if(notificationActivity.adapter!=null)
                                {
                                    notificationActivity.adapter.notifyDataSetChanged();
                                }

                            }

                        }
                    });

        }

    }



}





