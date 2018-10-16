package com.thePollerServer.handlers;

import com.shared.models.Command;
import com.shared.models.Player;
import com.shared.models.PollResponse;
import com.shared.utilities.Serializer;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.Queue;

import com.thePollerServer.command.CommandManager;

public class PollHandler implements HttpHandler
{



    @Override
    public void handle(HttpExchange exchange) throws IOException
    {

        try
        {
            Player p = (Player) Serializer.readData(exchange.getRequestBody());
            Queue<Command> commands = CommandManager._instance().getUserCommands(p.name);
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,0);

            OutputStream responseBody = exchange.getResponseBody();

            Serializer.writeData(new PollResponse(commands, null), responseBody);
            responseBody.close();
        }
        catch(ClassNotFoundException e)
        {

        }

    }
}
