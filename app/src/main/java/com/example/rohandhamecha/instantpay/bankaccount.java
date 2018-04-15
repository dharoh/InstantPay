package com.example.rohandhamecha.instantpay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by Rohan Dhamecha on 10-03-2018.
 */

public class bankaccount extends AppCompatActivity{

    String mobile;
    String bid;
    String acno;
    String bname;
    String bupi;
    Connection con;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bank_account);

        ImageView ig=(ImageView)findViewById(R.id.imageView11);

        Bundle b=getIntent().getExtras();
        mobile=b.getString("mobile");

        SQLConnection sc=new SQLConnection();
        con=sc.connect();

        ImageView img=(ImageView)findViewById(R.id.imageView12);
        try{

            Statement stmt=con.createStatement();
            ResultSet res=stmt.executeQuery("select * from [User] where mobile_no='"+mobile+"'");
            if(res.next())
            {
                bid=res.getString(8);
                acno=res.getString(6);
            }


            res=stmt.executeQuery("select * from [Bank] where bank_id='"+bid+"'");

            if(res.next())
            {
                bname=res.getString(2);
                bupi=res.getString(3);
            }

            TextView txt=(TextView)findViewById(R.id.textView31);
            TextView txt1=(TextView)findViewById(R.id.textView32);
            TextView txt2=(TextView)findViewById(R.id.textView30);

            txt2.setText("XXXXXXXXXX"+acno.substring(9));
            txt.setText(bname);
            txt1.setText(bupi+" digit UPI PIN exists");

            if(bid.equals("1"))
            {
                img.setImageResource(R.mipmap.pic1);
            }

            if(bid.equals("2"))
            {
                img.setImageResource(R.mipmap.pic3);
            }

            if(bid.equals("3"))
            {
                img.setImageResource(R.mipmap.pic4);
            }

            if(bid.equals("4"))
            {
                img.setImageResource(R.mipmap.pic5);
            }

            if(bid.equals("5"))
            {
                img.setImageResource(R.mipmap.pic6);
            }

            if(bid.equals("6"))
            {
                img.setImageResource(R.mipmap.pic7);
            }

            if(bid.equals("7"))
            {
                img.setImageResource(R.mipmap.pic8);
            }

            if(bid.equals("8"))
            {
                img.setImageResource(R.mipmap.pic9);
            }

            if(bid.equals("9"))
            {
                img.setImageResource(R.mipmap.pic2);
            }
        }
        catch (Exception e)
        {}

        Button cupi=(Button)findViewById(R.id.button5);

        ig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bankaccount.super.onBackPressed();
            }
        });


        cupi.setOnClickListener(new View.OnClickListener()
                                {
                                    @Override
                                    public void onClick(View v) {

                                        Bundle b=new Bundle();
                                        b.putString("mobile",mobile);
                                        b.putString("account",acno);
                                        b.putInt("bankid",Integer.parseInt(bid));
                                        b.putString("upi_sts","no");
                                        if(bupi.equals("6"))
                                        {
                                            Intent i=new Intent(bankaccount.this,upi6digit.class);
                                            i.putExtras(b);
                                            startActivity(i);
                                        }
                                        else
                                        {
                                            Intent i=new Intent(bankaccount.this,upi4digit.class);
                                            i.putExtras(b);
                                            startActivity(i);

                                        }
                                    }
                                }
        );

        cupi=(Button)findViewById(R.id.button7);

        cupi.setOnClickListener(new View.OnClickListener()
                                {
                                    @Override
                                    public void onClick(View v) {

                                        Intent i=new Intent(bankaccount.this,banklist.class);
                                        Bundle b=new Bundle();
                                        b.putString("mobile",mobile);
                                        i.putExtras(b);
                                        finishAffinity();
                                        startActivity(i);
                                    }
                                }

        );

       Button  cbal=(Button)findViewById(R.id.button6);

       cbal.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Bundle b=new Bundle();
               b.putString("mobile",mobile);
               b.putString("account",acno);
               b.putInt("bankid",Integer.parseInt(bid));
               b.putString("upi_sts","bal");
               if(bupi.equals("6"))
               {
                   Intent i=new Intent(bankaccount.this,upi6digit.class);
                   i.putExtras(b);
                   startActivity(i);
               }
               else
               {
                   Intent i=new Intent(bankaccount.this,upi4digit.class);
                   i.putExtras(b);
                   startActivity(i);

               }
           }
       });

    }
}
