package com.example.mymall;

import static com.example.mymall.DeliveryActivity.SELECT_ADDRESS;
import static com.example.mymall.fragment.DBquires.myAddressModelList;
import static com.example.mymall.fragment.DBquires.selectedIndex;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.example.mymall.fragment.DBquires;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MyAddressActivity extends AppCompatActivity {
    private RecyclerView myAddressRecyclerview;
    private static myadressAdapter adapter;
    private Button deliveryhere;
    private TextView addnewAdressBtn;
    private TextView save_adress;
    private int previousAdress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_address);

        // Set the color of the status bar icons
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decor = getWindow().getDecorView();
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("My Address");
        addnewAdressBtn=findViewById(R.id.add_new_address_btn);
        save_adress=findViewById(R.id.address_saved);
        previousAdress= DBquires.selectedIndex;
        myAddressRecyclerview=findViewById(R.id.myaddress_recyclerview);
        deliveryhere=findViewById(R.id.delivery_here_btn);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myAddressRecyclerview.setLayoutManager(layoutManager);
       // save_adress.setText(String.valueOf(myAddressModelList.size()));

         int mode=getIntent().getIntExtra("MODE",-1);
         if(mode==SELECT_ADDRESS)
         {
             deliveryhere.setVisibility(View.VISIBLE);
         }else
         {
             deliveryhere.setVisibility(View.GONE);
         }
         deliveryhere.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 if(DBquires.selectedIndex!=previousAdress)


                 {
                     int previousAddressIndex=previousAdress;

                     Map<String,Object> updateSelection=new HashMap<>();
                     updateSelection.put("selected_"+String.valueOf(previousAdress+1),false);
                     updateSelection.put("selected_"+String.valueOf(DBquires.selectedIndex+1),true);

                     previousAdress=DBquires.selectedIndex;
                     FirebaseFirestore.getInstance().collection("USERS").document(FirebaseAuth.getInstance().getUid())
                             .collection("USER_DATA").document("MY_ADRESS")
                             .update(updateSelection).addOnCompleteListener(new OnCompleteListener<Void>() {
                                 @Override
                                 public void onComplete(@NonNull Task<Void> task) {
                                     if(task.isSuccessful())
                                     {
                                         finish();
                                     }else
                                     {
                                         previousAdress=previousAddressIndex;
                                         String error=task.getException().getMessage();
                                         Toast.makeText(MyAddressActivity.this, ""+error, Toast.LENGTH_SHORT).show();
                                     }
                                 }
                             });
                 }else
                 {
                     finish();
                 }
             }
         });
         addnewAdressBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent addAddressIntent=new Intent(MyAddressActivity.this,AddAddressActivity.class);
                 addAddressIntent.putExtra("INTENT","null");
                 startActivity(addAddressIntent);

             }
         });



         adapter=new myadressAdapter(myAddressModelList,mode);
        ((SimpleItemAnimator)myAddressRecyclerview.getItemAnimator()).setSupportsChangeAnimations(false);
        myAddressRecyclerview.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }
    protected void onStart()
    {
        super.onStart();
        save_adress.setText(String.valueOf(myAddressModelList.size()));
    }
    public static  void refresh(int deselect,int preselect)
    {
        if(adapter!=null) {
            adapter.notifyItemChanged(deselect);
            adapter.notifyItemChanged(preselect);
        }
    }

    @Override
    public void onBackPressed() {
        if(DBquires.selectedIndex!=previousAdress)
        {
            myAddressModelList.get(selectedIndex).setSelected(false);
            myAddressModelList.get(previousAdress).setSelected(true);
            selectedIndex=previousAdress;
        }

        super.onBackPressed();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();

        if(id==android.R.id.home)
        {
            if(DBquires.selectedIndex!=previousAdress)
            {
                myAddressModelList.get(selectedIndex).setSelected(false);
                myAddressModelList.get(previousAdress).setSelected(true);
                selectedIndex=previousAdress;
            }
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}