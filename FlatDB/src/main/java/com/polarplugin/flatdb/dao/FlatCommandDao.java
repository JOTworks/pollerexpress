package com.polarplugin.flatdb.dao;

import com.plugin.ICommandDao;
import com.plugin.models.ServerGame;
import com.shared.models.Command;
import com.shared.utilities.Serializer;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FlatCommandDao implements ICommandDao {
    @Override
    public List<Command> getCommands(String gameId) throws IOException {
        List<Command> commands;


        try {
            InputStream fis = new FileInputStream(new File("games/" + gameId + ".txt"));
            commands = (ArrayList<Command>) Serializer.readData(fis);
            fis.close();

        } catch (FileNotFoundException e) { throw e;
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e.getClass() + ": " + e.getMessage());
        }
        return commands;
    }

    @Override
    public void addCommand(Command command, String gameId) throws IOException {
        List<Command> commands = new ArrayList<>();
        if (!Files.exists(Paths.get("games")))
            Files.createDirectories(Paths.get("games"));

        // get an array of commands
        try { commands = getCommands(gameId); } // get a list of Command objects for a given game
        catch (FileNotFoundException e) {} // if the file is not found, we will use the list of commands created above
        finally { commands.add(command); } // regardless, we want to add the command that just came in

        // write the new array of commands to a file
        OutputStream fos = new FileOutputStream(new File("games/" + gameId + ".txt"));
        Serializer.writeData(commands, fos);
        fos.close();
    }

    @Override
    public void removeCommands(String gameId) throws IOException {
            // set the output stream to overwrite instead of appending
            OutputStream fos = new FileOutputStream(new File("games/" + gameId + ".txt"), false);
            Serializer.writeData(new ArrayList<Command>(), fos);
            fos.close();
    }

    public void clearAllCommands() throws IOException {
        FileUtils.cleanDirectory(new File("games"));
    }

    public static void createNewCommandFile(ServerGame game) throws IOException {
        List<Command> empty = new ArrayList<>();
        if (!Files.exists(Paths.get("games")))
            Files.createDirectories(Paths.get("games"));

        FileOutputStream fos = new FileOutputStream(new File("games/" + game.getId() + ".txt"));
        Serializer.writeData(empty, fos);
        fos.close();

    }
}
