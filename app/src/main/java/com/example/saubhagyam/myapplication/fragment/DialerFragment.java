package com.example.saubhagyam.myapplication.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.saubhagyam.myapplication.R;
import com.example.saubhagyam.myapplication.javaclass.CustomTypefaceSpan;
import com.example.saubhagyam.myapplication.util.Config;


public class DialerFragment extends Fragment implements View.OnClickListener {
    EditText edtPhoneNo;
    View mView;
    LinearLayout btnOne, btnTwo, btnThree, btnFour, btnFive, btnSix, btnSeven, btnEight, btnNine, btnZero, btnHash, btnAterisk;
    ImageView btnCall, btnDel;
/*     String s1="1";
       String s2="abc"; */

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.activity_dialer, container, false);
        initiliazation();

/*//code for show button with two text

        int n = s1.length();
        int m = s2.length();

        AssetManager assetManager = getContext().getAssets();
       Typeface customFont = Typeface.createFromAsset(assetManager,"fonts/Segoe.ttf");
        Spannable span = new SpannableString(s1 + "\n" +  s2);
//Big font till you find `\n`
        span.setSpan(new CustomTypefaceSpan("",customFont), 0, n, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//Small font from `\n` to the end
        span.setSpan(new RelativeSizeSpan(0.8f), n, (n+m+1), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        btnOne.setText(span);*/
     return mView;
    }


    @Override
    public void onResume() {
        super.onResume();
        Config.visibility="1";
    }

    private void initiliazation() {
        getActivity().setTitle("Dialer");
        edtPhoneNo = (EditText) mView.findViewById(R.id.edtPhoneNumber);
        btnOne = (LinearLayout ) mView.findViewById(R.id.btnOne);
        btnOne.setOnClickListener(this);
        btnTwo = (LinearLayout ) mView.findViewById(R.id.btnTwo);
        btnTwo.setOnClickListener(this);
        btnThree = (LinearLayout ) mView.findViewById(R.id.btnThree);
        btnThree.setOnClickListener(this);
        btnFour = (LinearLayout ) mView.findViewById(R.id.btnFour);
        btnFour.setOnClickListener(this);
        btnFive = (LinearLayout ) mView.findViewById(R.id.btnFive);
        btnFive.setOnClickListener(this);
        btnSix = (LinearLayout ) mView.findViewById(R.id.btnSix);
        btnSix.setOnClickListener(this);
        btnSeven = (LinearLayout ) mView.findViewById(R.id.btnSeven);
        btnSeven.setOnClickListener(this);
        btnEight = (LinearLayout ) mView.findViewById(R.id.btnEight);
        btnEight.setOnClickListener(this);
        btnNine = (LinearLayout ) mView.findViewById(R.id.btnNine);
        btnNine.setOnClickListener(this);
        btnZero = (LinearLayout ) mView.findViewById(R.id.btnZero);
        btnZero.setOnClickListener(this);
        btnHash = (LinearLayout ) mView.findViewById(R.id.btnHash);
        btnHash.setOnClickListener(this);
        btnAterisk = (LinearLayout ) mView.findViewById(R.id.btnAterisk);
        btnAterisk.setOnClickListener(this);
        btnCall = (ImageView) mView.findViewById(R.id.btnCall);
        btnCall.setOnClickListener(this);
        btnDel = (ImageView) mView.findViewById(R.id.btndel);
        btnDel.setOnClickListener(this);
        btnDel.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                edtPhoneNo.setText("");
                return false;
            }
        });

    }

    @Override
    public void onClick(View v) {
        String phoneNo = edtPhoneNo.getText().toString();
        try {

            switch (v.getId()) {
                case R.id.btnAterisk:
                    phoneNo += "*";
                    edtPhoneNo.setText(phoneNo);
                    break;
                case R.id.btnHash:

                    phoneNo += "#";
                    edtPhoneNo.setText(phoneNo);
                    break;
                case R.id.btnZero:

                    phoneNo += "0";
                    edtPhoneNo.setText(phoneNo);
                    break;
                case R.id.btnOne:

                    phoneNo += "1";
                    edtPhoneNo.setText(phoneNo);
                    break;
                case R.id.btnTwo:

                    phoneNo += "2";
                    edtPhoneNo.setText(phoneNo);
                    break;
                case R.id.btnThree:

                    phoneNo += "3";
                    edtPhoneNo.setText(phoneNo);
                    break;
                case R.id.btnFour:

                    phoneNo += "4";
                    edtPhoneNo.setText(phoneNo);
                    break;
                case R.id.btnFive:

                    phoneNo += "5";
                    edtPhoneNo.setText(phoneNo);
                    break;
                case R.id.btnSix:

                    phoneNo += "6";
                    edtPhoneNo.setText(phoneNo);
                    break;
                case R.id.btnSeven:

                    phoneNo += "7";
                    edtPhoneNo.setText(phoneNo);
                    break;
                case R.id.btnEight:

                    phoneNo += "8";
                    edtPhoneNo.setText(phoneNo);
                    break;
                case R.id.btnNine:

                    phoneNo += "9";
                    edtPhoneNo.setText(phoneNo);
                    break;
                case R.id.btndel:

                    if (phoneNo != null && phoneNo.length() > 0) {
                        phoneNo = phoneNo.substring(0, phoneNo.length() - 1);
                    }

                    edtPhoneNo.setText(phoneNo);
                    break;

                case R.id.btnCall:
                    if (phoneNo.trim().equals("")) {
                        //   lblinfo.setText("Please enter a number to call on!");
                    } else {
                        Boolean isHash = false;
                        if (phoneNo.subSequence(phoneNo.length() - 1, phoneNo.length()).equals("#")) {
                            phoneNo = phoneNo.substring(0, phoneNo.length() - 1);
                            String callInfo = "tel:" + phoneNo + Uri.encode("#");
                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                            callIntent.setData(Uri.parse(callInfo));
                            if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                                return;
                            }
                            startActivity(callIntent);
                        } else {
                            String callInfo = "tel:" + phoneNo;
                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                            callIntent.setData(Uri.parse(callInfo));
                            startActivity(callIntent);

                        }
                    }
                    break;
            }

        } catch (Exception ex) {

        }
    }
}