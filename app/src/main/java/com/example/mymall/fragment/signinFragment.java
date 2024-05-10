package com.example.mymall.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.mymall.HomeActivity;
import com.example.mymall.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class signinFragment extends Fragment {


    private EditText singinemail;
    private EditText singinpassword;
    private Button signinbtn,forgot;
    private ProgressBar signinprogress;

    private FirebaseAuth firebaseAuth;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";



    private TextView dontHaveAnAccount;
    private FrameLayout parentFrameLayout;





    public signinFragment() {
        // Required empty public constructor
    }





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=   inflater.inflate(R.layout.fragment_signin, container, false);
        dontHaveAnAccount=view.findViewById(R.id.dont_have_an_account);
        parentFrameLayout=getActivity().findViewById(R.id.registerframelayout);
        singinemail=view.findViewById(R.id.signupemail);
        singinpassword=view.findViewById(R.id.signupconpassword);
        signinbtn=view.findViewById(R.id.signupbtn);
        signinprogress=view.findViewById(R.id.signinloading);
        forgot=view.findViewById(R.id.forgot);

        firebaseAuth=FirebaseAuth.getInstance();





        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               setFragment(new resetpasswordFragment());
            }
        });

        dontHaveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new signupFragment());

            }
        });


        singinemail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                cheackInput();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        singinpassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                cheackInput();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        signinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // signinprogress.setVisibility(View.VISIBLE);
                cheackEmailAndPasswrd();
            }
        });

    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(parentFrameLayout.getId(),fragment);
        fragmentTransaction.commit();
    }
    private void cheackInput()
    {
        if(!TextUtils.isEmpty(singinemail.getText()))
        {
            if(!TextUtils.isEmpty(singinpassword.getText()))
            {
                signinbtn.setEnabled(true);
                signinbtn.setBackgroundColor(getContext().getResources().getColor(R.color.md_green_800));
            }else
            {
                signinprogress.setVisibility(View.GONE);
                signinbtn.setEnabled(false);
                signinbtn.setBackgroundColor(getContext().getResources().getColor(R.color.md_black_1000_20));

            }
        }else
        {
            signinbtn.setEnabled(false);
            signinbtn.setBackgroundColor(getContext().getResources().getColor(R.color.md_black_1000_20));
        }
    }
    private void cheackEmailAndPasswrd()
    {
        if(singinemail.getText().toString().matches(emailPattern))
        {
            if(singinpassword.length()>=4)
            {

                signinbtn.setEnabled(false);
                signinbtn.setBackgroundColor(getContext().getResources().getColor(R.color.md_black_1000_15));
                signinprogress.setVisibility(View.VISIBLE);
                firebaseAuth.signInWithEmailAndPassword(singinemail.getText().toString().trim(),singinpassword.getText().toString().trim())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful())
                                {

                                    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                                    if (currentUser != null && currentUser.isEmailVerified()) {
                                        mainIntent();
                                        return;
                                    }
                                    signinprogress.setVisibility(View.GONE);
                                }else
                                {

                                    signinbtn.setEnabled(true);
                                    signinbtn.setBackgroundColor(getContext().getResources().getColor(R.color.md_green_800));

                                    String error=task.getException().getMessage();
                                    Toast.makeText(getActivity(),error, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }else
            {
                Toast.makeText(getActivity(), "Incorrect email or password", Toast.LENGTH_SHORT).show();
            }
        }else
        {
            Toast.makeText(getActivity(), "Incorrect email or password", Toast.LENGTH_SHORT).show();
        }
    }
    private void mainIntent()
    {
        if(FirebaseAuth.getInstance().getCurrentUser().isEmailVerified()) {

            Intent in = new Intent(getActivity(), HomeActivity.class);
            startActivity(in);
            getActivity().finish();
        }else
        {
            Toast.makeText(getContext(), "Email not verified", Toast.LENGTH_SHORT).show();
        }




    }
    private void  setUpOnBackpress()
    {
        requireActivity().getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if(isEnabled())
                {
                    // Toast.makeText(requireContext(), "Go Back", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(getActivity(), HomeActivity.class);
                    startActivity(intent);
                    setEnabled(false);
                    requireActivity().onBackPressed();
                }
            }
        });
    }
    }
