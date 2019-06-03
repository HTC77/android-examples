package com.example.androidexamples;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class StudentListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<StudentModel> arrayList;
    private LayoutInflater inflater;

    public StudentListAdapter(Context context, ArrayList<StudentModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public  static class viewholder
    {
        private TextView txtName;
        private TextView txtPass;
        private TextView txtId;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View via = view;
        viewholder holder = new viewholder();

        if (via == null){
            via = inflater.inflate(R.layout.item_student,null);
            holder.txtName = via.findViewById(R.id.txtName);
            holder.txtId = via.findViewById(R.id.txtId);
            holder.txtPass = via.findViewById(R.id.txtPass);
            via.setTag(holder);
        }else{
            holder = (viewholder) via.getTag();
        }

        if (arrayList.size() > 0){
            final StudentModel tempvalue = arrayList.get(i);
            holder.txtName.setText(tempvalue.getName());
            holder.txtId.setText(tempvalue.getId());
            holder.txtPass.setText(tempvalue.getPass());
        }

        return via;
    }
}
