package com.example.saubhagyam.myapplication.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saubhagyam.myapplication.R;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        final TextView textView = (TextView) findViewById(R.id.input_password);
        final TextView textView1 = (TextView) findViewById(R.id.input_password1);
        final TextView textView2 = (TextView) findViewById(R.id.input_password2);
        Button button = (Button) findViewById(R.id.ok);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String abc = textView.getText().toString();
                String abc1 = textView1.getText().toString();
                String abc2 = textView2.getText().toString();
                if (abc.equals("")) {
                   // Toast.makeText(Main2Activity.this, "Empty", Toast.LENGTH_SHORT).show();
                    textView.setError("Please Enter Details");
                    if (abc1.equals("")) {
                       // Toast.makeText(Main2Activity.this, "Empty", Toast.LENGTH_SHORT).show();
                        textView1.setError("Please Enter Details");
                        if (abc2.equals("")) {
                          //  Toast.makeText(Main2Activity.this, "Empty", Toast.LENGTH_SHORT).show();
                            textView2.setError("Please Enter Details");
                        } else {

                        }
                    } else {

                    }
                } else {
                    /*Toast.makeText(Main2Activity.this, "Empty", Toast.LENGTH_SHORT).show();
                    textView.setError("Please Enter Details");*/
                }
               /* if (abc.equals("")) {
                    Toast.makeText(Main2Activity.this, "Empty", Toast.LENGTH_SHORT).show();
                    textView.setError("Please Enter Details");
                } else if (abc1.equals("")) {
                    Toast.makeText(Main2Activity.this, "Empty", Toast.LENGTH_SHORT).show();
                    textView1.setError("Please Enter Details");
                } else if (abc2.equals("")) {
                    Toast.makeText(Main2Activity.this, "Empty", Toast.LENGTH_SHORT).show();
                    textView2.setError("Please Enter Details");
                } else {
                    Toast.makeText(Main2Activity.this, abc, Toast.LENGTH_SHORT).show();

                }*/
            }
        });

    }
}
