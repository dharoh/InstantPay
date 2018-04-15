package com.example.rohandhamecha.instantpay;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

public class MerchantLoginActivity extends AppCompatActivity {

    String mid,mpin;
    Connection con;
    String mobile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_login);
        SQLConnection sc=new SQLConnection();
        con=sc.connect();

        Bundle b=getIntent().getExtras();
       mobile=b.getString("mobile");

        try{
            Statement stmt=con.createStatement();
            ResultSet res=stmt.executeQuery("select merc_id,pin from [merchant] where mobile_no='"+mobile+"'");
            if(res.next())
            {
                mid=res.getString(1);
                mpin=res.getString(2);
            }
        }
        catch (Exception e)
        {}


        ImageView bt=(ImageView)findViewById(R.id.imageView_backlogin);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        setListeners();
    }

    public void setListeners(){

        final EditText editText = (EditText) findViewById(R.id.editText_merchantLoginPIN);
        final EditText editText1=(EditText)findViewById(R.id.editText_merchantID);

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    if(editText.getText().toString().equals(mpin) && editText1.getText().toString().equals(mid))
                    {
                        Intent i=new Intent(MerchantLoginActivity.this,MerchantMainActivity.class);
                        Bundle b=new Bundle();
                        b.putString("mobile",mobile);
                        i.putExtras(b);
                        finishAffinity();
                        startActivity(i);
                    }
                    else
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MerchantLoginActivity.this);
                        builder.setMessage("Invalid merchant ID or merchant PIN..please try again.").setCancelable(true).setNeutralButton("OK",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        editText.setText("");
                                        editText1.setText("");
                                    }
                                }
                        );
                        AlertDialog alert = builder.create();
                        alert.setTitle("Alert Message");
                        alert.setIcon(R.mipmap.ic_launcher);
                        alert.show();
                    }

                    handled = true;
                }
                return handled;
            }
        });
    }
}
