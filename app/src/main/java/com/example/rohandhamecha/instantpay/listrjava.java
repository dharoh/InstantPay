package com.example.rohandhamecha.instantpay;

/**
 * Created by Rohan Dhamecha on 03-04-2018.
 */
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class listrjava extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] name;
    private final String[] amount;

    public listrjava(Activity context, String[] name, String[] amount) {
        super(context, R.layout.mylist, name);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.name=name;
        this.amount=amount;
    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.list_request, null,true);

        TextView tname = (TextView) rowView.findViewById(R.id.txtnam);
        TextView amt=(TextView)rowView.findViewById(R.id.textrup);

        tname.setText("From: "+name[position]);
        amt.setText("₹ "+amount[position]);

        return rowView;

    };
}
