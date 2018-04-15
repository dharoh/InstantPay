package com.example.rohandhamecha.instantpay;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.rohandhamecha.instantpay.R;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;

public class MerchantDetailsActivity extends AppCompatActivity {

    String mobile;
    String email;
    String mid=" ";
    int pin;
    Connection con;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_details);
        Bundle b=getIntent().getExtras();
        mobile=b.getString("mobile");
        EditText mob=(EditText)findViewById(R.id.editText_contactNumber);
        mob.setText(mobile);
        SQLConnection sc=new SQLConnection();
        con=sc.connect();
    }

    public void getValue(View view){

        EditText bname,maddress,mpincode;
        Spinner category;
        Spinner mstate;

        bname = (EditText)findViewById(R.id.editText_bussinessName);
        maddress = (EditText)findViewById(R.id.editText_merchantAddress);
        mstate = (Spinner) findViewById(R.id.spinner_state);
        mpincode = (EditText)findViewById(R.id.editText_pincode);
        category = (Spinner) findViewById(R.id.spinner_category);

        String name,address,state,pincode,cat;

        name = bname.getText().toString();
        address = maddress.getText().toString();
        state = mstate.getSelectedItem().toString();
        pincode = mpincode.getText().toString();
        cat = category.getSelectedItem().toString();

        if(name.length()!=0 && address.length()!=0 && !state.equals("Select State") && pincode.length()==6 && !cat.equals("Select Category")){

            try{

                Statement stmt=con.createStatement();
               String ac_no=" ";
                int bid=0;
                ResultSet res=stmt.executeQuery("select * from [merc_id]");
                if(res.next())
                {
                    mid=res.getString(1);
                }
                CallableStatement st=con.prepareCall("EXEC update_mid");
                st.execute();
                res=stmt.executeQuery("select account_no,bank_id,email_id from [User] where mobile_no='"+mobile+"'");
                if(res.next())
                {
                    ac_no=res.getString(1);
                    bid=res.getInt(2);
                    email=res.getString(3);
                }

                Random rnd=new Random();
                pin = (int)(Math.random()*9000)+1000;

                PreparedStatement smt=con.prepareStatement("INSERT INTO [merchant] VALUES(?,?,?,?,?,?,?,?,?,?)");
                smt.setBigDecimal(1, BigDecimal.valueOf(Long.parseLong(mid)));
                smt.setBigDecimal(2,BigDecimal.valueOf(Long.parseLong(mobile)));
                smt.setBigDecimal(3,BigDecimal.valueOf(Long.parseLong(ac_no)));
                smt.setString(4,state);
                smt.setString(5,pincode);
                smt.setString(6,name);
                smt.setString(7,cat);
                smt.setString(8,address);
                smt.setBigDecimal(9,BigDecimal.valueOf(pin));
                smt.setBigDecimal(10,BigDecimal.valueOf(bid));
                if(smt.executeUpdate()>0)
                {
                    new SendMail().execute("");
                    AlertDialog.Builder builder = new AlertDialog.Builder(MerchantDetailsActivity.this);
                    builder.setMessage("You are successfully registered as merchant with mobile number "+mobile+" and account XXXXXXXXX"+ac_no.substring(ac_no.length()-4)+". Your merchant ID and merchant PIN has been sent to registered mail ID.Please make login by using it.").setCancelable(true).setNeutralButton("OK",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent main = new Intent(MerchantDetailsActivity.this,MerchantLoginActivity.class);
                                    Bundle b=new Bundle();
                                    b.putString("mobile",mobile);
                                    main.putExtras(b);
                                    startActivity(main);
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(MerchantDetailsActivity.this);
                    builder.setMessage("Some error occurred...please try again later.").setCancelable(true).setNeutralButton("OK",
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
        else{
            Toast.makeText(this,"Fill empty fields!!",Toast.LENGTH_SHORT).show();
        }

        ImageView bt=(ImageView) findViewById(R.id.imageView_back);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private class SendMail extends AsyncTask<String, Integer, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

        }

        protected Void doInBackground(String... params) {
            Mail m = new Mail("instantpayoffice@gmail.com", "finalproject");

            String[] toArr = {email};
            m.setTo(toArr);
            m.setFrom("instantpayoffice@gmail.com");
            m.setSubject("Successfully Registered");
            m.setBody("Welcome to InstantPay, You are successfully registered as merchant. Please use following details for login:\n\n Merchant ID: "+mid+"\nMerchant PIN: "+pin);


            try {
                if(m.send()) {
                    Toast.makeText(MerchantDetailsActivity.this, "Email was sent successfully.", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(MerchantDetailsActivity.this, "Email was not sent.", Toast.LENGTH_LONG).show();
                }
            } catch(Exception e) {

            }
            return null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
// Handle action bar item clicks here. The action bar will
// automatically handle clicks on the Home/Up button, so long
// as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

//noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

}
