package com.example.thibanglaixe.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thibanglaixe.MainActivity;
import com.example.thibanglaixe.R;
import com.example.thibanglaixe.untilities.Constants;

import java.util.ArrayList;

public class CertificateAdapter extends RecyclerView.Adapter<CertificateAdapter.MyViewHolder> {

    Context context;
    ArrayList<String> listCertificate;
    ArrayList<String> listDesc;
    SharedPreferences sharedPreferences;

    public CertificateAdapter(Context context, ArrayList<String> listCertificate,  ArrayList<String> listDesc) {
        this.context = context;
        this.listCertificate = listCertificate;
        this.listDesc = listDesc;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.certificate, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.title.setText(listCertificate.get(position));
        holder.description.setText(listDesc.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = holder.itemView.getContext().getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(Constants.PREF_LEVEL_CERTIFICATE, listCertificate.get(position));
                editor.apply();
                Intent toMain = new Intent(context, MainActivity.class);
                toMain.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                holder.itemView.getContext().startActivity(toMain);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listCertificate.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title, description;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_title_certificate);
            description = itemView.findViewById(R.id.tv_description_certificate);
        }
    }
}
