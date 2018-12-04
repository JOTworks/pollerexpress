package com.plugin;

import com.shared.exceptions.database.DatabaseException;
import com.shared.models.User;

public interface IUserDao {
    public User getUser(String username) throws DatabaseException;
    public void addUser(User user) throws DatabaseException;
    public void updateUser(User user) throws DatabaseException;
}
