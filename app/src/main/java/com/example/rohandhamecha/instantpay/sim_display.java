package com.example.rohandhamecha.instantpay;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telecom.TelecomManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.telephony.*;

import java.util.List;

/**
 * Created by Rohan Dhamecha on 20-01-2018.
 */

public class sim_display extends AppCompatActivity {
RadioButton rsim1;
   RadioButton rsim2;
   String email;
   String mobile;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sim_options);

        Bundle bundle=getIntent().getExtras();
        email=bundle.getString("email");
        List<SubscriptionInfo>si=SubscriptionManager.from(getApplicationContext()).getActiveSubscriptionInfoList();
        rsim1=(RadioButton)findViewById(R.id.rsim1);
        rsim2=(RadioButton)findViewById(R.id.rsim2);
        if(si.size()==1)
        {
            SubscriptionInfo spi=si.get(0);
            rsim1.setText(spi.getCarrierName()+" - "+spi.getNumber());
            rsim2.setVisibility(View.INVISIBLE);
        }
        if(si.size()==2)
        {
            SubscriptionInfo spi=si.get(0);
            rsim1.setText(spi.getCarrierName()+" - "+spi.getNumber());
            spi=si.get(1);
            rsim2.setText(spi.getCarrierName()+" - "+spi.getNumber());
        }
    }

    public void sim_select1(View v)
    {
        if(rsim2.isChecked())
            rsim2.setChecked(false);
    }
    public void sim_select2(View v)
    {
if(rsim1.isChecked())
    rsim1.setChecked(false);

    }

    @Override
    public void onBackPressed()
    {
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

    public void simnext(View v)
    {
        if(rsim1.isChecked())
        {
            mobile=rsim1.getText().toString().split(" - ")[1].toString();
        }

        if(rsim2.isChecked())
        {
            mobile=rsim2.getText().toString().split(" - ")[1].toString();
        }

        Intent i=new Intent(sim_display.this,app_pin.class);
//TODO Stop last activity
        Bundle bundle=new Bundle();
        bundle.putString("email",email);
        bundle.putString("mobile",mobile);
        i.putExtras(bundle);
        finishAffinity();
        startActivity(i);
    }
}
