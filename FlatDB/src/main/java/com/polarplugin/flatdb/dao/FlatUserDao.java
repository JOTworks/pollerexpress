package com.polarplugin.flatdb.dao;

import com.plugin.IUserDao;
import com.polarplugin.flatdb.exceptions.DeleteFailedException;
import com.polarplugin.flatdb.exceptions.UserNotFoundException;
import com.shared.models.User;
import com.shared.utilities.Serializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class FlatUserDao implements IUserDao {

    public void createAllUsersFile() throws IOException {
        List<User> empty = new ArrayList<>();
        OutputStream fos = new FileOutputStream(new File("allUsers.txt"), false);
        Serializer.writeData(empty, fos);
        fos.close();
    }

    public List<User> getAllUsers() throws FileNotFoundException {
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
        List<User> users = new ArrayList<>();

        // get an array of users
        try { users = getAllUsers(); } // get a list comprising all users
        catch (FileNotFoundException e) {} // if the file is not found, we will use the list of users created above
        finally { users.add(user); } // regardless, we want to add the user that just came in

        // write the new array of users to a file
        OutputStream fos = new FileOutputStream(new File("allUsers.txt"), false);
        Serializer.writeData(users, fos);
        fos.close();
    }



    @Override
    public void updateUser(User user) throws IOException {
        List<User> users = getAllUsers();
        int index = users.indexOf(user);
        if (index == -1)
            throw new UserNotFoundException(user.getName());

        users.set(index, user);
    }

    public void clearUsers() throws IOException {
        File oldFile = new File("allUsers.txt");
        boolean deleteSucceeded = oldFile.delete();
        if (!deleteSucceeded)
            throw new DeleteFailedException("allUsers.txt");
    }
}
