package com.example.mymall;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymall.fragment.DBquires;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class notificationActivity extends AppCompatActivity {
    private RecyclerView notificationrecycler;
    public static notificationAdapter adapter;
    private boolean runQuery=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Notification");
        notificationrecycler=findViewById(R.id.notificationrecyclerview);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        notificationrecycler.setLayoutManager(layoutManager);
        adapter=new notificationAdapter(DBquires.notificationModelList);
        notificationrecycler.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        Map<String,Object> readedMap=new HashMap<>();
        for(int x=0;x<DBquires.notificationModelList.size();x++)
        {
            if(!DBquires.notificationModelList.get(x).isReaded())
            {
                runQuery=true;
            }
            readedMap.put("readed_"+x,true);
        }
        if(runQuery) {
            FirebaseFirestore.getInstance().collection("USERS").document(currentUser.getUid()).collection("USER_DATA").document("MY_NOTIFICATION")
                    .update(readedMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {

                            } else {
                                String error = task.getException().getMessage();
                                Toast.makeText(notificationActivity.this, "" + error, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for(int x=0;x<DBquires.notificationModelList.size();x++)
        {
           DBquires.notificationModelList.get(x).setReaded(true);


        }

    }
}
