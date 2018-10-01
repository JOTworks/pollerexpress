package com.pollerexpress.models;

public class User extends Player
{
    public String password;//this really shouldn't be public ;) keep your password private

    public User(String name, String password)
    {
        super(name);
        this.password = password;
    }
}
