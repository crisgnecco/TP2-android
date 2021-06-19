package com.example.myapplication.dto;

public class ConstanteToken {
    public static String token;
    public static String token_refresh;

    public static String getToken_refresh() {
        return token_refresh;
    }

    public static void setToken_refresh(String token_refresh) {
        ConstanteToken.token_refresh = token_refresh;
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        ConstanteToken.token = token;
    }
}
