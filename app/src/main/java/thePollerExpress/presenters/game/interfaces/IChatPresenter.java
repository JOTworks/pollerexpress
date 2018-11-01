package thePollerExpress.presenters.game.interfaces;

import com.shared.models.Chat;

import java.util.ArrayList;
import java.util.List;

public interface IChatPresenter {
    public void PressedSendButton(String message);
    public void PressedChatViewButton();
    public void PressedDevViewButton();
    ArrayList<String> getChat();

}
