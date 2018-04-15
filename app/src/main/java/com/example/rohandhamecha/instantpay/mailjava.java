package com.example.rohandhamecha.instantpay;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rohan Dhamecha on 18-01-2018.
 */

public class mailjava extends AppCompatActivity {

    Spinner dropdwn;
    EditText emailt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selectmailid);


        Account[] accounts = AccountManager.get(this).getAccountsByType("com.google");

        dropdwn = (Spinner) findViewById(R.id.spinner);
        List<String> email = new ArrayList<String>();
        email.add(0, "Select Email ID");
        int i = 1;
        for (Account account : accounts) {
            email.add(i, account.name);
            i++;
        }

        ArrayAdapter<String> adp = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, email);
        dropdwn.setAdapter(adp);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure want to exit?").setCancelable(false).setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        moveTaskToBack(true);

                        android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(1);

                    }
                }


        ).setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alert = builder.create();
        alert.setTitle("Alert Message");
        alert.setIcon(R.mipmap.ic_launcher);
        alert.show();

    }

    protected void sim_dis(View v) {
        emailt = (EditText) findViewById(R.id.editText2);
        String ab = emailt.getText().toString();
        if (dropdwn.getSelectedItemId() > 0) {
            Intent i = new Intent(mailjava.this, sim_display.class);
            Bundle bundle=new Bundle();
            bundle.putString("email",dropdwn.getSelectedItem().toString());
            i.putExtras(bundle);
            startActivity(i);
        } else if (!ab.equals("")) {

            Intent i =new Intent(mailjava.this,mailotp.class);

            Bundle bundle=new Bundle();
            bundle.putString("email_pass",ab);
            i.putExtras(bundle);
                        startActivity(i);

        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Please select email ID or Enter the email ID.").setCancelable(false).setNeutralButton("OK",
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


