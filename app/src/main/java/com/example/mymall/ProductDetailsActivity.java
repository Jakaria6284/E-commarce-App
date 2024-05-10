package com.example.mymall;


import static com.example.mymall.HomeActivity.registerDialog;
import static com.example.mymall.HomeActivity.showcart;
import static com.example.mymall.fragment.DBquires.cartitemModelList;
import static com.example.mymall.fragment.DBquires.cartlist;
import static com.example.mymall.fragment.DBquires.loadRatingList;
import static com.example.mymall.fragment.DBquires.loadcartlist;
import static com.example.mymall.fragment.DBquires.loadwishlist;
import static com.example.mymall.fragment.DBquires.myratings;
import static com.example.mymall.fragment.DBquires.removefromwishlist;
import static com.example.mymall.fragment.DBquires.wishlist;
import static com.example.mymall.fragment.DBquires.wishlistModelList;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.mymall.fragment.DBquires;
import com.example.mymall.fragment.signinFragment;
import com.example.mymall.fragment.signupFragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductDetailsActivity extends AppCompatActivity {
    private ViewPager productImagesViewPager;
    private DocumentSnapshot documentSnapshot;
    private TabLayout viewPagerIndicator;
    private TextView product_title;
    public static Dialog loading_dialog;
    private TextView avg_rating;
    private TextView toatal_rating;
    private TextView product_price;
    private TextView cutted_price;
    private  ImageView COD;
    public static MenuItem cartItem;
    private TextView CODTEXT;
    private TextView reward_title;
    private TextView reward_body;
    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    FirebaseUser currentuser=firebaseAuth.getCurrentUser();
    public static Activity productDetailsActivity;
    private boolean in_stock=false;
    private Button coupenRedemp;
    Dialog loading;
    public static boolean fromsearch=false;




    //coupen selected item in dialog
    public static TextView coupen_title;
    public static TextView coupen_body;
    public static TextView coupen_validity;
    public static  RecyclerView coupenrecyclerview;
    public static  LinearLayout selectedcoupen;
    public static rewardAdapter RewardAdapter;
    private String productOrginalprice;
    TextView discountprice;
    TextView originalprice;
    //coupen selected item in dialog


    private LinearLayout addToCart;



    public static FloatingActionButton addToWhishListbtn;
    public static boolean AlreadyAddedToWhislist=false;
    public static boolean  AlreadyAddedToCartlist=false;
    //product description
    private ConstraintLayout productDetailsTabsContainer;
    private ViewPager productDetailsViewpager;
    private TabLayout productDetailsTablayout;
    public static String productDesriptionBpdy;
    public static String productOtherDesriptionBpdy;
    public static int tabposition=-1;
    private TextView totalRatingfigure;
   // private List<productSpecificationModel> productSpecificationModelList=new ArrayList<>();
    public static List<productSpecificationModel> productSpecificationModelList=new ArrayList<>();


    //product description
    FirebaseFirestore firebaseFirestore;
    public static String productId;
    private TextView badgeCount;


    ////rating layout
  //  public static boolean
    public static LinearLayout rateNowContainer;
    private LinearLayout rateNoContainer;
    private TextView Totalrating;
    private TextView avg_rating2;
    private static final int COLOR_GREY = 0xFF999999;
    private static final int COLOR_RED = 0xFFFF0000;
    private LinearLayout ratingprogressbarContainer;
    public static int initialrating;
    //public static  String productId;

    ///rating layout
    private Button buy_now;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       // getSupportActionBar().setDisplayShowTitleEnabled(true);
        // Set the color of the status bar icons
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decor = getWindow().getDecorView();
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.ic_baseline_arrow_back_24);
        upArrow.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);
        actionBar.setHomeAsUpIndicator(upArrow);
        loading_dialog=new Dialog(ProductDetailsActivity.this);
        loading_dialog.setContentView(R.layout.loading);
        loading_dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        loading_dialog.setCancelable(false);

        loading_dialog=new Dialog(ProductDetailsActivity.this);
        loading_dialog.setContentView(R.layout.loading);
        loading_dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        loading_dialog.setCancelable(true);
        coupenRedemp=findViewById(R.id.coupen_redempbtn);
        buy_now=findViewById(R.id.buy_now_btn);
        toatal_rating=findViewById(R.id.total_rating_miniview);
        product_price=findViewById(R.id.tv_product_price);
        cutted_price=findViewById(R.id.cutted_price);
        COD=findViewById(R.id.cashOndeleveryImgView);
        reward_title=findViewById(R.id.reward_title);
        avg_rating2=findViewById(R.id.average_rating_tv);
        reward_body=findViewById(R.id.reward_body);
        Totalrating=findViewById(R.id.total_ratings);
        CODTEXT=findViewById(R.id.cashOnDeleveryTxtView);
        product_title=findViewById(R.id.product_title);
        avg_rating=findViewById(R.id.tv_product_rating_miview);
        totalRatingfigure=findViewById(R.id.total_rating_figure);
        rateNoContainer=findViewById(R.id.rating_number_containers);
        addToCart=findViewById(R.id.addToCartt);
        initialrating=-1;
        ratingprogressbarContainer=findViewById(R.id.rating_progress_bar_container);
        productDetailsTabsContainer=findViewById(R.id.product_details_tab_container);
        firebaseFirestore=FirebaseFirestore.getInstance();
       // String productId = getIntent().getStringExtra("product_id");



        registerDialog=new Dialog(ProductDetailsActivity.this);
        registerDialog.setContentView(R.layout.signindialog);
        signupFragment signupFragment=new signupFragment();
        signinFragment signinFragment=new signinFragment();



        registerDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        Button registerbtn=registerDialog.findViewById(R.id.registerplease);
        Button registerbtn2=registerDialog.findViewById(R.id.registerplease2);

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerDialog.dismiss();
                Intent intent=new Intent(ProductDetailsActivity.this,RegisterActivity.class);
                startActivity(intent);

            }
        });
        registerbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerDialog.dismiss();
                Intent intent=new Intent(ProductDetailsActivity.this,RegisterActivity.class);
                startActivity(intent);

            }
        });
        registerDialog.setCancelable(true);

        coupenRedemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog coupenredemdialog=new Dialog(ProductDetailsActivity.this);
                coupenredemdialog.setContentView(R.layout.coupen_redemp_dialog);
                coupenredemdialog.setCancelable(true);
                coupenredemdialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                ImageView toggglerecyclerview=coupenredemdialog.findViewById(R.id.togglerecyclerview);
                coupenrecyclerview=coupenredemdialog.findViewById(R.id.coupen_recyclerview);
                selectedcoupen=coupenredemdialog.findViewById(R.id.selected_coupen);

                coupen_title=coupenredemdialog.findViewById(R.id.reward_product_title);
                coupen_body=coupenredemdialog.findViewById(R.id.reward_product_body);
                coupen_validity=coupenredemdialog.findViewById(R.id.reward_due_date);

                 originalprice=coupenredemdialog.findViewById(R.id.orginal_price);
                 discountprice=coupenredemdialog.findViewById(R.id.discount_price);
                LinearLayoutManager layoutManager=new LinearLayoutManager(ProductDetailsActivity.this);
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                coupenrecyclerview.setLayoutManager(layoutManager);
                originalprice.setText("TK. "+product_price.getText());
                RewardAdapter=new rewardAdapter(DBquires.rewardModelList,true,coupenrecyclerview,selectedcoupen,productOrginalprice,coupen_title,coupen_body,coupen_validity,discountprice);
                coupenrecyclerview.setAdapter(RewardAdapter);
                RewardAdapter.notifyDataSetChanged();


                //adapter and list


                toggglerecyclerview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        togglerecyclerview();
                    }
                });
                coupenredemdialog.show();

            }
        });





        DBquires dBquires=new DBquires();


        List<String>productImages=new ArrayList<>();
         productId = getIntent().getStringExtra("product_id");
        if (productId != null) {
            String formattedProductId = productId.replace("/", "-");
           loading_dialog.show();
            firebaseFirestore.collection("PRODUCTS").document(formattedProductId)
                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                documentSnapshot = task.getResult();

                                //start
                                firebaseFirestore.collection("PRODUCTS").document(productId)
                                        .collection("QUANTITY").orderBy("time", Query.Direction.ASCENDING)
                                        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if(task.isSuccessful())
                                                {
                                                    Long onOfProductImages = documentSnapshot.getLong("on_of_product_images");
                                                    if (onOfProductImages != null) {
                                                        for (long x = 1; x <= onOfProductImages.longValue(); x++) {
                                                            productImages.add(documentSnapshot.getString("product_img_" + x));
                                                        }
                                                    }
                                                    product_images_Adaptar productImagesAdaptar = new product_images_Adaptar(productImages);
                                                    productImagesViewPager.setAdapter(productImagesAdaptar);

                                                    product_title.setText(documentSnapshot.getString("product_title"));
                                                     avg_rating2.setText(documentSnapshot.getString("avg_rating"));
                                                     avg_rating.setText(documentSnapshot.getString("avg_rating"));
                                                    // avg_rating.setText(String.valueOf(calculateAverageRating(starposition+1)));

                                                    if (documentSnapshot != null) {
                                                        Long totalRating = documentSnapshot.getLong("total_rating");
                                                        if (totalRating != null) {
                                                            toatal_rating.setText(String.valueOf(totalRating.longValue()));
                                                        }
                                                    }


                                                    product_price.setText(documentSnapshot.getString("product_price"));

                                                    productOrginalprice= documentSnapshot.getString("product_price");
                                                    //for coupen dialog

                                                    //for coupen dialog

                                                    cutted_price.setText(documentSnapshot.getString("cutted_price"));
                                                    if (documentSnapshot != null) {
                                                        Boolean cod = documentSnapshot.getBoolean("COD");
                                                        if (cod != null && cod.booleanValue()) {
                                                            COD.setVisibility(View.VISIBLE);
                                                            CODTEXT.setVisibility(View.VISIBLE);
                                                        } else {
                                                            COD.setVisibility(View.INVISIBLE);
                                                            CODTEXT.setVisibility(View.INVISIBLE);
                                                        }
                                                    }

                                                    // reward_title.setText((long) documentSnapshot.get("free_coupen") + documentSnapshot.getString("free_coupen_title"));
                                                    if (documentSnapshot != null) {
                                                        Long freeCoupen = documentSnapshot.getLong("free_coupen");
                                                        String freeCoupenTitle = documentSnapshot.getString("free_coupen_title");
                                                        if (freeCoupen != null) {
                                                            reward_title.setText(freeCoupen.longValue() + freeCoupenTitle);
                                                        } else {
                                                            reward_title.setText(freeCoupenTitle);
                                                        }
                                                    }

                                                    reward_body.setText(documentSnapshot.getString("free_coupen_body"));
                                                    if (documentSnapshot != null) {
                                                        Boolean useTabLayout = documentSnapshot.getBoolean("use_tablayout");


                                                        if (useTabLayout != null && useTabLayout.booleanValue()) {
                                                            productDetailsTabsContainer.setVisibility(View.VISIBLE);


                                                            // SpecificationFragment.productSpecificationModelList=new ArrayList<>();
                                                            //  productOtherDesriptionBpdy=documentSnapshot.getString("other_details");
                                                            productDesriptionBpdy = documentSnapshot.getString("product_description");
                                                            productSpecificationModelList.clear();
                                                            for (long x = 0; x <= (long) documentSnapshot.get("spec_field_no"); x++) {

                                                                productSpecificationModelList.add(new productSpecificationModel(documentSnapshot.getString("spec_field_name_" + x), documentSnapshot.getString("spec_field_value_" + x)));

                                                            }


                                                        } else {
                                                            productDetailsTabsContainer.setVisibility(View.GONE);
                                                        }


                                                    }


                                                    // Totalrating.setText(String.valueOf((long) documentSnapshot.get("total_rating") + "Rating"));
                                                    if (documentSnapshot != null) {
                                                        Long totalRating = documentSnapshot.getLong("total_rating");
                                                        if (totalRating != null) {
                                                            Totalrating.setText(totalRating.longValue() + " Rating");
                                                        }
                                                    }

                                                    for (int x = 0; x < 5; x++) {
                                                        TextView rating = (TextView) rateNoContainer.getChildAt(x);
                                                        //  rating.setText((String.valueOf((long) documentSnapshot.get((5 - x) + "_star"))));
                                                        if (documentSnapshot != null) {
                                                            String starFieldName = (5 - x) + "_star";
                                                            Long starValue = documentSnapshot.getLong(starFieldName);
                                                            if (starValue != null) {
                                                                rating.setText(String.valueOf(starValue));
                                                            }
                                                        }

                                                        ProgressBar progressBar = (ProgressBar) ratingprogressbarContainer.getChildAt(x);
                                                        // progressBar.setMax(Integer.parseInt(String.valueOf((long) documentSnapshot.get("total_rating"))));
                                                        if (documentSnapshot != null) {
                                                            Long totalRating = documentSnapshot.getLong("total_rating");
                                                            if (totalRating != null) {
                                                                int maxProgress = totalRating.intValue();
                                                                progressBar.setMax(maxProgress);
                                                            }
                                                        }

                                                        // progressBar.setProgress(Integer.parseInt(String.valueOf((long) documentSnapshot.get((5 - x) + "_star"))));
                                                        if (documentSnapshot != null) {
                                                            String starFieldName = (5 - x) + "_star";
                                                            Long starValue = documentSnapshot.getLong(starFieldName);
                                                            if (starValue != null) {
                                                                int progressValue = starValue.intValue();
                                                                progressBar.setProgress(progressValue);
                                                            }
                                                        }

                                                    }
                                                    //  totalRatingfigure.setText(String.valueOf((long) documentSnapshot.get("total_rating")));
                                                    if (documentSnapshot != null) {
                                                        Long totalRating = documentSnapshot.getLong("total_rating");
                                                        if (totalRating != null) {
                                                            totalRatingfigure.setText(String.valueOf(totalRating));
                                                        }
                                                    }

                                                    productDetailsViewpager.setAdapter(new product_details_Adaptar(getSupportFragmentManager(), productDetailsTablayout.getTabCount()));
                                                    productDetailsTablayout.setupWithViewPager(productDetailsViewpager);
                                                    if (currentuser != null) {
                                                        if (wishlist.size() == 0) {
                                                            loadwishlist(ProductDetailsActivity.this, false,loading_dialog);
                                                        }

                                                        if (cartlist.contains(productId)) {
                                                            AlreadyAddedToCartlist = true;
                                                            // addToWhishListbtn.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.md_red_500)));
                                                        } else {
                                                            AlreadyAddedToCartlist = false;
                                                        }

                                                        if (cartlist.size() == 0) {
                                                            loadcartlist(ProductDetailsActivity.this, false, badgeCount, new TextView(ProductDetailsActivity.this),loading_dialog);
                                                        }

                                                        if (myratings.size() == 0) {
                                                            loadRatingList(ProductDetailsActivity.this);
                                                        }

                                                    } else {
                                                        //String error = task.getException().getMessage();
                                                        // Toast.makeText(ProductDetailsActivity.this, "" + error, Toast.LENGTH_SHORT).show();
                                                    }


                                                    if (DBquires.myRatedId.contains(productId)) {
                                                        int index = DBquires.myRatedId.indexOf(productId);
                                                        initialrating = Integer.parseInt(String.valueOf(myratings.get(index) - 1));
                                                        setReting(initialrating);


                                                    }

                                                    if (wishlist.contains(productId)) {
                                                        AlreadyAddedToWhislist = true;
                                                        addToWhishListbtn.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.md_red_500)));

                                                    } else {
                                                        AlreadyAddedToWhislist = false;
                                                    }
                                                    if(DBquires.rewardModelList.size()==0)
                                                    {
                                                        DBquires.loadrewards(ProductDetailsActivity.this,false,loading_dialog);
                                                    }


                                                    //akhon





                                                    if(task.getResult().getDocuments().size()<documentSnapshot.getLong("stock_qty"))
                                                    {
                                                        in_stock=true;
                                                        buy_now.setVisibility(View.VISIBLE);
                                                        addToCart.setOnClickListener(new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View view) {
                                                                if (currentuser == null) {
                                                                    registerDialog.show();
                                                                } else {
                                                                    if (cartlist.contains(productId)) {
                                                                        Toast.makeText(ProductDetailsActivity.this, "Already Added in Cart", Toast.LENGTH_SHORT).show();
                                                                    } else {
                                                                        Map<String, Object> producttID = new HashMap<>();
                                                                        producttID.put("product_id_" + String.valueOf(cartlist.size()), productId);
                                                                        producttID.put("list_size", (long) cartlist.size() + 1);

                                                                        firebaseFirestore.collection("USERS").document(currentuser.getUid()).collection("USER_DATA").document("MY_CARTLIST")
                                                                                .update(producttID).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                    @Override
                                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                                        if (task.isSuccessful()) {

                                                                                            if (task.isSuccessful()) {
                                                                                                if (cartitemModelList.size() != 0) {
                                                                                                    cartitemModelList.add(0, new CartitemModel(CartitemModel.CART_ITEM,formattedProductId, documentSnapshot.getString("product_img_1")
                                                                                                            , documentSnapshot.getString("product_title")
                                                                                                            , documentSnapshot.getLong("free_coupen")
                                                                                                            , documentSnapshot.getString("product_price")
                                                                                                            , documentSnapshot.getString("cutted_price")
                                                                                                            , (long) 1
                                                                                                            , documentSnapshot.getLong("max_qty")
                                                                                                            , (long) documentSnapshot.getLong("offer_aplied")
                                                                                                            , (long) 0,
                                                                                                            in_stock,
                                                                                                            documentSnapshot.getLong("stock_qty")));
                                                                                                }
                                                                                                AlreadyAddedToCartlist = true;
                                                                                                cartlist.add(productId);
                                                                                                Toast.makeText(ProductDetailsActivity.this, "Added in CartList", Toast.LENGTH_SHORT).show();
                                                                                                invalidateOptionsMenu();

                                                                                            } else {

                                                                                            }


                                                                                        }


                                                                                    }
                                                                                });


                                                                    }

                                                                }

                                                            }
                                                        });

                                                    }else
                                                    {
                                                        in_stock=false;
                                                        buy_now.setVisibility(View.GONE);
                                                        TextView outofstock = (TextView) addToCart.getChildAt(0);
                                                        outofstock.setText("OUT OF STOCK");
                                                        outofstock.setTextColor(getResources().getColor(R.color.md_red_500));
                                                        outofstock.setCompoundDrawables(null, null, null, null);

                                                    }
                                                }else
                                                {
                                                    String error=task.getException().getMessage();
                                                    Toast.makeText(ProductDetailsActivity.this, ""+error, Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                //end
                                loading_dialog.dismiss();
                            }
                        }


                        //else


                    });
        }




    buy_now.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            paymentActivity.fromcart=false;
           productDetailsActivity=ProductDetailsActivity.this;
            DeliveryActivity.cartitemModelList=new ArrayList<>();
            DeliveryActivity.cartitemModelList.add(new CartitemModel(CartitemModel.CART_ITEM,productId, documentSnapshot.getString("product_img_1")
                    , documentSnapshot.getString("product_title")
                    , documentSnapshot.getLong("free_coupen")
                    , documentSnapshot.getString("product_price")
                    , documentSnapshot.getString("cutted_price")
                    , (long)1
                    ,  documentSnapshot.getLong("max_qty")
                    , (long)documentSnapshot.getLong("offer_aplied")
                    , (long)0 ,
                    in_stock,
                    documentSnapshot.getLong("stock_qty")));

            DeliveryActivity.cartitemModelList.add(new CartitemModel(CartitemModel.TOTAL_AMOUNT));
            if(DBquires.myAddressModelList.size()==0)
            {
                DBquires.loadAdresses(ProductDetailsActivity.this,new Dialog(ProductDetailsActivity.this));
            }else
            {
                Intent intent=new Intent(ProductDetailsActivity.this,DeliveryActivity.class);
                startActivity(intent);
            }


        }

    });

        productImagesViewPager=findViewById(R.id.product_images_viewpager);
        viewPagerIndicator= findViewById(R.id.view_pager_indicator);
        addToWhishListbtn=findViewById(R.id.add_to_whishlist_btn);
        productDetailsViewpager=findViewById(R.id.product_details_viewpager);
        productDetailsTablayout=findViewById(R.id.product_detail_tabLayout);

        addToWhishListbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentuser != null) {

                    if (AlreadyAddedToWhislist) {
                        // addToWhishListbtn.setEnabled(false);
                        int index = wishlist.indexOf(productId);
                        removefromwishlist(index, ProductDetailsActivity.this);
                        //AlreadyAddedToWhislist = false;
                        //addToWhishListbtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(String.valueOf(R.color.md_red_500))));
                        // addToWhishListbtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("9e9e9e")));
                        addToWhishListbtn.setBackgroundColor(Color.parseColor("#999999"));
                        addToWhishListbtn.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.md_blue_grey_200)));

                        // AlreadyAddedToWhislist = false;
                    } else {
                        Map<String, Object> producttID = new HashMap<>();
                        producttID.put("product_id_" + String.valueOf(wishlist.size()), productId);

                        firebaseFirestore.collection("USERS").document(currentuser.getUid()).collection("USER_DATA").document("MY_WISHSLIST")
                                .update(producttID).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Map<String, Object> updatelistsize = new HashMap<>();
                                            updatelistsize.put("list_size", (long) wishlist.size() + 1);

                                              //hello
                                            firebaseFirestore.collection("USERS").document(currentuser.getUid()).collection("USER_DATA").document("MY_WISHSLIST")
                                                    .update(updatelistsize).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                if (wishlistModelList.size() != 0) {
                                                                    wishlistModelList.add(new wishlistModel(productId,documentSnapshot.getString("product_img_1")
                                                                            , documentSnapshot.getString("product_title")
                                                                            , documentSnapshot.getLong("free_coupen")
                                                                            , documentSnapshot.getString("avg_rating")
                                                                            , documentSnapshot.getLong("total_rating")
                                                                            , documentSnapshot.getString("product_price")
                                                                            , documentSnapshot.getString("cutted_price")
                                                                            , documentSnapshot.getBoolean("COD")
                                                                            ,in_stock));
                                                                }
                                                                AlreadyAddedToWhislist = true;
                                                                addToWhishListbtn.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.md_red_500)));
                                                                wishlist.add(productId);
                                                                Toast.makeText(ProductDetailsActivity.this, "Added in Wishlist", Toast.LENGTH_SHORT).show();

                                                            } else {

                                                            }
                                                        }
                                                    });//hello


                                        }


                                    }
                                });


                        productDetailsTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                            @Override
                            public void onTabSelected(TabLayout.Tab tab) {
                                tabposition = tab.getPosition();

                                productDetailsViewpager.setCurrentItem(tab.getPosition());
                            }

                            @Override
                            public void onTabUnselected(TabLayout.Tab tab) {

                            }

                            @Override
                            public void onTabReselected(TabLayout.Tab tab) {

                            }
                        });
                        productDetailsViewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(productDetailsTablayout));


                        ///rating now


                        ///rating now

                    }
                }else
                {
                    registerDialog.show();
                }




            }


        });

        rateNowContainer = findViewById(R.id.rate_now_container);
        for (int x = 0; x < rateNowContainer.getChildCount(); x++) {
            final int starposition = x;
            rateNowContainer.getChildAt(x).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Map<String,Object>  updaterating=new HashMap<>();
                   if(currentuser==null)
                   {
                       registerDialog.show();
                   }else {
                       if (starposition != initialrating) {
                           setReting(starposition);
                           if (DBquires.myRatedId.contains(productId)) {

                               TextView finalrating = (TextView) rateNoContainer.getChildAt(5 - starposition - 1);
                               TextView oldrating = (TextView) rateNoContainer.getChildAt(5 - initialrating - 1);
                               updaterating.put(initialrating + 1 + "_star", Long.parseLong(oldrating.getText().toString()) - 1);
                               updaterating.put("avg_rating", calculateAverageRating(starposition - initialrating, true));
                               updaterating.put(starposition + 1 + "_star", Long.parseLong(finalrating.getText().toString()) + 1);

                           } else {
                               updaterating.put(starposition + 1 + "_star", (long) documentSnapshot.get(starposition + 1 + "_star") + 1);
                               updaterating.put("avg_rating", calculateAverageRating(starposition + 1, false));
                               updaterating.put("total_rating", documentSnapshot.getLong("total_rating") + 1);

                           }
                           firebaseFirestore.collection("PRODUCTS").document(productId)
                                   .update(updaterating).addOnCompleteListener(new OnCompleteListener<Void>() {
                                       @Override
                                       public void onComplete(@NonNull Task<Void> task) {
                                           Map<String, Object> rating = new HashMap<>();
                                           if (task.isSuccessful()) {

                                               if (DBquires.myRatedId.contains(productId)) {
                                                   rating.put("rating_" + DBquires.myRatedId.indexOf(productId), starposition + 1);

                                               } else {

                                                   rating.put("list_size", (long) DBquires.myRatedId.size() + 1);
                                                   rating.put("product_id_" + DBquires.myRatedId.size(), productId);
                                                   rating.put("rating_" + DBquires.myRatedId.size(), (long) starposition + 1);

                                               }


                                               rating.put("list_size", (long) DBquires.myRatedId.size() + 1);
                                               rating.put("product_id_" + DBquires.myRatedId.size(), productId);
                                               rating.put("rating_" + DBquires.myRatedId.size(), (long) starposition + 1);
                                               if (task.isSuccessful()) {
                                                   firebaseFirestore.collection("USERS").document(currentuser.getUid()).collection("USER_DATA")
                                                           .document("MY_RATINGLIST")
                                                           .update(rating).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                               @Override
                                                               public void onComplete(@NonNull Task<Void> task) {
                                                                   if (task.isSuccessful()) {
                                                                       if (DBquires.myRatedId.contains(productId)) {
                                                                           myratings.set(DBquires.myRatedId.indexOf(productId), (long) starposition + 1);
                                                                           TextView finalrating = (TextView) rateNoContainer.getChildAt(5 - starposition - 1);
                                                                           TextView oldrating = (TextView) rateNoContainer.getChildAt(5 - initialrating - 1);
                                                                           finalrating.setText(String.valueOf(Integer.parseInt(finalrating.getText().toString()) + 1));
                                                                           oldrating.setText(String.valueOf(Integer.parseInt(oldrating.getText().toString()) - 1));

                                                                       } else {

                                                                           DBquires.myRatedId.add(productId);
                                                                           myratings.add((long) starposition + 1);
                                                                           toatal_rating.setText(String.valueOf((long) documentSnapshot.get("total_rating") + 1));
                                                                           Totalrating.setText(String.valueOf((long) documentSnapshot.get("total_rating") + 1 + "Rating"));

                                                                           totalRatingfigure.setText(String.valueOf((long) documentSnapshot.get("total_rating") + 1));
                                                                           TextView rating = (TextView) rateNoContainer.getChildAt(5 - starposition - 1);
                                                                           rating.setText(String.valueOf(Integer.parseInt(rating.getText().toString()) + 1));


                                                                           Toast.makeText(ProductDetailsActivity.this, "Thank you for rating", Toast.LENGTH_SHORT).show();

                                                                       }

                                                                       for (int x = 0; x < 5; x++) {
                                                                           TextView ratingfigure = (TextView) rateNoContainer.getChildAt(x);
                                                                           // ratingfigure.setText((String.valueOf((long) documentSnapshot.get((5 - x) + "_star"))));

                                                                           ProgressBar progressBar = (ProgressBar) ratingprogressbarContainer.getChildAt(x);
                                                                           // int maxprogress= Integer.parseInt(String.valueOf((long) documentSnapshot.get("total_rating")+1));

                                                                           progressBar.setMax(Integer.parseInt(totalRatingfigure.getText().toString()));


                                                                           progressBar.setProgress(Integer.parseInt(ratingfigure.getText().toString()));
                                                                       }
                                                                       initialrating = starposition;
                                                                       avg_rating.setText(calculateAverageRating(0, true));
                                                                       avg_rating2.setText(calculateAverageRating(0, true));

                                                                       if (wishlist.contains(productId) && wishlistModelList.size() != 0) {
                                                                           int index = wishlist.indexOf(productId);
                                                                           wishlistModel chngerating = wishlistModelList.get(index);
                                                                           wishlistModelList.get(index).setRating(avg_rating.getText().toString());
                                                                           wishlistModelList.get(index).setTotalRating(Long.parseLong(totalRatingfigure.getText().toString()));
                                                                       }
                                                                   } else {
                                                                       setReting(initialrating);
                                                                       String error = task.getException().getMessage();
                                                                       Toast.makeText(ProductDetailsActivity.this, "" + error, Toast.LENGTH_SHORT).show();
                                                                   }
                                                               }
                                                           });

                                               } else {
                                                   setReting(initialrating);
                                                   String error = task.getException().getMessage();
                                                   Toast.makeText(ProductDetailsActivity.this, "" + error, Toast.LENGTH_SHORT).show();

                                               }
                                           }

                                       }
                                   });

                       }

                   }
                }
            });
        }
        //coupen dialog

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
    //coupen dialog
    @Override
    protected void onStart() {
        super.onStart();
       // String productID=getIntent().getStringExtra("product_id").toString();

        // Your code here
        if (currentuser != null) {
            if (wishlist.size() == 0) {
                loadwishlist(ProductDetailsActivity.this, false,loading_dialog);
            }

            if (myratings.size() == 0) {
                loadRatingList(ProductDetailsActivity.this);
            }
            if(DBquires.rewardModelList.size()==0)
            {
                DBquires.loadrewards(ProductDetailsActivity.this,false,loading_dialog);
            }

        } else {
            //String error = task.getException().getMessage();
            // Toast.makeText(ProductDetailsActivity.this, "" + error, Toast.LENGTH_SHORT).show();
        }
        if(DBquires.myRatedId.contains(productId))
        {
            int index=DBquires.myRatedId.indexOf(productId);
          //  initialrating= Integer.parseInt(String.valueOf(myratings.get(index)-1));
            if (index < myratings.size()) {
                initialrating = Integer.parseInt(String.valueOf(myratings.get(index) - 1));
                // Your code here
            }

            setReting(initialrating);


        }else
        {

        }

        if(cartlist.contains(productId))
        {
            AlreadyAddedToCartlist=true;
           // addToWhishListbtn.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.md_red_500)));
        }else
        {
            AlreadyAddedToCartlist=false;
           // addToWhishListbtn.setBackgroundColor(Color.parseColor("#999999"));
            //addToWhishListbtn.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.md_blue_grey_200)));
        }


        if(wishlist.contains(productId))
        {
            AlreadyAddedToWhislist=true;
            addToWhishListbtn.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.md_red_500)));
        }else
        {
            AlreadyAddedToWhislist=false;
            addToWhishListbtn.setBackgroundColor(Color.parseColor("#999999"));
            addToWhishListbtn.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.md_blue_grey_200)));
        }
    }

    public static void setReting(int starposition)
    {
        for (int x = 0; x < rateNowContainer.getChildCount(); x++) {
            ImageView starbtn = (ImageView) rateNowContainer.getChildAt(x);
           // ColorStateList csl = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.orange));
            starbtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#bebebe")));
            if (x <= starposition) {

                starbtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#ffbb00")));

            }
        }
    }


    public String calculateAverageRating(long currentuserRating,boolean update)
    {
        float totalstars=0;
        for(int x=1;x<6;x++){
        TextView ratingno=(TextView) rateNoContainer.getChildAt(5-x);
        totalstars= totalstars+(Long.parseLong(ratingno.getText().toString())*x);

        }
        totalstars=totalstars+currentuserRating;
        if(update)
        {
            return String.valueOf(totalstars/Long.parseLong(totalRatingfigure.getText().toString())).substring(0,3);
        }else
        {
            return String.valueOf(totalstars/(Long.parseLong(totalRatingfigure.getText().toString())+1)).substring(0,3);
        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

            getMenuInflater().inflate(R.menu.serach_and_cart_icon,menu);
        MenuItem cartItem= menu.findItem(R.id.cart);


            cartItem.setActionView(R.layout.badge_layout_file);

            TextView badgeCount=cartItem.getActionView().findViewById(R.id.badge_count);
            ImageView badgeIcon=cartItem.getActionView().findViewById(R.id.badgeIcon);
            if(currentuser!=null)
            {
                if (cartlist.size() == 0) {
                    loadcartlist(ProductDetailsActivity.this, false,badgeCount,new TextView(ProductDetailsActivity.this),loading_dialog);
                }else
                {
                    if(badgeCount!=null) {
                        badgeCount.setVisibility(View.VISIBLE);
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

            badgeIcon.setImageResource(R.drawable.shopping);

            cartItem.getActionView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(ProductDetailsActivity.this,HomeActivity.class);
                    showcart=true;
                    startActivity(intent);

                }
            });


        return true;

        // return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==android.R.id.home)
        {
            productDetailsActivity=null;
            finish();
            return true;
        }else if(id==R.id.search)
        {
            if(fromsearch)
            {
                finish();
            }else
            {
                Intent searchIntent=new Intent(this,searchActivity.class);
                startActivity(searchIntent);
            }


            return true;
        }

        else if(id==R.id.cart)
        {
            if(currentuser!=null) {
                Intent intent = new Intent(ProductDetailsActivity.this, HomeActivity.class);
                showcart=true;
                startActivity(intent);
            }else {
                registerDialog.show();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        productDetailsActivity=null;
        super.onBackPressed();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        fromsearch=false;
    }
}


