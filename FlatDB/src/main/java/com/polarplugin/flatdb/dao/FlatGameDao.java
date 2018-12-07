package com.polarplugin.flatdb.dao;

import com.plugin.IGameDao;
import com.polarplugin.flatdb.exceptions.DeleteFailedException;
import com.polarplugin.flatdb.exceptions.GameNotFoundException;
import com.shared.models.Game;
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

public class FlatGameDao implements IGameDao {

    @Override
    public Game getGame(String id) throws IOException {
        List<Game> games = getAllGames();
        for (Game game : games) {
            if (game.getId().equals(id))
                    return game;
        }
        throw new GameNotFoundException(id);
    }

    @Override
    public List<Game> getAllGames() throws FileNotFoundException {
        List<Game> games;
        try {
            InputStream fis = new FileInputStream(new File("allGames.txt"));
            games = (ArrayList<Game>) Serializer.readData(fis);
            fis.close();

        } catch (FileNotFoundException e) { throw new FileNotFoundException();
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e.getClass() + ": " + e.getMessage());
        }
        return games;
    }

    @Override
    public void addGame(Game game) throws IOException {
        List<Game> games = new ArrayList<>();

        // get an array of games
        try { games = getAllGames(); } // get a list comprising all games
        catch (FileNotFoundException e) {} // if the file is not found, we will use the list of games created above
        finally { games.add(game); } // regardless, we want to add the game that just came in

        // write the new array of games to a file
        OutputStream fos = new FileOutputStream(new File("allGames.txt"), false);
        Serializer.writeData(games, fos);
        fos.close();
    }

    @Override
    public void updateGame(Game game) throws IOException {
        List<Game> games = getAllGames();
        int index = games.indexOf(game);
        if (index == -1)
            throw new GameNotFoundException(game.getId());

        games.set(index, game);
    }

    @Override
    public void deleteGame(Game game) throws IOException {
        List<Game> games = getAllGames();
        // remove and check at the same time
        if (!games.remove(game))
            throw new DeleteFailedException(game.getId());
        // write the updated games list back to the file
        OutputStream fos = new FileOutputStream(new File("allGames.txt"), false);
        Serializer.writeData(games, fos);
    }

    @Override
    public void clearGames() throws IOException {
        File oldFile = new File("allGames.txt");
        boolean deleteSucceeded = oldFile.delete();
        if (!deleteSucceeded)
            throw new DeleteFailedException("allGames.txt");
    }
}
