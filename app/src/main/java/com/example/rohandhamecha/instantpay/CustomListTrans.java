package com.example.rohandhamecha.instantpay;

/**
 * Created by Rohan Dhamecha on 18-03-2018.
 */

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomListTrans extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] ttext;
    private final String[] date;
    private final String[] rup;
    private final String[] name;
    private final String[] num;
    private final Integer[] imgid;

    public CustomListTrans(Activity context, String[] ttext, Integer[] imgid,String[] date,String[] rup,String[] name,String[] num) {
        super(context, R.layout.mylist,ttext);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.ttext=ttext;
        this.imgid=imgid;
        this.date=date;
        this.rup=rup;
        this.name=name;
        this.num=num;
    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.trans_list, null,true);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.imgtrans);
        imageView.setImageResource(imgid[position]);

        TextView textsr=(TextView)rowView.findViewById(R.id.ttext);
        TextView textdate=(TextView)rowView.findViewById(R.id.textView44);
        TextView textrup=(TextView)rowView.findViewById(R.id.textView48);
        TextView textname=(TextView)rowView.findViewById(R.id.textView46);
        TextView textnum=(TextView)rowView.findViewById(R.id.textView47);

        textsr.setText(ttext[position]);
        textdate.setText(date[position]);
        textrup.setText(rup[position]);
        textname.setText(name[position]);
        textnum.setText(num[position]);
        return rowView;

    };
}
