package com.example.saubhagyam.myapplication.adepter;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.saubhagyam.myapplication.R;
import com.example.saubhagyam.myapplication.model.ContactModel;

import java.util.ArrayList;


public class CustomAdapter extends BaseAdapter /*implements Filterable*/ {

    private Context context;
    private ArrayList<ContactModel> contactModelArrayList;

    /* ValueFilter valueFilter;*/
    public CustomAdapter(Context context, ArrayList<ContactModel> contactModelArrayList) {

        this.context = context;
        this.contactModelArrayList = contactModelArrayList;
    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public int getCount() {
        return contactModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return contactModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.single_contact_view, null, true);

            holder.txtFirstChar = (TextView) convertView.findViewById(R.id.txtFirstCharecter);
            holder.txtName = (TextView) convertView.findViewById(R.id.name);
            holder.linearLayoutCall = (LinearLayout) convertView.findViewById(R.id.linearLayoutCall);
            convertView.setTag(holder);
        } else {

            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtName.setText(contactModelArrayList.get(position).getName());
        holder.txtFirstChar.setText((contactModelArrayList.get(position).getName().toUpperCase().charAt(0) + ""));
        holder.txtFirstChar.setTextColor(getMatColor("600"));
        holder.linearLayoutCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return convertView;
    }

    private int getMatColor(String typeColor) {
        int returnColor = Color.BLACK;
        int arrayId = context.getResources().getIdentifier("mdcolor_" + typeColor, "array", context.getPackageName());

        if (arrayId != 0) {
            TypedArray colors = context.getResources().obtainTypedArray(arrayId);
            int index = (int) (Math.random() * colors.length());
            returnColor = colors.getColor(index, Color.BLACK);
            colors.recycle();
        }
        return returnColor;
    }

    private class ViewHolder {

        protected TextView txtName;
        TextView txtFirstChar;
        LinearLayout linearLayoutCall;

    }

}