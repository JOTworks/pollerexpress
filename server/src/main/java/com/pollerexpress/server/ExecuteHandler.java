package com.pollerexpress.server;



import com.pollerexpress.models.Command;
import com.pollerexpress.models.CommandFailed;
import com.pollerexpress.models.Player;
import com.pollerexpress.models.PollResponse;
import com.pollerexpress.models.serializer.Serializer;
import com.pollerexpress.reponse.ErrorResponse;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.Queue;

import command.CommandManager;


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
                //we will assumned that the user is authorized for this test.

                //extract the data from the request

               Command req = (Command) Serializer.readData(exchange.getRequestBody());


                //get the data
                Player p = null;
                OutputStream responseBody = null;
                try
                {
                    req.execute();
                    Queue<Command> commands = CommandManager._instance().addCommand(req, p.name);

                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,0);

                    responseBody = exchange.getResponseBody();

                    Serializer.writeData(commands, responseBody);
                }
                catch(CommandFailed error)
                {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,0);
                    PollResponse response = new PollResponse(null, new ErrorResponse("could not run command",null,req));
                    Serializer.writeData(response,responseBody);
                }
                finally
                {
                    if(responseBody != null) responseBody.close();
                }

            }
            else
            {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST,0);

                exchange.getResponseBody().close();
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
    }*/

}