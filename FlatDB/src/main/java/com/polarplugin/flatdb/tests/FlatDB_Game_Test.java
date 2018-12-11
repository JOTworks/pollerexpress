package com.polarplugin.flatdb.tests;

import com.plugin.models.ServerGame;
import com.polarplugin.flatdb.dao.FlatGameDao;
import com.polarplugin.flatdb.exceptions.DeleteFailedException;
import com.polarplugin.flatdb.exceptions.GameNotFoundException;
import com.shared.models.GameInfo;

import org.junit.Assert;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

public class FlatDB_Game_Test {

    @Test
    public void testFileWriting() throws IOException {
        FlatGameDao fgd = new FlatGameDao();
        ServerGame myGame = new ServerGame(new GameInfo("myGameId", "myGame", 2));
        fgd.addGame(myGame);
        ServerGame myReadGame = fgd.getGame("myGameId");
        Assert.assertTrue(myReadGame.getId().equals("myGameId"));
    }

    @Test
    public void testDeleteGame() throws IOException {
            FlatGameDao fgd = new FlatGameDao();
            try { fgd.clearGames(); } catch (Exception e) {};
            ServerGame myGame = new ServerGame(new GameInfo("myGameId", "myGame", 2));
            fgd.addGame(myGame);
            fgd.deleteGame(myGame);
            try {
                ServerGame myReadGame = fgd.getGame("myGameId");
                // fail if we don't throw
                Assert.assertTrue(false);
            } catch (GameNotFoundException e) {
                Assert.assertTrue(true);
        }
    }

    @Test
    public void testDeleteGame_GameDoesNotExist() throws IOException {
        boolean testPassed = false;

        FlatGameDao fgd = new FlatGameDao();
        try { fgd.clearGames(); } catch (Exception e) {};
        ServerGame myGame = new ServerGame(new GameInfo("myGameId", "myGame", 2));
        ServerGame myGame2 = new ServerGame(new GameInfo("myGameId2", "myGame", 2));
        fgd.addGame(myGame);
        try {
            fgd.deleteGame(myGame2);
        } catch (DeleteFailedException e) {
            testPassed = true;
        }
        Assert.assertTrue(testPassed);
    }

    @Test
    public void testClearAllFiles() throws IOException {
        boolean testPassed = false;

        FlatGameDao fgd = new FlatGameDao();
        ServerGame myGame = new ServerGame(new GameInfo("myGameId", "myGame", 2));
        fgd.addGame(myGame);
        fgd.addGame(myGame);
        fgd.addGame(myGame);
        fgd.clearGames();
        try {
            ServerGame myReadGame = fgd.getGame("myGameId");
        } catch (FileNotFoundException e) {
            testPassed = true;
        }

        Assert.assertTrue(testPassed);
    }
}
