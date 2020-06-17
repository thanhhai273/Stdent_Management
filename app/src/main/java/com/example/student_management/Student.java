package com.example.student_management;

import java.io.Serializable;


public class Student implements Serializable {
    private int code;
    private String name;
    private String date;
    private String email;
    private String address;

    public Student(int code, String name, String date, String email, String address) {
        this.code = code;
        this.name = name;
        this.date = date;
        this.email = email;
        this.address = address;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
