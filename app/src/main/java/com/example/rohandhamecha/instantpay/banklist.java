package com.example.rohandhamecha.instantpay;


/**
 * Created by Rohan Dhamecha on 28-01-2018.
 */

import android.app.Activity;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLClientInfoException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class banklist extends Activity{

    ListView list;

    SQLConnection sc=new SQLConnection();
    Connection con=sc.connect();
String mobile;
    ResultSet res;


    Integer[] imgid={
            R.mipmap.pic1,
            R.mipmap.pic3,
            R.mipmap.pic4,
            R.mipmap.pic5,
            R.mipmap.pic6,
            R.mipmap.pic7,
            R.mipmap.pic8,
            R.mipmap.pic9,
            R.mipmap.pic2};


    String[] itemname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bank_list);
        final Bundle bundle=getIntent().getExtras();
        mobile=bundle.getString("mobile");

        try {
            Statement stmt = con.createStatement();
            String q = "select * from [Bank]";
            res = stmt.executeQuery(q);

            int total=0;
            List<String>bname= new ArrayList<String>();
            while(res.next())
            {
                bname.add(total,res.getString(2));
                total++;
            }
            itemname=new String[total];

            for(int i=0;i<total;i++) {
                itemname[i] = bname.get(i);
            }

        }
        catch (Exception e)
        {

        }


        CustomListAdapter adapter=new CustomListAdapter(this, itemname, imgid);
        list=(ListView)findViewById(R.id.list);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                String Slecteditem= itemname[+position];
                try {
                    Statement stmt = con.createStatement();
                    String q = "select * from [bank_data] where mobile_no='"+mobile+"' and bank_id='"+(position+1)+"'";
                    res = stmt.executeQuery(q);

                    if(res.next())
                    {
                          Intent i =new Intent(banklist.this,verify_bank.class);
                          Bundle bundle1=new Bundle();
                          bundle1.putString("mobile",mobile);
                          bundle1.putInt("bankid",(position+1));
                          i.putExtras(bundle1);
                          startActivity(i);
                    }
                    else
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(banklist.this);
                        builder.setMessage("You have not account with "+Slecteditem+". Select any other bank.").setCancelable(false).setNeutralButton("OK",
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
                catch(Exception e){}
            }
        });
    }
}
