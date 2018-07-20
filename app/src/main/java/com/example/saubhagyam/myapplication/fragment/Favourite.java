package com.example.saubhagyam.myapplication.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.saubhagyam.myapplication.R;
import com.example.saubhagyam.myapplication.util.Config;

public class Favourite extends Fragment {

    View mView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.favourite, container, false);
        getActivity().setTitle("Favourite");
        return  mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        Config.visibility="1";
    }

}
