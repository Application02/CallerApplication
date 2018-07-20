package com.example.saubhagyam.myapplication.services;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;


import java.lang.reflect.Method;


public class PhoneStateReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        try {

            // Instantiating TelephonyManager
        /*    TelephonyManager telephony = (TelephonyManager)
                    context.getSystemService(Context.TELEPHONY_SERVICE);

            // Registering the telephony to listen CALL STATE change
            telephony.listen(new PhoneStateListener(), PhoneStateListener.LISTEN_CALL_STATE);*/

            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);



            if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                ComponentName componentName = new ComponentName("com.example.saubhagyam.myapplication", "com.example.saubhagyam.myapplication.activity.IncomingCallActivity");

                    Intent i = new Intent(Intent.ACTION_MAIN);
                    i.setComponent(componentName);
                    i.addCategory(Intent.CATEGORY_LAUNCHER);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra("mNumber", incomingNumber);


                    context.startActivity(i);

                Toast.makeText(context, "Incoming Call State", Toast.LENGTH_SHORT).show();
              //  Toast.makeText(context, "Ringing State Number is -" + incomingNumber, Toast.LENGTH_SHORT).show();

            }
            if ((state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK))) {

                ComponentName componentName = new ComponentName("com.example.saubhagyam.myapplication", "com.example.saubhagyam.myapplication.activity.DialCallActivity");

                Intent i = new Intent(Intent.ACTION_MAIN);
                i.setComponent(componentName);
                i.addCategory(Intent.CATEGORY_LAUNCHER);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("number", incomingNumber);
                System.out.println("OutgoingNumber123"+incomingNumber);
                context.startActivity(i);
                Toast.makeText(context, "Call Received State", Toast.LENGTH_SHORT).show();
            }
            if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
                Toast.makeText(context, "Call Idle State", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}