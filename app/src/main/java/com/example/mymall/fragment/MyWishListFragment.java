package com.example.mymall.fragment;


import static com.example.mymall.fragment.MyCartFragment.loading_dialog;

import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymall.HomeActivity;
import com.example.mymall.R;
import com.example.mymall.wishlistAdapter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyWishListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyWishListFragment extends Fragment {
    public static RecyclerView wishlist_recyclerview;
    public static  wishlistAdapter adapter;

    public static  int fragmentOpenCount = 0;
    public static Dialog paymentmethod;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MyWishListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyWishListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyWishListFragment newInstance(String param1, String param2) {
        MyWishListFragment fragment = new MyWishListFragment();
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
        View view= inflater.inflate(R.layout.fragment_my_wish_list, container, false);
        setUpOnBackpress();
        loading_dialog=new Dialog(getContext());
        loading_dialog.setContentView(R.layout.loading);
        loading_dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        loading_dialog.setCancelable(true);
        wishlist_recyclerview=view.findViewById(R.id.wishlist_recyclerview);

        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        wishlist_recyclerview.setLayoutManager(layoutManager);
        DBquires dBquires=new DBquires();

     if(DBquires.wishlistModelList.size()==0)
     {
         DBquires.wishlist.clear();
         DBquires.loadwishlist(getContext(),true,loading_dialog);
     }





        adapter=new wishlistAdapter(DBquires.wishlistModelList,true);
        wishlist_recyclerview.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onResume() {
        super.onResume();

    }


    @Override
    public void onStop() {
        super.onStop();
        if (isAppClosedFromRecentTab() || isBackPressedAndRemainsInRecentApps()) {

        }
    }

    private boolean isAppClosedFromRecentTab() {
        if (getActivity() != null) {
            int appTaskId = getActivity().getTaskId();
            ActivityManager am = (ActivityManager) getActivity().getSystemService(getContext().ACTIVITY_SERVICE);
            List<ActivityManager.AppTask> appTasks = am.getAppTasks();
            for (ActivityManager.AppTask task : appTasks) {
                if (task.getTaskInfo().id == appTaskId) {
                    // The app was closed from the recent tab
                    return true;
                }
            }
        }
        return false;
    }



    private boolean isBackPressedAndRemainsInRecentApps() {
        if (getActivity() != null) {
            ActivityManager am = (ActivityManager) getActivity().getSystemService(getContext().ACTIVITY_SERVICE);
            List<ActivityManager.AppTask> tasks = am.getAppTasks();
            if (!tasks.isEmpty()) {
                ActivityManager.AppTask task = tasks.get(0);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    return task.getTaskInfo().numActivities == 1 && fragmentOpenCount > 0;
                }
            }
        }
        return false;
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