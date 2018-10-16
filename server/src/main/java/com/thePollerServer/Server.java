package com.thePollerServer;

import com.sun.net.httpserver.*;
import com.thePollerServer.handlers.ExecuteHandler;
import com.thePollerServer.handlers.LoginHandler;
import com.thePollerServer.handlers.PollHandler;
import com.thePollerServer.handlers.RegisterHandler;

import java.io.*;
import java.net.*;

public class Server
{

    //the maximum players in a game is five
    private static final int MAX_WAITING_CONNECTIONS = 12;


    private HttpServer server;

    private void run(String portNumber) {

        System.out.println("Initializing HTTP Server");

        try {
            server = HttpServer.create(
                    new InetSocketAddress(Integer.parseInt(portNumber)),
                    MAX_WAITING_CONNECTIONS);
        }
        catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // Indicate that we are using the default "executor".
        // This line is necessary, but its function is unimportant for our purposes.
        server.setExecutor(null);

        System.out.println("Creating contexts");

        server.createContext("/login", new LoginHandler());
        server.createContext("/register", new RegisterHandler());
        server.createContext("/exec", new ExecuteHandler());
        server.createContext("/poll", new PollHandler());
        System.out.println("Starting Server");
        server.start();

        // Log message indicating that the Server has successfully started.
        System.out.println("Server started");
    }

    // "main" method for the Server program
    // "args" should contain one command-line argument, which is the port number
    // on which the Server should accept incoming client connections.
    public static void main(String[] args) {
        String portNumber;
        if(args.length > 0)
        {
            portNumber = args[0];
        }
        else
        {
            portNumber = "8080";
        }
        new Server().run(portNumber);
    }
}