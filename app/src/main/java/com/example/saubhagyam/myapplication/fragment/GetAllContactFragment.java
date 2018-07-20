package com.example.saubhagyam.myapplication.fragment;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.saubhagyam.myapplication.R;
import com.example.saubhagyam.myapplication.adepter.CustomAdapter;
import com.example.saubhagyam.myapplication.adepter.ExampleAdapter;
import com.example.saubhagyam.myapplication.database.DatabaseHelper;
import com.example.saubhagyam.myapplication.model.ContactModel;
import com.example.saubhagyam.myapplication.util.Config;
import com.l4digital.fastscroll.FastScrollRecyclerView;
import com.l4digital.fastscroll.FastScroller;

import java.util.ArrayList;

public class GetAllContactFragment extends Fragment {
    private static final String TAG = "GetAllContactFragment";
    static int count = 0;
    View mView;

    ArrayList<String> StoreContacts;
    RecyclerView mRecyclerView;
    ProgressDialog mProgressDialog;
    //SearchView txtSearch;
    FastScrollRecyclerView recyclerView;
    ExampleAdapter exampleAdapter;
    SearchView searchView;
    TextView txtSetText;
    ContactModel contactModel;
    //  private ListView listView;
    private CustomAdapter customAdapter;
    private ArrayList<ContactModel> contactModelArrayList;
    private DatabaseHelper db;

    @RequiresApi(api = Build.VERSION_CODES.ECLAIR)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.contact_fragment, container, false);
        initialization();
        return mView;
    }


    @RequiresApi(api = Build.VERSION_CODES.ECLAIR)
    private void initialization() {
        getActivity().setTitle("Contact");
        db = new DatabaseHelper(getActivity());
        contactModelArrayList = new ArrayList<>();
        recyclerView = mView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setFastScrollEnabled(true);

        getAllContacts();


    }

    @Override
    public void onResume() {
        super.onResume();
        Config.visibility="0";
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        MenuItem menuItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        Log.e(TAG, "onCreateOptionsMenu: ");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.e(TAG, "onQueryTextSubmit: ");
                filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.e(TAG, "onQueryTextChange: ");
                filter(newText);
                return true;
            }
        });

    }

    private void filter(String s) {
        try
        {
            ArrayList<ContactModel> temp = new ArrayList();
            for (ContactModel d : contactModelArrayList) {

                if (d.getName().contains(s)) {
                    temp.add(d);

                }
            }
            exampleAdapter.updateList(temp);

        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }


    @RequiresApi(api = Build.VERSION_CODES.ECLAIR)
    private void getAllContacts() {

        try {
            Cursor phones = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
            while (phones.moveToNext()) {
                String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                ContactModel contactModel = new ContactModel();
                contactModel.setName(name);
                contactModel.setNumber(phoneNumber);
                contactModelArrayList.add(contactModel);

                //  db.insertNote(name,phoneNumber);
                //  Log.d("name>>", name + "  " + phoneNumber);

            }
            phones.close();

        }catch (Exception e)
        {
            e.printStackTrace();
        }


       // getFragmentManager().beginTransaction().detach(GetAllContactFragment.this).attach(GetAllContactFragment.this).commit();
        exampleAdapter = new ExampleAdapter(getActivity(), contactModelArrayList);
        recyclerView.setAdapter(exampleAdapter);

    }



}
