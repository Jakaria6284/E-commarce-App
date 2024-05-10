package com.example.mymall;

import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CashOnActivity extends AppCompatActivity {
    private String MOBILENUMBER;

    private TextView phoneNumberTEXT,Timer;
    private EditText OTPVERFICATION;
    private Button VERIFYbtn;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();
    String otpid;
    String mobilenumber;
    private static final String TAG = "CashonActivity";

    private static final String API_KEY = "0veHXk0QvJfEJtH30quvBx8sX798dggUDhmy62I5";
    private static final String API_ENDPOINT = "https://api.sms.net.bd/sendsms";
    private  String PHONE_NUMBER ;
    private static final String MESSAGE = "Test";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_on);
        // Set the color of the status bar icons
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decor = getWindow().getDecorView();
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }


        //MOBILENUMBER = getIntent().getStringExtra("mobilenumber");
       PHONE_NUMBER=MOBILENUMBER;
        Timer = findViewById(R.id.timer);
        phoneNumberTEXT = findViewById(R.id.phonea_no);
        OTPVERFICATION = findViewById(R.id.otp);
        VERIFYbtn = findViewById(R.id.verify);
        phoneNumberTEXT.setText("verification code has been send to +88" + mobilenumber);










        new CountDownTimer(120000, 1000) {
            public void onTick(long millisUntilFinished) {

                long secondsRemaining = millisUntilFinished / 1000;
                String timeRemainingString = Long.toString(secondsRemaining);
                Timer.setText(timeRemainingString);


            }

            public void onFinish() {
                Timer.setText("Time Out");
            }
        }.start();

    }












}