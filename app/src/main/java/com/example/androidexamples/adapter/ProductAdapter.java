package com.example.androidexamples.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.androidexamples.R;
import com.example.androidexamples.model.Product;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<SetViewHolder> {
    private Context mContext;
    private List<Product> mProductList;
    private OnTabListener onTabListener;


    public ProductAdapter(Context mContext, List<Product> mProductList) {
        this.mContext = mContext;
        this.mProductList = mProductList;
    }

    @Override
    public SetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product,parent,false);
        return new SetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SetViewHolder holder, final int position) {
        holder.tvName.setText(mProductList.get(position).getName());
        holder.tvPrice.setText(String.valueOf(mProductList.get(position).getPrice()));
        holder.tvDescription.setText(mProductList.get(position).getDescription());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onTabListener!=null)
                    onTabListener.onTapView(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mProductList.size();
    }

    public void setOnTabListener(OnTabListener onTabListener) {
        this.onTabListener = onTabListener;
    }
}