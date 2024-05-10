package com.example.mymall;

import static com.example.mymall.DeliveryActivity.cartitemModelList;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class  CashOnConfarmActivity extends AppCompatActivity {
    TextView Congratulationtxt,deliveryDate,Orderid,smsAndemail;
    Button continueToshopping;
    ImageView congratulationImg;
    public static boolean fromcason=true;
    public static String orderID;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    FirebaseUser currentuser;
    //String productID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_on_confarm);
        Congratulationtxt=findViewById(R.id.congrateText);
        deliveryDate=findViewById(R.id.deliverdate);
        Orderid=findViewById(R.id.orderid);
        congratulationImg=findViewById(R.id.congrateImage);
        continueToshopping=findViewById(R.id.continouetoshoping);
        smsAndemail=findViewById(R.id.smsandemail);
        orderID= UUID.randomUUID().toString().substring(0,28);

        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        currentuser=firebaseAuth.getCurrentUser();



        // Set the color of the status bar icons
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decor = getWindow().getDecorView();
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }




        Orderid.setText("OrderID---> "+orderID);

        continueToshopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CashOnConfarmActivity.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentuser = firebaseAuth.getCurrentUser();
        for (CartitemModel cartitemModel : cartitemModelList)
            if (cartitemModel.getType() == CartitemModel.CART_ITEM ) {
                Map<String, Object> orderdetails = new HashMap<>();

                orderdetails.put("ORDER_ID",orderID);

                orderdetails.put("product_id", cartitemModel.getProduct_id());
                orderdetails.put("user_id", currentuser.getUid());
                orderdetails.put("product_quantity", cartitemModel.getProductQuantity());
                if (cartitemModel.getCuttedPrice() != null) {
                    orderdetails.put("cutted_price", cartitemModel.getCuttedPrice());
                }
                orderdetails.put("product_price", cartitemModel.getProductPrice());
                if (cartitemModel.getSelectedCoupenID() != null) {
                    orderdetails.put("coupen_id", cartitemModel.getSelectedCoupenID());
                }
                if (cartitemModel.getDiscountedpricerewardmodel() != null) {
                    orderdetails.put("discounted_price", cartitemModel.getDiscountedpricerewardmodel());
                }
                orderdetails.put("Date", FieldValue.serverTimestamp());
                orderdetails.put("payment_method", "Cash_On-Delivery");
                orderdetails.put("Address", DeliveryActivity.fullAdress.getText());
                orderdetails.put("Full_Name", DeliveryActivity.fullname.getText());
                orderdetails.put("pincode", DeliveryActivity.pincode.getText());
                orderdetails.put("order_status", "ordered");
                orderdetails.put("product_name",cartitemModel.getProductTitle());


                firebaseFirestore.collection("ORDERS").document(orderID)
                        .collection("Order_items").document(cartitemModel.getProduct_id())
                        .set(orderdetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (!task.isSuccessful()) {

                                }
                            }
                        });

            }else
            {

                Map<String, Object> orderdetails = new HashMap<>();
                orderdetails.put("Order Date", FieldValue.serverTimestamp());
                orderdetails.put("ORDER_ID",orderID);
                orderdetails.put("Total_item", cartitemModel.getTotal_item());
                orderdetails.put("Total item price", cartitemModel.getTotal_item_price());
                orderdetails.put("Delivery price",cartitemModel.getDelivery_price());
                orderdetails.put("Total Amount", cartitemModel.getTotal_amount());
                orderdetails.put("Saved Amount", cartitemModel.getSaved_amount());
                orderdetails.put("payment_method", "Cash_on_Delivery");
                orderdetails.put("order_status", "ordered");
                //orderdetails.put("productid",pro)

                firebaseFirestore.collection("ORDERS").document(orderID)
                        .set(orderdetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    String Ordered="ordered";
                                    String Packed="";
                                    String Shift="";
                                    String Delivery="";



                                    Map<String,Object> userorder=new HashMap<>();
                                    userorder.put("ORDER_ID",orderID);
                                    userorder.put("Ordered",Ordered);
                                    userorder.put("Packed",Packed);
                                    userorder.put("Shift",Shift);
                                    userorder.put("Delivery",Delivery);



                                    firebaseFirestore.collection("USERS").document(currentuser.getUid())
                                            .collection("USER_ORDER").document(orderID)
                                            .set(userorder).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful())
                                                    {

                                                    }else
                                                    {
                                                        Toast.makeText(CashOnConfarmActivity.this, "Failed to user order add in user document", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                } else {
                                    String error = task.getException().getMessage();
                                    //Toast.makeText(paymentWayActivity.this, "" + error, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        for(int x=0;x<cartitemModelList.size()-1;x++)
        {
            for(String qtyID: cartitemModelList.get(x).getQtyids())
            {
                DeliveryActivity.getQtyids=false;
                firebaseFirestore.collection("PRODUCTS").document(cartitemModelList.get(x).getProduct_id()).collection("QUANTITY")
                        .document(qtyID)
                        .update("user_id",currentuser.getUid());

            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        DeliveryActivity.getQtyids=false;

    }

    @Override
    public void onBackPressed() {

        FragmentManager fm =CashOnConfarmActivity.this.getSupportFragmentManager();
        fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        Intent intent = new Intent(this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // this flag clears the activity stack, so that the user cannot go back to the previous activities
        startActivity(intent);
    }

}