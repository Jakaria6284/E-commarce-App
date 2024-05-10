package com.example.mymall;

import static com.example.mymall.DeliveryActivity.SELECT_ADDRESS;
import static com.example.mymall.MyAddressActivity.refresh;
import static com.example.mymall.fragment.MyAccountFragment.MANAGE_ADDRESS;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymall.fragment.DBquires;

import java.util.List;

public class myadressAdapter extends RecyclerView.Adapter<myadressAdapter.viewholder> {
    List<myAddressModel>myAddressModelList;
    private int MODE;
    private int preselectedposition;


    public myadressAdapter(List<myAddressModel> myAddressModelList,int MODE) {
        this.myAddressModelList = myAddressModelList;
        this.MODE=MODE;
        preselectedposition= DBquires.selectedIndex;
    }

    @NonNull
    @Override
    public myadressAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.my_address_item_layout,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myadressAdapter.viewholder holder, int position) {
      String fullname=myAddressModelList.get(position).getFullname();
      String mobilNumber=myAddressModelList.get(position).getMobileNumber();
      String alternativeMobileNumber=myAddressModelList.get(position).getAlternativeMobileNumber();
      String address=myAddressModelList.get(position).getAddress();
      String pincode=myAddressModelList.get(position).getPincode();
      Boolean selected=myAddressModelList.get(position).getSelected();
        Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(),R.anim.left);
        holder.itemView.startAnimation(animation);
      holder.setData(fullname,mobilNumber,alternativeMobileNumber,address,pincode,selected,position);
    }

    @Override
    public int getItemCount() {
        return myAddressModelList.size();
    }



    public class viewholder extends RecyclerView.ViewHolder {
        private TextView fullname;
        private TextView address;
        private TextView pincode;
        private ImageView icon;
        private LinearLayout option_container;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            fullname=itemView.findViewById(R.id.myaddresss_name);
            address=itemView.findViewById(R.id.myAddress_adress);
            pincode=itemView.findViewById(R.id.myadress_pincode);
            icon=itemView.findViewById(R.id.icon_view);
            option_container=itemView.findViewById(R.id.option_container);
        }
        private void setData(String name,String mobileNumber,String alternativeMobileNumber,String adress,String pincodee,Boolean selected,int position)
        {
            fullname.setText(name+" "+mobileNumber+" "+alternativeMobileNumber);
            address.setText(adress);
            pincode.setText(pincodee);
            if(MODE==SELECT_ADDRESS)
            {
                icon.setImageResource(R.drawable.ic_baseline_check_24);
                if(selected)
                {
                  icon.setVisibility(View.VISIBLE);
                  preselectedposition=position;
                }else
                {
                    icon.setVisibility(View.GONE);
                }
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(preselectedposition!=position) {
                            myAddressModelList.get(position).setSelected(true);
                            myAddressModelList.get(preselectedposition).setSelected(false);
                            refresh(preselectedposition, position);
                            preselectedposition = position;
                            DBquires.selectedIndex=position;
                        }
                    }
                });
            }else if(MODE==MANAGE_ADDRESS)
            {
                option_container.setVisibility(View.GONE);
                icon.setImageResource(R.drawable.more);
                icon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        option_container.setVisibility(View.VISIBLE);
                        refresh(preselectedposition,preselectedposition);
                        preselectedposition=position;
                    }
                });
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        refresh(preselectedposition,preselectedposition);
                        preselectedposition=-1;
                    }
                });

            }
        }
    }
}
