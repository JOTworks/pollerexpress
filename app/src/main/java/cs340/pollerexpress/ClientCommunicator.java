package cs340.pollerexpress;

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

    //-------------------------------------------------------------------

    public LoginResponse sendLoginRequest(String command, LoginRequest request) {

        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = "http://localhost:8080/" + command;

        LoginResponse response = restTemplate.postForObject(resourceUrl, request, LoginResponse.class);

        return response;
    }
}

