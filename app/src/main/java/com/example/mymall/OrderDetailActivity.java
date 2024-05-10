package com.example.mymall;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class OrderDetailActivity extends AppCompatActivity {
    private TextView toolbar_txt,ordertitle,packedtitle,shifttitle,deliverytitle,orderbody,packedbody,shippedbody,deliverbody;
    private ImageView orderproductImage;
    private TextView orderproduct_title,orderproductPrice,FullName,FullAddress,pincode,userid,orderproductid,orderid,orderdate;
    private ImageView orderIndecator,packedIndecator,shippedIndecator,deliveryIndecator;
    private ProgressBar packedProgress,shippedProgress,deliveryprogress;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        toolbar_txt=findViewById(R.id.toolbar_txt);
        toolbar_txt.setVisibility(View.INVISIBLE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Order Details");
        orderproductImage=findViewById(R.id.producted_imaged);
        orderproduct_title=findViewById(R.id.producted_titled);
        orderproductPrice=findViewById(R.id.producted_priced);
        orderIndecator=findViewById(R.id.order_indicator);
        packedIndecator=findViewById(R.id.packed_indicator);
        shippedIndecator=findViewById(R.id.shift_indicator);
        deliveryIndecator=findViewById(R.id.delivery_indicator);
        ordertitle=findViewById(R.id.order_title);
        packedtitle=findViewById(R.id.packed_title);
        shifttitle=findViewById(R.id.shift_title);
        deliverytitle=findViewById(R.id.delivery_title);
        packedProgress=findViewById(R.id.order_packed_progress);
        shippedProgress=findViewById(R.id.packed_shift_progress);
        deliveryprogress=findViewById(R.id.shift_delivery_progress);
        FullName=findViewById(R.id.name);
        FullAddress=findViewById(R.id.adress);
        pincode=findViewById(R.id.pincode);
        userid=findViewById(R.id.userid);
        orderproductid=findViewById(R.id.orderproductid);
        orderid=findViewById(R.id.orderid);
        orderdate=findViewById(R.id.orderdate);

        orderbody=findViewById(R.id.order_body);
        packedbody=findViewById(R.id.packe_body);
        shippedbody=findViewById(R.id.shift_body);
        deliverbody=findViewById(R.id.delivery_body);




        String product_Image=getIntent().getStringExtra("productimage");
        String product_title=getIntent().getStringExtra("producttitle");
        String product_price=getIntent().getStringExtra("price");

        String ordered=getIntent().getStringExtra("Ordered");
        String packed=getIntent().getStringExtra("Packed");
        String shipped=getIntent().getStringExtra("Shipped");
        String delivery=getIntent().getStringExtra("Delivery");
        String Name=getIntent().getStringExtra("fullName");
        String Address=getIntent().getStringExtra("fullAddress");
        String PINCODE=getIntent().getStringExtra("Pincode");
        String USERID=getIntent().getStringExtra("userID");
        String PRODUCTID=getIntent().getStringExtra("productID");
        String ORDERID=getIntent().getStringExtra("orderID");
        Date ORDERDATE=(Date) getIntent().getSerializableExtra("orderDate");
        if(ORDERDATE != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String formattedDate = dateFormat.format(ORDERDATE);
            orderdate.setText(formattedDate);
        }
        userid.setText("User Id: "+USERID);
        orderproductid.setText("Product ID:"+PRODUCTID);
        orderid.setText("Order ID: "+ORDERID);

        Log.d("Order", "Ordered: " + ordered);
        Log.d("Order", "Packed: " + packed);
        Log.d("Order", "shipped: " + shipped);
        Log.d("Order", "Delivery: " + delivery);
        FullName.setText(Name);
        FullAddress.setText(Address);
        pincode.setText(PINCODE);


       if(ordered!=null && !ordered.isEmpty())
       {
           orderbody.setTextColor(ContextCompat.getColor(this,R.color.md_green_800));
           ordertitle.setTextColor(ContextCompat.getColor(this,R.color.md_green_800));
           orderIndecator.setColorFilter(ContextCompat.getColor(this,R.color.md_green_800));
       }

       if(packed!=null &&!packed.isEmpty())
       {
           orderbody.setTextColor(ContextCompat.getColor(this,R.color.md_green_800));
           packedbody.setTextColor(ContextCompat.getColor(this,R.color.md_green_800));
           ordertitle.setTextColor(ContextCompat.getColor(this,R.color.md_green_800));
           orderIndecator.setColorFilter(ContextCompat.getColor(this,R.color.md_green_800));
           packedtitle.setTextColor(ContextCompat.getColor(this,R.color.md_green_800));
           packedIndecator.setColorFilter(ContextCompat.getColor(this,R.color.md_green_800));
           packedProgress.setProgressTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_green_800)));
       }
       if(shipped!=null && !shipped.isEmpty())
       {
           orderbody.setTextColor(ContextCompat.getColor(this,R.color.md_green_800));
           packedbody.setTextColor(ContextCompat.getColor(this,R.color.md_green_800));
           shippedbody.setTextColor(ContextCompat.getColor(this,R.color.md_green_800));

           shifttitle.setTextColor(ContextCompat.getColor(this,R.color.md_green_800));
           if(shippedIndecator!=null) {
               shippedIndecator.setColorFilter(ContextCompat.getColor(this, R.color.md_green_800));
           }
           shippedProgress.setProgressTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_green_800)));
           ordertitle.setTextColor(ContextCompat.getColor(this,R.color.md_green_800));
           orderIndecator.setColorFilter(ContextCompat.getColor(this,R.color.md_green_800));
           packedtitle.setTextColor(ContextCompat.getColor(this,R.color.md_green_800));
           packedIndecator.setColorFilter(ContextCompat.getColor(this,R.color.md_green_800));
           packedProgress.setProgressTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_green_800)));
       }
       if(delivery!=null && !delivery.isEmpty())
       {
           orderbody.setTextColor(ContextCompat.getColor(this,R.color.md_green_800));
           packedbody.setTextColor(ContextCompat.getColor(this,R.color.md_green_800));
           shippedbody.setTextColor(ContextCompat.getColor(this,R.color.md_green_800));
           deliverbody.setTextColor(ContextCompat.getColor(this,R.color.md_green_800));

           deliverytitle.setTextColor(ContextCompat.getColor(this,R.color.md_green_800));
           if(deliveryIndecator!=null) {
               deliveryIndecator.setColorFilter(ContextCompat.getColor(this, R.color.md_green_800));
           }
           deliveryprogress.setProgressTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_green_800)));


           shifttitle.setTextColor(ContextCompat.getColor(this,R.color.md_green_800));
           if(shippedIndecator!=null) {
               shippedIndecator.setColorFilter(ContextCompat.getColor(this, R.color.md_green_800));
           }
           shippedProgress.setProgressTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_green_800)));
           ordertitle.setTextColor(ContextCompat.getColor(this,R.color.md_green_800));
           orderIndecator.setColorFilter(ContextCompat.getColor(this,R.color.md_green_800));
           packedtitle.setTextColor(ContextCompat.getColor(this,R.color.md_green_800));
           packedIndecator.setColorFilter(ContextCompat.getColor(this,R.color.md_green_800));
           packedProgress.setProgressTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_green_800)));

       }



        Glide.with(this).load(product_Image).apply(new RequestOptions().placeholder(R.drawable.strip)).into(orderproductImage);
        orderproduct_title.setText(product_title);
        orderproductPrice.setText(product_price);

        // Set the color of the status bar icons
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decor = getWindow().getDecorView();
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

    }

    @Override
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