package thePollerExpress.views.game.interfaces;

import android.widget.Toast;

import com.shared.models.Chat;

import java.util.ArrayList;

public interface IChatView {

    public void displayMessage(String message);
    public void changeToDevView();
    public void displayChats(ArrayList<String> messageList);
}
