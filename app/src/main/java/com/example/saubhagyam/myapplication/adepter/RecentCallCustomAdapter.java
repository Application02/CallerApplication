package com.example.saubhagyam.myapplication.adepter;

import android.Manifest;
import android.annotation.SuppressLint;
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
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.saubhagyam.myapplication.R;
import com.example.saubhagyam.myapplication.model.RecentCallModel;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class RecentCallCustomAdapter extends BaseAdapter {
    private static final String TAG = "RecentCallCustomAdapter";
    private Context context;
   ArrayList<RecentCallModel> contactModelArrayList;
    BitmapDrawable ob;

    public RecentCallCustomAdapter(Context context, ArrayList<RecentCallModel> contactModelArrayList) {

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

    @SuppressLint({"InflateParams", "SetTextI18n"})
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert inflater != null;
            convertView = inflater.inflate(R.layout.recent_call_row, null, true);
            holder.txtFirstChar =  convertView.findViewById(R.id.txtFirstCharecter1);
            holder.txtNumber =  convertView.findViewById(R.id.txtRecentnumber);
            holder.txtName =  convertView.findViewById(R.id.txtRecentName);
            holder.txtTime =  convertView.findViewById(R.id.txtTimeAndDate);
            holder.txtCallTimeDuration =  convertView.findViewById(R.id.txtCallTimeDuration);
            holder.linearLayout =  convertView.findViewById(R.id.linearLayoutCall);
            holder.imgCallType =  convertView.findViewById(R.id.imgCallType);

            holder.imageView2 = (CircleImageView) convertView.findViewById(R.id.imageview2);
            holder.txtFirstChar.setBackgroundColor(getMatColor());
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String callInfo = "tel:" + contactModelArrayList.get(position).getNumber();
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse(callInfo));
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }
                context.startActivity(callIntent);

            }
        });
        holder.txtCallTimeDuration.setText(contactModelArrayList.get(position).getCallduration());
        holder.txtTime.setText(contactModelArrayList.get(position).getTime());
        holder.txtNumber.setText(contactModelArrayList.get(position).getNumber());
        holder.txtName.setText(contactModelArrayList.get(position).getName());

        if (contactModelArrayList.get(position).getName()!=null)
        {
            holder.txtFirstChar.setText((contactModelArrayList.get(position).getName().toUpperCase().charAt(0) + ""));
        }


        Log.d(TAG, "getView In Adaper: "+contactModelArrayList.get(position).getUri());


        if (contactModelArrayList.get(position).getUri() != null) {
            retrieveContactPhoto(context, contactModelArrayList.get(position).getNumber());

            holder.txtFirstChar.setVisibility(View.GONE);

            holder.imageView2.setVisibility(View.VISIBLE);
            holder.imageView2.setImageDrawable(ob);

        } else {
            holder.txtFirstChar.setVisibility(View.VISIBLE);
            holder.imageView2.setVisibility(View.GONE);
          //  holder.txtFirstChar.setText((contactModelArrayList.get(position).getName().toUpperCase().charAt(0) + ""));
        }





        holder.txtFirstChar.setBackgroundColor(getMatColor());
        Log.e(TAG, "getView: "+ contactModelArrayList.get(position).getNumber());

        if (holder.txtName.getText().length() == 0)
        {
            holder.txtName.setText("UnKnown");
        }

/*        if (contactModelArrayList.get(position).getNumber() == null)
        {
            holder.txtNumber.setText("No Number Found!");
        }*/


/*        try {
            holder.txtFirstChar.setText(contactModelArrayList.get(position).getName().toUpperCase().charAt(0) + "");

        } catch (Exception e) {

        }*/
        try {
            switch (contactModelArrayList.get(position).getCalltype()) {
                case "2":
                    holder.imgCallType.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_list_outgoing));
                    break;
                case "1":
                    holder.imgCallType.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_list_incoming));
                    break;
                default:
                    holder.imgCallType.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_missedcall));
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return convertView;
    }

    private int getMatColor() {

        int returnColor = Color.BLACK;
        int arrayId = context.getResources().getIdentifier("mdcolor_" + "600", "array", context.getPackageName());

        if (arrayId != 0) {
            TypedArray colors = context.getResources().obtainTypedArray(arrayId);
            int index = (int) (Math.random() * colors.length());
            returnColor = colors.getColor(index, Color.BLACK);
            colors.recycle();
        }
        return returnColor;
    }


    private class ViewHolder {
        LinearLayout linearLayout;
        TextView txtFirstChar, txtNumber, txtName, txtTime, txtCallTimeDuration;
        CircleImageView imageView2;
        protected ImageView imgCallType;
    }


}