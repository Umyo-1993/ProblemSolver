package com.example.problemsolver;

public class modelUpload2 {
    private String fullname;
    private String phonenumber;

    public modelUpload2() {
        //empty constructor needed
    }

    public modelUpload2(String fullname, String phonenumber, String productprice) {
        this.fullname = fullname;
        this.phonenumber = phonenumber;

    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }
}