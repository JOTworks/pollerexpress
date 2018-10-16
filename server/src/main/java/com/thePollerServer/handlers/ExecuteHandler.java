package com.thePollerServer.handlers;



import com.shared.models.Command;
import com.shared.exceptions.CommandFailed;
import com.shared.models.Player;
import com.shared.models.PollResponse;
import com.shared.utilities.Serializer;
import com.shared.models.reponses.ErrorResponse;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.LinkedList;
import java.util.Queue;

import com.thePollerServer.command.CommandManager;


/**
 * Created by xeonocide on 9/15/18.
 */

public class ExecuteHandler implements HttpHandler
{


    @Override
    public void handle(HttpExchange exchange) throws IOException
    {

        System.out.print("Entered ExecHandler\n");
        try{

            //if(exchange.getRequestMethod().toLowerCase().equals("get"))
            {
                //we will assume that the user is authorized for this test, because i am to annoyed to try to do any other.

                //extract the data from the request

               Command req = (Command) Serializer.readData(exchange.getRequestBody());


                //get the data
                Player p = new Player( exchange.getRequestHeaders().get("username").toArray()[0].toString() );
                OutputStream responseBody = null;
                try
                {
                    req.execute();
                    Queue<Command> commands = CommandManager._instance().getUserCommands(p.name);
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,0);

                    responseBody = exchange.getResponseBody();

                    Serializer.writeData(new PollResponse(commands, null), responseBody);
                }
                catch(CommandFailed error)
                {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,0);
                    PollResponse response = new PollResponse(new LinkedList<>(), new ErrorResponse("could not run command",error,req));
                    responseBody = exchange.getResponseBody();
                    Serializer.writeData(response,responseBody);
                }
                finally
                {
                    if(responseBody != null) responseBody.close();
                }

            }
        } catch (IOException e )
        {
            //something bad happened, tell the client that we had an accident
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR,0);
            System.out.print("IoException\n");
            exchange.getResponseBody().close();
            //log the error in the terminal
            e.printStackTrace();
        }
        catch (ClassNotFoundException e)
        {
            System.out.print("ClassException\n");
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_UNSUPPORTED_TYPE, 0);
            exchange.getResponseBody().close();
            //log the error in the terminal
            e.printStackTrace();
        }
        System.out.print("Left Execute Handler\n");
    }

}