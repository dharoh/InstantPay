package com.example.rohandhamecha.instantpay;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.design.widget.*;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SendMoneyActivity extends Activity {

    List<String> number=new ArrayList<String>();
    List<String>name=new ArrayList<String>();

    List<String>new_number=new ArrayList<String>();
    List<String>new_name=new ArrayList<String>();
    CustomListAdapter2 adp;
    EditText mobileno;
    String mobile;
    String type;
    private PagerAdapter mPagerAdapter;
    private ViewPager mViewPager;
String[] item;
String[] desc;
ListView list;
Connection con;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_money);
        Bundle b=getIntent().getExtras();
        mobile=b.getString("mobile");
        type=b.getString("type");


        TextView txt=(TextView)findViewById(R.id.textView_SendMoney);
        if(type.equals("req"))
        {
            txt.setText("Request Money");
        }

        SQLConnection sc=new SQLConnection();
        con=sc.connect();
        // Create the adapter that will return a fragment for each of the TWO
        // primary pages of the activity.

        Cursor phones=getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+" ASC");



        int i=0;
        while(phones.moveToNext())
        {
            number.add(i,phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
            name.add(i,phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)));
            i++;
        }

String n;

        for(int k=0;k<number.size();k++)
        {
            n=number.get(k);
            char[] nr=new char[10];
            int u=9;
            for(int s=n.length()-1;s>=0;s--)
            {
                if(u>=0)
                {
                    if(Character.isDigit(n.charAt(s)))
                    {
                        nr[u]=n.charAt(s);
                        u--;
                    }
                }
            }

            number.set(k,String.valueOf(nr));
        }

        for(int k=0;k<number.size();k++)
        {
            boolean yes=false;
            for(int p=0;p<new_number.size();p++)
            {
                if(number.get(k).equals(new_number.get(p)))
                {
                    yes=true;
                    break;
                }
            }
            if(yes==false)
            {


                new_number.add(new_number.size(),number.get(k));
                new_name.add(new_name.size(),name.get(k));
            }
        }


        item=new String[new_number.size()+2];
        desc=new String[new_number.size()+2];

        for(int j=0;j<new_number.size();j++)
        {
            item[j]=new_name.get(j);
            desc[j]=new_number.get(j);
        }

        adp=new CustomListAdapter2(this,item,desc);

        list=(ListView)findViewById(R.id.list_contact);
        list.setAdapter(adp);

        setActionListeners();

    }

    public void setActionListeners(){

        //Mobile no checker
       mobileno = (EditText)findViewById(R.id.editText_MobileUPI);
        final TextView verify = (TextView)findViewById(R.id.textView_Verify);
        ImageView img=(ImageView)findViewById(R.id.imgev);
        mobileno.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

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


        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendMoneyActivity.super.onBackPressed();
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

               int pos=position;
               mobileno.setText(new_number.get(position).toString());

            }
        });


        //Verify Button listener
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){

                //TODO when verify clicked
try {
    Statement stmt = con.createStatement();
    ResultSet res = stmt.executeQuery("select bank_id from [User] where mobile_no='" + mobileno.getText().toString() + "'");







        if (res.next()) {
            if (!res.getString(1).equals(null)) {
                Intent i;
                if(type.equals("send")) {
                    i = new Intent(SendMoneyActivity.this, enteramount.class);
                }
                else
                {
                    i=new Intent(SendMoneyActivity.this,requestmoney.class);
                }
                Bundle b = new Bundle();
                b.putString("smobile", mobile);
                b.putString("rmobile", mobileno.getText().toString());
                i.putExtras(b);
                startActivity(i);
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(SendMoneyActivity.this);

                builder.setMessage("You can't make transaction with this person due to not registered on InstantPay.").setCancelable(false).setNeutralButton("OK",
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
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(SendMoneyActivity.this);
            builder.setMessage("You can't make transaction with this person due to not registered on InstantPay.").setCancelable(false).setNeutralButton("OK",
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
