package com.example.saubhagyam.myapplication.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.example.saubhagyam.myapplication.R;
import com.example.saubhagyam.myapplication.fragment.DialerFragment;
import com.example.saubhagyam.myapplication.fragment.Favourite;
import com.example.saubhagyam.myapplication.fragment.GetAllContactFragment;
import com.example.saubhagyam.myapplication.fragment.RecentsFragment;
import com.example.saubhagyam.myapplication.fragment.SettingFragment;
import com.example.saubhagyam.myapplication.util.Config;

import java.util.Objects;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    LinearLayout imgDaDialer, imgContact, imgRecents, imgFavourites, imgSettings;
    ProgressDialog progressDialog;
    BottomNavigationView mBottomNavigationView;
    FragmentTransaction ft;
    String frag="abc";
    Toolbar toolbar;
    private Menu menu;
    SearchView searchView;

  /*  private static final String INSTALL_SCRIPT =
            "mount -o rw,remount /system\n" +
                    "cat %s > /system/priv-app/RemoteDroid.apk.tmp\n" +
                    "chmod 644 /system/priv-app/RemoteDroid.apk.tmp\n" +
                    "pm uninstall %s\n" +
                    "mv /system/priv-app/RemoteDroid.apk.tmp /system/priv-app/RemoteDroid.apk\n" +
                    "pm install -r /system/priv-app/RemoteDroid.apk\n" +
                    "sleep 5\n" +
                    "am start -n in.tosc.remotedroid.app/.MainActivity";
*/


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Run Time Permission

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{android.Manifest.permission.CAMERA, android.Manifest.permission.READ_CONTACTS, android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.RECORD_AUDIO, android.Manifest.permission.READ_SMS, android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.CALL_PHONE, android.Manifest.permission.CALL_PRIVILEGED}, 100);
        }


      /*  String.format(INSTALL_SCRIPT, new String[] {
                MainActivity.this.getPackageCodePath(),
                MainActivity.this.getPackageName()
        });
*/


 /*       Shell.SU.run(String.format(INSTALL_SCRIPT, new String[] {
                MainActivity.this.getPackageCodePath(),
                MainActivity.this.getPackageName()
        }));*/
        try {
            Intent intent = getIntent();

            frag = Objects.requireNonNull(intent.getExtras()).getString("frag");
            Log.e(TAG, "Fragment: " + frag);

        } catch (Exception e) {
            e.printStackTrace();
        }



/*        // check if we already  have permission to draw over other apps
        if (!Settings.canDrawOverlays(this)) { // WHAT IF THIS EVALUATES TO FALSE.
            // if not construct intent to request permission
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            // request permission via start activity for result
            initiliazation();
        } else { // ADD THIS.
            // Add code to bind and start the service directly.
            try {
                Settings.canDrawOverlays(this);
            }
            catch(NoSuchMethodError e){
                 e.printStackTrace();
            }
        }*/

        // initiliazation();


     /*   TelephonyManager telephonyManager =
                (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);

        PhoneStateListener callStateListener = new PhoneStateListener() {
            public void onCallStateChanged(int state, String incomingNumber)
            {
                if(state==TelephonyManager.CALL_STATE_RINGING){
                    Toast.makeText(getApplicationContext(),"Phone Is Riging",
                            Toast.LENGTH_LONG).show();
                }
                if(state==TelephonyManager.CALL_STATE_OFFHOOK){
                    Toast.makeText(getApplicationContext(),"Phone is Currently in A call",
                            Toast.LENGTH_LONG).show();
                }

                if(state==TelephonyManager.CALL_STATE_IDLE){
                    Toast.makeText(getApplicationContext(),"phone is neither ringing nor in a call",
                            Toast.LENGTH_LONG).show();
                }
            }
        };
        telephonyManager.listen(callStateListener,PhoneStateListener.LISTEN_CALL_STATE);*/

    }


    /*public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, new GetAllContactFragment());
                ft.commit();
            }
        }
    }*/

    @Override
    protected void onStart() {
        super.onStart();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) { // WHAT IF THIS EVALUATES TO FALSE.
                // if not construct intent to request permission
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                // request permission via start activity for result
                startActivity(intent);
                initiliazation();
            } else { // ADD THIS.

                initiliazation();
                // Add code to bind and start the service directly.
               /* try {
                    Settings.canDrawOverlays(this);
                }
                catch(NoSuchMethodError e){
                    e.printStackTrace();
                }*/
            }
        }

        /* initiliazation();*/
    }


    private void initiliazation() {
        //   toolbar = (Toolbar) findViewById(R.id.toolbar);
        progressDialog = new ProgressDialog(MainActivity.this);
        mBottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);

        //when app start by default selected Contect menu



        try {
            if(frag.equals("Contact")){
                mBottomNavigationView.getMenu().getItem(1).setChecked(true);
                ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, new GetAllContactFragment());
                ft.commit();
                itemselect();
            }else {
                mBottomNavigationView.getMenu().getItem(2).setChecked(true);
                ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, new DialerFragment());
                ft.commit();
                itemselect();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

       if (Config.visibility.equals("0")) {
            inflater.inflate(R.menu.menu, menu);
            this.menu = menu;
            MenuItem menuItem = menu.findItem(R.id.action_search);
            searchView = (SearchView) MenuItemCompat.getActionView(menuItem);


        }
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_search:
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void itemselect() {
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_contacts:
                        Log.e(TAG, "3 ");
                        ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.content_frame, new GetAllContactFragment());
                        ft.commit();
                        break;
                   /* case R.id.action_favorites:
                        Log.e(TAG, "1 ");
                        searchView.setVisibility(View.GONE);
                        menu.findItem(R.id.action_search).setVisible(false);
                        ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.content_frame, new Favourite());
                        ft.commit();

                        *//*    overFlow menu    setIcon dynamic
                      menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.accept_call));*//*
                        break;*/
                    case R.id.action_recents:
                        Log.e(TAG, "2 ");
                        ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.content_frame, new RecentsFragment());
                        ft.commit();
                        break;

                    case R.id.action_keypad:
                        Log.e(TAG, "4 ");
                        ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.content_frame, new DialerFragment());
                        ft.commit();
                        break;
                    case R.id.action_settings:
                        ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.content_frame, new SettingFragment());
                        ft.commit();
                        Log.e(TAG, "5 ");
                        break;
                }
                return true;
            }
        });

    }
}

