package com.pollerexpress.models;

import java.util.UUID;

public class Authtoken
{
    private String userName;
    private String token;

    public Authtoken(String userName, String token) {
        this.userName = userName;
        this.token = token;
    }

    /**
     *
     * @param u the user the token is being created for
     * @post the token will be a unique authentication tool.
     */
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
