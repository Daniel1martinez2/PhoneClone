package com.example.phoneclone;

public class User {

    String id, userName, correo, contra1;

    public User() {
    }

    public User(String id, String userName, String correo, String contra1) {
        this.id = id;
        this.contra1 = contra1;
        this.correo = correo;
        this.userName = userName;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContra1() {
        return contra1;
    }

    public void setContra1(String contra1) {
        this.contra1 = contra1;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
