package com.example.androidexamples.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.androidexamples.R;

public class SetViewHolder extends RecyclerView.ViewHolder {
    public TextView tvName;
    public TextView tvPrice;
    public TextView tvDescription;

    public SetViewHolder(View itemView) {
        super(itemView);
        tvName = itemView.findViewById(R.id.tvName);
        tvPrice = itemView.findViewById(R.id.tvPrice);
        tvDescription = itemView.findViewById(R.id.tvDescription);

    }
}