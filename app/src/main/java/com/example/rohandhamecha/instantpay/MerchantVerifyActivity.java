package com.example.rohandhamecha.instantpay;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class MerchantVerifyActivity extends AppCompatActivity {

    String smobile,mobile;
    Connection con;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_verify);

        SQLConnection sc=new SQLConnection();
        con=sc.connect();

        Bundle b=getIntent().getExtras();
        smobile=b.getString("smobile");
        mobile=b.getString("mobile");

        ImageView bt=(ImageView)findViewById(R.id.imageView_backver);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        setListeners();
    }

    public void setListeners(){

        final EditText editText = (EditText) findViewById(R.id.editText_merchantPIN);

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    try{

                        Statement stmt=con.createStatement();
                        Statement stm1=con.createStatement();
                        Statement stm2=con.createStatement();
                        ResultSet res=stmt.executeQuery("select pin from [merchant] where mobile_no='"+mobile+"'");
                        ResultSet rest=stm1.executeQuery("select bank_id,account_no from [User] where mobile_no='"+smobile+"'");


                        int bid=0;
                        String acno=" ";
                        if(rest.next()) {
                            bid = rest.getInt(1);
                            acno=rest.getString(2);
                        }

                        rest=stm2.executeQuery("select upi_size from [Bank] where bank_id='"+bid+"'");

                        String upi=" ";
                        if(rest.next())
                            upi=rest.getString(1);

                        if(res.next())
                        {
                            if(res.getString(1).equals(editText.getText().toString()))
                            {
                                if (upi.equals("6")) {
                                    Intent i = new Intent(MerchantVerifyActivity.this, upi6digit.class);
                                    Bundle b1 = new Bundle();
                                    b1.putString("mobile", smobile);
                                    b1.putString("account", acno);
                                    b1.putString("upi_sts", "check");
                                    b1.putInt("bankid", bid);
                                    b1.putString("paytype","merchant");
                                    b1.putString("rec", mobile);
                                    EditText t = (EditText) findViewById(R.id.editText_merchantAmount);
                                    b1.putString("money", t.getText().toString());
                                    i.putExtras(b1);
                                    startActivity(i);
                                }

                                if (upi.equals("4")) {
                                    Intent i = new Intent(MerchantVerifyActivity.this, upi4digit.class);
                                    Bundle b1 = new Bundle();
                                    b1.putString("mobile", smobile);
                                    b1.putString("account", acno);
                                    b1.putString("upi_sts", "check");
                                    b1.putInt("bankid", bid);
                                    b1.putString("rec", mobile);
                                    b1.putString("paytype","merchant");
                                    EditText t = (EditText) findViewById(R.id.editText_merchantAmount);
                                    b1.putString("money", t.getText().toString());
                                    i.putExtras(b1);
                                    startActivity(i);
                                }
                            }
                            else
                            {
                                AlertDialog.Builder builder = new AlertDialog.Builder(MerchantVerifyActivity.this);
                                builder.setMessage("You have entered wrong merchant PIN.").setCancelable(false).setNeutralButton("OK",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

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
                    catch (Exception e){

                        Toast.makeText(MerchantVerifyActivity.this,e.toString(),Toast.LENGTH_SHORT).show();
                    }

                    handled = true;
                }
                return handled;
            }
        });
    }
}
