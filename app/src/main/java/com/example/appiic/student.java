package com.example.appiic;


public class student {

    public String Username,IDNo,Email,MobileNumber,Dept,Year,Section;
    public  student(){
    }
    public student(String username, String IDNo,String dept,String Year,String section ,String email,String mobileNumber) {
        this.Username= username;
        this.IDNo = IDNo;
        this.Dept = dept;
        this.Year = Year;
        this.Section = section;
        this.Email = email;
        this.MobileNumber = mobileNumber;
    }
}
