/*
 * Copyright 2017 L4 Digital LLC. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.saubhagyam.myapplication.adepter;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.saubhagyam.myapplication.R;
import com.example.saubhagyam.myapplication.model.ContactModel;
import com.l4digital.fastscroll.FastScroller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ViewHolder> implements FastScroller.SectionIndexer {
    ArrayList<ContactModel> contactModel;
    LayoutInflater inflater;
    String Name = "A";
    ViewHolder holder;
    private Context context;
    BitmapDrawable ob;




    public ExampleAdapter(FragmentActivity context, ArrayList<ContactModel> contactModels) {
        this.contactModel = contactModels;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(parent.getContext());




        return new ViewHolder(inflater.inflate(R.layout.single_contact_view, parent, false));
    }

    public Bitmap retrieveContactPhoto(Context context, String number) {
        ContentResolver contentResolver = context.getContentResolver();
        String contactId = null;
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(number));

        String[] projection = new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME, ContactsContract.PhoneLookup._ID};

        Cursor cursor =
                contentResolver.query(
                        uri,
                        projection,
                        null,
                        null,
                        null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                contactId = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.PhoneLookup._ID));
            }
            cursor.close();
        }

        Bitmap photo = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.back6);

        try {
            InputStream inputStream = ContactsContract.Contacts.openContactPhotoInputStream(context.getContentResolver(),
                    ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, new Long(contactId)));

            if (inputStream != null) {
                photo = BitmapFactory.decodeStream(inputStream);
            }

            assert inputStream != null;
            inputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        ob = new BitmapDrawable(context.getResources(), photo);
       // imageview1.setBackgroundDrawable(ob);

        //Log.d(TAG, "retrieveContactPhoto: "+photo);
        return photo;
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.txtName.setText(contactModel.get(position).getName());

        if (contactModel.get(position).getUri() != null) {
            retrieveContactPhoto(context, contactModel.get(position).getNumber());

            holder.txtFirstChar.setVisibility(View.GONE);

            holder.imageView.setVisibility(View.VISIBLE);
            holder.imageView.setImageDrawable(ob);

        } else {
            holder.txtFirstChar.setVisibility(View.VISIBLE);
            holder.imageView.setVisibility(View.GONE);
            holder.txtFirstChar.setText((contactModel.get(position).getName().toUpperCase().charAt(0) + ""));
        }




        holder.txtFirstChar.setBackgroundColor(getMatColor("600"));
        holder.linearLayoutCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String callInfo = "tel:" + contactModel.get(position).getNumber();
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse(callInfo));
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }
                context.startActivity(callIntent);

            }
        });
    }


    @Override
    public int getItemCount() {
        return contactModel.size();
    }

    @Override
    public String getSectionText(int position) {

        return String.valueOf(contactModel.get(position).getName().charAt(0));
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

    public void updateList(ArrayList<ContactModel> list) {
        contactModel = list;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtFirstChar, mTextView, txtName, txtSetFirst;
        LinearLayout linearLayoutCall;
        //ImageView imageView;
        CircleImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.txtView);
            txtFirstChar = (TextView) itemView.findViewById(R.id.txtFirstCharecter);
           // imageView = (ImageView) itemView.findViewById(R.id.imageview1);
             imageView = (CircleImageView) itemView.findViewById(R.id.imageview1);


            txtName = (TextView) itemView.findViewById(R.id.name);
            linearLayoutCall = (LinearLayout) itemView.findViewById(R.id.linearLayoutCall);
        }

    }
}
