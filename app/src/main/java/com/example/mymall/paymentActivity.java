package com.example.mymall;

import static com.example.mymall.DeliveryActivity.cartitemModelList;
import static com.example.mymall.DeliveryActivity.getQtyids;
import static com.example.mymall.DeliveryActivity.total_cart_amount_below;

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

import com.example.mymall.fragment.DBquires;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.sslwireless.sslcommerzlibrary.model.initializer.SSLCommerzInitialization;
import com.sslwireless.sslcommerzlibrary.model.response.SSLCTransactionInfoModel;
import com.sslwireless.sslcommerzlibrary.model.util.SSLCCurrencyType;
import com.sslwireless.sslcommerzlibrary.model.util.SSLCSdkType;
import com.sslwireless.sslcommerzlibrary.view.singleton.IntegrateSSLCommerz;
import com.sslwireless.sslcommerzlibrary.viewmodel.listener.SSLCTransactionResponseListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class paymentActivity extends AppCompatActivity implements SSLCTransactionResponseListener {
TextView Congratulationtxt,deliveryDate,Orderid,smsAndemail;
Button continueToshopping;
ImageView congratulationImg;
public static boolean fromcart;
FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    FirebaseUser currentuser;
public static String orderID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        Congratulationtxt=findViewById(R.id.congrateText);
        deliveryDate=findViewById(R.id.deliverdate);
        Orderid=findViewById(R.id.orderid);
        congratulationImg=findViewById(R.id.congrateImage);
        continueToshopping=findViewById(R.id.continouetoshoping);
        smsAndemail=findViewById(R.id.smsandemail);
       orderID= UUID.randomUUID().toString().substring(0,28);
        double total=Double.parseDouble(total_cart_amount_below.getText().toString().substring(3));
        // Set the color of the status bar icons
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decor = getWindow().getDecorView();
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

       // paymentWayActivity.placeOrderDetails("PAY_ONLINE");

        congratulationImg.setVisibility(View.INVISIBLE);
        Congratulationtxt.setVisibility(View.INVISIBLE);
        continueToshopping.setVisibility(View.INVISIBLE);
        deliveryDate.setVisibility(View.INVISIBLE);
        Orderid.setVisibility(View.INVISIBLE);
        smsAndemail.setVisibility(View.INVISIBLE);

        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        currentuser=firebaseAuth.getCurrentUser();

        final SSLCommerzInitialization sslCommerzInitialization = new SSLCommerzInitialization ("fiham6433d9fe6aa46","fiham6433d9fe6aa46@ssl",total, SSLCCurrencyType.BDT,orderID, "yourProductType", SSLCSdkType.TESTBOX);
        IntegrateSSLCommerz
                .getInstance(paymentActivity.this)
                .addSSLCommerzInitialization(sslCommerzInitialization)
                .buildApiCall(this);


        continueToshopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(paymentActivity.this,HomeActivity.class);
                startActivity(intent);

                finish();
            }
        });




    }

    @Override
    public void transactionSuccess(SSLCTransactionInfoModel sslcTransactionInfoModel) {


        if(HomeActivity.HomeActivity!=null)
        {
            HomeActivity.HomeActivity.finish();
            HomeActivity.HomeActivity=null;
            HomeActivity.showcart=false;
        }

        if(ProductDetailsActivity.productDetailsActivity!=null)
        {
          ProductDetailsActivity.productDetailsActivity.finish();
          ProductDetailsActivity.productDetailsActivity=null;
        }

        for(int x=0;x<cartitemModelList.size()-1;x++)
        {
            for(String qtyID: cartitemModelList.get(x).getQtyids())
            {
                getQtyids=false;
                firebaseFirestore.collection("PRODUCTS").document(cartitemModelList.get(x).getProduct_id()).collection("QUANTITY")
                        .document(qtyID)
                        .update("user_id",currentuser.getUid());

            }
        }

        if(fromcart)
        {
            long cartlistSize=0;
            List<Integer> indexlist=new ArrayList<>();

            Map<String,Object> updatecartlist=new HashMap<>();
            for(int x=0;x< DBquires.cartlist.size();x++)
            {
               if(!cartitemModelList.get(x).isIn_stock())
               {
                   updatecartlist.put("product_id_"+cartlistSize,cartitemModelList.get(x).getProduct_id());
                   cartlistSize++;
               }else
               {
                   indexlist.add(x);
               }

            }
            updatecartlist.put("list_size",cartlistSize);

            firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA")
                    .document("MY_CARTLIST")
                    .set(updatecartlist).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                for(int x=0;x<indexlist.size();x++)
                                {
                                    DBquires.cartlist.remove(indexlist.get(x).intValue());
                                    DBquires.cartitemModelList.remove(indexlist.get(x).intValue());
                                    DBquires.cartitemModelList.remove(DBquires.cartitemModelList.size()-1);
                                }
                            }else
                            {
                                String error=task.getException().getMessage();
                                Toast.makeText(paymentActivity.this, ""+error, Toast.LENGTH_SHORT).show();

                            }
                        }
                    });

        }

        congratulationImg.setVisibility(View.VISIBLE);
        Congratulationtxt.setVisibility(View.VISIBLE);
        continueToshopping.setVisibility(View.VISIBLE);
       deliveryDate.setVisibility(View.VISIBLE);
        Orderid.setVisibility(View.VISIBLE);
        smsAndemail.setVisibility(View.VISIBLE);

        Orderid.setText("OrderID-->\n"+sslcTransactionInfoModel.getTranId());

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentuser = firebaseAuth.getCurrentUser();
        for (CartitemModel cartitemModel : cartitemModelList)
            if (cartitemModel.getType() == CartitemModel.CART_ITEM ) {
                Map<String, Object> orderdetails = new HashMap<>();

                    orderdetails.put("ORDER_ID",orderID);
                    orderdetails.put("product_img",cartitemModel.getProductImage());
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
                orderdetails.put("Order Date", FieldValue.serverTimestamp());
                orderdetails.put("Packed Date", FieldValue.serverTimestamp());
                orderdetails.put("shipped Date", FieldValue.serverTimestamp());
                orderdetails.put("delevery Date", FieldValue.serverTimestamp());
                orderdetails.put("Cancelled date", FieldValue.serverTimestamp());

                orderdetails.put("payment_method", "PAY_ONLINE");
                orderdetails.put("Address", DeliveryActivity.fullAdress.getText());
                orderdetails.put("Full_Name", DeliveryActivity.fullname.getText());
                orderdetails.put("pincode", DeliveryActivity.pincode.getText());
                orderdetails.put("payment_status", "paid");
                orderdetails.put("order_status", "ordered");
                orderdetails.put("product_name",cartitemModel.getProductTitle());
                orderdetails.put("free_coupen", cartitemModel.getFreeCoupens());


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
                orderdetails.put("Order Date", FieldValue.serverTimestamp());
                orderdetails.put("Total_item", cartitemModel.getTotal_item());
                orderdetails.put("Total item price", cartitemModel.getTotal_item_price());
                 orderdetails.put("Delivery price",cartitemModel.getDelivery_price());
                orderdetails.put("Total Amount", cartitemModel.getTotal_amount());
                orderdetails.put("Saved Amount", cartitemModel.getSaved_amount());
                orderdetails.put("payment_method", "PAY_ONLINE");
                orderdetails.put("payment_status", "paid");
                orderdetails.put("order_status", "ordered");

                firebaseFirestore.collection("ORDERS").document(orderID)
                        .set(orderdetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Map<String,Object> userorder=new HashMap<>();
                                    userorder.put("order_id",orderID);
                                   firebaseFirestore.collection("USERS").document(currentuser.getUid())
                                           .collection("USER_ORDER").document(orderID)
                                           .set(userorder).addOnCompleteListener(new OnCompleteListener<Void>() {
                                               @Override
                                               public void onComplete(@NonNull Task<Void> task) {
                                                   if(task.isSuccessful())
                                                   {

                                                   }else
                                                   {
                                                       Toast.makeText(paymentActivity.this, "Failed to user order add in user document", Toast.LENGTH_SHORT).show();
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




    }

    @Override
    public void transactionFail(String s) {

        for (int x = 0; x < cartitemModelList.size() - 1; x++) {
            List<String> qtyids = cartitemModelList.get(x).getQtyids();
            if (qtyids != null) {
                for (String qtyID : qtyids) {
                    int finalX = x;
                    firebaseFirestore.collection("PRODUCTS").document(cartitemModelList.get(x).getProduct_id()).collection("QUANTITY")
                            .document(qtyID)
                            .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    if(qtyID.equals(qtyids.get(qtyids.size()-1)))
                                    {
                                        qtyids.clear();

                                        firebaseFirestore.collection("PRODUCTS").document(cartitemModelList.get(finalX).getProduct_id())
                                                .collection("QUANTITY").orderBy("time", Query.Direction.ASCENDING)
                                                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                        if(task.isSuccessful())
                                                        {
                                                            if(task.getResult().getDocuments().size()<cartitemModelList.get(finalX).getStock_qty())
                                                            {
                                                                firebaseFirestore.collection("PRODUCTS").document(cartitemModelList.get(finalX).getProduct_id())
                                                                        .update("in_stock",true);
                                                            }
                                                        }else
                                                        {
                                                            String error=task.getException().getMessage();
                                                            Toast.makeText(paymentActivity.this, ""+error, Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });



                                    }
                                }
                            });
                }
                qtyids.clear();
            }
        }


    }

    @Override
    public void closed(String s) {

        for (int x = 0; x < cartitemModelList.size() - 1; x++) {
            List<String> qtyids = cartitemModelList.get(x).getQtyids();
            if (qtyids != null) {
                for (String qtyID : qtyids) {
                    int finalX = x;
                    firebaseFirestore.collection("PRODUCTS").document(cartitemModelList.get(x).getProduct_id()).collection("QUANTITY")
                            .document(qtyID)
                            .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    if(qtyID.equals(qtyids.get(qtyids.size()-1)))
                                    {
                                        qtyids.clear();

                                        firebaseFirestore.collection("PRODUCTS").document(cartitemModelList.get(finalX).getProduct_id())
                                                .collection("QUANTITY").orderBy("time", Query.Direction.ASCENDING)
                                                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                        if(task.isSuccessful())
                                                        {
                                                            if(task.getResult().getDocuments().size()<cartitemModelList.get(finalX).getStock_qty())
                                                            {
                                                                firebaseFirestore.collection("PRODUCTS").document(cartitemModelList.get(finalX).getProduct_id())
                                                                        .update("in_stock",true);
                                                            }
                                                        }else
                                                        {
                                                            String error=task.getException().getMessage();
                                                            Toast.makeText(paymentActivity.this, ""+error, Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });



                                    }
                                }
                            });
                }
                qtyids.clear();
            }
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);

    }
}


