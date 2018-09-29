package cs340.pollerexpress;

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

    public PollResponse runCommand(String command, String s) {

        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = "http://localhost:8080/" + command;

        PollResponse pollResponse = restTemplate.postForObject(resourceUrl, s, Results.class);

        return results;
    }
}

