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
import android.widget.ImageButton;
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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class signupFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public void SignUpFragment() {

    }

    private TextView AlReadyHaveAnAccount;
    private FrameLayout parentFrameLayout;
    private EditText email;
    private EditText fullname;
    private EditText password;
    private EditText confarmpassword;
    private Button button2;
    private ImageButton closebtn;
    public static FirebaseAuth firebaseAuth;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";
    private FirebaseFirestore firebaseFirestore;


    public static signupFragment newInstance(String param1, String param2) {
        signupFragment fragment = new signupFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        AlReadyHaveAnAccount = view.findViewById(R.id.dont_have_an_account);
        email = view.findViewById(R.id.signupemail);
        setUpOnBackpress();

        fullname = view.findViewById(R.id.signupname);
        password = view.findViewById(R.id.signuppasswordd);
        confarmpassword = view.findViewById(R.id.signupconpassword);


        button2 = view.findViewById(R.id.signupbtn);
        parentFrameLayout = getActivity().findViewById(R.id.registerframelayout);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AlReadyHaveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new signinFragment());

            }
        });

        email.addTextChangedListener(new TextWatcher() {
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
        fullname.addTextChangedListener(new TextWatcher() {
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
        password.addTextChangedListener(new TextWatcher() {
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
        confarmpassword.addTextChangedListener(new TextWatcher() {
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


        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cheackemailAndpassword();
            }
        });
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(parentFrameLayout.getId(), fragment);
        fragmentTransaction.commit();
    }

    private void cheackInput() {
        if (!TextUtils.isEmpty(email.getText())) {
            if (!TextUtils.isEmpty(fullname.getText())) {
                if (!TextUtils.isEmpty(password.getText())) {
                    if (!TextUtils.isEmpty(confarmpassword.getText())) {
                        button2.setEnabled(true);
                        button2.setBackgroundColor(getContext().getResources().getColor(R.color.md_green_800));

                    } else {
                        button2.setEnabled(false);
                        button2.setBackgroundColor(getContext().getResources().getColor(R.color.md_black_1000_20));
                    }

                } else {
                    button2.setEnabled(false);
                    button2.setBackgroundColor(getContext().getResources().getColor(R.color.md_black_1000_20));
                }
            } else {
                button2.setEnabled(false);
               button2.setBackgroundColor(getContext().getResources().getColor(R.color.md_black_1000_20));
            }
        } else {
            button2.setEnabled(false);
            button2.setBackgroundColor(getContext().getResources().getColor(R.color.md_black_1000_20));
        }
    }

    private void cheackemailAndpassword() {
        if (email.getText().toString().trim().matches(emailPattern)) {
            if (password.getText().toString().trim().equals(confarmpassword.getText().toString())) {

                button2.setEnabled(false);
                button2.setBackgroundColor(getContext().getResources().getColor(R.color.md_black_1000_20));
                firebaseAuth.createUserWithEmailAndPassword(email.getText().toString().trim(), password.getText().toString().trim())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = firebaseAuth.getCurrentUser();
                                    user.sendEmailVerification();
                                    Map<Object, String> userdata = new HashMap<>();
                                    userdata.put("fullname", fullname.getText().toString());
                                    userdata.put("email",email.getText().toString());
                                    firebaseFirestore.collection("USERS").document(firebaseAuth.getUid())
                                            .set(userdata)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        FirebaseUser user = firebaseAuth.getCurrentUser();
                                                        user.sendEmailVerification();

                                                        CollectionReference userDataReference= firebaseFirestore.collection("USERS").document(firebaseAuth.getUid()).collection("USER_DATA");




                                                        Map<Object,Long> wishlistmap = new HashMap<>();
                                                        wishlistmap.put("list_size", (long)0);

                                                        Map<Object,Long> ratinglistmap = new HashMap<>();
                                                        ratinglistmap.put("list_size", (long)0);


                                                        Map<Object,Long> cartlistmap = new HashMap<>();
                                                        ratinglistmap.put("list_size", (long)0);


                                                        Map<Object,Long> myAdressesmap = new HashMap<>();
                                                        ratinglistmap.put("list_size", (long)0);

                                                        Map<Object,Long> myNotificationmap = new HashMap<>();
                                                        myNotificationmap.put("list_size", (long)0);



                                                        List<String> documentName=new ArrayList<>();
                                                        documentName.add("MY_WISHSLIST");
                                                        documentName.add("MY_RATINGLIST");
                                                        documentName.add("MY_CARTLIST");
                                                        documentName.add("MY_ADRESS");
                                                        documentName.add("MY_NOTIFICATION");

                                                        List<Map<Object,Long>> documentField=new ArrayList<>();
                                                        documentField.add(wishlistmap);
                                                        documentField.add(ratinglistmap);
                                                        documentField.add(cartlistmap);
                                                        documentField.add(myAdressesmap);
                                                        documentField.add(myNotificationmap);



                                                        for(int x=0;x<documentName.size();x++)
                                                        {
                                                            int finalX = x;
                                                            userDataReference.document(documentName.get(x))
                                                                    .set(documentField.get(x)).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                            if(task.isSuccessful())
                                                                            {
                                                                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                                                                user.sendEmailVerification();


                                                                                    Toast.makeText(getActivity(), "Please verify your email address", Toast.LENGTH_SHORT).show();



                                                                            }else
                                                                            {
                                                                                button2.setEnabled(true);
                                                                                button2.setBackgroundColor(getContext().getResources().getColor(R.color.md_green_800));
                                                                                String error = task.getException().getMessage();
                                                                                Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
                                                                            }

                                                                        }
                                                                    });
                                                        }









                                                    } else {
                                                        String error = task.getException().getMessage();
                                                        Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
                                                        button2.setEnabled(true);
                                                        button2.setBackgroundColor(getContext().getResources().getColor(R.color.md_green_800));
                                                    }
                                                }
                                            });

                                } else {
                                    String error = task.getException().getMessage();
                                    Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
                                    button2.setEnabled(true);
                                    button2.setBackgroundColor(getContext().getResources().getColor(R.color.md_green_800));
                                }
                            }
                        });
            } else {
                confarmpassword.setError("Password dosen't match");
            }
        } else {
            confarmpassword.setError(" Email is invalid");
        }
    }


    private void mainIntent() {
        Intent home = new Intent(getActivity(), HomeActivity.class);
        startActivity(home);
        getActivity().finish();
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null && currentUser.isEmailVerified()) {
           mainIntent();
        }
    }

    private void sendVerificationEmail() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            user.sendEmailVerification()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(getActivity(), "Verification email sent", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "Failed to send verification email", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
    private void  setUpOnBackpress()
    {
        requireActivity().getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if(isEnabled())
                {
                    FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(parentFrameLayout.getId(), new signinFragment());
                    fragmentTransaction.commit();

                    setEnabled(false);
                }
            }
        });
    }





}






