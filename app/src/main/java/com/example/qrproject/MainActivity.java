package com.example.qrproject;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Set Internet Request
        final ImageView mImageView = (ImageView) findViewById(R.id.imageView1);
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://api.qrserver.com/v1/create-qr-code/?data=HelloWorld!&size=100x100";
        ImageRequest imageRequest = QRCode.GenerateQR(url, mImageView);
        queue.add(imageRequest);
        //Set Internet Receive
        final TextView mTextView = (TextView) findViewById(R.id.textView);
        String url2 = "http://api.qrserver.com/v1/read-qr-code/?fileurl=http%3A%2F%2Fapi.qrserver.com%2Fv1%2Fcreate-qr-code%2F%3Fdata%3DHelloWorld";
        JsonArrayRequest jsonRequest = QRCode.GetQRInfo(url2, mTextView);
        queue.add(jsonRequest);
    }
}