package com.example.rohandhamecha.instantpay;


import android.app.Activity;
import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment{


    public ContactFragment() {
        // Required empty public constructor

    }

    String[] item;
    String[] desc;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

//        ListView list=(ListView)getView().findViewById(R.id.ListView_Contacts);
//        item=new String[3];
//        desc=new String[3];
//
//        for(int j=0;j<3;j++)
//        {
//            item[j]="hello";
//            desc[j]="rohan";
//        }
//        CustomListAdapter2 adp=new CustomListAdapter2(super.getActivity(),item,desc);
//        list.setAdapter(adp);

        return inflater.inflate(R.layout.fragment_contact, container, false);

    }

}
