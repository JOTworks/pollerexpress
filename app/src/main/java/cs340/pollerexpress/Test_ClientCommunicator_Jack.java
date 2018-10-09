package cs340.pollerexpress;

import com.pollerexpress.models.Authtoken;
import com.pollerexpress.models.Command;
import com.pollerexpress.reponse.ErrorResponse;
import com.pollerexpress.models.GameInfo;
import com.pollerexpress.request.LoginRequest;
import com.pollerexpress.reponse.LoginResponse;
import com.pollerexpress.models.PollResponse;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;

public class Test_ClientCommunicator_Jack {

    //---------------------Singleton setup------------------------------
    private static Test_ClientCommunicator_Jack _instance;

    public static Test_ClientCommunicator_Jack instance() {

        if (_instance == null)
            _instance = new Test_ClientCommunicator_Jack();

        return _instance;
    }

    private Test_ClientCommunicator_Jack() {}

    private RestTemplate restTemplate = new RestTemplate();
    private static String URL_BASE = "http://localhost:8080";
    private static String EXECUTE_URL = URL_BASE + "/execute";
    private static String TEST_URL = URL_BASE + "/test";


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

        ArrayList<GameInfo> info = new ArrayList<GameInfo>(Arrays.asList(new GameInfo("theID", "theName", 4)));

        return new LoginResponse( new Authtoken("Jackson", "myAuthTokenString"), info, new ErrorResponse("this is the error message",null,null));

        //String resourceUrl = URL_BASE + requestType;

       // LoginResponse response = restTemplate.postForObject(resourceUrl, request, LoginResponse.class);

        //return response;
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

    /**
     * Polls the server.
     */
    public PollResponse sendPoll() {

        return null;
    }

    public String sendTest() {
        String resourceUrl = TEST_URL;

        HttpHeaders headers = new HttpHeaders();
        headers.add("AUTH_TOKEN", "world");
        HttpEntity<String> entity = new HttpEntity<>("Hello", headers);

        String response = restTemplate.postForObject(resourceUrl, entity, String.class);

        return response;
    }

}

