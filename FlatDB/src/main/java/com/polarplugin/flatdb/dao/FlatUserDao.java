package com.polarplugin.flatdb.dao;

import com.plugin.IUserDao;
import com.polarplugin.flatdb.exceptions.UserNotFoundException;
import com.shared.models.User;
import com.shared.utilities.Serializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class FlatUserDao implements IUserDao {

    private List<User> getAllUsers() throws FileNotFoundException {
        List<User> users;
        try {
            InputStream fis = new FileInputStream(new File("allUsers.txt"));
            users = (ArrayList<User>) Serializer.readData(fis);
            fis.close();

        } catch (FileNotFoundException e) { throw new FileNotFoundException();
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e.getClass() + ": " + e.getMessage());
        }
        return users;
    }

    @Override
    public User getUser(String username) throws IOException {
        List<User> users = getAllUsers();
        for (User user : users) {
            if (user.getName().equals(username))
                return user;
        }
        throw new UserNotFoundException(username);
    }

    @Override
    public void addUser(User user) throws IOException {

    }

    @Override
    public void updateUser(User user) throws IOException {

    }

    @Override
    public void clearUsers() throws IOException {

    }
}
