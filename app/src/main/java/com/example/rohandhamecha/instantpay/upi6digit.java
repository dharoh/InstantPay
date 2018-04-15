package com.example.rohandhamecha.instantpay;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Rohan Dhamecha on 08-02-2018.
 */

public class upi6digit extends AppCompatActivity {

    EditText[] p;
    int cur_et=0;
    Boolean show=true;
    String account;
    String mobile;
    String upists;
    int bankid;
    int attempt=1;
    String upis;
    TextView t;
    Connection con;
    Bundle bundle;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upi_6digit);

        bundle=getIntent().getExtras();
        account=bundle.getString("account");
        mobile=bundle.getString("mobile");
        upists=bundle.getString("upi_sts");
        bankid=bundle.getInt("bankid");

        t=(TextView)findViewById(R.id.textView15);
        SQLConnection sc=new SQLConnection();
        con=sc.connect();
        TextView tv=(TextView)findViewById(R.id.Bname);
        try{
            Statement stmt=con.createStatement();
            ResultSet res=stmt.executeQuery("select bank_name from [Bank] where bank_id='"+bankid+"'");
            if(res.next())
            {
                tv.setText(res.getString(1));
            }

        }
        catch (Exception e)
        {

        }


        p=new EditText[6];

        p[0]=(EditText)findViewById(R.id.Upi1);
        p[1]=(EditText)findViewById(R.id.Upi2);
        p[2]=(EditText)findViewById(R.id.Upi3);
        p[3]=(EditText)findViewById(R.id.Upi4);
        p[4]=(EditText)findViewById(R.id.upi5);
        p[5]=(EditText)findViewById(R.id.upi6);



        for(int i=0;i<6;i++)
            p[i].setEnabled(false);

    }

    public void press0(View v)
    {
        if(p[cur_et].getText().toString().equals(""))
            p[cur_et].setText("0");
        if(cur_et!=5)
            cur_et++;
    }

    public void press1(View v1)
    {
        if(p[cur_et].getText().toString().equals(""))
            p[cur_et].setText("1");
        if(cur_et!=5)
            cur_et++;
    }

    public void press2(View v2)
    {
        if(p[cur_et].getText().toString().equals(""))
            p[cur_et].setText("2");
        if(cur_et!=5)
            cur_et++;
    }

    public void press3(View v3)
    {
        if(p[cur_et].getText().toString().equals(""))
            p[cur_et].setText("3");
        if(cur_et!=5)
            cur_et++;
    }

    public void press4(View v4)
    {
        if(p[cur_et].getText().toString().equals(""))
            p[cur_et].setText("4");
        if(cur_et!=5)
            cur_et++;
    }

    public void press5(View v5)
    {
        if(p[cur_et].getText().toString().equals(""))
            p[cur_et].setText("5");
        if(cur_et!=5)
            cur_et++;
    }

    public void press6(View v6)
    {
        if(p[cur_et].getText().toString().equals(""))
            p[cur_et].setText("6");
        if(cur_et!=5)
            cur_et++;
    }

    public void press7(View v7)
    {
        if(p[cur_et].getText().toString().equals(""))
            p[cur_et].setText("7");
        if(cur_et!=5)
            cur_et++;
    }

    public void press8(View v8)
    {
        if(p[cur_et].getText().toString().equals(""))
            p[cur_et].setText("8");
        if(cur_et!=5)
            cur_et++;
    }

    public void press9(View v9)
    {
        if(p[cur_et].getText().toString().equals(""))
            p[cur_et].setText("9");
        if(cur_et!=5)
            cur_et++;
    }

    public void backspace(View vv)
    {
        if(cur_et==5)
            p[cur_et].setText("");
        else if(cur_et>=0)
        p[cur_et].setText("");
        if(cur_et!=0)
            cur_et--;

    }

    public void Show(View v)
    {
        ImageView img=(ImageView)findViewById(R.id.imageView4);

        if(show==false) {
            img.setImageResource(R.mipmap.eye1);
            for(int i=0;i<6;i++)
                p[i].setTransformationMethod(PasswordTransformationMethod.getInstance());

            show=true;
        }
        else if(show==true)
        {
            img.setImageResource(R.mipmap.eye);
            for(int i=0;i<6;i++)
                p[i].setTransformationMethod(HideReturnsTransformationMethod.getInstance());

            show=false;
        }


    }

    public void forgetUpi(View v)
    {
        Intent i=new Intent(upi6digit.this,set_upi.class);
        bundle.remove("upi_sts");
        bundle.putInt("upisize",6);
        i.putExtras(bundle);
        startActivity(i);
    }

    public void next(View v)
    {
        String upi=p[0].getText().toString()+p[1].getText().toString()+p[2].getText().toString()+p[3].getText().toString()+p[4].getText().toString()+p[5].getText().toString();
        ResultSet res;
        try {

            if(upists.equals("yes")) {
                Statement stmt = con.createStatement();
                res = stmt.executeQuery("select upi_pin from [bank_data] where account_no='" + account + "' and upi_pin='" + upi + "'");

                if (res.next()) {

                    ResultSet rest= stmt.executeQuery("select * from [bank_data] where account_no='"+account+"'");

                    if(rest.next()) {
                        stmt.executeUpdate("update [User] set bank_id='" + rest.getString(3) + "',debit_card_no='"+rest.getString(2)+"',account_no='"+rest.getString(1)+"',name='"+rest.getString(7)+"' where mobile_no='"+mobile+"'");
                        SimpleDateFormat sd=new SimpleDateFormat("dd/MM/yyyy,HH:mm");
                        sd.setTimeZone(TimeZone.getTimeZone("GMT+05:30"));
                        Date dt=new Date();
                        String time=sd.format(dt);
                        stmt.execute("insert into [Login_detail](mobile_no,tdate,login_type,account_no)values('"+mobile+"','"+time+"','N','"+account+"')");

                    }
                    Toast.makeText(this, "Successfully bank added", Toast.LENGTH_SHORT).show();

                    Intent i=new Intent(upi6digit.this,MainActivity.class);

                    Bundle b=new Bundle();
                    b.putString("mobile",mobile);
                    i.putExtras(b);
                    startActivity(i);
                }
                else
                {
                    for(int i=0;i<6;i++)
                        p[i].setText("");

                    cur_et=0;
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("You have entered wrong UPI pin...").setCancelable(false).setNeutralButton("OK",
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
            else if(upists.equals("check"))
            {
                String rmobile;
                String paytype=bundle.getString("paytype");
                Statement stmt=con.createStatement();
                Float money=Float.parseFloat(bundle.getString("money"));
                ResultSet rs = stmt.executeQuery("select upi_pin,balance from [bank_data] where account_no='" + account + "' and upi_pin='" + upi + "'");

                if(rs.next())
                {
                    if( rs.getFloat(2)>=money) {

                        String tid=null;
                        String rec_ac=null;
                        String time=null;

                        ResultSet rst=stmt.executeQuery("select * from [Trans_id]");

                        if(rst.next())
                        {
                            tid=rst.getString(1);
                            CallableStatement cst=con.prepareCall("EXEC dbo.update_id @id='"+tid+"'");
                            cst.execute();
                        }
                        rst.close();
                        rst=stmt.executeQuery("select account_no from [User] where mobile_no='"+bundle.getString("rec")+"'");
                        if(rst.next())
                        {
                            rec_ac=rst.getString(1);
                        }

                        SimpleDateFormat sd=new SimpleDateFormat("dd/MM/yyyy,HH:mm");
                        sd.setTimeZone(TimeZone.getTimeZone("GMT+05:30"));
                        Date dt=new Date();
                        time=sd.format(dt);

                        PreparedStatement smt=con.prepareStatement("INSERT INTO [Transaction](trans_id,amount,rec_acno,rec_mobile,trans_type,send_acno,send_mobile,tdate) VALUES(?,?,?,?,?,?,?,? )");
                        smt.setBigDecimal(1, BigDecimal.valueOf(Long.parseLong(tid)));
                        smt.setFloat(2,money);
                        smt.setBigDecimal(3,BigDecimal.valueOf(Long.parseLong(rec_ac)));
                        smt.setBigDecimal(4,BigDecimal.valueOf(Long.parseLong(bundle.getString("rec"))));
                        smt.setString(5,paytype);
                        smt.setBigDecimal(6,BigDecimal.valueOf(Long.parseLong(account)));
                        smt.setBigDecimal(7,BigDecimal.valueOf(Long.parseLong(mobile)));
                        smt.setString(8,time);
                        int z=smt.executeUpdate();

                        if(z>0) {
                            Intent i = new Intent(upi6digit.this, sucesstransaction.class);
                            Bundle b=new Bundle();
                            b.putString("tid",tid);
                            b.putString("ptype",paytype);

                            i.putExtras(b);
                            finishAffinity();
                            startActivity(i);
                        }
                        else
                        {
                            AlertDialog.Builder builder = new AlertDialog.Builder(this);
                            builder.setMessage("Sorry..due to some technical reasons we are not able to complete your transaction..Please try again later.").setCancelable(false).setNeutralButton("OK",
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
                        AlertDialog.Builder builder = new AlertDialog.Builder(upi6digit.this);
                        builder.setMessage("Sorry, you can't complete the transaction due to insufficient balance.").setCancelable(true).setNeutralButton("OK",
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
            else if(upists.equals("bal"))
            {
                Statement stmt=con.createStatement();
                ResultSet rest=stmt.executeQuery("select balance from [bank_data] where account_no=(select account_no from [User] where mobile_no='"+mobile+"')");
                if(rest.next())
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(upi6digit.this);
                    builder.setMessage("You have current balance is account is "+rest.getString(1).substring(0,rest.getString(1).length()-2)).setCancelable(false).setNeutralButton("OK",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    Intent i=new Intent(upi6digit.this,bankaccount.class);
                                    Bundle b=new Bundle();
                                    b.putString("mobile",mobile);
                                    i.putExtras(b);
                                    finishAffinity();
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
            else
            {

                if(attempt==1)
                {
                    upis=p[0].getText().toString()+p[1].getText().toString()+p[2].getText().toString()+p[3].getText().toString()+p[4].getText().toString()+p[5].getText().toString();
                    for(int i=0;i<6;i++)
                        p[i].setText("");
                        attempt=2;
                    t.setText("CONFIRM UPI PIN");
                    cur_et=0;
                }
                else if(attempt==2)
                {

                    if(upis.equals(p[0].getText().toString()+p[1].getText().toString()+p[2].getText().toString()+p[3].getText().toString()+p[4].getText().toString()+p[5].getText().toString()))
                    {
                        Statement s=con.createStatement();
                        s.executeUpdate("update bank_data set upi_pin='"+upis+"' where account_no='"+account+"'");
                        Toast.makeText(this,"UPI confirmed",Toast.LENGTH_SHORT).show();
                        Statement stmt=con.createStatement();
                        ResultSet rest= stmt.executeQuery("select * from [bank_data] where account_no='"+account+"'");

                        if(rest.next()) {
                            stmt.executeUpdate("update [User] set bank_id='" + rest.getString(3) + "',debit_card_no='"+rest.getString(2)+"',account_no='"+rest.getString(1)+"',name='"+rest.getString(7)+"' where mobile_no='"+mobile+"'");

                        }
                        Intent i=new Intent(upi6digit.this,MainActivity.class);
                        SimpleDateFormat sd=new SimpleDateFormat("dd/MM/yyyy,HH:mm");
                        sd.setTimeZone(TimeZone.getTimeZone("GMT+05:30"));
                        Date dt=new Date();
                        String time=sd.format(dt);
                        stmt.execute("insert into [Login_detail](mobile_no,tdate,login_type,account_no)values('"+mobile+"','"+time+"','N','"+account+"')");

                        Bundle b=new Bundle();
                        b.putString("mobile",mobile);
                        i.putExtras(b);
                        startActivity(i);
                    }
                    else
                    {
                        for(int i=0;i<6;i++)
                            p[i].setText("");
                        attempt=1;
                        t.setText("ENTER UPI PIN");
                        Toast.makeText(this,"UPI not confirmed.Try again",Toast.LENGTH_SHORT).show();
                        cur_et=0;
                    }


                }

            }
        }
        catch (Exception e)
        {}


    }

}
