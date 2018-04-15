package com.example.rohandhamecha.instantpay;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;

/**
 * Created by Rohan Dhamecha on 30-03-2018.
 */

public class requestmoney extends AppCompatActivity {

    String smobile,rmobile;
    String email1;
    Connection con;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_money);
        SQLConnection sc=new SQLConnection();

        Bundle b=getIntent().getExtras();
        smobile=b.getString("smobile");
        rmobile=b.getString("rmobile");
        TextView txt=(TextView)findViewById(R.id.textView45);

        con=sc.connect();

        try{
            Statement stmt=con.createStatement();
            ResultSet res=stmt.executeQuery("select name from [User] where mobile_no='"+rmobile+"'");
            if(res.next())
            {
                txt.setText(res.getString(1));
            }

            res=stmt.executeQuery("select email_id from [User] where mobile_no='"+rmobile+"'");
            if(res.next())
            {
                email1=res.getString(1);
            }

        }
        catch (Exception e){}

       final EditText et=(EditText)findViewById(R.id.editText6);
        final DatePicker dt=(DatePicker)findViewById(R.id.datePicker);

        dt.setMinDate(System.currentTimeMillis());

        et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                    dt.setVisibility(View.VISIBLE);

            }
        });

        et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dt.setVisibility(View.VISIBLE);
            }
        });

        Calendar calendar = Calendar.getInstance();

        dt.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),new DatePicker.OnDateChangedListener() {

            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int dayOfMonth) {

                dt.setVisibility(View.INVISIBLE);
                et.setText(dayOfMonth+"/"+(month+1)+"/"+year);
            }
        });

        Button btn=(Button)findViewById(R.id.button10);
        final EditText amt=(EditText)findViewById(R.id.editText5);
        final EditText date=(EditText)findViewById(R.id.editText6);
        final EditText rmark=(EditText)findViewById(R.id.editText7);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    PreparedStatement smt=con.prepareStatement("insert into [Request](rmobile,smobile,amount,remark,validity)VALUES(?,?,?,?,?)");
                    smt.setString(1,rmobile);
                    smt.setString(2,smobile);
                    smt.setFloat(3,Float.parseFloat(amt.getText().toString()));
                    smt.setString(4,rmark.getText().toString());
                    smt.setString(5,date.getText().toString());

                    if(smt.executeUpdate()>0)
                    {
                        new SendMail().execute("");

                    }
                }
                catch (Exception e){}
            }
        });
    }

    private class SendMail extends AsyncTask<String, Integer, Void> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(requestmoney.this, "Please wait", "Sending Request", true, false);

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
            AlertDialog.Builder builder = new AlertDialog.Builder(requestmoney.this);
            builder.setMessage("You have successfully sent request for money."+"\n"+"Thanks").setCancelable(false).setNeutralButton("OK",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Intent i=new Intent(requestmoney.this,MainActivity.class);
                            Bundle b=new Bundle();
                            b.putString("mobile",smobile);
                            i.putExtras(b);
                            finishAffinity();
                            startActivity(i);
                        }
                    }
            );
            AlertDialog alert = builder.create();
            alert.setTitle("Confirmation Message");
            alert.setIcon(R.mipmap.ic_launcher);
            alert.show();
        }

        protected Void doInBackground(String... params) {
            Mail m1 = new Mail("instantpayoffice@gmail.com", "finalproject");
            String[] arr1={email1};
            m1.setTo(arr1);
            m1.setFrom("instantpayoffice@gmail.com");
            m1.setSubject("Request For Money");
            m1.setBody("Welcome to InstantPay,\n"+"You have received one request for money from "+smobile+"."+"\nPlease open the InstantPay to see request.");


            try {
                if(m1.send()) {
                    Toast.makeText(requestmoney.this, "Email was sent successfully.", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(requestmoney.this, "Email was not sent.", Toast.LENGTH_LONG).show();
                }
            } catch(Exception e) {

            }
            return null;
        }
    }
}
