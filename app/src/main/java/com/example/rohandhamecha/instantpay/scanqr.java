package com.example.rohandhamecha.instantpay;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by Rohan Dhamecha on 06-02-2018.
 */
public class scanqr extends AppCompatActivity {
    Connection con;
    TextView txt;
    String mobile;
    IntentIntegrator qrScan;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_qr);
        Bundle b=getIntent().getExtras();
        mobile=b.getString("mobile");

        SQLConnection sc=new SQLConnection();
        con=sc.connect();
        txt=(TextView)findViewById(R.id.dis_qr_res);
        qrScan=new IntentIntegrator(this);
        qrScan.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                //if qr contains data
                try {
                    //setting values to textviews
                if(result.getContents().toString().length()==10)
                {


                    Statement stmt = con.createStatement();
                    ResultSet res=stmt.executeQuery("select bank_id from [User] where mobile_no='"+result.getContents().toString()+"'");

                    if(res.next())
                    {
                        if(!res.getString(1).equals(null))
                        {
                            Intent i=new Intent(scanqr.this,enteramount.class);
                            Bundle b=new Bundle();
                            b.putString("smobile",mobile);
                            b.putString("rmobile",result.getContents().toString());
                            i.putExtras(b);
                            startActivity(i);
                        }
                }
                }
                }

                catch (Exception e) {
                }
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}
