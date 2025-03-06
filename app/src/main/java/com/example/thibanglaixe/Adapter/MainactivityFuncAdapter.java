package com.example.thibanglaixe.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thibanglaixe.R;
import com.example.thibanglaixe.activity.PracticeTestActivity;
import com.example.thibanglaixe.activity.TipsRememberActivity;
import com.example.thibanglaixe.activity.TrafficSignActivity;
import com.example.thibanglaixe.activity.ViewItemRecyclerViewMainActivity;

import java.util.ArrayList;

public class MainactivityFuncAdapter extends RecyclerView.Adapter<MainactivityFuncAdapter.MyViewHolder> {
    Context context;
    ArrayList<Integer> listIdIcon;
    ArrayList<String> listFunc;
    ArrayList<String> listTag;
    ArrayList<Integer> listColor;

    public MainactivityFuncAdapter(Context context, ArrayList<Integer> listColor, ArrayList<Integer> listIdIcon, ArrayList<String> listFunc, ArrayList<String> listTag) {
        this.context = context;
        this.listColor = listColor;
        this.listIdIcon = listIdIcon;
        this.listFunc = listFunc;
        this.listTag = listTag;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_func, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
         int idIcon = listIdIcon.get(position);
         int idColor = listColor.get(position);
         String func = listFunc.get(position);
         String tag = listTag.get(position);
         holder.title.setText(func);
         holder.icon.setImageResource(idIcon);
         holder.constraintLayout.setBackgroundResource(idColor);
         holder.itemView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if(tag == "note") {
                     Intent noteActivity = new Intent(context, TipsRememberActivity.class);
                     holder.itemView.getContext().startActivity(noteActivity);
                 } else if(tag == "random" | tag == "test"){
                     Intent intent = new Intent(context, PracticeTestActivity.class);
                     holder.itemView.getContext().startActivity(intent);
                 }else if(tag == "wrong" | tag == "practice" | tag == "point" |  tag == "top") {
                     Intent intent = new Intent(context, ViewItemRecyclerViewMainActivity.class);
                     holder.itemView.getContext().startActivity(intent);
                 }else {
                     Intent trafficSign = new Intent(context, TrafficSignActivity.class);
                     holder.itemView.getContext().startActivity(trafficSign);
                 }
             }
         });
    }

    @Override
    public int getItemCount() {
        return listFunc.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ConstraintLayout constraintLayout;
        TextView title;
        ImageView icon;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            constraintLayout = itemView.findViewById(R.id.item_func_layout);
            title = itemView.findViewById(R.id.item_func_iv_title);
            icon = itemView.findViewById(R.id.item_func_iv);
        }
    }
}
