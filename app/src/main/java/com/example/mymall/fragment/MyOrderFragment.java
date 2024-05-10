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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyOrderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyOrderFragment extends Fragment {
    private RecyclerView myorderrecylerView;
    public static  myorderAdapter orderAdapter;
    private Dialog loading_dialog;
    TextView nodata;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MyOrderFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyOrderFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyOrderFragment newInstance(String param1, String param2) {
        MyOrderFragment fragment = new MyOrderFragment();
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
        View view= inflater.inflate(R.layout.fragment_my_order, container, false);
        setUpOnBackpress();
        nodata=view.findViewById(R.id.nodata);
        loading_dialog=new Dialog(getContext());
        loading_dialog.setContentView(R.layout.loading);
        loading_dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        loading_dialog.setCancelable(true);

        myorderrecylerView=view.findViewById(R.id.my_order_recylerview);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myorderrecylerView.setLayoutManager(layoutManager);

        if(DBquires.myOrderItemModelList.size()==0)
        {
            DBquires.loadOrder(getContext(),loading_dialog);
        }
        orderAdapter=new myorderAdapter(DBquires.myOrderItemModelList);
        myorderrecylerView.setAdapter(orderAdapter);


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