package cs340.pollerexpress;

import com.pollerexpress.models.ISetupService;

class ClientSetupService implements ISetupService {
    private static final ClientSetupService ourInstance = new ClientSetupService();

    static ClientSetupService getInstance() {
        return ourInstance;
    }

    private ClientSetupService() {
    }
}
