package com.example.appiic;

public class Faculty {

    public String Username, IDNo, Email, MobileNumber,Dept;

    public Faculty() {}

    public Faculty(String usernames, String IDNo, String emaili, String mobno,String dept) {
        this.Username = usernames;
        this.IDNo = IDNo;
        this.Dept=dept;
        Email = emaili;
        MobileNumber = mobno;
    }
}