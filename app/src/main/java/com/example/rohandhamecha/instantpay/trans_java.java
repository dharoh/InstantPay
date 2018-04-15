package com.example.rohandhamecha.instantpay;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by Rohan Dhamecha on 18-03-2018.
 */

public class trans_java extends AppCompatActivity {

    Integer[] imgid;
    String[] ttext;
    String[] tdate;
    String[] rup;
    String[] name;
    String[] number;
    String[] tid;
    String[] ptype;
    Connection con;
    ListView list;
    String mobile;
    String ttype;
    int count=0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transaction);
        SQLConnection sc=new SQLConnection();
        con=sc.connect();
        Bundle b= getIntent().getExtras();
        mobile=b.getString("mobile");
        ttype=b.getString("ttype");

        try {

            Statement stmt=con.createStatement();

            if(ttype.equals("normal")) {
                ResultSet res = stmt.executeQuery("select trans_id from [Transaction] where send_mobile='" + mobile + "' or (rec_mobile='" + mobile + "' and trans_type='normal')");
                while (res.next()) {
                    count++;
                }
                imgid = new Integer[count];
                ttext = new String[count];
                tdate = new String[count];
                rup = new String[count];
                name = new String[count];
                number = new String[count];
                tid=new String[count];
                ptype=new String[count];

                res = stmt.executeQuery("select * from [Transaction] where send_mobile='" + mobile + "' order by trans_id DESC");
                int i = 0;
                while (res.next()) {
                    tid[i]=res.getString(1);
                    ptype[i]=res.getString(5);
                    imgid[i] = R.mipmap.sent;
                    ttext[i] = "Sent To";
                    tdate[i] = res.getString(8);
                    rup[i] = "₹" + res.getString(2).substring(0, res.getString(2).length() - 5);
                    number[i] = res.getString(4);
                    if(res.getString(5).equals("normal")) {
                        Statement stmt1 = con.createStatement();
                        ResultSet rest = stmt1.executeQuery("select name from [User] where mobile_no='" + number[i] + "'");
                        if (rest.next()) {
                            name[i] = rest.getString(1);
                        }
                    }
                    else
                    {
                        Statement stmt1 = con.createStatement();
                        ResultSet rest = stmt1.executeQuery("select buis_name from [merchant] where mobile_no='" + number[i] + "'");
                        if (rest.next()) {
                            name[i] = rest.getString(1);
                        }
                    }
                    i++;
                }
                res.close();
                res = stmt.executeQuery("select * from [Transaction] where rec_mobile='" + mobile + "' and trans_type='normal' order by trans_id DESC");
                while (res.next()) {
                    tid[i]=res.getString(1);
                    ptype[i]=res.getString(5);
                    imgid[i] = R.mipmap.rec;
                    ttext[i] = "Receive From";
                    tdate[i] = res.getString(8);
                    rup[i] = "₹" + res.getString(2).substring(0, res.getString(2).length() - 5);
                    number[i] = res.getString(7);
                    Statement stmt1 = con.createStatement();
                    ResultSet rest = stmt1.executeQuery("select name from [User] where mobile_no='" + number[i] + "'");
                    if (rest.next()) {
                        name[i] = rest.getString(1);
                    }
                    i++;
                }
                res.close();
                CustomListTrans clt = new CustomListTrans(this, ttext, imgid, tdate, rup, name, number);
                list = (ListView) findViewById(R.id.tlist);
                list.setAdapter(clt);
            }
            else
            {
                ResultSet res = stmt.executeQuery("select trans_id from [Transaction] where rec_mobile='" + mobile + "' and trans_type='merchant'");
                while (res.next()) {
                    count++;
                }
                imgid = new Integer[count];
                ttext = new String[count];
                tdate = new String[count];
                rup = new String[count];
                name = new String[count];
                number = new String[count];
                tid=new String[count];
                ptype=new String[count];

                res = stmt.executeQuery("select * from [Transaction] where rec_mobile='" + mobile + "' and trans_type='merchant' order by trans_id DESC");
                int i = 0;
                while (res.next()) {
                    tid[i]=res.getString(1);
                    ptype[i]=res.getString(5);
                    imgid[i] = R.mipmap.rec;
                    ttext[i] = "Received from";
                    tdate[i] = res.getString(8);
                    rup[i] = "₹" + res.getString(2).substring(0, res.getString(2).length() - 5);
                    number[i] = res.getString(7);
                    Statement stmt1 = con.createStatement();
                    ResultSet rest = stmt1.executeQuery("select name from [User] where mobile_no='" + number[i] + "'");
                    if (rest.next()) {
                        name[i] = rest.getString(1);
                    }
                    i++;
                }
                res.close();

                CustomListTrans clt = new CustomListTrans(this, ttext, imgid, tdate, rup, name, number);
                list = (ListView) findViewById(R.id.tlist);
                list.setAdapter(clt);
            }
        }
        catch (Exception e){
            Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show();
        }

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                AlertDialog.Builder builder = new AlertDialog.Builder(trans_java.this);
                builder.setMessage("Transaction ID: "+tid[position]+"\nName: "+name[position]+"\nMobile Number: "+number[position]+"\nDate: "+tdate[position]+"\nAmount: "+rup[position]+"\nPayment Type: "+ptype[position]).setCancelable(false).setNeutralButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }
                );
                AlertDialog alert = builder.create();
                alert.setTitle("Transaction Detail");
                alert.setIcon(R.mipmap.ic_launcher);
                alert.show();

            }
        });

        ImageView bt=(ImageView)findViewById(R.id.imageView17);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
