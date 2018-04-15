package com.example.rohandhamecha.instantpay;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.sql.CallableStatement;
import java.text.SimpleDateFormat;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class RaiseComplaint extends AppCompatActivity {

    Connection con;
    Bundle main;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raise_complaint);
        ImageView bt=(ImageView)findViewById(R.id.imageView);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        main = getIntent().getExtras();
    }

    public void submitClick(View view){

        EditText comp = findViewById(R.id.editText_complaint);

        if(comp.getText().length()!=0){

            SQLConnection sc=new SQLConnection();
            con=sc.connect();

            try{
                Statement stmt1 = con.createStatement();
                ResultSet rs = stmt1.executeQuery("select * from [Trans_id]");
                String tid=null;
                if(rs.next())
                {
                    tid=rs.getString(1);
                    CallableStatement cst=con.prepareCall("EXEC dbo.update_id @id='"+tid+"'");
                    cst.execute();
                }

                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy,HH:mm");
                Date date = new Date();
                Statement stmt = con.createStatement();
                if(!tid.equals("")){
                    stmt.executeUpdate("insert into [complain](ctid,cdata,cdate,mobile_no)values('C"+tid+"','"+comp.getText()+"','"+format.format(date)+"','"+main.getString("mobile")+"')");
                }

                con.close();
                stmt.close();
                stmt1.close();

                AlertDialog.Builder builder = new AlertDialog.Builder(RaiseComplaint.this);
                builder.setMessage("Complain is successfully submitted.").setCancelable(false).setNeutralButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Intent i=new Intent(RaiseComplaint.this,MainActivity.class);
                                i.putExtras(main);
                                finishAffinity();
                                startActivity(i);
                            }
                        }
                );
                AlertDialog alert = builder.create();
                alert.setTitle("Alert Message");
                alert.setIcon(R.mipmap.ic_launcher);
                alert.show();

            }catch (Exception e){

                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(RaiseComplaint.this,"Empty field!",Toast.LENGTH_SHORT).show();
        }
    }
}
