package com.example.saubhagyam.myapplication.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.ContactsContract;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saubhagyam.myapplication.R;
import com.example.saubhagyam.myapplication.model.ContactModel;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.saubhagyam.myapplication.adepter.CustomAdapterBackground.themecoloor;


public class DialCallActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "DialCallActivity";
    TextView text3;
    ImageView btnCallReject, btnSpeaker, btnDialer, btnMicroPhone, btnTimer, btnContact;
    TextView txtNumber, txtName;
    boolean isOn = true;
    long starttime = 0;
    private ArrayList<ContactModel> contactModelArrayList;
    android.support.v4.app.FragmentTransaction ft;
    ContactModel contactModel;

    static ImageView imageView;

    public static final int REQ_CODE = 1;

    //timer
    private Chronometer chronometer;
    private boolean isStart;

/*    //for name print
    private DatabaseHelper db;

    private List<Note> notesList = new ArrayList<>();*/

    String channel;

    LinearLayout relativeLayout1;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";

    Handler h = new Handler();
    Runnable run = new Runnable() {

        @Override
        public void run() {
            long millis = System.currentTimeMillis() - starttime;
            int seconds = (int) (millis / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;

            text3.setText(String.format("%d:%02d", minutes, seconds));

            h.postDelayed(this, 500);
        }

    };
    Timer timer = new Timer();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outgoingcalll);

        relativeLayout1 = findViewById(R.id.backgroundoutgoing);

        imageView = findViewById(R.id.imageView);




        //code for timer
        chronometer = findViewById(R.id.chronometer);
        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometerChanged) {
                chronometer = chronometerChanged;
            }
        });
        startStopChronometer();




        sharedpreferences = getApplicationContext().getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        channel = (sharedpreferences.getString(themecoloor, ""));



        initialization();
        BackgroundImageChange();

    }

    public void startStopChronometer(){
        if(isStart){
            chronometer.stop();
            isStart = false;

        }else{
            chronometer.setBase(SystemClock.elapsedRealtime());
            chronometer.start();
            isStart = true;

        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.ECLAIR)
    private void getAllContacts() {

        Cursor phones = getApplicationContext().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        while (phones.moveToNext()) {
            String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            String uri = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));

             contactModel = new ContactModel();
            contactModel.setName(name);
            contactModel.setNumber(phoneNumber);
            contactModel.setUri(uri);
            contactModelArrayList.add(contactModel);

            System.out.println("contactModel.setName" + name);
            System.out.println("contactModel.setNumber" + phoneNumber);
            System.out.println("contactModel.setUri" + uri);

        }
        phones.close();

    }

    private void BackgroundImageChange() {
        final int sdk = android.os.Build.VERSION.SDK_INT;




            if (channel.equals("") || channel.equals(" ")) {
                if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    relativeLayout1.setBackgroundDrawable(getResources().getDrawable(R.drawable.back1));
                } else {
                    relativeLayout1.setBackground(getResources().getDrawable(R.drawable.back1));
                }

            } else if (channel.equals("1")) {
                if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    relativeLayout1.setBackgroundDrawable(getResources().getDrawable(R.drawable.back1));
                } else {
                    relativeLayout1.setBackground(getResources().getDrawable(R.drawable.back1));
                }

            } else if (channel.equals("2")) {
                if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    relativeLayout1.setBackgroundDrawable(getResources().getDrawable(R.drawable.back2));
                } else {
                    relativeLayout1.setBackground(getResources().getDrawable(R.drawable.back2));
                }
            } else if (channel.equals("3")) {
                if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    relativeLayout1.setBackgroundDrawable(getResources().getDrawable(R.drawable.back3));
                } else {
                    relativeLayout1.setBackground(getResources().getDrawable(R.drawable.back3));
                }
            } else if (channel.equals("4")) {
                if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    relativeLayout1.setBackgroundDrawable(getResources().getDrawable(R.drawable.back4));
                } else {
                    relativeLayout1.setBackground(getResources().getDrawable(R.drawable.back4));
                }
            } else if (channel.equals("5")) {
                if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    relativeLayout1.setBackgroundDrawable(getResources().getDrawable(R.drawable.back5));
                } else {
                    relativeLayout1.setBackground(getResources().getDrawable(R.drawable.back5));
                }
            } else if (channel.equals("6")) {
                if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    relativeLayout1.setBackgroundDrawable(getResources().getDrawable(R.drawable.back6));
                } else {
                    relativeLayout1.setBackground(getResources().getDrawable(R.drawable.back6));
                }
            } else if (channel.equals("7")) {
                if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    relativeLayout1.setBackgroundDrawable(getResources().getDrawable(R.drawable.back7));
                } else {
                    relativeLayout1.setBackground(getResources().getDrawable(R.drawable.back7));
                }
            } else if (channel.equals("8")) {
                if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    relativeLayout1.setBackgroundDrawable(getResources().getDrawable(R.drawable.back8));
                } else {
                    relativeLayout1.setBackground(getResources().getDrawable(R.drawable.back8));
                }
            }


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
        BitmapDrawable ob = new BitmapDrawable(getResources(), photo);
        imageView.setBackgroundDrawable(ob);

        Log.d(TAG, "retrieveContactPhoto: "+photo);
        return photo;
    }


    private void initialization() {

        contactModelArrayList = new ArrayList<>();

        txtNumber = findViewById(R.id.txtNumberOutgoing);
        txtName = findViewById(R.id.txtNameOutgoing);
        btnCallReject = (ImageView) findViewById(R.id.btnCallReject);
        btnCallReject.setOnClickListener(this);
        btnDialer = (ImageView) findViewById(R.id.btnDialer);
        btnDialer.setOnClickListener(this);
        btnSpeaker = (ImageView) findViewById(R.id.btnSpeaker);
        btnSpeaker.setOnClickListener(this);
        btnMicroPhone = (ImageView) findViewById(R.id.btnMicro_phone);
        btnMicroPhone.setOnClickListener(this);
        btnContact = (ImageView) findViewById(R.id.btnContact);
        btnContact.setOnClickListener(this);

      //  text3 = (TextView) findViewById(R.id.txtCountTime);


        Intent intent = getIntent();

        txtNumber.setText(intent.getStringExtra("number"));


        //get image in bitmap
        //retrieveContactPhoto(this,intent.getStringExtra("number"));

        String temp = intent.getStringExtra("number").trim();
        String temp1 = intent.getStringExtra("number").trim();

        temp = "+91" + temp;
        temp1 = temp1.substring(3);

        //Code for Get Name

        getAllContacts();

        for (int i = 0; i < contactModelArrayList.size(); i++) {

            if (contactModelArrayList.get(i).getNumber().trim().replaceAll("\\s+", "").equals(intent.getStringExtra("number").trim()) || contactModelArrayList.get(i).getNumber().trim().replaceAll("\\s+", "").equals(temp) || contactModelArrayList.get(i).getNumber().trim().replaceAll("\\s+", "").equals(temp1)) {
                txtName.setText(contactModelArrayList.get(i).getName());
            }

        }

      /*  //for name print
        notesList.addAll(db.getAllNotes());
        for (int i = 0; i < notesList.size(); i++) {
            if (notesList.get(i).getNumber().trim().equalsIgnoreCase(intent.getStringExtra("number").trim())) {
                Log.e(TAG, "initialization: ======>" + notesList.get(i).getName());
                txtName.setText(notesList.get(i).getName());
                break;
            }
        }*/


        //    btnTimer = (Button) findViewById(R.id.btnClick);

      /*  btnTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                starttime = System.currentTimeMillis();
                timer = new Timer();
                timer.schedule(new firstTask(), 0, 500);
                h.postDelayed(run, 0);


            }
        });*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnMicro_phone:
                microPhone();
                break;
            case R.id.btnCallReject:
                CallReject();
                break;
            case R.id.btnSpeaker:
                speakerOnOff();
                break;
            case R.id.btnDialer:
                onpenDialer();
                break;
            case R.id.btnContact:
                getContact();
                break;
        }
    }

    private void microPhone() {

        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        assert audioManager != null;
        audioManager.setMode(AudioManager.MODE_IN_CALL);

        if (!audioManager.isMicrophoneMute()) {
            audioManager.setMicrophoneMute(true);

            btnMicroPhone.setBackgroundResource(R.drawable.imagesroundblue);
            btnMicroPhone.setImageResource(R.drawable.unmuted);

            Log.e(TAG, "microphone on ");
        } else {
            audioManager.setMicrophoneMute(false);
            btnMicroPhone.setBackgroundResource(R.drawable.imagesround);
            btnMicroPhone.setImageResource(R.drawable.muted);
            Log.e(TAG, "microphone off");

        }

    }

    private void onpenDialer() {


        //open main activity dialer fragment
        Intent in = new Intent(this, MainActivity.class);
        startActivityForResult(in, REQ_CODE);


        /*btnDialer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               *//* android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, new DialerFragment());
                ft.commit();*//*
            }
        });*/
    }

    private void getContact() {

        Intent in = new Intent(this, MainActivity.class);
        in.putExtra("frag", "Contact");
        setResult(RESULT_OK, in);
        startActivityForResult(in, REQ_CODE);



    /*    android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, new GetAllContactFragment());
        ft.commit();*/
    }

    /*public void replaceFragment(Fragment newFragment, boolean backStackTag, String fragmentTag) {
        try {
            Fragment selectedFrag = getSupportFragmentManager().findFragmentByTag(fragmentTag);
            if (selectedFrag != null && selectedFrag.isVisible()) {
                return;
            }
            FragmentManager fm = DialCallActivity.this.getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.container, GetAllContactFragment).commit();
        } catch (Exception e) {
            Log.e("exception ", " on adding fragment");
            // TODO: handle exception
        }
    }*/

    private void speakerOnOff() {

        if (isOn) {
            isOn = false;
            AudioManager audioManager = (AudioManager)
                    getSystemService(Context.AUDIO_SERVICE);
            audioManager.setMode(AudioManager.MODE_IN_CALL);
            audioManager.setSpeakerphoneOn(true);
            btnSpeaker.setBackgroundResource(R.drawable.imagesroundblue);

            btnSpeaker.setImageResource(R.drawable.speakerblue);

            Toast.makeText(DialCallActivity.this, "Speaker On", Toast.LENGTH_SHORT).show();
        } else {
            isOn = true;
            AudioManager audioManager = (AudioManager)
                    getSystemService(Context.AUDIO_SERVICE);
            audioManager.setMode(AudioManager.MODE_IN_CALL);
            audioManager.setSpeakerphoneOn(false);
            btnSpeaker.setImageResource(R.drawable.speakerwhite);
            btnSpeaker.setBackgroundResource(R.drawable.imagesround);
            Toast.makeText(DialCallActivity.this, "Speaker Off", Toast.LENGTH_SHORT).show();

        }
    }

    private void setOutgoingNumber() {
        Intent intent = getIntent();
        txtNumber.setText(intent.getStringExtra("number"));
    }

    private void CallReject() {

        try {

            TelephonyManager tm = (TelephonyManager)
                    getSystemService(Context.TELEPHONY_SERVICE);
            try {
                Class c = Class.forName(tm.getClass().getName());
                Method m = c.getDeclaredMethod("getITelephony");
                m.setAccessible(true);
                Object telephonyService = m.invoke(tm); // Get the internal ITelephony object
                c = Class.forName(telephonyService.getClass().getName()); // Get its class
                m = c.getDeclaredMethod("endCall"); // Get the "endCall()" method
                m.setAccessible(true); // Make it accessible
                m.invoke(telephonyService); // invoke endCall()
                ActivityManager am = (ActivityManager) getSystemService(Activity.ACTIVITY_SERVICE);
                am.killBackgroundProcesses("com.example.saubhagyam.myapplication");

                DialCallActivity.this.finish();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {

        }

    }

    class firstTask extends TimerTask {

        @Override
        public void run() {
            h.sendEmptyMessage(0);
        }
    }
}