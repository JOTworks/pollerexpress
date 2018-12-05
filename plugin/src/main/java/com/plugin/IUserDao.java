package com.plugin;

import com.shared.models.User;

import java.io.IOException;

public interface IUserDao {
    public User getUser(String username) throws IOException;

    public void addUser(User user) throws IOException;

    public void updateUser(User user) throws IOException;
}
