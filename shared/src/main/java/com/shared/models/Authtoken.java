package com.shared.models;

import java.io.Serializable;
import java.util.UUID;

public class Authtoken implements Serializable
{
    private String userName;
    private String token;

    public Authtoken()
    {

    }
    public Authtoken(String userName, String token) {
        this.userName = userName;
        this.token = token;
    }

    public Authtoken(User u)
    {
        this.userName = u.name;
        this.token = UUID.randomUUID().toString();
    }
    public String getUserName() {
        return userName;
    }

    public String getToken() {
        return token;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
