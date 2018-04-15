package com.example.rohandhamecha.instantpay;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Rohan Dhamecha on 24-01-2018.
 */

public class mailotp extends AppCompatActivity {
    EditText et;
String email;
int otp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mail_otp);
        TextView emailt=(TextView)findViewById(R.id.textView6);
        et=(EditText)findViewById(R.id.editText3);
        Bundle bundle = getIntent().getExtras();
        email=bundle.getString("email_pass");
        emailt.setText(email);

        otp=(int)(Math.random()*9000)+1000;

       new SendMail().execute("");

    }

    @Override
    public void onBackPressed()
    {}

    public void next(View v)
    {
        String Otp=String.valueOf(otp);
        if(et.getText().toString().equals(Otp)) {
            Intent i = new Intent(mailotp.this, sim_display.class);
            Bundle bundle=new Bundle();
            bundle.putString("email",email);
            i.putExtras(bundle);

            startActivity(i);
        }
    }
    public void prev(View v)
    {
        Intent i=new Intent(mailotp.this,mailjava.class);
        startActivity(i);
    }

    public void resendotp(View v)
    {
        otp=(int)(Math.random()*9000)+1000;
        new SendMail().execute("");

    }

    private class SendMail extends AsyncTask<String, Integer, Void> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(mailotp.this, "Please wait", "Sending OTP", true, false);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();

        }

        protected Void doInBackground(String... params) {
            Mail m = new Mail("instantpayoffice@gmail.com", "finalproject");

            String[] toArr = {email};
            m.setTo(toArr);
            m.setFrom("instantpayoffice@gmail.com");
            m.setSubject("OTP - InstantPay");
            m.setBody("Your OTP is: "+otp);

            try {
                if(m.send()) {
                    Toast.makeText(mailotp.this, "Email was sent successfully.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(mailotp.this, "Email was not sent.", Toast.LENGTH_LONG).show();
                }
            } catch(Exception e) {
                Log.e("MailApp", "Could not send email", e);
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
