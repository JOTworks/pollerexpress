package thePollerExpress.views.game.interfaces;

import android.widget.Toast;

import com.shared.models.Chat;

import java.util.ArrayList;

import thePollerExpress.views.IPollerExpressView;

public interface IGameHistoryView extends IPollerExpressView
{
    public void displayError(String message);
    public void changeToChatView();
    public void displayGameHistoryItems(String message);
}
