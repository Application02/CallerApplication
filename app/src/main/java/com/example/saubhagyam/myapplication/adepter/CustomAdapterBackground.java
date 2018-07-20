package com.example.saubhagyam.myapplication.adepter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saubhagyam.myapplication.R;

import java.util.ArrayList;
public class CustomAdapterBackground extends RecyclerView.Adapter <CustomAdapterBackground.MyViewHolder>{
    ArrayList personNames;
    ArrayList personImages;
    Context context;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String themecoloor = "themecoloorKey";
    public CustomAdapterBackground(Context context, ArrayList personNames, ArrayList personImages) {
        this.context = context;
        this.personNames = personNames;
        this.personImages = personImages;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlayout, parent, false);
        sharedpreferences = v.getContext().getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        try
        {
            // set the data in items
            holder.name.setText(personNames.get(position).toString());
            holder.image.setImageResource((Integer) personImages.get(position));
            // implement setOnClickListener event on item view.
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                final SharedPreferences.Editor editor = sharedpreferences.edit();
                @Override
                public void onClick(View view) {

                    switch (position) {
                        case 0:
                            editor.clear();
                            editor.putString(themecoloor, "1");
                            editor.apply();

                            //  Toast.makeText(context, "yellow", Toast.LENGTH_SHORT).show();
                            break;

                        case 1:
                            editor.clear();
                            editor.putString(themecoloor, "2");
                            editor.apply();

                           //  Toast.makeText(context, "red", Toast.LENGTH_SHORT).show();
                            break;


                        case 2:
                            editor.clear();
                            editor.putString(themecoloor, "3");
                            editor.apply();

                            // Toast.makeText(context, "black", Toast.LENGTH_SHORT).show();
                            break;


                        case 3:
                            editor.clear();
                            editor.putString(themecoloor, "4");
                            editor.apply();

                            // Toast.makeText(context, "white", Toast.LENGTH_SHORT).show();
                            break;


                        case 4:
                            editor.clear();
                            editor.putString(themecoloor, "5");
                            editor.apply();

                           //  Toast.makeText(context, "green", Toast.LENGTH_SHORT).show();
                            break;

                        case 5:
                            editor.clear();
                            editor.putString(themecoloor, "6");
                            editor.apply();

                            //  Toast.makeText(context, "green", Toast.LENGTH_SHORT).show();
                            break;

                        case 6:
                            editor.clear();
                            editor.putString(themecoloor, "7");
                            editor.apply();

                            //  Toast.makeText(context, "green", Toast.LENGTH_SHORT).show();
                            break;

                        case 7:
                            editor.clear();
                            editor.putString(themecoloor, "8");
                            editor.apply();

                            //  Toast.makeText(context, "green", Toast.LENGTH_SHORT).show();
                            break;


                    }

                    // open another activity on item click
              /*  Intent intent = new Intent(context, SecondActivity.class);
               intent.putExtra("image", (Integer)personImages.get(position)); // put image data in Intent
                context.startActivity(intent); // start Intent*/
                }
            });
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    @Override
    public int getItemCount() {
        return personNames.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        // init the item view's
        TextView name;
        ImageView image;
        public MyViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            name = (TextView) itemView.findViewById(R.id.name);
            image = (ImageView) itemView.findViewById(R.id.image);
        }
    }
}