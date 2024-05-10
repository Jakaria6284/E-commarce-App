package com.example.mymall;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class productSpecificationAdaptar extends RecyclerView.Adapter<productSpecificationAdaptar.ViewHolder>{
    private List<productSpecificationModel> productSpecificationModelList;
    public productSpecificationAdaptar(List<productSpecificationModel> productSpecificationModelList) {
        this.productSpecificationModelList = productSpecificationModelList;
    }


    @NonNull
    @Override
    public productSpecificationAdaptar.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.product_specification_item_layout,parent,false);
       return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull productSpecificationAdaptar.ViewHolder holder, int position) {
        String featureTitle=productSpecificationModelList.get(position).getFeaturename();
        String featureDetail=productSpecificationModelList.get(position).getFeaturevalue();
        holder.setFeature(featureTitle,featureDetail);

    }

    public int getItemCount() {
        if (productSpecificationModelList != null) {
            return productSpecificationModelList.size();
        } else {
            return 0;
        }
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView featurename;
        private TextView featurevalue;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            featurename=itemView.findViewById(R.id.feature_name);
            featurevalue=itemView.findViewById(R.id.feature_value);
        }
        private void setFeature(String featureTitle,String featureDetail)
        {
            featurename.setText(featureTitle);
            featurevalue.setText(featureDetail);
        }
    }
}
