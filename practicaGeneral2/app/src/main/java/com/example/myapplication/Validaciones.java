package com.example.myapplication;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validaciones {

    public static Boolean esEmailValido(String email) {
        Pattern pattern = Pattern.compile("^([0-9a-zA-Z]+[-._+&])*[0-9a-zA-Z]+@([-0-9a-zA-Z]+[.])+[a-zA-Z]{2,6}$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static Boolean soloContieneNumeros(String text) {
        if (text.matches("[0-9]+") && text.length() > 2) {
            return true;
        }
        return false;
    }

    public static boolean esPasswordValida(String pass) {

        if (pass.length() >= 8) {
            return true;
        }else{
            return false;
        }
    }
}
