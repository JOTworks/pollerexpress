package thePollerExpress.communication;


import com.shared.models.Command;
import com.shared.utilities.Serializer;
import com.shared.models.requests.LoginRequest;
import com.shared.models.reponses.LoginResponse;
import com.shared.models.PollResponse;


import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import thePollerExpress.models.ClientData;

/**
 * Who was in charge of this class?
 */
public class ClientCommunicator
{

    private String serverHost = "10.0.2.2";//"192.168.1.177";//"192.168.1.177";//local host
    private String serverPort = "8080";
    private static ClientCommunicator _instance;
    private ClientCommunicator()
    {

    }


    public static ClientCommunicator instance()
    {
        //not currently thread safe
        if(_instance == null)
        {
            _instance = new ClientCommunicator();
        }
        return _instance;
    }
    public LoginResponse sendLoginRequest(String requestType, LoginRequest request)
    {
        return (LoginResponse)sendRequest(request, requestType);
    }

    public PollResponse sendCommand(Command command)
    {
        return (PollResponse) sendRequest(command, "exec");
    }
    public PollResponse sendPoll()
    {
        return (PollResponse)sendRequest( ClientData.getInstance().getUser(),"poll");
    }
    public Object sendRequest(Object r, String operation)
    {
        Object response = null;
        try
        {
            //construct the url we are querying
            URL url = new URL("http://"+ serverHost + ":" + serverPort + "/"+operation);

            HttpURLConnection http = (HttpURLConnection)url.openConnection();
            //specify the request type
            http.setRequestMethod("GET");

            if(ClientData.getInstance().getUser() != null)
            {
                http.setRequestProperty("username", ClientData.getInstance().getUser().getName());
            }

            http.setDoOutput(true);

            http.connect();

            OutputStream reqBody = http.getOutputStream();

            Serializer.writeData(r, reqBody);

            reqBody.close();

            if (http.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
                try
                {

                    response = Serializer.readData(http.getInputStream());
                }
                catch(ClassNotFoundException e)
                {
                    return null;
                }
            }
            else {
                // The HTTP response status code indicates an error
                // occurred, so print out the message from the HTTP response
                System.out.println("ERROR: " + http.getResponseMessage());
            }

        }
        catch(IOException e)
        {
            System.out.println("ERROR: could not connect to server");
            e.printStackTrace();
        }
        return response;
    }
}


