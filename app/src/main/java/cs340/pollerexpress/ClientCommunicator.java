package cs340.pollerexpress;

import com.pollerexpress.models.Authtoken;
import com.pollerexpress.models.Command;
import com.pollerexpress.models.LoginRequest;
import com.pollerexpress.models.LoginResponse;

import org.springframework.web.client.RestTemplate;

public class ClientCommunicator {

    //---------------------Singleton setup------------------------------
    private static ClientCommunicator _instance;

    public static ClientCommunicator instance() {

        if (_instance == null)
            _instance = new ClientCommunicator();

        return _instance;
    }

    private ClientCommunicator() {}

    private RestTemplate restTemplate = new RestTemplate();
    private static String URL_BASE = "http://localhost:8080";
    private static String EXECUTE_URL = URL_BASE + "/execute";


    //-------------------------------------------------------------------

    /**
     * Creates a new Player object
     * @param requestType either the string 'login' or 'register' to indicate which command should
     *                executed.
     * @param request the LoginRequest object containing a username and password
     *
     * @pre command has either the string 'login' or the string 'register'
     * @pre request is not null
     * @pre the username and password fields in request are not null
     * @post returns the response object returned by restTemplate
     */
    public LoginResponse sendLoginRequest(String requestType, LoginRequest request) {
        String resourceUrl = URL_BASE + requestType;

        LoginResponse response = restTemplate.postForObject(resourceUrl, request, LoginResponse.class);

        return response;
    }

    /**
     * Creates a new Player object
     * @param command a command object that will be sent to the server
     *
     * @pre command is not null
     * @post returns the response object returned by restTemplate
     */
    public PollResponse sendCommand(Authtoken authtoken, Command command) {
        String resourceUrl = EXECUTE_URL;

        //TODO put authToken in a header
        PollResponse response = restTemplate.postForObject(resourceUrl, command, PollResponse.class);

        return response;
    }

}

