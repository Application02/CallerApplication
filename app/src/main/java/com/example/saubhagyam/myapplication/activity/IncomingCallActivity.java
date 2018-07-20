package com.example.saubhagyam.myapplication.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;
import android.text.Selection;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import com.example.saubhagyam.myapplication.R;
import com.example.saubhagyam.myapplication.adepter.ExampleAdapter;
import com.example.saubhagyam.myapplication.database.DatabaseHelper;
import com.example.saubhagyam.myapplication.database.Note;
import com.example.saubhagyam.myapplication.model.ContactModel;

import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static com.example.saubhagyam.myapplication.adepter.CustomAdapterBackground.themecoloor;


@TargetApi(Build.VERSION_CODES.O)
@SuppressLint("WrongConstant")
@RequiresApi(api = Build.VERSION_CODES.M)
public class IncomingCallActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    ImageView btnCallAnswer, btnCallReject;
    String enforcedPerm = "android.permission.CALL_PRIVILEGED";
    TextView txtNumber, txtName;
    public static final int MULTIPLE_PERMISSIONS = 100;
    private List<Note> notesList = new ArrayList<>();
    private DatabaseHelper db;
    String channel;
    Intent intent;

    private ArrayList<ContactModel> contactModelArrayList;

    RelativeLayout relativeLayout;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";

    String[] permissions = new String[]{
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.CALL_PRIVILEGED,
            Manifest.permission.READ_CALL_LOG,
            Manifest.permission.PROCESS_OUTGOING_CALLS,
            Manifest.permission.USE_SIP,
            Manifest.permission.ADD_VOICEMAIL,
            Manifest.permission.WRITE_CALL_LOG,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.SEND_SMS,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CONTACTS,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.ANSWER_PHONE_CALLS};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incoming_call);
        relativeLayout = findViewById(R.id.backgroundincoming);

        sharedpreferences = getApplicationContext().getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        channel = (sharedpreferences.getString(themecoloor, ""));

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        BackgroundImageChange();

        initialization();
        call_permissions();





       /* boolean settingsCanWrite = Settings.System.canWrite(IncomingCallActivity.this);

        if(!settingsCanWrite) {
            // If do not have write settings permission then open the Can modify system settings panel.
            Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
            startActivity(intent);
        }else {
            // If has permission then show an alert dialog with message.
            AlertDialog alertDialog = new AlertDialog.Builder(IncomingCallActivity.this).create();
            alertDialog.setMessage("You have system write settings permission now.");
            alertDialog.show();
        }*/

    }


    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.ECLAIR)
    private void getAllContacts() {

        Cursor phones = getApplicationContext().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        while (phones.moveToNext()) {
            String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            ContactModel contactModel = new ContactModel();
            contactModel.setName(name);
            contactModel.setNumber(phoneNumber);
            contactModelArrayList.add(contactModel);


        }
        phones.close();

    }


    private void BackgroundImageChange() {
        final int sdk = android.os.Build.VERSION.SDK_INT;

        if (channel.equals("") || channel.equals(" ")) {
            if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                relativeLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.back6));
            } else {
                relativeLayout.setBackground(getResources().getDrawable(R.drawable.back6));
            }

        } else if (channel.equals("1")) {
            if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                relativeLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.back1));
            } else {
                relativeLayout.setBackground(getResources().getDrawable(R.drawable.back1));
            }

        } else if (channel.equals("2")) {
            if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                relativeLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.back2));
            } else {
                relativeLayout.setBackground(getResources().getDrawable(R.drawable.back2));
            }
        } else if (channel.equals("3")) {
            if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                relativeLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.back3));
            } else {
                relativeLayout.setBackground(getResources().getDrawable(R.drawable.back3));
            }
        } else if (channel.equals("4")) {
            if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                relativeLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.back4));
            } else {
                relativeLayout.setBackground(getResources().getDrawable(R.drawable.back4));
            }
        } else if (channel.equals("5")) {
            if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                relativeLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.back5));
            } else {
                relativeLayout.setBackground(getResources().getDrawable(R.drawable.back5));
            }
        } else if (channel.equals("6")) {
            if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                relativeLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.back6));
            } else {
                relativeLayout.setBackground(getResources().getDrawable(R.drawable.back6));
            }
        } else if (channel.equals("7")) {
            if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                relativeLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.back7));
            } else {
                relativeLayout.setBackground(getResources().getDrawable(R.drawable.back7));
            }
        } else if (channel.equals("8")) {
            if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                relativeLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.back8));
            } else {
                relativeLayout.setBackground(getResources().getDrawable(R.drawable.back8));
            }
        }

    }

    private void call_permissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(getApplicationContext(), p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray
                    (new String[listPermissionsNeeded.size()]), MULTIPLE_PERMISSIONS);
        }
        //return;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MULTIPLE_PERMISSIONS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e(TAG, ":permissions granted ");
                } else {
                    Log.e(TAG, ": No permissions granted ");
                }
                //i  return;
            }
        }
    }

    private void initialization() {
        db = new DatabaseHelper(this);
        contactModelArrayList = new ArrayList<>();


        if (!Settings.canDrawOverlays(this)) {
            Intent localIntent = new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION");
            localIntent.setData(Uri.parse("package:" + getPackageName()));
            localIntent.setFlags(268435456);
            startActivity(localIntent);
        }
        txtNumber = findViewById(R.id.txtNumber);
        txtName = findViewById(R.id.txtName);
        btnCallAnswer = findViewById(R.id.btnCallAnswer);
        btnCallAnswer.setOnClickListener(this);
        btnCallReject = findViewById(R.id.btnReject);
        btnCallReject.setOnClickListener(this);
        intent = getIntent();
        txtNumber.setText(intent.getStringExtra("mNumber"));
        Log.e(TAG, "Number" + intent.getStringExtra("mNumber"));

        String temp = intent.getStringExtra("mNumber").trim();
        String temp1 = intent.getStringExtra("mNumber").trim();

        temp = "+91" + temp;
        temp1 = temp1.substring(3);


        //Code for Get Name

        getAllContacts();

        //String temp = intent.getStringExtra("mNumber");

        for (int i = 0; i < contactModelArrayList.size(); i++) {

            if (contactModelArrayList.get(i).getNumber().trim().replaceAll("\\s+", "").equals(intent.getStringExtra("mNumber").trim()) || (contactModelArrayList.get(i).getNumber().trim().replaceAll("\\s+", "").equals(temp)) || (contactModelArrayList.get(i).getNumber().trim().replaceAll("\\s+", "").equals(temp1))) {
                txtName.setText(contactModelArrayList.get(i).getName());
            }

        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCallAnswer:
                Log.e(TAG, "answer ");
                btnCallAnswer();

                break;
            case R.id.btnReject:
                Log.e(TAG, "reject");
                btnCallReject();
                break;
        }
    }

    public void Notifation_access() {
        if (Settings.Secure.getString(IncomingCallActivity.this.getContentResolver(), "enabled_notification_listeners").contains(getApplicationContext().getPackageName())) {

            // Notification access service already enabled
            Intent intent = new Intent(
                    "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
            startActivity(intent);

        } else {


            Intent intent = new Intent(
                    "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
            startActivity(intent);
        }
    }


    private void btnCallAnswer() {

        try {
            Process proc = Runtime.getRuntime().exec("su");
            DataOutputStream os = new DataOutputStream(proc.getOutputStream());

            os.writeBytes("service call phone 5\n");
            os.flush();

            os.writeBytes("exit\n");
            os.flush();

            if (proc.waitFor() == 255) {

                Toast.makeText(this, "Aceepted call!!!!!!", Toast.LENGTH_LONG).show();
                // TODO handle being declined root access
                // 255 is the standard code for being declined root for SU
            }
        } catch (IOException e) {
            // TODO handle I/O going wrong
            // this probably means that the device isn't rooted
        } catch (InterruptedException e) {
            // don't swallow interruptions
            Thread.currentThread().interrupt();
        }


      /*  final String LOG_TAG = "TelephonyAnswer";

        TelephonyManager tm = (TelephonyManager)
                getSystemService(Context.TELEPHONY_SERVICE);

        try {
            if (tm == null) {
                // this will be easier for debugging later on
                throw new NullPointerException("tm == null");
            }

            // do reflection magic
            tm.getClass().getMethod("answerRingingCall").invoke(tm);
        } catch (Exception e) {
            // we catch it all as the following things could happen:
            // NoSuchMethodException, if the answerRingingCall() is missing
            // SecurityException, if the security manager is not happy
            // IllegalAccessException, if the method is not accessible
            // IllegalArgumentException, if the method expected other arguments
            // InvocationTargetException, if the method threw itself
            // NullPointerException, if something was a null value along the way
            // ExceptionInInitializerError, if initialization failed
            // something more crazy, if anything else breaks

            // TODO decide how to handle this state
            // you probably want to set some failure state/go to fallback
            Log.e(LOG_TAG, "Unable to use the Telephony Manager directly.", e);
        }*/
      /*  try {
            Log.e(TAG, "btnCallReject: ");
            TelephonyManager tm = (TelephonyManager)
                    getSystemService(Context.TELEPHONY_SERVICE);
            try {
                Class c = Class.forName(tm.getClass().getName());
                Method m = c.getDeclaredMethod("getITelephony");
                m.setAccessible(true);
                Object telephonyService = m.invoke(tm); // Get the internal ITelephony object
                c = Class.forName(telephonyService.getClass().getName()); // Get its class
                m = c.getDeclaredMethod("answerRingingCall"); // Get the "endCall()" method
                m.setAccessible(true); // Make it accessible
                m.invoke(telephonyService); // invoke endCall()
                finish();
            } catch (Exception e) {
                e.printStackTrace();
            }


        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "btnCallReject: 3");
        }
*/
       /* TelecomManager tm = (TelecomManager)
                getSystemService(Context.TELECOM_SERVICE);

        if (tm == null) {
            // whether you want to handle this is up to you really
            throw new NullPointerException("tm == null");
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ANSWER_PHONE_CALLS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        tm.acceptRingingCall();

*/
        Toast.makeText(this, "Answer Call", Toast.LENGTH_SHORT).show();

/*        try {
            Runtime.getRuntime().exec("input keyevent " + Integer.toString(KeyEvent.KEYCODE_HEADSETHOOK));
        } catch (IOException e) {
            //handle error here
        }*/


      /*  Intent i = new Intent(Intent.ACTION_MEDIA_BUTTON);
        i.putExtra(Intent.EXTRA_KEY_EVENT, new KeyEvent(KeyEvent.ACTION_UP,
                KeyEvent.KEYCODE_HEADSETHOOK));
       sendOrderedBroadcast(i, "android.permission.CALL_PRIVILEGED");
*/
      //  IncomingCallActivity.this.sendOrderedBroadcast(i, "android.permission.CALL_PRIVILEGED");
              /*  final String LOG_TAG = "TelephonyAnswer";

                TelephonyManager tm = (TelephonyManager)
                        getSystemService(Context.TELEPHONY_SERVICE);

                try {
                    if (tm == null) {
                        // this will be easier for debugging later on
                        throw new NullPointerException("tm == null");
                    }

                    // do reflection magic
                    tm.getClass().getMethod("answerRingingCall").invoke(tm);
                } catch (Exception e) {
                    // we catch it all as the following things could happen:
                    // NoSuchMethodException, if the answerRingingCall() is missing
                    // SecurityException, if the security manager is not happy
                    // IllegalAccessException, if the method is not accessible
                    // IllegalArgumentException, if the method expected other arguments
                    // InvocationTargetException, if the method threw itself
                    // NullPointerException, if something was a null value along the way
                    // ExceptionInInitializerError, if initialization failed
                    // something more crazy, if anything else breaks

                    // TODO decide how to handle this state
                    // you probably want to set some failure state/go to fallback
                    Log.e(LOG_TAG, "Unable to use the Telephony Manager directly.", e);
                }*/
    /*            try {

                    TelephonyManager tm = (TelephonyManager)
                            getSystemService(Context.TELEPHONY_SERVICE);
                    try {
                        Class c = Class.forName(tm.getClass().getName());
                        Method m = c.getDeclaredMethod("getITelephony");
                        m.setAccessible(true);
                        Object telephonyService = m.invoke(tm); // Get the internal ITelephony object
                        c = Class.forName(telephonyService.getClass().getName()); // Get its class
                        m = c.getDeclaredMethod("answerRingingCall"); // Get the "endCall()" method
                        m.setAccessible(true); // Make it accessible
                        m.invoke(telephonyService); // invoke endCall()

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                } catch (Exception e) {

                }*/
    }

    public void acceptRingingCall ()
    {

    }


    private void btnCallReject() {
        Log.e(TAG, "btnCallReject: 1");
        try {
            Log.e(TAG, "btnCallReject: ");
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
                finish();
            } catch (Exception e) {
                e.printStackTrace();
            }


        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "btnCallReject: 3");
        }
    }



}
