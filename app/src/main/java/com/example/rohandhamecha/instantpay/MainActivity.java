package com.example.rohandhamecha.instantpay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


public class MainActivity extends AppCompatActivity {

    String mobile;
    Connection con;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SQLConnection sc=new SQLConnection();
        con=sc.connect();
        Bundle b=getIntent().getExtras();
        mobile=b.getString("mobile");

        TextView txt=(TextView)findViewById(R.id.textView42);

        try {
            Statement stmt = con.createStatement();
            ResultSet res=stmt.executeQuery("select bank_id from [User] where mobile_no='"+mobile+"'" );
            ImageView img=(ImageView)findViewById(R.id.imageView_banklogo);
            String bid=null;
            if(res.next())
            {
                bid=res.getString(1);
            }

            if(bid.equals("1"))
            {
                img.setImageResource(R.mipmap.pic1);
                txt.setText("Axis Bank");
            }

            if(bid.equals("2"))
            {
                img.setImageResource(R.mipmap.pic3);
                txt.setText("Bank of Baroda");
            }

            if(bid.equals("3"))
            {
                img.setImageResource(R.mipmap.pic4);
                txt.setText("Canara Bank");
            }

            if(bid.equals("4"))
            {
                img.setImageResource(R.mipmap.pic5);
                txt.setText("Dena Bank");
            }

            if(bid.equals("5"))
            {
                img.setImageResource(R.mipmap.pic6);
                txt.setText("HDFC Bank");
            }

            if(bid.equals("6"))
            {
                img.setImageResource(R.mipmap.pic7);
                txt.setText("ICICI Bank");
            }

            if(bid.equals("7"))
            {
                img.setImageResource(R.mipmap.pic8);
                txt.setText("IDFC Bank");
            }

            if(bid.equals("8"))
            {
                img.setImageResource(R.mipmap.pic9);
                txt.setText("Punjab National Bank");
            }

            if(bid.equals("9"))
            {
                img.setImageResource(R.mipmap.pic2);
                txt.setText("State Bank of India");
            }
        }
        catch (Exception e){}

        ListView list=(ListView)findViewById(R.id.mainlist);
        Integer[] imgid={
          R.drawable.trans,
          R.drawable.user,
          R.drawable.bank,
          R.mipmap.bell
        };

        String[] item={
          "Transactions","Profile","Bank Account","Notifications"
        };
        CustomListAdapter ap=new CustomListAdapter(this,item,imgid);
        list.setAdapter(ap);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(position==0)
                {
                    Intent i=new Intent(MainActivity.this,trans_java.class);
                    Bundle b=new Bundle();
                    b.putString("mobile",mobile);
                    b.putString("ttype","normal");
                    i.putExtras(b);
                    startActivity(i);
                }

                if(position==1)
                {
                    Intent i=new Intent(MainActivity.this,displayqr.class);
                    Bundle b=new Bundle();
                    b.putString("mobile",mobile);
                    i.putExtras(b);
                    startActivity(i);
                }

                if(position==2)
                {
                    Intent i=new Intent(MainActivity.this,bankaccount.class);
                    Bundle b=new Bundle();
                    b.putString("mobile",mobile);
                    i.putExtras(b);
                    startActivity(i);
                }
                if(position==3)
                {
                    Intent i=new Intent(MainActivity.this,notification_java.class);
                    Bundle b=new Bundle();
                    b.putString("mobile",mobile);
                    i.putExtras(b);
                    startActivity(i);
                }
            }
        });

        setOnClickListeners();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id=item.getItemId();

        switch (id)
        {

            case R.id.cmail:
                Intent i5=new Intent(MainActivity.this,mailjava.class);
                finishAffinity();
                startActivity(i5);
                break;


            case R.id.csim:
                Intent i=new Intent(MainActivity.this,sim_display.class);
                try {
                    Statement stmt = con.createStatement();
                    ResultSet res=stmt.executeQuery("select email_id from [User] where mobile_no='"+mobile+"'");
                    if(res.next())
                    {
                        Bundle b=new Bundle();
                        b.putString("email",res.getString(1));
                        i.putExtras(b);
                        startActivity(i);
                        finishAffinity();
                    }

                }
                catch (Exception e){}

                break;

            case R.id.mlogin:

                try {
                    Statement stmt = con.createStatement();
                    ResultSet res=stmt.executeQuery("select merc_id from [merchant] where mobile_no='"+mobile+"'");
                    if(res.next())
                    {
                        Intent i3=new Intent(MainActivity.this,MerchantLoginActivity.class);
                        Bundle b2=new Bundle();
                        b2.putString("mobile",mobile);
                        i3.putExtras(b2);
                        startActivity(i3);
                    }
                    else
                    {
                        Intent i3=new Intent(MainActivity.this,MerchantDetailsActivity.class);
                        Bundle b2=new Bundle();
                        b2.putString("mobile",mobile);
                        i3.putExtras(b2);
                        startActivity(i3);
                    }


                }
                catch (Exception e){}




                break;

            case R.id.gqc:
                Intent i1=new Intent(MainActivity.this,displayqr.class);
                Bundle b=new Bundle();
                b.putString("mobile",mobile);
                i1.putExtras(b);
                startActivity(i1);
                break;

            case R.id.complaint:
                Intent raise = new Intent(MainActivity.this,RaiseComplaint.class);
                Bundle br =new Bundle();
                br.putString("mobile",mobile);
                raise.putExtras(br);
                startActivity(raise);
                break;

            case R.id.us:
                Intent i3 = new Intent(MainActivity.this,About_us.class);
                startActivity(i3);
                break;

            case R.id.logout:
                Intent i2=new Intent(MainActivity.this,app_pin.class);
                try {
                    Statement stmt = con.createStatement();
                    ResultSet res=stmt.executeQuery("select email_id from [User] where mobile_no='"+mobile+"'");
                    if(res.next())
                    {
                        Bundle b1=new Bundle();
                        b1.putString("email",res.getString(1));
                        b1.putString("mobile",mobile);
                        i2.putExtras(b1);
                        startActivity(i2);
                        finishAffinity();
                    }

                }
                catch (Exception e){}

                break;




        }

        return true;
    }

    protected void setOnClickListeners(){

        //Send-Money Button
        ImageButton Send = (ImageButton)findViewById(R.id.imageButton_Send);
        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,SendMoneyActivity.class);
                Bundle b=new Bundle();
                b.putString("mobile",mobile);
                b.putString("type","send");
                intent.putExtras(b);
                startActivity(intent);
            }
        });


        ImageButton req=(ImageButton)findViewById(R.id.imageButton_Request);
        req.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SendMoneyActivity.class);
                Bundle b=new Bundle();
                b.putString("mobile",mobile);
                b.putString("type","req");
                intent.putExtras(b);
                startActivity(intent);
            }
        });



        //Scan&Pay button
        ImageButton QR = (ImageButton)findViewById(R.id.imageButton_QR);
        QR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,scanqr.class);
                Bundle b=new Bundle();
                b.putString("mobile",mobile);
                i.putExtras(b);
                startActivity(i);
            }
        });

    }


}
