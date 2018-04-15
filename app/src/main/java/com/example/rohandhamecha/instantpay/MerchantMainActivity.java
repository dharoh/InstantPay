package com.example.rohandhamecha.instantpay;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class MerchantMainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{

    String mobile;
    Connection con;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_main);
        SQLConnection sc=new SQLConnection();
        con=sc.connect();


        Bundle b=getIntent().getExtras();
        mobile=b.getString("mobile");

        try {
            Statement stmt = con.createStatement();
            ResultSet res=stmt.executeQuery("select bank_id from [merchant] where mobile_no='"+mobile+"'" );
            ImageView img=(ImageView)findViewById(R.id.imageView_banklog);
            String bid=null;
            if(res.next())
            {
                bid=res.getString(1);
            }

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
        catch (Exception e){


        }


        setActionListeners();
    }

    public void showPopup(View v) {

        PopupMenu menu = new PopupMenu(this, v);
        menu.setOnMenuItemClickListener(this);
        menu.inflate(R.menu.merchant_main_menu);
        menu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.merchant_trans:
                Intent i=new Intent(MerchantMainActivity.this,trans_java.class);
                Bundle b=new Bundle();
                b.putString("ttype","merchant");
                b.putString("mobile",mobile);
                i.putExtras(b);
                startActivity(i);
                return true;

            case R.id.logoutMerchant:
                Intent i1=new Intent(MerchantMainActivity.this,MainActivity.class);
                Bundle b1=new Bundle();
                b1.putString("mobile",mobile);
                i1.putExtras(b1);
                startActivity(i1);
                return true;
            default:
                return false;
        }
    }

    public void setActionListeners(){

        //Mobile no checker
        final EditText mobileno = (EditText)findViewById(R.id.editText_Mobile);
        final TextView verify = (TextView)findViewById(R.id.textView_VerifyNo);
        mobileno.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {

                if(mobileno.length() == 10){

                    verify.setVisibility(View.VISIBLE);
                }
                else{

                    verify.setVisibility(View.GONE);
                }
            }
        });

        //Verify Button listener
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){

                //TO DO when verify clicked
                String smobile=mobileno.getText().toString();
                try {
                    Statement stmt = con.createStatement();
                    ResultSet res=stmt.executeQuery("select bank_id from [User] where mobile_no='"+smobile+"'");

                    if(res.next())
                    {
                        if(!res.getString(1).equals(null))
                        {
                            Intent i=new Intent(MerchantMainActivity.this,MerchantVerifyActivity.class);
                            Bundle b=new Bundle();
                            b.putString("smobile",smobile);
                            b.putString("mobile",mobile);
                            i.putExtras(b);
                            startActivity(i);
                        }
                        else
                        {
                            AlertDialog.Builder builder = new AlertDialog.Builder(MerchantMainActivity.this);
                            builder.setMessage("You can't pay to this person due to not registered on InstantPay.").setCancelable(false).setNeutralButton("OK",
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
                    else
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MerchantMainActivity.this);
                        builder.setMessage("You are either not registered on InstantPay or you have entered wrong mobile number.").setCancelable(false).setNeutralButton("OK",
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
                catch (Exception e){}






            }
        });
    }
}
