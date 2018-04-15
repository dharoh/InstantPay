package com.example.rohandhamecha.instantpay;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.support.v4.app.Fragment;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rohan Dhamecha on 08-03-2018.
 */

public class fragmentcontact extends Activity{

    ListView list;
    String[] item;
    String[] desc;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_contact);

  /*      Cursor phones=getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null);

        List<String>number=new ArrayList<String>();
        List<String>name=new ArrayList<String>();

        int i=0;
        while(phones.moveToNext())
        {
            number.add(i,phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
            name.add(i,phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)));
            i++;
        }
*/
        item=new String[3];
        desc=new String[3];

        for(int j=0;j<3;j++)
        {
            item[j]="hello";
            desc[j]="rohan";
        }

        CustomListAdapter2 adp=new CustomListAdapter2(this,item,desc);

        list=(ListView)findViewById(R.id.ListView_Contacts);
        list.setAdapter(adp);


    }
}
