package com.example.saubhagyam.myapplication.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.saubhagyam.myapplication.R;
import com.example.saubhagyam.myapplication.adepter.CustomAdapterBackground;
import com.example.saubhagyam.myapplication.util.Config;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class SettingFragment extends Fragment implements View.OnClickListener,AdapterView.OnItemClickListener {

    View mView;

    ArrayList personNames = new ArrayList<>(Arrays.asList("1", "2", "3", "4", "5","6", "7", "8"/*, "9", "10","11", "12", "13", "14", "15","16"*/));
    ArrayList personImages = new ArrayList<>(Arrays.asList(R.drawable.back1, R.drawable.back2, R.drawable.back3, R.drawable.back4, R.drawable.back5,R.drawable.back6, R.drawable.back7, R.drawable.back8/*, R.drawable.back9, R.drawable.back10, R.drawable.back11,R.drawable.back12, R.drawable.back13, R.drawable.back14, R.drawable.back15, R.drawable.back16*/));


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.setting, container, false);

        RecyclerView recyclerView = (RecyclerView) mView.findViewById(R.id.recyclerView);

        getActivity().setTitle("Setting");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if(Objects.requireNonNull(getActivity()).getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
                recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
            }
            else{
                recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
            }
        }

        //  recyclerView.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
            //  call the constructor of CustomAdapter to send the reference and data to Adapter
            CustomAdapterBackground customAdapter = new CustomAdapterBackground(getActivity(), personNames,personImages);
            recyclerView.setAdapter(customAdapter); // set the Adapter to RecyclerView


        return mView;
    }


    @Override
    public void onResume() {
        super.onResume();
        Config.visibility="1";
    }


    @Override
    public void onClick(View view) {
      /* final SharedPreferences.Editor editor = sharedpreferences.edit();*/

        /*PopupMenu popupMenu = new PopupMenu(getActivity(), btnthemecolor);

        popupMenu.getMenuInflater().inflate(R.menu.priority, popupMenu.getMenu());
        popupMenu.show();

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.back1:
                        editor.clear();
                        editor.putString(themecoloor, "1");
                        editor.apply();

                      //  Toast.makeText(getActivity(), "yellow", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.back2:
                        editor.clear();
                        editor.putString(themecoloor, "2");
                        editor.apply();

                       // Toast.makeText(getActivity(), "red", Toast.LENGTH_SHORT).show();
                        break;


                    case R.id.back3:
                        editor.clear();
                        editor.putString(themecoloor, "3");
                        editor.apply();

                       // Toast.makeText(getActivity(), "black", Toast.LENGTH_SHORT).show();
                        break;


                    case R.id.back4:
                        editor.clear();
                        editor.putString(themecoloor, "4");
                        editor.apply();

                       // Toast.makeText(getActivity(), "white", Toast.LENGTH_SHORT).show();
                        break;


                    case R.id.back5:
                        editor.clear();
                        editor.putString(themecoloor, "5");
                        editor.apply();

                       // Toast.makeText(getActivity(), "green", Toast.LENGTH_SHORT).show();
                        break;


                }
                return false;
            }
        });*/
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
