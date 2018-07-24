package com.example.saubhagyam.myapplication.fragment;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Contacts;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saubhagyam.myapplication.R;
import com.example.saubhagyam.myapplication.adepter.CustomAdapter;
import com.example.saubhagyam.myapplication.adepter.ExampleAdapter;
import com.example.saubhagyam.myapplication.database.DatabaseHelper;
import com.example.saubhagyam.myapplication.model.ContactModel;
import com.example.saubhagyam.myapplication.util.Config;
import com.l4digital.fastscroll.FastScrollRecyclerView;
import com.l4digital.fastscroll.FastScroller;
import com.libRG.CustomTextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class GetAllContactFragment extends Fragment {
    private static final String TAG = "GetAllContactFragment";
    static int count = 0;
    View mView;
    CustomTextView txtFirstCharecter;
    ImageView imageView;

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



    String phoneNumber;
    int flag = 0;

    @RequiresApi(api = Build.VERSION_CODES.ECLAIR)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.contact_fragment, container, false);

        txtFirstCharecter = mView.findViewById(R.id.txtFirstCharecter);
        imageView = mView.findViewById(R.id.imageView);
        initialization();
        return mView;
    }

    /**
     * @return the photo URI
     */
    public  Uri getPhotoUri() {
        try {
            Cursor cur = getActivity().getContentResolver().query(
                    ContactsContract.Data.CONTENT_URI,
                    null,
                    ContactsContract.Data.CONTACT_ID + "=" + contactModel.getId() + " AND "
                            + ContactsContract.Data.MIMETYPE + "='"
                            + ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE + "'", null,
                    null);
            if (cur != null) {
                if (!cur.moveToFirst()) {
                    return null; // no photo
                }
            } else {
                return null; // error in cursor process
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        Uri person = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, Long
                .parseLong(contactModel.getId()));


        Log.e(TAG, "getPhotoUri: "+Uri.withAppendedPath(person, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY) );
        // System.out.println("URI"+Uri.withAppendedPath(person, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY));

        return Uri.withAppendedPath(person, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);
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
                 phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                //String id =phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID));
                String uri = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));

                ContactModel contactModel = new ContactModel();
                contactModel.setName(name);
                contactModel.setNumber(phoneNumber);
                contactModel.setUri(uri);
               // contactModel.setId(id);



                //code for remove duplicate name

                if(contactModelArrayList.size() == 0){
                    contactModelArrayList.add(contactModel);
                }
                for(int i=0;i<contactModelArrayList.size();i++){

                    if(!contactModelArrayList.get(i).getName().trim().equals(name)){
                        flag = 1;

                    }else{
                        flag =0;
                        break;
                    }

                }
                if(flag == 1){
                    contactModelArrayList.add(contactModel);
                }

                //code over  for remove duplicate name

            }
            phones.close();

        }catch (Exception e)
        {
            e.printStackTrace();
        }


        //method call for image get
       // exampleAdapter.retrieveContactPhoto(getActivity(),phoneNumber);

     /*   for (int i=0 ; i <contactModelArrayList.size(); i++)
        {
            if (contactModel.getUri() != null)
            {

                Toast.makeText(getActivity(), "IF", Toast.LENGTH_SHORT).show();
              //  imageView.setVisibility(View.VISIBLE);
            }
            else
            {
                Toast.makeText(getActivity(), "ELSE", Toast.LENGTH_SHORT).show();
              //  imageView.setVisibility(View.INVISIBLE);
            }
        }
*/
       // getFragmentManager().beginTransaction().detach(GetAllContactFragment.this).attach(GetAllContactFragment.this).commit();
        exampleAdapter = new ExampleAdapter(getActivity(), contactModelArrayList);
        recyclerView.setAdapter(exampleAdapter);

    }



}
