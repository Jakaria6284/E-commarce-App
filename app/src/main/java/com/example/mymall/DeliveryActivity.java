package com.example.mymall;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymall.fragment.DBquires;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class DeliveryActivity extends AppCompatActivity {
  private RecyclerView deliver_recylerview;
  private Button changeoraddAddress;
  private Button buynowbtn;
   public static TextView total_cart_amount_below;
   public static  TextView fullname,fullAdress,pincode;
  public static final int SELECT_ADDRESS=0;
  private String name,mobileNo;
  public static boolean fromCart;
  FirebaseFirestore firebaseFirestore;
  public static boolean allproductAvailable;
  public static boolean getQtyids=true;
  static cartAdapter cartadapter;



    // Set the color of the status bar icons





    public static List<CartitemModel> cartitemModelList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        // Set the color of the status bar icons
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decor = getWindow().getDecorView();
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        allproductAvailable=true;
        getQtyids=true;


        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       // getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setTitle("Delivery");
        fullname=findViewById(R.id.name);
        fullAdress=findViewById(R.id.adress);
        pincode=findViewById(R.id.pincode);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.ic_baseline_arrow_back_24);
        upArrow.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);
        actionBar.setHomeAsUpIndicator(upArrow);
        firebaseFirestore=FirebaseFirestore.getInstance();


       buynowbtn=findViewById(R.id.buy_now_btn_delivery);
        deliver_recylerview=findViewById(R.id.delivery_recyclerview);
        total_cart_amount_below=findViewById(R.id.total_cart_amount_fragment);
        changeoraddAddress=findViewById(R.id.change_or_add_address);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        deliver_recylerview.setLayoutManager(layoutManager);
        changeoraddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getQtyids=false;

                Intent intent=new Intent(DeliveryActivity.this,MyAddressActivity.class);
                intent.putExtra("MODE",SELECT_ADDRESS);
                startActivity(intent);
            }
        });

        if (!DBquires.myAddressModelList.isEmpty() && DBquires.selectedIndex != -1) {

            name=DBquires.myAddressModelList.get(DBquires.selectedIndex).getFullname();
            mobileNo=DBquires.myAddressModelList.get(DBquires.selectedIndex).getMobileNumber();

            fullname.setText(name + " - " +mobileNo);
            fullAdress.setText(DBquires.myAddressModelList.get(DBquires.selectedIndex).getAddress());
            pincode.setText(DBquires.myAddressModelList.get(DBquires.selectedIndex).getPincode());

        } else {

        }



        changeoraddAddress.setVisibility(View.VISIBLE);

        buynowbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            getQtyids=false;
            Intent intent=new Intent(DeliveryActivity.this,paymentWayActivity.class);
            intent.putExtra("mobileNo",mobileNo);


            overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
            startActivity(intent);

            }
        });

        cartadapter=new cartAdapter(DeliveryActivity.cartitemModelList,total_cart_amount_below,false);
        deliver_recylerview.setAdapter(cartadapter);
        cartadapter.notifyDataSetChanged();
    }

   protected void onStart()
   {
       super.onStart();
       //accessing quantity
       if(getQtyids) {
           for (int x = 0; x < cartitemModelList.size() - 1; x++) {
               for(int y=0;y< cartitemModelList.get(x).getProductQuantity();y++) {
                   String qtydocumentName = UUID.randomUUID().toString().substring(0, 15);
                   Map<String, Object> timestamp = new HashMap<>();
                   timestamp.put("time", FieldValue.serverTimestamp());
                   int finalX = x;
                   int finalY = y;
                   firebaseFirestore.collection("PRODUCTS").document(cartitemModelList.get(x).getProduct_id())
                           .collection("QUANTITY")
                           .document(qtydocumentName).set(timestamp)
                           .addOnCompleteListener(new OnCompleteListener<Void>() {
                               @Override
                               public void onComplete(@NonNull Task<Void> task) {
                                   if (task.isSuccessful()) {
                                       cartitemModelList.get(finalX).getQtyids().add(qtydocumentName);
                                       if (finalY + 1 == cartitemModelList.get(finalX).getProductQuantity()) {
                                           firebaseFirestore.collection("PRODUCTS").document(cartitemModelList.get(finalX).getProduct_id())
                                                   .collection("QUANTITY").orderBy("time", Query.Direction.ASCENDING)
                                                   .limit(cartitemModelList.get(finalX).getStock_qty())
                                                   .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                       @Override
                                                       public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                           if (task.isSuccessful()) {
                                                               List<String> serverquantity = new ArrayList<>();
                                                               for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                                                                   serverquantity.add(queryDocumentSnapshot.getId());
                                                               }
                                                               for (String quantityID : cartitemModelList.get(finalX).getQtyids()) {
                                                                   long Availableqty = 0;
                                                                   boolean notavailable = true;
                                                                   cartitemModelList.get(finalX).setQty_error(false);
                                                                   if (!serverquantity.contains(quantityID)) {

                                                                       if (notavailable) {
                                                                           cartitemModelList.get(finalX).setIn_stock(false);
                                                                       } else {
                                                                           cartitemModelList.get(finalX).setQty_error(true);
                                                                           cartitemModelList.get(finalX).setMaxQty(Availableqty);

                                                                           Toast.makeText(DeliveryActivity.this, "Sorry, All product may not be available at your required", Toast.LENGTH_SHORT).show();
                                                                       }
                                                                       allproductAvailable = false;
                                                                   } else {
                                                                       Availableqty++;
                                                                       notavailable = false;
                                                                   }

                                                               }
                                                               cartadapter.notifyDataSetChanged();
                                                           } else {
                                                               String error = task.getException().getMessage();
                                                               Toast.makeText(DeliveryActivity.this, "" + error, Toast.LENGTH_SHORT).show();
                                                           }
                                                       }
                                                   });
                                       }

                                   } else {
                                       String error = task.getException().getMessage();
                                       Toast.makeText(DeliveryActivity.this, "" + error, Toast.LENGTH_SHORT).show();
                                   }
                               }
                           });
               }
           }
       }else
       {
           getQtyids=true;
       }
       //accessing quantity

       if (!DBquires.myAddressModelList.isEmpty() && DBquires.selectedIndex != -1) {
           fullname.setText(DBquires.myAddressModelList.get(DBquires.selectedIndex).getFullname());
           fullAdress.setText(DBquires.myAddressModelList.get(DBquires.selectedIndex).getAddress());
           pincode.setText(DBquires.myAddressModelList.get(DBquires.selectedIndex).getPincode());

       } else {

       }
   }

    protected void onPause() {
        super.onPause();
        if(getQtyids) {
            for (int x = 0; x < cartitemModelList.size() - 1; x++) {
                CartitemModel cartItem = cartitemModelList.get(x);
                List<String> qtyids = cartitemModelList.get(x).getQtyids();
                if (qtyids != null && !qtyids.isEmpty()) {
                    for (String qtyID : qtyids) {
                        int finalX = x;
                        firebaseFirestore.collection("PRODUCTS").document(cartitemModelList.get(x).getProduct_id()).collection("QUANTITY")
                                .document(qtyID)
                                .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                       if(!qtyids.isEmpty() && qtyID.equals(qtyids.get(qtyids.size()-1)))
                                       {
                                           qtyids.clear();

                                       }
                                    }
                                });
                    }
                    qtyids.clear();
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(getQtyids) {
            for (int x = 0; x < cartitemModelList.size() - 1; x++) {
                CartitemModel cartItem = cartitemModelList.get(x);
                List<String> qtyids = cartitemModelList.get(x).getQtyids();
                if (qtyids != null && !qtyids.isEmpty()) {
                    for (String qtyID : qtyids) {
                        int finalX = x;
                        firebaseFirestore.collection("PRODUCTS").document(cartitemModelList.get(x).getProduct_id()).collection("QUANTITY")
                                .document(qtyID)
                                .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        if(!qtyids.isEmpty() && qtyID.equals(qtyids.get(qtyids.size()-1)))
                                        {
                                            qtyids.clear();

                                        }
                                    }
                                });
                    }
                    qtyids.clear();
                }
            }
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();

         if(id==android.R.id.home)
        {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}