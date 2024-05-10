package com.example.mymall;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.mymall.fragment.ProductDescriptionFragment;
import com.example.mymall.fragment.SpecificationFragment;

import java.util.List;

public class product_details_Adaptar extends FragmentPagerAdapter {
    private int totaltab;
    private String productdecription;
    private String productOtherdetails;
    private List<productSpecificationModel> productSpecificationModelList;

    public product_details_Adaptar(@NonNull FragmentManager fm,int totaltab) {
        super(fm);

        this.totaltab=totaltab;
    }




    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                ProductDescriptionFragment productDescriptionFragment1 = new ProductDescriptionFragment();
              // productDescriptionFragment1.body=productdecription;
                return productDescriptionFragment1;
            case 1:
                SpecificationFragment specificationFragment = new SpecificationFragment();
              // specificationFragment. productSpecificationModelList=productSpecificationModelList;
                return specificationFragment;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totaltab;
    }
}
