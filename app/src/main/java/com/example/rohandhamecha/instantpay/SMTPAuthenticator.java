package com.example.rohandhamecha.instantpay;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * Created by Rohan Dhamecha on 24-01-2018.
 */

public class SMTPAuthenticator extends Authenticator {
    public SMTPAuthenticator() {

        super();
    }

    @Override
    public PasswordAuthentication getPasswordAuthentication() {
        String username = "instantpayoffice@gmail.com";
        String password = "finalproject";
        if ((username != null) && (username.length() > 0) && (password != null)
                && (password.length() > 0)) {

            return new PasswordAuthentication(username, password);
        }

        return null;
    }
}

