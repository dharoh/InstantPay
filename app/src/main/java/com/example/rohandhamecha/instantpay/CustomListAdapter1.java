package com.example.rohandhamecha.instantpay;

/**
 * Created by Rohan Dhamecha on 05-02-2018.
 */

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomListAdapter1 extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] itemname;
    private final Integer[] imgid;
    private final String[] desc;

    public CustomListAdapter1(Activity context, String[] itemname,String[] desc, Integer[] imgid) {
        super(context, R.layout.blist, itemname);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.itemname=itemname;
        this.desc=desc;
        this.imgid=imgid;
    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.blist, null,true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        TextView descr=(TextView)rowView.findViewById(R.id.descripton);

        txtTitle.setText(itemname[position]);
        imageView.setImageResource(imgid[position]);
        descr.setText(desc[position]);
        return rowView;

    };
}
