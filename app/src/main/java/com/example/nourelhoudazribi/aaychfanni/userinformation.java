package com.example.nourelhoudazribi.aaychfanni;

/**
 * Created by Nour El Houda Zribi on 16/11/2017.
 */

public class userinformation {
    public String Name;
    public String Lastname;
    public userinformation(){

    }

    public userinformation(String name, String lastname) {
        this.Name = name;
        this.Lastname = lastname;
    }

    public String getName() {
        return Name;
    }

    public userinformation(userinformation u) {
        Name = u.Name;
        Lastname=u.Lastname;
    }
}
