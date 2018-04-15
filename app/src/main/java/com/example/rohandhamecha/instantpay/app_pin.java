package com.example.rohandhamecha.instantpay;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Rohan Dhamecha on 28-01-2018.
 */

public class app_pin extends AppCompatActivity {

    DatabaseHelper myDb;

    TextView t0,t1,t2,t3,t4,t5,t6,t7,t8,t9,text11;
    ImageButton b1,b2;
    Switch s;
EditText[] p;
    int cur_et=0;
    String sts="signup";
    int attempt=1;
String pw;
String email,mobile;
Connection con;
ResultSet res;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apppin);

        Bundle bundle=getIntent().getExtras();
        email=bundle.getString("email");
        mobile=bundle.getString("mobile");
        text11=(TextView)findViewById(R.id.textView11);

        SQLConnection sc=new SQLConnection();
        con=sc.connect();

        try {
            Statement stmt = con.createStatement();
            String q="select * from [User] where mobile_no='"+mobile+"'";
            res=stmt.executeQuery(q);

            if(res.next())
            {
                if(email.equals(res.getString(2)))
                {
                    text11.setText("Login - Enter Passcode");

                    sts="login";
                    pw=res.getString(3);
                }
                else
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("Email ID does not matched.Select again.").setCancelable(false).setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent i =new Intent(app_pin.this,mailjava.class);
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
        }
        catch (Exception e)
        {
            text11.setText(e.getMessage().toString());
        }

        p=new EditText[4];

        t0=(TextView)findViewById(R.id.t0);
        t1=(TextView)findViewById(R.id.t1);
        t2=(TextView)findViewById(R.id.t2);
        t3=(TextView)findViewById(R.id.t3);
        t4=(TextView)findViewById(R.id.t4);
        t5=(TextView)findViewById(R.id.t5);
        t6=(TextView)findViewById(R.id.t6);
        t7=(TextView)findViewById(R.id.t7);
        t8=(TextView)findViewById(R.id.t8);
        t9=(TextView)findViewById(R.id.t9);

        s=(Switch)findViewById(R.id.switch2);
        p[0]=(EditText)findViewById(R.id.p1);
        p[1]=(EditText)findViewById(R.id.p2);
        p[2]=(EditText)findViewById(R.id.p3);
        p[3]=(EditText)findViewById(R.id.p4);

        b1=(ImageButton)findViewById(R.id.imageButton3);
        b2=(ImageButton)findViewById(R.id.imageButton4);

        p[0].setEnabled(false);
        p[1].setEnabled(false);
        p[2].setEnabled(false);
        p[3].setEnabled(false);
    }

    public void press0(View v)
    {
        if(p[cur_et].getText().toString().equals(""))
        p[cur_et].setText("0");
        if(cur_et!=3)
        cur_et++;
    }

    public void press1(View v1)
    {
        if(p[cur_et].getText().toString().equals(""))
        p[cur_et].setText("1");
        if(cur_et!=3)
            cur_et++;
    }

    public void press2(View v2)
    {
        if(p[cur_et].getText().toString().equals(""))
        p[cur_et].setText("2");
        if(cur_et!=3)
            cur_et++;
    }

    public void press3(View v3)
    {
        if(p[cur_et].getText().toString().equals(""))
        p[cur_et].setText("3");
        if(cur_et!=3)
            cur_et++;
    }

    public void press4(View v4)
    {
        if(p[cur_et].getText().toString().equals(""))
        p[cur_et].setText("4");
        if(cur_et!=3)
            cur_et++;
    }

    public void press5(View v5)
    {
        if(p[cur_et].getText().toString().equals(""))
        p[cur_et].setText("5");
        if(cur_et!=3)
            cur_et++;
    }

    public void press6(View v6)
    {
        if(p[cur_et].getText().toString().equals(""))
        p[cur_et].setText("6");
        if(cur_et!=3)
            cur_et++;
    }

    public void press7(View v7)
    {
        if(p[cur_et].getText().toString().equals(""))
        p[cur_et].setText("7");
        if(cur_et!=3)
            cur_et++;
    }

    public void press8(View v8)
    {
        if(p[cur_et].getText().toString().equals(""))
        p[cur_et].setText("8");
        if(cur_et!=3)
            cur_et++;
    }

    public void press9(View v9)
    {
        if(p[cur_et].getText().toString().equals(""))
        p[cur_et].setText("9");
        if(cur_et!=3)
            cur_et++;
    }

    public void backspace(View vv)
    {
        p[cur_et].setText("");
        if(cur_et!=0)
            cur_et--;

    }

    public void nextpressed(View v)
    {
        if(sts.equals("signup") && attempt==1)
        {

            pw=p[0].getText().toString()+p[1].getText().toString()+p[2].getText().toString()+p[3].getText().toString();
            text11.setText("Confirm Passcode");
            for(int i=0;i<4;i++) {
                p[i].setText("");
            }
            cur_et=0;
            attempt=2;
        }
         else if(attempt==2)
        {

            if(pw.equals(p[0].getText().toString()+p[1].getText().toString()+p[2].getText().toString()+p[3].getText().toString()))
            {
                try {
                    PreparedStatement stmt=con.prepareStatement("INSERT INTO [User](mobile_no,email_id,app_pin,user_type,bank_id,debit_card_no,account_no)VALUES(?,?,?,?,?,?,?)");
                    stmt.setString(1,mobile);
                    stmt.setString(2,email);
                    stmt.setString(3,pw);
                    stmt.setString(4,"N");
                    stmt.setString(5,null);
                    stmt.setBigDecimal(6,null);
                    stmt.setBigDecimal(7,null);
                    stmt.execute();

//                    myDb = new DatabaseHelper(this);
//                    myDb.insertData(mobile,email);

                    SharedPreferences.Editor editor=getSharedPreferences("InstantPay_db",MODE_PRIVATE).edit();
                    editor.putString("email",email);
                    editor.putString("mobile",mobile);
                    editor.apply();

                    Intent i=new Intent(app_pin.this,banklist.class);
                    Bundle bundle=new Bundle();
                    bundle.putString("mobile",mobile);
                    finishAffinity();
                    i.putExtras(bundle);
                    startActivity(i);
                }
                catch (Exception e)
                {
                    text11.setText(e.getMessage().toString());
                }

            }

            else
            {
                Toast.makeText(this,"Passcode not match..Try again!!",Toast.LENGTH_LONG);
                text11.setText("Set the Passcode");
                for(int i=0;i<4;i++) {
                    p[i].setText("");
                }
                cur_et=0;
                attempt=1;
                pw="";
            }
        }

        if(sts.equals("login"))
        {
            if(pw.equals(p[0].getText().toString()+p[1].getText().toString()+p[2].getText().toString()+p[3].getText().toString()))
            {


                SharedPreferences.Editor editor=getSharedPreferences("InstantPay_db",MODE_PRIVATE).edit();
                editor.putString("email",email);
                editor.putString("mobile",mobile);
                editor.apply();
ResultSet res;
                try {
                    Statement stmt = con.createStatement();
                    res=stmt.executeQuery("select bank_id,account_no from [User] where mobile_no='"+mobile+"'");
                    if(res.next())
                    {
                        Intent i = new Intent(app_pin.this, MainActivity.class);
                        SimpleDateFormat sd=new SimpleDateFormat("dd/MM/yyyy,HH:mm");
                        sd.setTimeZone(TimeZone.getTimeZone("GMT+05:30"));
                        Date dt=new Date();
                       String time=sd.format(dt);
                        stmt.execute("insert into [Login_detail](mobile_no,tdate,login_type,account_no)values('"+mobile+"','"+time+"','N','"+res.getString(2)+"')");
                        Bundle bundle = new Bundle();
                        bundle.putString("mobile", mobile);
                        finishAffinity();
                        i.putExtras(bundle);
                        startActivity(i);
                    }
                    else {
                        Intent i = new Intent(app_pin.this, banklist.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("mobile", mobile);
                        finishAffinity();
                        i.putExtras(bundle);
                        startActivity(i);
                    }
                }
                catch (Exception e){}


            }
            else
            {
                for(int i=0;i<4;i++) {
                    p[i].setText("");
                }
                cur_et=0;
                attempt=1;
            }
        }
    }

    public void show(View v)
   {
        if(!s.isChecked())
        {
            for(int i=0;i<4;i++)
                p[i].setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        else
        {
            for(int i=0;i<4;i++)
                p[i].setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }
    }

}
