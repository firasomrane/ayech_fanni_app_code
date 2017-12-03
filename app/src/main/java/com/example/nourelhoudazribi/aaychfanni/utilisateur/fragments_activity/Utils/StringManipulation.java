package com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.Utils;

/**
 * Created by ASUS on 01/12/2017.
 */

public class StringManipulation {

    public static String expandUsername(String username){
        return username.replace(".", " ");
    }

    public static String condenseUsername(String username){
        return username.replace(" " , ".");
    }
}