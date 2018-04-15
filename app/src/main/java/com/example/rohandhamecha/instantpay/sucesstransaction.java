package com.example.rohandhamecha.instantpay;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.xml.transform.Result;

/**
 * Created by Rohan Dhamecha on 13-03-2018.
 */

public class sucesstransaction extends AppCompatActivity {

    Connection con;
    String mobile,rmobile,sname,rname;
    String email,email1;
    String tid;
    String ptype;
    String buis;
    Float amt;
    String sac,rac;
    String date;
    String sbname,rbname;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.successful_transaction);
        super.onCreate(savedInstanceState);
        SQLConnection sc=new SQLConnection();
        con=sc.connect();
        TextView txt = (TextView) findViewById(R.id.textView41);

        try {
            Bundle b = getIntent().getExtras();
            tid = b.getString("tid");
            ptype=b.getString("ptype");
            Statement stmt=con.createStatement();


            ResultSet res=stmt.executeQuery("select rec_mobile,amount,send_mobile,send_acno,rec_acno,tdate from [Transaction] where trans_id='"+tid+"'");
            if(res.next()) {
                rmobile=res.getString(1);
                mobile=res.getString(3);
                amt=res.getFloat(2);
                sac=res.getString(4);
                rac=res.getString(5);
                date=res.getString(6);

                if(ptype.equals("normal")) {
                    txt.setText("You have successfully sent ₹" + res.getFloat(2) + " to " + res.getString(1));
                }
                else
                {
                    txt.setText("SuccessFull Transaction of ₹"+ res.getFloat(2)+"\nTHANK YOU.");
                }
            }

            int sbid=0,rbid=0;
            res=stmt.executeQuery("select email_id,name,bank_id from [User] where mobile_no='"+mobile+"'");
            if(res.next())
            {
                email=res.getString(1);
                sname=res.getString(2);
                sbid=res.getInt(3);
            }

            res=stmt.executeQuery("select email_id,name,bank_id from [User] where mobile_no='"+rmobile+"'");
            if(res.next())
            {
                email1=res.getString(1);
                rname=res.getString(2);
                rbid=res.getInt(3);
            }

            res=stmt.executeQuery("select bank_name from [Bank] where bank_id="+sbid+"");
            if(res.next())
                sbname=res.getString(1);

            res=stmt.executeQuery("select bank_name from [Bank] where bank_id="+rbid+"");
            if(res.next()){
                rbname=res.getString(1);}

             if(ptype.equals("merchant"))
             {
                 res=stmt.executeQuery("select buis_name from [merchant] where mobile_no='"+rmobile+"'");
                 if(res.next())
                     rname=res.getString(1);
             }

        }
        catch (Exception e){}

        new SendMail().execute("");

        Button b=(Button)findViewById(R.id.button9);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle b=new Bundle();
                if(ptype.equals("normal")) {
                    b.putString("mobile", mobile);
                    Intent i=new Intent(sucesstransaction.this,MainActivity.class);
                    i.putExtras(b);
                    startActivity(i);

                }
                else
                {
                    b.putString("mobile",rmobile);
                    Intent i=new Intent(sucesstransaction.this,MerchantMainActivity.class);
                    i.putExtras(b);
                    startActivity(i);
                }

            }
        });
    }

    private class SendMail extends AsyncTask<String, Integer, Void> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(sucesstransaction.this, "Please wait", "Sending Receipt", true, false);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();

        }

        protected Void doInBackground(String... params) {
            Mail m = new Mail("instantpayoffice@gmail.com", "finalproject");

            String[] toArr = {email};
            m.setTo(toArr);
            m.setFrom("instantpayoffice@gmail.com");
            m.setSubject("Successful Transaction");
            m.setBody("Welcome to InstantPay,\n"+"Your money has been successfully transferred to "+rmobile+"\n"+"Transaction Details:\n\n"+"Payment ID: "+tid+"\nAmount: "+amt+"\nAmount Debited to: "+rname+"\nDebited from bank: "+sbname+"\nFrom account: "+sac.substring(0,2)+"XXXXXX"+sac.substring(sac.length()-4)+"\nOn date: "+date);

            Mail m1 = new Mail("instantpayoffice@gmail.com", "finalproject");
            String[] arr1={email1};
            m1.setTo(arr1);
            m1.setFrom("instantpayoffice@gmail.com");
            m1.setSubject("Payment Received");
            m1.setBody("Welcome to InstantPay,\n"+"You have successfully received money from "+mobile+".\nTransaction Details:\n\n"+"Payment ID: "+tid+"\nAmount Creditd from: "+sname+"\nCredited to bank: "+rbname+"\nTo account: "+rac.substring(0,2)+"XXXXXX"+rac.substring(rac.length()-4)+"\nOn date: "+date);


            try {
                if(m.send() && m1.send()) {
                    Toast.makeText(sucesstransaction.this, "Email was sent successfully.", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(sucesstransaction.this, "Email was not sent.", Toast.LENGTH_LONG).show();
                }
            } catch(Exception e) {

            }
            return null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
// Handle action bar item clicks here. The action bar will
// automatically handle clicks on the Home/Up button, so long
// as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

//noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

}
