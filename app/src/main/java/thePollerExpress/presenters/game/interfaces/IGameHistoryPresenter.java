package thePollerExpress.presenters.game.interfaces;

import com.shared.models.Chat;

import java.util.ArrayList;
import java.util.List;

public interface IGameHistoryPresenter {
    public void PressedChatViewButton();
    ArrayList<String> getHistoryItems();

    void PressedGameHistoryViewButton();
}
