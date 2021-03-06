package com.thePollerServer.handlers;



import com.shared.models.Player;
import com.shared.utilities.Serializer;
import com.shared.models.reponses.LoginResponse;
import com.shared.models.requests.LoginRequest;
import com.thePollerServer.services.LoginService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

import com.thePollerServer.command.CommandManager;


/**
 * Created by xeonocide on 9/15/18.
 */

public class RegisterHandler implements HttpHandler
{


    @Override
    public void handle(HttpExchange exchange) throws IOException
    {

        System.out.print("Entered RegisterHandler\n");
        try
        {

            //if(exchange.getRequestMethod().toLowerCase().equals("get"))
            {
                //we will assumned that the user is authorized for this test.

                //extract the data from the request

              LoginRequest req = (LoginRequest) Serializer.readData(exchange.getRequestBody());


                //get the data
                Player p = null;
                OutputStream responseBody = null;
                try
                {
                    LoginService LS = new LoginService();

                    LoginResponse resp = LS.register(req);

                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,0);

                    responseBody = exchange.getResponseBody();

                    CommandManager._instance().addPlayer(new Player(req.username ) );
                    Serializer.writeData(resp, responseBody);
                }
                catch(Exception e)
                {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,0);
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
        System.out.print("Left Register Handler\n");
    }

}