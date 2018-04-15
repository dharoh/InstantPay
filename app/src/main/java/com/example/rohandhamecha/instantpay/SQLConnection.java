package com.example.rohandhamecha.instantpay;

/**
 * Created by Rohan Dhamecha on 17-01-2018.
 */

import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * Created by Rohan Dhamecha on 03-01-2018.
 */

public class SQLConnection{

    private static String ip="192.168.43.61";
    private static String port="1433";
    private static String classs="net.sourceforge.jtds.jdbc.Driver";
    private static String db="pay_data";
    private static String un="AS";
    private static String password="08121996";



    public static Connection connect() {
        Connection con = null;
        String ConnURL = null;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try
        {
            Class.forName(classs);
            ConnURL = "jdbc:jtds:sqlserver://" + ip +":"+port+";"
                    + "databaseName=" + db + ";user=" + un + ";password="
                    + password + ";";
            con=DriverManager.getConnection(ConnURL);

        }
        catch (Exception e)
        {

        }
        return con;
    }
}

