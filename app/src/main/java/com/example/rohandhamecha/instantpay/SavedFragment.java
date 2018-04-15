package com.example.rohandhamecha.instantpay;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class SavedFragment extends Fragment {

    @SuppressLint("ValidFragment")
    public SavedFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

//        TextView txt=(TextView)getView().findViewById(R.id.saved);
        return inflater.inflate(R.layout.fragment_saved, container, false);
    }

}
