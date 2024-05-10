package com.example.mymall.fragment;


//import static androidx.core.app.AppOpsManagerCompat.Api23Impl.getSystemService;

import static com.example.mymall.fragment.DBquires.categorymodelList;
import static com.example.mymall.fragment.DBquires.list;
import static com.example.mymall.fragment.DBquires.loadCategories;
import static com.example.mymall.fragment.DBquires.loadedcategoryName;
import static com.example.mymall.fragment.DBquires.setFragmentData;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.mymall.HomePageAdaptar;
import com.example.mymall.HomepageModel;
import com.example.mymall.R;
import com.example.mymall.categoryAdaptar;
import com.example.mymall.categorymodel;
import com.example.mymall.horizental_scroll_product_model;
import com.example.mymall.sliderModel;
import com.example.mymall.wishlistModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
     RecyclerView categoryRecyclerview;
     public static categoryAdaptar categoryAdaptar;
    private boolean isFragmentLoaded;
     private   RecyclerView homepageRecyclerview;
     List<categorymodel> categoryfakelist=new ArrayList<>();
     List<HomepageModel>Homepagemodelfakelist=new ArrayList<>();
     public static SwipeRefreshLayout swipeRefreshLayout;
     public static HomePageAdaptar homePageAdaptar;
    NetworkInfo networkInfo;
    ConnectivityManager connectivityManager;

    // private  List<categorymodel> categorymodelList;
     //FirebaseFirestore firebaseFirestore;










    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

     //@SuppressLint({"NotifyDataSetChanged", "MissingInflatedId"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.homfragment, container, false);


        swipeRefreshLayout=view.findViewById(R.id.swipeRefreshLayout);
        categoryRecyclerview=view.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        categoryRecyclerview.setLayoutManager(linearLayoutManager);

        homepageRecyclerview =view.findViewById(R.id.homepage_recyclerview);
        LinearLayoutManager testingLayout=new LinearLayoutManager(getContext());
        testingLayout.setOrientation(LinearLayoutManager.VERTICAL);
        homepageRecyclerview.setLayoutManager(testingLayout);
        // categorymodelList=new ArrayList<categorymodel>();


        categoryfakelist.add(new categorymodel("null",""));
        categoryfakelist.add(new categorymodel("",""));
        categoryfakelist.add(new categorymodel("",""));
        categoryfakelist.add(new categorymodel("",""));
        categoryfakelist.add(new categorymodel("",""));
        categoryfakelist.add(new categorymodel("",""));
        categoryfakelist.add(new categorymodel("",""));
        categoryfakelist.add(new categorymodel("",""));


        ////Banner Slider
        List<sliderModel>sliderModelListfake=new ArrayList<sliderModel>();
        sliderModelListfake.add(new sliderModel("null","#FF6200EE"));
        sliderModelListfake.add(new sliderModel("null","#FF6200EE"));
        sliderModelListfake.add(new sliderModel("null","#FF6200EE"));
        sliderModelListfake.add(new sliderModel("null","#FF6200EE"));
        sliderModelListfake.add(new sliderModel("null","#FF6200EE"));
        sliderModelListfake.add(new sliderModel("null","#FF6200EE"));

        /////Banner slider

        ////horizental product layout
        List<horizental_scroll_product_model> horizental_scroll_product_modelListfake=new ArrayList<>();
        horizental_scroll_product_modelListfake.add(new horizental_scroll_product_model("","","","",""));
        horizental_scroll_product_modelListfake.add(new horizental_scroll_product_model("","","","",""));
        horizental_scroll_product_modelListfake.add(new horizental_scroll_product_model("","","","",""));
        horizental_scroll_product_modelListfake.add(new horizental_scroll_product_model("","","","",""));
        horizental_scroll_product_modelListfake.add(new horizental_scroll_product_model("","","","",""));
        horizental_scroll_product_modelListfake.add(new horizental_scroll_product_model("","","","",""));
        horizental_scroll_product_modelListfake.add(new horizental_scroll_product_model("","","","",""));



        Homepagemodelfakelist.add(new HomepageModel(0,sliderModelListfake));
        Homepagemodelfakelist.add(new HomepageModel(2,"",horizental_scroll_product_modelListfake,new ArrayList<wishlistModel>()));
        Homepagemodelfakelist.add(new HomepageModel(2,"",horizental_scroll_product_modelListfake,new ArrayList<wishlistModel>()));
        Homepagemodelfakelist.add(new HomepageModel(1,""));
        Homepagemodelfakelist.add(new HomepageModel(2,"",horizental_scroll_product_modelListfake,new ArrayList<wishlistModel>()));


        ////horizental product layout

        categoryAdaptar=new categoryAdaptar(categoryfakelist);


        homePageAdaptar=new HomePageAdaptar(Homepagemodelfakelist);



        if(categorymodelList.size()==0)
        {
            loadCategories(categoryRecyclerview,getContext());

        }else
        {
            categoryAdaptar=new categoryAdaptar(categorymodelList);
            categoryAdaptar.notifyDataSetChanged();
        }
        categoryRecyclerview.setAdapter(categoryAdaptar);






      if(list.size()==0)
      {
          loadedcategoryName.add("HOME");
          list.add(new ArrayList<HomepageModel>());
          setFragmentData( homepageRecyclerview,getContext(),0,"Home");
      }else
      {
           homePageAdaptar=new HomePageAdaptar(list.get(0));
          homePageAdaptar.notifyDataSetChanged();
      }

        homepageRecyclerview.setAdapter( homePageAdaptar);



       // homepageModelList.add(new HomepageModel(3,g));








    ///refresh layoyut
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                categorymodelList.clear();
                loadedcategoryName.clear();
                list.clear();
                loadCategories(categoryRecyclerview,getContext());
                categoryAdaptar=new categoryAdaptar(categoryfakelist);
                homePageAdaptar=new HomePageAdaptar(Homepagemodelfakelist);
                categoryRecyclerview.setAdapter(categoryAdaptar);
                homepageRecyclerview.setAdapter(homePageAdaptar);
                loadedcategoryName.add("HOME");
                list.add(new ArrayList<HomepageModel>());
                homePageAdaptar=new HomePageAdaptar(list.get(0));
                setFragmentData( homepageRecyclerview,getContext(),0,"Home");



            }
        });

        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        if(isFragmentLoaded)
        {
            categoryRecyclerview.setAdapter(categoryAdaptar);
        }


    }
}

