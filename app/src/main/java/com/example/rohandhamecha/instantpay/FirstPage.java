package com.example.rohandhamecha.instantpay;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Timer;
import java.util.TimerTask;

public class FirstPage extends AppCompatActivity{
 Connection con;
  TextView tv;
    DatabaseHelper myDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);
        tv=(TextView)findViewById(R.id.textView2);
        try {

            tv.setText("Connecting to server....");
            SQLConnection sc = new SQLConnection();
            con = sc.connect();
//
//            myDb=new DatabaseHelper(this);
//            Cursor res=myDb.getAllData();

            SharedPreferences sp=getSharedPreferences("InstantPay_db",MODE_PRIVATE);
            String restoredText=sp.getString("text",null);

            if(con!=null)
            {

                StringBuffer buffer=new StringBuffer();
                if(con!=null)
                {

                    final String mobile=sp.getString("mobile","No name defined");
                    final String email=sp.getString("email","No name defined");

                    ResultSet rest;
                    Statement stmt = con.createStatement();
                    String q="select * from [User] where mobile_no='"+mobile+"'";
                    rest=stmt.executeQuery(q);

                    if(rest.next()) {
                        new Timer().schedule(new TimerTask() {
                            @Override
                            public void run() {

                                Intent i = new Intent(FirstPage.this, app_pin.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("email", email);
                                bundle.putString("mobile", mobile);
                                i.putExtras(bundle);
                                finishAffinity();
                                startActivity(i);
                            }
                        }, 2000);

                    }
                    else
                    {
                        new Timer().schedule(new TimerTask(){
                            public void run() {
                                startActivity(new Intent(FirstPage.this, mailjava.class));
                                finishAffinity();
                            }
                        }, 5000);
                    }
                }
            }

            else if (con!= null) {
                new Timer().schedule(new TimerTask(){
                    public void run() {
                        startActivity(new Intent(FirstPage.this, mailjava.class));
                        finishAffinity();
                    }
                }, 5000);
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Server Not Found. Check your Internet Connection or try again later.").setCancelable(false).setNeutralButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                moveTaskToBack(true);

                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(1);
                            }
                        }
                );
                AlertDialog alert = builder.create();
                alert.setTitle("Alert Message");
                alert.setIcon(R.mipmap.ic_launcher);
                alert.show();
            }

        } catch (Exception e) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Server Not Found. Check your Internet Connection or try again later.").setCancelable(false).setNeutralButton("OK",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            moveTaskToBack(true);

                            android.os.Process.killProcess(android.os.Process.myPid());
                            System.exit(1);
                        }
                    }
            );
            AlertDialog alert = builder.create();
            alert.setTitle("Alert Message");
            alert.setIcon(R.mipmap.ic_launcher);
            alert.show();

        }
    }



}
