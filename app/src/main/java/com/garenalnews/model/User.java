package com.garenalnews.model;

import java.io.Serializable;

/**
 * Created by ducth on 4/9/2018.
 */

public class User implements Serializable {
    private String id;

    public User(String id, String typeLogin, String name, String phone, String email, String imAvata) {
        this.id = id;
        this.typeLogin = typeLogin;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.imAvata = imAvata;
    }

    public String getId() {

        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String typeLogin;
    private String name;
    private String phone;
    private String email;
    private String imAvata;

    public String getTypeLogin() {
        return typeLogin;
    }

    public void setTypeLogin(String typeLogin) {
        this.typeLogin = typeLogin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImAvata() {
        return imAvata;
    }

    public void setImAvata(String imAvata) {
        this.imAvata = imAvata;
    }

    public User(String typeLogin, String phone) {
        this.typeLogin = typeLogin;
        this.phone = phone;
    }
}
