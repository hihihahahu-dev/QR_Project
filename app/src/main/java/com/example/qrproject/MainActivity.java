package com.example.qrproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.zxing.integration.android.IntentIntegrator;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    Uri Image_Uri;
    ImageView testImageView;
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Testing new scanner
        final Button button_scan_qr_code = findViewById(R.id.button_scan_qr_code);
        button_scan_qr_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                (new IntentIntegrator(MainActivity.this)).initiateScan();
            }
        });
        /**
        //Set Internet Request
        final ImageView mImageView = (ImageView) findViewById(R.id.imageView1);
        final RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://api.qrserver.com/v1/create-qr-code/?data=HelloWorld!&size=100x100";
        ImageRequest imageRequest = QRCode.GenerateQR(url, mImageView);
        queue.add(imageRequest);
        //Set Internet Receive
        //final TextView mTextView = (TextView) findViewById(R.id.textView);
        //String url2 = "http://api.qrserver.com/v1/read-qr-code/?fileurl=http%3A%2F%2Fapi.qrserver.com%2Fv1%2Fcreate-qr-code%2F%3Fdata%3DHelloWorld";
        //JsonArrayRequest jsonRequest = QRCode.GetQRInfo(url2, mTextView);
        //queue.add(jsonRequest);
        //Open Camera
        final Button button = findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // get an image from the camera
                testImageView = (ImageView) findViewById(R.id.imageView2);
                openCamera();
            }
        });
        //Take a picture
        final Button button2 = findViewById(R.id.button_test);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Post image to API
                toastMsg("Hello how are you today!!");
                final TextView mTextView = (TextView) findViewById(R.id.textView2);
                String url3 = "http://api.qrserver.com/v1/read-qr-code/";
                byte[] tes = getFileDataFromDrawable(bitmap);
                VolleyMultipartRequest multRequest = QRCode.GetQRPhoto(url3, mTextView, getFileDataFromDrawable(bitmap));
                queue.add(multRequest);
            }
        });
        //Test the link
        */



    }
    public void toastMsg(String msg) {
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
        toast.show();
    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private void openCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Image");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Testing");
        Image_Uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent CameraInt = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        CameraInt.putExtra(MediaStore.EXTRA_OUTPUT, Image_Uri);
        startActivityForResult(CameraInt, 1001);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
            }
        }
    }
}