package com.bendani.bibliomania.login.domain;

public class User {

    private String username;
    private String email;
    private String token;
    private String password;

    public User(String username, String password, String token, String email) {
        this.username = username;
        this.token = token;
        this.password = password;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }
}
