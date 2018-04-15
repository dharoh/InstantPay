package com.example.rohandhamecha.instantpay;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rohan Dhamecha on 02-02-2018.
 */

public class verify_bank extends AppCompatActivity {
    ListView list;
    String mobile;
    int bid;
    int upisize=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verifybank);

        Bundle bundle=getIntent().getExtras();
        mobile=bundle.getString("mobile");
        bid=bundle.getInt("bankid");
        Integer[] imgid;
        String[] itemname;
        String[] desc;

        final List<String>acno=new ArrayList<String>();
        SQLConnection sc=new SQLConnection();
        final Connection con=sc.connect();
try {
    Statement stmt = con.createStatement();
    String q="select account_no from [bank_data] where mobile_no='"+mobile+"' and bank_id='"+bid+"'";
    ResultSet res=stmt.executeQuery(q);

    int i=0;
    while(res.next())
    {
        acno.add(i,res.getString(1));
        i++;
    }

    for(int j=0;j<i;j++)
    {
        String a=acno.get(j);
        for(int k=j+1;k<i;k++)
        {
            if(acno.get(k).toString().equals(a))
            {
                acno.remove(k);
                i--;
            }
        }
    }

    itemname=new String[i];
    imgid=new Integer[i];
    desc=new String[i];

    String descr=null;
    int img=0;
    if(bid==1)
    {
        descr="Axis Bank";
        img=R.mipmap.pic1;
    }
    else if(bid==2)
    {
        descr="Bank of Baroda";
        img=R.mipmap.pic3;
    }
    else if(bid==3)
    {
        descr="Canara Bank";
        img=R.mipmap.pic4;
    }
    else if(bid==4)
    {
        descr="Dena Bank";
        img=R.mipmap.pic5;
    }
    else if(bid==5)
    {
        descr="HDFC Bank";
        img=R.mipmap.pic6;
    }
    else if(bid==6)
    {
        descr="ICICI Bank";
        img=R.mipmap.pic7;
    }
    else if(bid==7)
    {
        descr="IDFC Bank";
        img=R.mipmap.pic8;
    }
    else if(bid==8)
    {
        descr="Punjab National Bank";
        img=R.mipmap.pic9;
    }
    else if(bid==9)
    {
        descr="State Bank of India";
        img=R.mipmap.pic2;
    }


    for(int j=0;j<i;j++)
    {
        itemname[j]="XXXXXXXXXX"+acno.get(j).substring(9);
        imgid[j]=img;
        desc[j]=descr;
    }
    CustomListAdapter1 cla=new CustomListAdapter1(this,itemname,desc,imgid);
   list=(ListView)findViewById(R.id.list1);
    list.setAdapter(cla);
}
catch (Exception e){}

list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        boolean check_upi=true;
        final String select_ac=acno.get(position);
        try {
            final Statement stmt1 = con.createStatement();
            String q="select upi_pin from bank_data where account_no='"+select_ac+"'";
            final ResultSet res1=stmt1.executeQuery(q);

            while(res1.next())
            {
                if(res1.getString(1).equals(""))
                    check_upi=false;
            }

            if(check_upi==true)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(verify_bank.this);
                builder.setMessage("UPI pin exists with your bank account.You have to enter your UPI for verification.").setCancelable(true).setNeutralButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                try {
                                   Statement stmt1 = con.createStatement();
                                  ResultSet res1=stmt1.executeQuery("select upi_size from [Bank] where bank_id='"+bid+"'");

                                  if(res1.next())
                                  {
                                      upisize=res1.getInt(1);
                                  }
                                }
                                catch (Exception e){}
                                Bundle bundle2=new Bundle();
                                bundle2.putString("mobile",mobile);
                                bundle2.putString("account",select_ac);
                                bundle2.putString("upi_sts","yes");
                                bundle2.putInt("bankid",bid);
                                Intent i=null;
                                if(upisize==6) {
                                    i = new Intent(verify_bank.this, upi6digit.class);
                                }
                                if(upisize==4)
                                {
                                    i = new Intent(verify_bank.this, upi4digit.class);
                                }
                               i.putExtras(bundle2);
                                startActivity(i);
                            }
                        }
                );
                AlertDialog alert = builder.create();
                alert.setTitle("Alert Message");
                alert.setIcon(R.mipmap.ic_launcher);
                alert.show();

            }
            else
            {
                final Bundle bundle2=new Bundle();
                bundle2.putString("mobile",mobile);
                bundle2.putString("account",select_ac);
                bundle2.putInt("bankid",bid);
                bundle2.putInt("upisize",upisize);

                AlertDialog.Builder builder = new AlertDialog.Builder(verify_bank.this);
                builder.setMessage("You have to set your UPI pin.").setCancelable(true).setNeutralButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Intent i=new Intent(verify_bank.this,set_upi.class);
                                i.putExtras(bundle2);
                                startActivity(i);
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
