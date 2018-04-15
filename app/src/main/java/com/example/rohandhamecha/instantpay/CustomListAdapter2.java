package com.example.rohandhamecha.instantpay;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Rohan Dhamecha on 08-03-2018.
 */

public class CustomListAdapter2 extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] itemname;
    private final String[] desc;


    public CustomListAdapter2(Activity context,String[] itemname,String[] desc)
    {
        super(context,R.layout.clist,itemname);

        this.context=context;
        this.itemname=itemname;
        this.desc=desc;
    }
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.clist, null,true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.item_c);
        TextView descr=(TextView)rowView.findViewById(R.id.descripton_c);

        txtTitle.setText(itemname[position]);
        descr.setText(desc[position]);
        return rowView;

    };
}
