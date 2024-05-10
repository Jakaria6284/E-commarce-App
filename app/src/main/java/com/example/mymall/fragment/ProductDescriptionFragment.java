package com.example.mymall.fragment;

import static com.example.mymall.ProductDetailsActivity.productDesriptionBpdy;
import static com.example.mymall.ProductDetailsActivity.tabposition;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.mymall.R;

public class ProductDescriptionFragment extends Fragment {

   // public static String productDesription;
  //  public String body;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public ProductDescriptionFragment() {
        // Required empty public constructor
    }
    private TextView descriptionbody;
   public static String  productdescription;
    public String body;


    public static ProductDescriptionFragment newInstance(String param1, String param2) {
        ProductDescriptionFragment fragment = new ProductDescriptionFragment();
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
        View view= inflater.inflate(R.layout.fragment_product_description, container, false);
       descriptionbody=view.findViewById(R.id.tv_productDescription);

      if (tabposition==0)
      {
          descriptionbody.setText( productDesriptionBpdy);
      }


        return view;
    }
}