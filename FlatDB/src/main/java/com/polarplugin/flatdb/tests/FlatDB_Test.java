package com.polarplugin.flatdb.tests;

import com.polarplugin.flatdb.dao.FlatCommandDao;
import com.shared.models.Command;

import org.junit.Assert;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.List;

public class FlatDB_Test {

    @Test
    public void testFileWriting() throws FileNotFoundException {
        FlatCommandDao fcd = new FlatCommandDao();
        Command myCommand = new Command("myClass", "myMethod", null, null);
        fcd.addCommand(myCommand, "myGameId");
        List<Command> myReadCommands = fcd.getCommands("myGameId");
        Assert.assertTrue(myReadCommands.get(0).getClassName().equals("myClass"));
    }
}
