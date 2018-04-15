package com.example.rohandhamecha.instantpay;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by Keyur on 3/18/2018.
 */

public class MerchantDetailsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_merchant_details,container,false);
        String [] values =
                {"Select Category","Direct Marketing-Insurance Services","Securities-Brokers/Dealers","Schools,Correspondence","Financial Institutions-Merchandise and Services","Insurance Sales,Underwriting,and Premiums","Schools,Elementary and Secondary","Tax Payments","Fines","Business and Secretarial Schools","Government Services","Intra-Government Purchases,Government Only","Restaurants,Food Stalls","Newspaper Payments","Collections","Others"};
        Spinner spinner = (Spinner) view.findViewById(R.id.spinner_category);
        ArrayAdapter<String> LTRadapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, values);
        LTRadapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(LTRadapter);


        String [] states = {"Select State","Andhra Pradesh","Arunachal Pradesh","Assam","Bihar","Chhattisgarh","Goa","Gujarat","Haryana","Himachal Pradesh","Jammu and Kashmir","Jharkhand","Karnataka","Kerala","Madya Pradesh","Maharashtra","Manipur","Meghalaya","Mizoram","Nagaland","Orissa","Punjab","Rajasthan","Sikkim","Tamil Nadu","Tripura","Uttaranchal","Uttar Pradesh","West Bengal","Andaman and Nicobar Islands","Chandigarh","Dadar and Nagar Haveli","Daman and Diu","Delhi","Lakshadeep","Pondicherry"};
        Spinner spinner1 = (Spinner) view.findViewById(R.id.spinner_state);
        ArrayAdapter<String> LTRadapter1 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, states);
        LTRadapter1.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner1.setAdapter(LTRadapter1);

        return view;
    }
}
