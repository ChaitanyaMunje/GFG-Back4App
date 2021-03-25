package com.gtappdevelopers.gfgparse;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class ContactDetailActivity extends AppCompatActivity {
    //creating variables for our image view and text view and string. .
    private String contactName, contactNumber;
    private TextView contactTV, nameTV;
    private ImageView contactIV, callIV, messageIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);
        //on below line we are getting data which we passed in our adaoter class with intent.
        contactName = getIntent().getStringExtra("name");
        contactNumber = getIntent().getStringExtra("contact");
        //initializing our views.
        nameTV = findViewById(R.id.idTVName);
        contactIV = findViewById(R.id.idIVContact);
        contactTV = findViewById(R.id.idTVPhone);
        nameTV.setText(contactName);
        contactTV.setText(contactNumber);
        callIV = findViewById(R.id.idIVCall);
        messageIV = findViewById(R.id.idIVMessage);
        //on below line adding click listner for our calling image view.
        callIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //calling a method to make a call.
                makeCall(contactNumber);
            }
        });
        //on below line adding on click listner for our message image view.
        messageIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //calling a method to send message
                sendMessage(contactNumber);
            }
        });
    }

    private void sendMessage(String contactNumber) {
        //in this method we are calling an intent to send sms.
        //on below line we are passing our contact number.
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + contactNumber));
        intent.putExtra("sms_body", "Enter your messaage");
        startActivity(intent);
    }

    private void makeCall(String contactNumber) {
        //this method is called for making a call.
        //on below line we are calling an intent to make a call.
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        //on below line we are setting data to it.
        callIntent.setData(Uri.parse("tel:" + contactNumber));
        //on below line we are checking if the calling permissions are grantedor not.
        if (ActivityCompat.checkSelfPermission(ContactDetailActivity.this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        //at last we are starting activity.
        startActivity(callIntent);
    }

}