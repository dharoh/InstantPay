package com.example.rohandhamecha.instantpay;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


/**
 * Created by Rohan Dhamecha on 06-02-2018.
 */

public class displayqr extends AppCompatActivity {

    ImageView img;
    String mobile ;
    Thread thread ;
    public final static int QRcodeWidth = 500 ;
    Bitmap bitmap ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_qr);

        Bundle bundle=getIntent().getExtras();
        mobile=bundle.getString("mobile");
        img=(ImageView)findViewById(R.id.qrimg);
        ImageView ig=(ImageView)findViewById(R.id.imageView10);
        SQLConnection sc=new SQLConnection();
        Connection con=sc.connect();

        try {
            Statement stmt = con.createStatement();
            ResultSet res=stmt.executeQuery("select name from [User] where mobile_no='"+mobile+"'");
            TextView txt=(TextView)findViewById(R.id.textView28);
            if(res.next())
            {
                txt.setText(res.getString(1));
            }

        }
        catch (Exception e){}

        try{

            bitmap=TextToImageEncode(mobile);
            img.setImageBitmap(bitmap);
        }
        catch (Exception e){}

        Button button=(Button)findViewById(R.id.saveqr);


        ig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayqr.super.onBackPressed();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ImagePath = MediaStore.Images.Media.insertImage(
                        getContentResolver(),
                        bitmap,
                        "instantpay",
                        null
                );
                Uri.parse(ImagePath);
                Toast.makeText(displayqr.this,"Saved image to gallery",Toast.LENGTH_SHORT).show();
            }
        });

        Button share=(Button)findViewById(R.id.button4);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent shre = new Intent(Intent.ACTION_SEND);
                String ImagePath = MediaStore.Images.Media.insertImage(
                        getContentResolver(),
                        bitmap,
                        "instantpay",
                        null
                );
                shre.setType("image/jpeg");
                shre.putExtra(Intent.EXTRA_STREAM,Uri.parse(ImagePath));
                startActivity(Intent.createChooser(shre,"Share Image"));
            }
        });

    }

    Bitmap TextToImageEncode(String Value) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    Value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    QRcodeWidth, QRcodeWidth, null
            );

        } catch (IllegalArgumentException Illegalargumentexception) {

            return null;
        }
        int bitMatrixWidth = bitMatrix.getWidth();

        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++) {

                pixels[offset + x] = bitMatrix.get(x, y) ?
                        getResources().getColor(R.color.black):getResources().getColor(R.color.white);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }
}
