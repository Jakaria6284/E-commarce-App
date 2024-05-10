package com.example.mymall.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymall.HomeActivity;
import com.example.mymall.R;
import com.example.mymall.myorderAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyAccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyAccountFragment extends Fragment {
    public static final int MANAGE_ADDRESS=1;

    private RecyclerView myrecentorderrecylerView;
    public static myorderAdapter orderAdapter;
    private Dialog loading_dialog;
    private TextView userName,userEmail,UserProfile;
    FirebaseFirestore firebaseFirestore;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MyAccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyAccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyAccountFragment newInstance(String param1, String param2) {
        MyAccountFragment fragment = new MyAccountFragment();
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
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_my_account, container, false);
        userEmail=view.findViewById(R.id.user_email);
        userName=view.findViewById(R.id.user_name);
        setUpOnBackpress();
        myrecentorderrecylerView=view.findViewById(R.id.recentorderrecyclerview);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myrecentorderrecylerView.setLayoutManager(layoutManager);
        loading_dialog=new Dialog(getContext());
        loading_dialog.setContentView(R.layout.loading);
        loading_dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        loading_dialog.setCancelable(true);
        firebaseFirestore=FirebaseFirestore.getInstance();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        firebaseFirestore.collection("USERS").document(currentUser.getUid()).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists())
                        {
                            String username=documentSnapshot.getString("fullname");
                            String useremail=documentSnapshot.getString("email");

                            userName.setText(username);
                            userEmail.setText(useremail);
                        }
                    }
                });

        if(DBquires.myOrderItemModelList.size()==0)
        {
            DBquires.loadOrderforAcount(getContext(),loading_dialog);
        }
        orderAdapter=new myorderAdapter(DBquires.myOrderItemModelList);
        myrecentorderrecylerView.setAdapter(orderAdapter);


        orderAdapter.notifyDataSetChanged();



        return view;
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