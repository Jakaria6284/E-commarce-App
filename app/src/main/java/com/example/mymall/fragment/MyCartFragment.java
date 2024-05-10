package com.example.mymall.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.mymall.CartitemModel;
import com.example.mymall.DeliveryActivity;
import com.example.mymall.HomeActivity;
import com.example.mymall.R;
import com.example.mymall.cartAdapter;
import com.example.mymall.paymentActivity;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyCartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyCartFragment extends Fragment {
   private RecyclerView cartitemsRecylerView;
   //public static cartAdapter cartadapter;
    private Button contenue;
    private TextView total_ammount_below;
    TextView badgeCount;
   public static Dialog loading_dialog;

    private boolean isRefreshing;
    public static  cartAdapter cartAdapter;
    public static SwipeRefreshLayout swipeRefreshLayout;
    public static Dialog paymentmethod;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MyCartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyCartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyCartFragment newInstance(String param1, String param2) {
        MyCartFragment fragment = new MyCartFragment();
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
        View view= inflater.inflate(R.layout.fragment_my_cart, container, false);
        cartitemsRecylerView=view.findViewById(R.id.cartRecylerview);
        total_ammount_below=view.findViewById(R.id.total_cart_amount_fragment);
        setUpOnBackpress();
        swipeRefreshLayout=view.findViewById(R.id.swipeRefreshLayout);
        contenue=view.findViewById(R.id.buy_now_btn);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        cartitemsRecylerView.setLayoutManager(layoutManager);

        DBquires dBquires=new DBquires();
        cartAdapter=new cartAdapter(DBquires.cartitemModelList,total_ammount_below,true);
        cartitemsRecylerView.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();

        //dialog
        loading_dialog=new Dialog(getContext());
        loading_dialog.setContentView(R.layout.loading);
        loading_dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        loading_dialog.setCancelable(true);

        //dialog




        contenue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paymentActivity.fromcart=true;

                DeliveryActivity.cartitemModelList=new ArrayList<>();

                for(int x=0;x<DBquires.cartitemModelList.size();x++)
                {
                    CartitemModel cartitemModel=DBquires.cartitemModelList.get(x);
                    if(cartitemModel.isIn_stock())
                    {
                        DeliveryActivity.cartitemModelList.add(cartitemModel);
                    }
                }
                DeliveryActivity.cartitemModelList.add(new CartitemModel(CartitemModel.TOTAL_AMOUNT));
             if(DBquires.myAddressModelList.size()==0) {
                 DBquires.loadAdresses(getContext(),new Dialog(getContext()));
             }else
             {

                 Intent intent=new Intent(getContext(),DeliveryActivity.class);
                 startActivity(intent);
             }
            }

        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {


                  
                    DBquires.cartitemModelList.clear();
                    DBquires.cartlist.clear();
                    cartAdapter.notifyDataSetChanged();
                    DBquires.loadcartlist(getContext(), true, badgeCount,total_ammount_below,loading_dialog);

                    swipeRefreshLayout.setRefreshing(false);







            }


        });


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
                    HomeFragment.homePageAdaptar.notifyDataSetChanged();
                    startActivity(intent);
                    setEnabled(false);
                    requireActivity().onBackPressed();
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        cartAdapter.notifyDataSetChanged();
        if(DBquires.cartitemModelList.size()==0)
        {
            DBquires.loadrewards(getContext(),false,loading_dialog);
        }
        if(DBquires.cartitemModelList.size()==0)
        {
            DBquires.cartlist.clear();
            DBquires.loadcartlist(getContext(),true,new TextView(getContext()),total_ammount_below,loading_dialog);


        }else
        {
            if(DBquires.cartitemModelList.get(DBquires.cartitemModelList.size()-1).getType()== CartitemModel.TOTAL_AMOUNT)
            {
                LinearLayout parent=(LinearLayout) total_ammount_below.getParent().getParent();
                parent.setVisibility(View.VISIBLE);
            }
        }



    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        for(CartitemModel cartitemModel:DBquires.cartitemModelList)
        {
            if(!TextUtils.isEmpty(cartitemModel.getSelectedCoupenID()))
            {
                for (com.example.mymall.rewardModel rewardModel:DBquires.rewardModelList) {
                    if(rewardModel.getCoupenId().equals(cartitemModel.getSelectedCoupenID()))
                    {
                        rewardModel.setAlreadyusedCoupen(false);

                    }
                  cartitemModel.setSelectedCoupenID(null);
                    if(MyRewardFragment.RewardAdapter!=null) {
                        MyRewardFragment.RewardAdapter.notifyDataSetChanged();
                    }
                }
            }
        }
    }
}