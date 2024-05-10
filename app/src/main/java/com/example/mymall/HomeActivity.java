package com.example.mymall;


import static com.example.mymall.fragment.DBquires.cartlist;
import static com.example.mymall.fragment.DBquires.loadcartlist;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.airbnb.lottie.LottieAnimationView;
import com.example.mymall.fragment.DBquires;
import com.example.mymall.fragment.HomeFragment;
import com.example.mymall.fragment.MyAccountFragment;
import com.example.mymall.fragment.MyCartFragment;
import com.example.mymall.fragment.MyOrderFragment;
import com.example.mymall.fragment.MyRewardFragment;
import com.example.mymall.fragment.MyWishListFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity<homeActivity> extends AppCompatActivity {
    Toolbar toolbar;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    private TextView toolbar_txt;
    private TextView badgeCount;
    NavigationView navigationView;
    public static MenuItem cartItem;
    public static MenuItem notificationItem;
    private static final int HOME_FRAGMENT = 0;
    private static final int CART_FRAGMENT = 1;
    private static final int ORDER_FRAGMENT = 2;
    private static final int WISHLIST_FRAGMENT = 3;
    private static final int REWARD_FRAGMENT = 4;
    private static final int ACCOUNT_FRAGMENT = 5;
    public static Boolean showcart = false;
    public static Dialog loading_dialog;
    FirebaseAuth firebaseAuth;


    FirebaseUser currentuser;
    public static Dialog registerDialog;
    private LottieAnimationView nointernet;
    private LottieAnimationView welcome;
    private LottieAnimationView welcometxt;


    private Window window;


    private int currentFragment;
    public static Activity HomeActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        firebaseAuth = FirebaseAuth.getInstance();
       // Toolbar toolbar = findViewById(R.id.toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationView);
        toolbar_txt = findViewById(R.id.toolbar_txt);
        toolbar_txt.setVisibility(View.INVISIBLE);
        nointernet = findViewById(R.id.nointernet);
        currentuser = firebaseAuth.getCurrentUser();
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set the color of the status bar icons
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decor = getWindow().getDecorView();
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        loading_dialog=new Dialog(HomeActivity.this);
        loading_dialog.setContentView(R.layout.loading);
        loading_dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        loading_dialog.setCancelable(false);




        //registerDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);


        //dialog

        //dialog



        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();


        if (networkInfo != null && networkInfo.isConnected()) {
            nointernet.setVisibility(View.INVISIBLE);


            if (showcart) {
                 HomeActivity = this;
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                gotoFragment("My cart", new MyCartFragment(), CART_FRAGMENT);
            } else {
                toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
                Drawable toggleIcon = toggle.getDrawerArrowDrawable().mutate();
                toggleIcon.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
                toggle.setHomeAsUpIndicator(toggleIcon);

                window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

                drawerLayout.addDrawerListener(toggle);
                toggle.syncState();
                setFragment(new HomeFragment(), HOME_FRAGMENT);
            }
        } else {
            nointernet.setVisibility(View.VISIBLE);
            toolbar.setVisibility(View.GONE);

        }

        navigationView.getMenu().getItem(0).setChecked(true);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (currentuser != null) {
                    int id = item.getItemId();
                    if (id == R.id.myhome) {
                        toolbar_txt.setVisibility(View.VISIBLE);
                        getSupportActionBar().setDisplayShowTitleEnabled(false);
                        invalidateOptionsMenu();

                        setFragment(new HomeFragment(), HOME_FRAGMENT);

                    } else if (id == R.id.mycart) {

                        // Toast.makeText(HomeActivity.this, "this is my order", Toast.LENGTH_SHORT).show();
                        gotoFragment("My Cart", new MyCartFragment(), CART_FRAGMENT);
                    } else if (id == R.id.myorder) {
                        gotoFragment("My Order", new MyOrderFragment(), ORDER_FRAGMENT);
                    } else if (id == R.id.mywhishlist) {
                        gotoFragment("My Wishlist", new MyWishListFragment(), WISHLIST_FRAGMENT);
                    } else if (id == R.id.myreward) {
                        gotoFragment("My Reward", new MyRewardFragment(), REWARD_FRAGMENT);
                    } else if (id == R.id.myprofile) {
                        gotoFragment("My Account", new MyAccountFragment(), ACCOUNT_FRAGMENT);
                    } else if (id == R.id.logout) {
                        FirebaseAuth.getInstance().signOut();
                        DBquires.cleardata();
                        Intent intent = new Intent(HomeActivity.this, RegisterActivity.class);
                        startActivity(intent);

                    }
                    item.setChecked(true);
                    drawerLayout.closeDrawer(GravityCompat.START);
                    return true;
                } else {
                    item.setChecked(true);
                    drawerLayout.closeDrawer(GravityCompat.START);
                    registerDialog.show();
                    return false;
                }


            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (currentFragment == HOME_FRAGMENT) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getMenuInflater().inflate(R.menu.toolbar_manu_item, menu);
            cartItem = menu.findItem(R.id.cart);
            cartItem.setActionView(R.layout.badge_layout_file);
            badgeCount = cartItem.getActionView().findViewById(R.id.badge_count);
            ImageView badgeIcon = cartItem.getActionView().findViewById(R.id.badgeIcon);
            badgeIcon.setImageResource(R.drawable.shopping);
            if (currentuser != null) {
                if (cartlist.size() == 0) {
                    loadcartlist(HomeActivity.this, false, badgeCount,new TextView(HomeActivity.this),loading_dialog);
                    if (badgeCount != null) {
                        badgeCount.setVisibility(View.INVISIBLE);
                    }
                } else {
                    if (badgeCount != null) {
                        badgeCount.setVisibility(View.VISIBLE);
                    }
                    if (DBquires.cartlist.size() < 99) {
                        if (badgeCount != null) {
                            badgeCount.setText(String.valueOf(DBquires.cartlist.size()));
                        }
                    } else {
                        if (badgeCount != null) {
                            badgeCount.setText(String.valueOf(99));
                        }
                    }//h
                }
            }

            cartItem.getActionView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    gotoFragment("My Cart", new MyCartFragment(), CART_FRAGMENT);
                }
            });
            notificationItem = menu.findItem(R.id.notification);
            notificationItem.setActionView(R.layout.badge_layout_file);
           TextView notifycountCount = notificationItem.getActionView().findViewById(R.id.badge_count);
            ImageView badgeicon = notificationItem.getActionView().findViewById(R.id.badgeIcon);
            badgeicon.setImageResource(R.drawable.nitification);

            if(currentuser!=null)
            {
                DBquires.cheacknotification(false,notifycountCount);
            }
            notificationItem.getActionView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent searchIntent=new Intent(HomeActivity.this,notificationActivity.class);
                    startActivity(searchIntent);
                }
            });


        }
        return true;

        // return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==android.R.id.home)
        {
            if(showcart)
            {
                HomeActivity=null;
                showcart=false;
                finish();
                return true;
            }


        }else if(id==R.id.search)
        {
            Intent searchIntent=new Intent(this,searchActivity.class);
            startActivity(searchIntent);

            return true;
        }else if(id==R.id.notification)
        {
            Intent searchIntent=new Intent(this,notificationActivity.class);
            startActivity(searchIntent);

            return true;
        }


        else if(id==R.id.cart)
        {
            if(currentuser!=null) {
                gotoFragment("My Cart", new MyCartFragment(), CART_FRAGMENT);
            }else {

            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

   private void gotoFragment(String title,Fragment fragment,int fragmentnumber)
   {
       invalidateOptionsMenu();
       toolbar_txt.setVisibility(View.INVISIBLE);
      getSupportActionBar().setDisplayShowTitleEnabled(true);
       SpannableString s = new SpannableString(title);
       s.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.black)), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
       getSupportActionBar().setTitle(s);



       setFragment(fragment,fragmentnumber);
       if(fragmentnumber==CART_FRAGMENT)
       {
           navigationView.getMenu().getItem(4).setChecked(true);
       }else if(fragmentnumber==ORDER_FRAGMENT)
       {
           navigationView.getMenu().getItem(1).setChecked(true);
       }else if(fragmentnumber==WISHLIST_FRAGMENT)
       {
           navigationView.getMenu().getItem(3).setChecked(true);
       }else if(fragmentnumber==REWARD_FRAGMENT){

           navigationView.getMenu().getItem(2).setChecked(true);
       }
       else if(fragmentnumber==ACCOUNT_FRAGMENT){

           navigationView.getMenu().getItem(5).setChecked(true);
       }

   }
    @Override
    public void onBackPressed() {
       if(drawerLayout.isDrawerOpen(GravityCompat.START))
       {
           drawerLayout.closeDrawer(GravityCompat.START);
       }else
       {
           super.onBackPressed();
       }if(showcart)
        {
            HomeActivity=null;
            showcart=false;

            finish();

        }
    }

    private void setFragment(Fragment fragment,int fragmentno)
    {
        currentFragment=fragmentno;
          if(fragmentno==REWARD_FRAGMENT)
          {
              window.setBackgroundDrawableResource(R.drawable.reward_gradient_background);
          }
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.main_framelayout,fragment);
        fragmentTransaction.commit();
    }
    @Override
    protected void onStart() {
        super.onStart();
        toolbar_txt.setVisibility(View.VISIBLE);
        if(currentuser!=null) {

            navigationView.getMenu().getItem(navigationView.getMenu().size() - 1).setEnabled(true);
        }else
        {
            navigationView.getMenu().getItem(navigationView.getMenu().size() - 1).setEnabled(false);
        }
        invalidateOptionsMenu();


    }

    @Override
    protected void onPause() {
        super.onPause();
          DBquires.cheacknotification(true,null);
    }
}