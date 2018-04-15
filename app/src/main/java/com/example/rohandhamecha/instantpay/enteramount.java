package com.example.rohandhamecha.instantpay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.xml.transform.Result;

/**
 * Created by Rohan Dhamecha on 10-03-2018.
 */

public class enteramount extends AppCompatActivity {

    String smobile;
    String rmobile;
    int bid = 0;
    String acno = null;
    String upi = null;
    Connection con;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enter_amount);

        SQLConnection sc = new SQLConnection();
        con = sc.connect();
        Bundle b=getIntent().getExtras();
        smobile=b.getString("smobile");
        rmobile=b.getString("rmobile");

        TextView txt=(TextView)findViewById(R.id.textView38);
        try {
            Statement stmt = con.createStatement();
            ResultSet res = stmt.executeQuery("select name from [User] where mobile_no='" + rmobile + "'");
            if (res.next()) {
                txt.setText(res.getString(1).toUpperCase().toString());

            }


            ResultSet res1=stmt.executeQuery("select bank_id,account_no from [User] where mobile_no='"+smobile+"'");
            if(res1.next())
            {
                bid=res1.getInt(1);
                acno=res1.getString(2);
            }
        }
           catch(Exception e){}

            Button btn = (Button) findViewById(R.id.button8);

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    try {
                        Statement stmt = con.createStatement();
                        ResultSet res = stmt.executeQuery("select upi_size from [Bank] where bank_id='" + bid + "'");
                        if (res.next()) {
                            upi = res.getString(1).toString();
                        }

                        if (upi.equals("6")) {
                            Intent i = new Intent(enteramount.this, upi6digit.class);
                            Bundle b1 = new Bundle();
                            b1.putString("mobile", smobile);
                            b1.putString("account", acno);
                            b1.putString("upi_sts", "check");
                            b1.putInt("bankid", bid);
                            b1.putString("paytype","normal");
                            b1.putString("rec", rmobile);
                            EditText t = (EditText) findViewById(R.id.editText4);
                            b1.putString("money", t.getText().toString());
                            i.putExtras(b1);
                            startActivity(i);
                        }

                        if (upi.equals("4")) {
                            Intent i = new Intent(enteramount.this, upi4digit.class);
                            Bundle b1 = new Bundle();
                            b1.putString("mobile", smobile);
                            b1.putString("account", acno);
                            b1.putString("upi_sts", "check");
                            b1.putInt("bankid", bid);
                            b1.putString("rec", rmobile);
                            b1.putString("paytype","normal");
                            EditText t = (EditText) findViewById(R.id.editText4);
                            b1.putString("money", t.getText().toString());
                            i.putExtras(b1);
                            startActivity(i);
                        }
                    }
                    catch(Exception e){}
                }

            });



        }

    }

