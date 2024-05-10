package com.example.mymall;

import static com.example.mymall.fragment.DBquires.firebaseFirestore;
import static com.example.mymall.fragment.DBquires.myAddressModelList;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mymall.fragment.DBquires;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;
import java.util.Map;

public class AddAddressActivity extends AppCompatActivity {
    private Button save;
    EditText city;
    EditText locality;
    EditText landmark;
    EditText pincode;
    EditText name ;
    EditText number;
    EditText alternativeNumber;
    private Spinner stateSpinner;


    private String selectedstate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);

        save=findViewById(R.id.save);
        city=findViewById(R.id.city);
        landmark=findViewById(R.id.landmark);
        locality=findViewById(R.id.locality);
        pincode=findViewById(R.id.pincode);
        name=findViewById(R.id.name);
        stateSpinner=findViewById(R.id.state_spiner);
        number=findViewById(R.id.mobile);
        alternativeNumber=findViewById(R.id.alternativephonenumber);
        String[] satelist =getResources().getStringArray(R.array.india_states);

        // Set the color of the status bar icons
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decor = getWindow().getDecorView();
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }



        ArrayAdapter spninnerAdapter=new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,getResources().getStringArray(R.array.india_states));
        spninnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stateSpinner.setAdapter(spninnerAdapter);

        stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                selectedstate=satelist[position];

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {


            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               cheackINput();



            }
        });











    }

    public void cheackINput()
    {
        if(!TextUtils.isEmpty(city.getText()))
        {
            if(!TextUtils.isEmpty(locality.getText()))
            {

                if(!TextUtils.isEmpty(landmark.getText()))
                {
                    if(!TextUtils.isEmpty(pincode.getText()))
                    {
                        if(!TextUtils.isEmpty(name.getText()))
                        {
                            if(!TextUtils.isEmpty(number.getText()))
                            {
                                if(!TextUtils.isEmpty(alternativeNumber.getText()))
                                {
                                    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
                                    FirebaseUser currentuser=firebaseAuth.getCurrentUser();
                                    String addressfull=locality.getText().toString()+"  "+landmark.getText().toString()+"  "+city.getText().toString()+"  "+selectedstate;

                                    Map<String,Object> addAdress=new HashMap();
                                    addAdress.put("list_size", Integer.parseInt(String.valueOf((long)myAddressModelList.size()+1)));
                                    addAdress.put("Mobile_"+ String.valueOf((long)myAddressModelList.size()+1),number.getText().toString());
                                    addAdress.put("Alternative_Mobile_"+ String.valueOf((long)myAddressModelList.size()+1),alternativeNumber.getText().toString());
                                    addAdress.put("fullname_"+ String.valueOf((long)myAddressModelList.size()+1),name.getText().toString());
                                    addAdress.put("address_"+ String.valueOf((long)myAddressModelList.size()+1),addressfull);
                                    addAdress.put("pincode_"+ String.valueOf((long)myAddressModelList.size()+1),pincode.getText().toString());
                                    addAdress.put("selected_"+ String.valueOf((long)myAddressModelList.size()+1),true);
                                    //addAdress.put("selected_"+ DBquires.selectedIndex+1,false);




                                    firebaseFirestore.collection("USERS").document(currentuser.getUid()).collection("USER_DATA").document("MY_ADRESS")
                                            .update(addAdress).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful())
                                                    {
                                                        if(myAddressModelList.size()>0) {
                                                            myAddressModelList.get(DBquires.selectedIndex).setSelected(false);
                                                        }

                                                        myAddressModelList.add(new myAddressModel(name.getText().toString(),number.getText().toString(),alternativeNumber.getText().toString(),addressfull,pincode.getText().toString(),true));


                                                        String intentExtra = getIntent().getStringExtra("INTENT");
                                                        if (intentExtra != null && intentExtra.equals("intent")) {
                                                            Intent intent = new Intent(AddAddressActivity.this, DeliveryActivity.class);
                                                            startActivity(intent);
                                                        }

                                                        else
                                                        {
                                                            MyAddressActivity.refresh(DBquires.selectedIndex, myAddressModelList.size()-1);
                                                        }
                                                        if (!DBquires.myAddressModelList.isEmpty()) {
                                                            DBquires.selectedIndex = DBquires.myAddressModelList.size() - 1;
                                                        }
                                                        finish();



                                                        Toast.makeText(AddAddressActivity.this, "Address recorded successfully", Toast.LENGTH_SHORT).show();


                                                    }else
                                                    {
                                                        String error=task.getException().getMessage();
                                                        Toast.makeText(AddAddressActivity.this, ""+error, Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                }else
                                {
                                    alternativeNumber.requestFocus();
                                }
                            }else
                            {
                                number.requestFocus();
                            }
                        }else
                        {
                            name.requestFocus();
                        }
                    }else
                    {
                        pincode.requestFocus();
                    }
                }else
                {
                    landmark.requestFocus();
                }
            }else
            {
                locality.requestFocus();
            }
        }else
        {
            city.requestFocus();
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