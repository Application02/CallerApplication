package com.example.saubhagyam.myapplication.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.saubhagyam.myapplication.R;
import com.example.saubhagyam.myapplication.adepter.RecentCallCustomAdapter;
import com.example.saubhagyam.myapplication.model.RecentCallModel;
import com.example.saubhagyam.myapplication.util.Config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class RecentsFragment extends Fragment {
    private static final String TAG = "RecentsFragment";
    String callDate;
    View mView;
    private RecentCallCustomAdapter callCustomAdapter;
    private ArrayList<RecentCallModel> recentCallModels;
    private ListView mListViewRecentCall;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.recents_fragment, container, false);
        initilization();
        return mView;
    }

    private void initilization() {
        getActivity().setTitle("Recent");
        parseDateToddMMyyyy();
        mListViewRecentCall = (ListView) mView.findViewById(R.id.listViewRecentCall);
        mListViewRecentCall.setFastScrollEnabled(true);
        recentCallModels = new ArrayList<>();

        gatRecentsCallList();
    }

    private void gatRecentsCallList() {
        Date date_txt = null;
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        Cursor cursor1 = getActivity().managedQuery(
                ContactsContract.Contacts.CONTENT_URI, null, "starred=?",
                new String[] {"1"}, null);
        Cursor cursor = getActivity().getContentResolver().query(android.provider.CallLog.Calls.CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()) {
            String Name = cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME));
            String Number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_FORMATTED_NUMBER));
            int date = cursor.getColumnIndex(CallLog.Calls.DATE);
            int CallType = cursor.getColumnIndex(CallLog.Calls.TYPE);
            int duration = cursor.getColumnIndex(CallLog.Calls.DURATION);
            callDate = cursor.getString(date);

            Date callDayTime = new Date(Long.valueOf(callDate));

            String callType = cursor.getString(CallType);
            String callDuration = cursor.getString(duration);
            String mDate = String.valueOf(callDayTime);

            String mMonthAndDay = mDate.substring(4, 10);//month and day
            String mTime = mDate.substring(11, 16);//time
            // Call Duration Time
            int remainder = Integer.parseInt(callDuration) % 3600;
            int minutes = remainder / 60;
            int seconds = remainder % 60;
            String mins = (minutes < 10 ? "0" : "") + minutes;
            String secs = (seconds < 10 ? "0" : "") + seconds;
            String formattedTime = mins + "m" + ":" + secs + "s";
           /* Log.w(TAG, "Month and Day: -->" + mMonthAndDay + " Time-->" + mTime);
            Log.e(TAG, "callDurationtime " + callDuration);
            Log.e(TAG, "getAllContacts: " + Name + "--" + Number + "--" + callDayTime + "--" + callDuration + "--" + callType);*/
            RecentCallModel recentCallModel = new RecentCallModel();
            recentCallModel.setName(Name);
            recentCallModel.setNumber(Number);
            recentCallModel.setTime(mMonthAndDay + " " + mTime);
            recentCallModel.setCallduration(formattedTime);

            String dir = null;
            int dircode = Integer.parseInt(callType);

            switch (dircode) {
                case CallLog.Calls.OUTGOING_TYPE:
                    dir = "OUTGOING";
                   // Log.e(TAG, "OUTGOING " + dircode);
                    recentCallModel.setCalltype(String.valueOf(dircode));
                    break;

                case CallLog.Calls.INCOMING_TYPE:
                    dir = "INCOMING";
                   // Log.e(TAG, "INCOMING " + dircode);
                    recentCallModel.setCalltype(String.valueOf(dircode));
                    break;

                case CallLog.Calls.MISSED_TYPE:
                    dir = "MISSED";
                   // Log.e(TAG, "MISSED " + dircode);
                    recentCallModel.setCalltype(String.valueOf(dircode));
                    break;
            }
            recentCallModels.add(recentCallModel);

        }

        Collections.reverse(recentCallModels);
        callCustomAdapter = new RecentCallCustomAdapter(getActivity(), recentCallModels);
        mListViewRecentCall.setAdapter(callCustomAdapter);


    }

    @Override
    public void onResume() {
        super.onResume();
        Config.visibility="1";
    }


    private void parseDateToddMMyyyy() {
        String date = "Sat May 26 18:49:28 GMT+05:30 2018";
        String mMonthAndDay = date.substring(4, 10);//month and day
        String mTime = date.substring(11, 16);
        Log.w(TAG, "Month and Day: -->" + mMonthAndDay + " Time-->" + mTime);


        int remainder = 158 % 3600;
        int minutes = remainder / 60;
        int seconds = remainder % 60;
        String mins = (minutes < 10 ? "0" : "") + minutes;
        String secs = (seconds < 10 ? "0" : "") + seconds;
        String formattedTime = mins + "m" + ":" + secs + "s";
        Log.e(TAG, "Duration-->: " + formattedTime);
      /*  Log.e(TAG, "parseDateToddMMyyyy: " );
        SimpleDateFormat sdf3 = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);

        Date d1 = null;
        try{

            d1 = sdf3.parse("Wed Mar 30 00:00:00 GMT+05:30 2016");

        }catch (Exception e){
            Log.e(TAG, "error---: "+e );
            e.printStackTrace(); }


        System.out.println("check..." + d1);*/
    }

}
