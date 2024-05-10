package com.example.mymall;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class notificationAdapter extends RecyclerView.Adapter<notificationAdapter.viewHolder> {
    List<notificationModel>notificationModelList;

    public notificationAdapter(List<notificationModel> notificationModelList) {
        this.notificationModelList = notificationModelList;
    }

    @NonNull
    @Override
    public notificationAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.notificationitem,parent,false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull notificationAdapter.viewHolder holder, int position) {
        String notificationtitle=notificationModelList.get(position).getNotificationTitle();
        String notificationbody=notificationModelList.get(position).getNotificationBody();
        boolean readed=notificationModelList.get(position).isReaded();
        holder.setData(notificationtitle,notificationbody,readed);


    }

    @Override
    public int getItemCount() {
        return notificationModelList.size();
    }
    public class viewHolder extends RecyclerView.ViewHolder{
        private TextView notificationTitle,notificationBody;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            notificationTitle=itemView.findViewById(R.id.notifcationtitle);
            notificationBody=itemView.findViewById(R.id.notificationbody);

        }
        public void setData(String title,String body,boolean readed)
        {

                notificationTitle.setText(title);
                notificationBody.setText(body);


        }
    }
}
