package com.example.MyPersonalContactManager.utils;

public class UtilsAuthorization {
    public String generateToken(String login, String password){

        return "001{" +login +"|" + password +"}";
    }
}
