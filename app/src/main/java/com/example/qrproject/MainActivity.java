package com.example.qrproject;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

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
        //Set Internet Request
        final ImageView mImageView = (ImageView) findViewById(R.id.imageView1);
        final RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://api.qrserver.com/v1/create-qr-code/?data=HelloWorld!&size=100x100";
        ImageRequest imageRequest = QRCode.GenerateQR(url, mImageView);
        queue.add(imageRequest);
        //Set Internet Receive
        final TextView mTextView = (TextView) findViewById(R.id.textView);
        String url2 = "http://api.qrserver.com/v1/read-qr-code/?fileurl=http%3A%2F%2Fapi.qrserver.com%2Fv1%2Fcreate-qr-code%2F%3Fdata%3DHelloWorld";
        JsonArrayRequest jsonRequest = QRCode.GetQRInfo(url2, mTextView);
        queue.add(jsonRequest);
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
                final TextView mTextView = (TextView) findViewById(R.id.textView2);
                String url3 = "http://api.qrserver.com/v1/read-qr-code/";
                VolleyMultipartRequest multRequest = QRCode.GetQRPhoto(url3, mTextView, getFileDataFromDrawable(bitmap));
                queue.add(multRequest);
            }
        });
        //Test the link
        final Button button3 = findViewById(R.id.tabs);
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Testing

                final TextView mTextView = (TextView) findViewById(R.id.textView2);
                String url3 = "http://api.qrserver.com/v1/read-qr-code/";
                VolleyMultipartRequest multRequest = QRCode.GetQRPhoto(url3, mTextView, getFileDataFromDrawable(bitmap));
                queue.add(multRequest);
            }
        });



    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
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
        if (resultCode == RESULT_OK) {
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Image_Uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            testImageView.setImageURI(Image_Uri);
        }
    }
}