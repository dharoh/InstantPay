package com.example.rohandhamecha.instantpay;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by Rohan Dhamecha on 09-02-2018.
 */

public class set_upi extends AppCompatActivity {

    TextView[] p;
    int cur_et=0;
    int cur=0;
    String debit6;
    String expdate;
    String acno;
    Bundle bundle;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setupi);
        bundle=getIntent().getExtras();
        p=new TextView[12];

        ImageView img=(ImageView)findViewById(R.id.imageView6);
        int bid=bundle.getInt("bankid");

        if(bid==1)
        {
            img.setImageResource(R.mipmap.pic1);
        }
        if(bid==9)
        {
            img.setImageResource(R.mipmap.pic2);
        }
        if(bid==2)
        {
            img.setImageResource(R.mipmap.pic3);
        }
        if(bid==3)
        {
            img.setImageResource(R.mipmap.pic4);
        }
        if(bid==4)
        {
            img.setImageResource(R.mipmap.pic5);
        }
        if(bid==5)
        {
            img.setImageResource(R.mipmap.pic6);
        }
        if(bid==6)
        {
            img.setImageResource(R.mipmap.pic7);
        }
        if(bid==7)
        {
            img.setImageResource(R.mipmap.pic8);
        }
        if(bid==8)
        {
            img.setImageResource(R.mipmap.pic9);
        }


        p[0]=(TextView)findViewById(R.id.su1);
        p[1]=(TextView)findViewById(R.id.su2);
        p[2]=(TextView)findViewById(R.id.su3);
        p[3]=(TextView)findViewById(R.id.su4);
        p[4]=(TextView)findViewById(R.id.su5);
        p[5]=(TextView)findViewById(R.id.su6);
        p[6]=(TextView)findViewById(R.id.su7);
        p[7]=(TextView)findViewById(R.id.su8);
        p[8]=(TextView)findViewById(R.id.su9);
        p[9]=(TextView)findViewById(R.id.su10);
        p[10]=(TextView)findViewById(R.id.su11);
        p[11]=(TextView)findViewById(R.id.su12);

        for(int i=0;i<12;i++)
            p[i].setEnabled(false);

        p[0].setBackgroundTintList(ColorStateList.valueOf(Color.RED));

    }

    public void prss0(View v)
    {
        if(p[cur_et].getText().toString().equals(""))
            p[cur_et].setText("0");
        if(cur_et!=11)
            cur_et++;
        refresh();
    }

    public void prss1(View v1)
    {
        if(p[cur_et].getText().toString().equals(""))
            p[cur_et].setText("1");
        if(cur_et!=11)
            cur_et++;
        refresh();
    }

    public void prss2(View v2)
    {
        if(p[cur_et].getText().toString().equals(""))
            p[cur_et].setText("2");
        if(cur_et!=11)
            cur_et++;
        refresh();
    }

    public void prss3(View v3)
    {
        if(p[cur_et].getText().toString().equals(""))
            p[cur_et].setText("3");
        if(cur_et!=11)
            cur_et++;
        refresh();
    }

    public void prss4(View v4)
    {
        if(p[cur_et].getText().toString().equals(""))
            p[cur_et].setText("4");
        if(cur_et!=11)
            cur_et++;
        refresh();
    }

    public void prss5(View v5)
    {
        if(p[cur_et].getText().toString().equals(""))
            p[cur_et].setText("5");
        if(cur_et!=11)
            cur_et++;
        refresh();
    }

    public void prss6(View v6)
    {
        if(p[cur_et].getText().toString().equals(""))
            p[cur_et].setText("6");
        if(cur_et!=11)
            cur_et++;
        refresh();
    }

    public void prss7(View v7)
    {
        if(p[cur_et].getText().toString().equals(""))
            p[cur_et].setText("7");
        if(cur_et!=11)
            cur_et++;
        refresh();
    }

    public void prss8(View v8)
    {
        if(p[cur_et].getText().toString().equals(""))
            p[cur_et].setText("8");
        if(cur_et!=11)
            cur_et++;
        refresh();
    }

    public void prss9(View v9)
    {
        if(p[cur_et].getText().toString().equals(""))
            p[cur_et].setText("9");
        if(cur_et!=11)
            cur_et++;
        refresh();
    }

    public void backpace(View vv)
    {
        if(cur_et==11)
            p[cur_et].setText("");
        else if(cur_et>=0)
        p[cur_et].setText("");
        if(cur_et!=0)
            cur_et--;
        cur=cur_et;
        p[cur+1].setBackgroundTintList(null);
        p[cur].setBackgroundTintList(ColorStateList.valueOf(Color.RED));
    }

    public void refresh()
    {
        if(cur>0)
        {
            p[cur-1].setBackgroundTintList(null);

        }
        if(cur<12) {
            p[cur].setBackgroundTintList(ColorStateList.valueOf(Color.RED));
            cur++;
        }

    }

    public void nxt(View v)
    {

        String deb6=p[0].getText().toString()+p[1].getText().toString()+p[2].getText().toString()+p[3].getText().toString()+p[4].getText().toString()+p[5].getText().toString();
        String expdate=p[6].getText().toString()+p[7].getText().toString()+"/"+p[8].getText().toString()+p[9].getText().toString()+p[10].getText().toString()+p[11].getText().toString();
        SQLConnection sc=new SQLConnection();
        Connection con=sc.connect();
         ResultSet res;
        try {
            Statement stmt = con.createStatement();
            res=stmt.executeQuery("select debit_card_no,expiry_dt from [bank_data] where account_no='"+bundle.get("account")+"'");

            Boolean checked=false;

           while (res.next())
            {
                if(((res.getString(1).substring(10)).equals(deb6)) && ((res.getString(2)).equals(expdate)))
                {
                    checked=true;
                }

            }
if(checked==true) {
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setMessage("Now you can change UPI pin for Account: "+bundle.getString("account")).setCancelable(false).setNeutralButton("OK",
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                            if(bundle.getInt("upisize")==6)
                            {
                                Intent i=new Intent(set_upi.this,upi6digit.class);
                                bundle.putString("upi_sts","no");
                                i.putExtras(bundle);
                                startActivity(i);
                    }
                    else
                            {
                                Intent i=new Intent(set_upi.this,upi4digit.class);
                                bundle.putString("upi_sts","no");
                                i.putExtras(bundle);
                                startActivity(i);
                            }
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
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setMessage("The data entered by you don't matched with your account..try again!").setCancelable(false).setNeutralButton("OK",
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
}
