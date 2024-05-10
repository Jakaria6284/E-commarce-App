package com.example.mymall;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.mymall.fragment.signinFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
    FrameLayout frameLayout ;
    FirebaseAuth firebaseAuth;
   FirebaseUser currentuser;
   public static boolean setSignupFragment=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);

        frameLayout= findViewById(R.id.registerframelayout);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decor = getWindow().getDecorView();
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }


          firebaseAuth=FirebaseAuth.getInstance();
          currentuser=firebaseAuth.getCurrentUser();
          if(currentuser!=null)
          {
              Intent intent=new Intent(RegisterActivity.this,SplashActivity.class);
              startActivity(intent);
              finish();
          }







       setDefaultFragment(new signinFragment());

        }



        private void setFragment(Fragment fragment) {
            FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(frameLayout.getId(),fragment);
            fragmentTransaction.commit();
        }
        private void setDefaultFragment(Fragment fragment) {
            FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(frameLayout.getId(),fragment);
            fragmentTransaction.commit();
        }





}
