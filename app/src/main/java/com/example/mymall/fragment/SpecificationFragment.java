package com.example.mymall.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymall.ProductDetailsActivity;
import com.example.mymall.R;
import com.example.mymall.productSpecificationAdaptar;


public class SpecificationFragment extends Fragment{
    //public static List<productSpecificationModel> productSpecificationModelList;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static  productSpecificationAdaptar productSpecificationAdaptar;


    private String mParam1;
    private String mParam2;

    public SpecificationFragment() {
        // Required empty public constructor
    }


    private RecyclerView productSpecificationRecyclerview;
    public static SpecificationFragment newInstance(String param1, String param2) {
        SpecificationFragment fragment = new SpecificationFragment();
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
        View view= inflater.inflate(R.layout.fragment_specification, container, false);
        productSpecificationRecyclerview=view.findViewById(R.id.product_specification_recycler_view);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        productSpecificationRecyclerview.setLayoutManager(linearLayoutManager);
       // List<productSpecificationModel> productSpecificationModelList=new ArrayList<>();
       // productSpecificationModelList.add(new productSpecificationModel("Ram","32gb"));
        //productSpecificationModelList.add(new productSpecificationModel("Ram","64gb"));

        productSpecificationAdaptar productSpecificationAdaptar=new productSpecificationAdaptar(ProductDetailsActivity.productSpecificationModelList);
        productSpecificationRecyclerview.setAdapter(productSpecificationAdaptar);


        productSpecificationAdaptar.notifyDataSetChanged();

        return  view;
    }
}