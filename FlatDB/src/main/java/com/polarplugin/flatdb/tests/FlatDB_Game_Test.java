package com.polarplugin.flatdb.tests;

import com.polarplugin.flatdb.dao.FlatCommandDao;
import com.shared.models.Command;

import org.junit.Assert;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class FlatDB_Game_Test {

    @Test
    public void testFileWriting() throws IOException {
        FlatCommandDao fcd = new FlatCommandDao();
        Command myCommand = new Command("myClass", "myMethod", null, null);
        fcd.addCommand(myCommand, "myGameId");
        List<Command> myReadCommands = fcd.getCommands("myGameId");
        Assert.assertTrue(myReadCommands.get(0).getClassName().equals("myClass"));
    }

    @Test
    public void testClearFile() throws IOException {
            FlatCommandDao fcd = new FlatCommandDao();
            Command myCommand = new Command("myClass", "myMethod", null, null);
            fcd.addCommand(myCommand, "myGameId");
            fcd.removeCommands("myGameId");
            List<Command> myReadCommands = fcd.getCommands("myGameId");
            Assert.assertTrue(myReadCommands.size() == 0);
    }

    @Test
    public void testClearAllFiles() throws IOException {
        boolean testPassed = false;

        FlatCommandDao fcd = new FlatCommandDao();
        Command myCommand = new Command("myClass", "myMethod", null, null);
        fcd.addCommand(myCommand, "myGameId");
        fcd.addCommand(myCommand, "myGameId2");
        fcd.addCommand(myCommand, "myGameId3");
        fcd.clearAllCommands();
        try {
            List<Command> myReadCommands = fcd.getCommands("myGameId");
        } catch (FileNotFoundException e) {
            testPassed = true;
        }

        Assert.assertTrue(testPassed);
    }
}
