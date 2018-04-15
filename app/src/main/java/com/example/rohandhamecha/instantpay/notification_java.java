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
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by Rohan Dhamecha on 03-04-2018.
 */

public class notification_java extends AppCompatActivity {

    ListView list;
    Connection con;
    String[] name;
    String[] amt;
    String[] rm;
    String[] sm;
    String[] rmark;
    String[] val;
    String mobile;

    int count;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification);

        Bundle b=getIntent().getExtras();
        mobile=b.getString("mobile");

        SQLConnection sc=new SQLConnection();
        con=sc.connect();
        count=0;
        try{

            Statement stmt=con.createStatement();
            ResultSet res=stmt.executeQuery("select * from Request where rmobile='"+mobile+"'");
            while (res.next())
            {
                count++;
            }

            name=new String[count];
            amt=new String[count];
            rm=new String[count];
            sm=new String[count];
            rmark=new String[count];
            val=new String[count];

            res=stmt.executeQuery("select * from [Request] where rmobile='"+mobile+"'");
            int i=0;
            while (res.next())
            {
                rm[i]=res.getString(1);
                sm[i]=res.getString(2);
                amt[i]=String.valueOf(res.getFloat(3));
                rmark[i]=res.getString(4);
                val[i]=res.getString(5);

                Statement stmt1 = con.createStatement();
                ResultSet rest = stmt1.executeQuery("select name from [User] where mobile_no='" + sm[i] + "'");
                if (rest.next()) {
                    name[i] = rest.getString(1);
                }

                i++;
            }
        }
        catch (Exception e){
            Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show();
        }

        listrjava lst=new listrjava(this,name,amt);
        list=(ListView)findViewById(R.id.nlist);
        list.setAdapter(lst);


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                AlertDialog.Builder builder = new AlertDialog.Builder(notification_java.this);
                builder.setMessage("Request from: "+name[position]+"\nReason: "+rmark[position]+"\nRequired Till: "+val[position]+"\nAmount: ₹ "+amt[position]+"\nDo you want to pay to request?").setCancelable(false).setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }
                ).setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent i=new Intent(notification_java.this,enteramount.class);
                        Bundle b=new Bundle();
                        b.putString("smobile",mobile);
                        b.putString("rmobile",sm[position]);
                        i.putExtras(b);
                        startActivity(i);
                    }
                });
                AlertDialog alert = builder.create();
                alert.setTitle("Transaction Detail");
                alert.setIcon(R.mipmap.ic_launcher);
                alert.show();
            }
        });

    }
}
