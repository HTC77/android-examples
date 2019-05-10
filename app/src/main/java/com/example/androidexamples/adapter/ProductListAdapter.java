package com.example.androidexamples.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.androidexamples.R;
import com.example.androidexamples.model.Product;

import java.util.List;

public class ProductListAdapter extends BaseAdapter {
    private Context mContext;
    private List<Product> mProductList;

    public ProductListAdapter(Context mContex, List<Product> mProductList) {
        this.mContext = mContex;
        this.mProductList = mProductList;
    }

    @Override
    public int getCount() {
        return mProductList.size();
    }

    @Override
    public Object getItem(int position) {
        return mProductList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mProductList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(mContext, R.layout.item_product,null);
        TextView tvName = v.findViewById(R.id.tvName);
        TextView tvPrice = v.findViewById(R.id.tvPrice);
        TextView tvDescription = v.findViewById(R.id.tvDescription);
        tvName.setText(mProductList.get(position).getName());
        tvPrice.setText(String.valueOf(mProductList.get(position).getPrice())+"$");
        tvDescription.setText(mProductList.get(position).getDescription());
        return v;
    }
}
