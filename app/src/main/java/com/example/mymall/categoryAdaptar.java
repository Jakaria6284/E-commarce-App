package com.example.mymall;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class categoryAdaptar  extends RecyclerView.Adapter<categoryAdaptar.ViewHolder> {
    private  List<categorymodel> categorymodelList;
    Context context;

    public categoryAdaptar(List<categorymodel> categorymodelList)
    {

        this.categorymodelList=categorymodelList;
    }



     @NonNull
    @Override
    public categoryAdaptar.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
     View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.categoryitem,parent,false);
     return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull categoryAdaptar.ViewHolder holder, int position) {
        String icon=categorymodelList.get(position).getCategoryIconlink();
        String name=categorymodelList.get(position).getCategoryname();
        holder.animation.startAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(),R.anim.left));
        holder.setCategorynameem(name,position);
        holder.setCategoryiconcon(icon,position);

    }

    @Override
    public int getItemCount() {
        return categorymodelList.size();
    }
    public  class ViewHolder extends RecyclerView.ViewHolder{

         private ImageView categoryiconcon;
        private  TextView categorynameem;
        private ConstraintLayout animation;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
             categoryiconcon=itemView.findViewById(R.id.categoryicon);
             categorynameem=itemView.findViewById(R.id.categoryname);
             animation=itemView.findViewById(R.id.catrgory_item);
        }
        private void setCategoryiconcon(String iconUrl, int position) {

                Glide.with(itemView.getContext()).load(iconUrl).apply(new RequestOptions().placeholder(R.drawable.image)).into(categoryiconcon);

        }


        private  void setCategorynameem(final String name,final int position)
        {
            categorynameem.setText(name);
            //set categoryname

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (position!=0) {
                        Intent categoryIntent = new Intent(itemView.getContext(), CategoryActivity.class);
                        categoryIntent.putExtra("Categoryname", name);
                        itemView.getContext().startActivity(categoryIntent);
                    }
                }
            });
        }
    }


}
