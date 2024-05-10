package com.example.mymall;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class paymentWayActivity extends AppCompatActivity {
    private ImageView cashOnDeliverMethod;
    private ImageView payWithSSLMethod;
    String mobilNumber;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_way);

        // Set the color of the status bar icons
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decor = getWindow().getDecorView();
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        cashOnDeliverMethod=findViewById(R.id.cashOnDelivery);
        payWithSSLMethod=findViewById(R.id.paywithSSLComerez);

        String mobilNumber=getIntent().getStringExtra("mobileNo");


        payWithSSLMethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // placeOrderDetails("PAY_ONLINE");
                Intent intent=new Intent(paymentWayActivity.this,paymentActivity.class);
                startActivity(intent);
            }
        });
        cashOnDeliverMethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(paymentWayActivity.this,CashOnConfarmActivity.class);
                startActivity(intent);
            }
        });
    }
    public static void placeOrderDetails(String value) {

    }

}