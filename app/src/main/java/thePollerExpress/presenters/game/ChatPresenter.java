package thePollerExpress.presenters.game;

import com.shared.models.Game;
import com.shared.models.GameInfo;
import com.shared.models.Player;

import java.util.Observable;
import java.util.Observer;

import thePollerExpress.communication.ClientCommunicator;
import thePollerExpress.models.ClientData;
import thePollerExpress.presenters.game.interfaces.IChatPresenter;
import thePollerExpress.presenters.setup.ILobbyPresenter;
import thePollerExpress.views.game.interfaces.IChatView;
import thePollerExpress.views.setup.ILobbyView;

public class ChatPresenter implements IChatPresenter, Observer {

        private IChatView chatView;
        private ClientData clientData;

        //todo: figure out if we should have this call game or setup facade instead of CC directly
        private ClientCommunicator CC;

        public ChatPresenter(IChatView chatView){

            this.chatView = chatView;
            clientData = ClientData.getInstance();
            clientData.addObserver(this);
        }


        @Override
        public void PressedSendButton(String message) {


            chatView.displayMessage("Will send chat eventualy");
        }

        @Override
        public void PressedChatViewButton() {
            chatView.displayMessage("Already in Chat");
        }
        @Override
        public void PressedDevViewButton() {
            chatView.changeToDevView();
        }

        /*
         * What needs to happen to the lobby when model data changes?
         * When a player enters or leaves the game, the lobby view
         * needs to be updated to reflect that.
         * */
        @Override
        public void update(Observable o, Object arg)
        {
 /*           if( !(arg instanceof Player) ) return;
            Player p = (Player) arg;
            Game game = clientData.getGame();
            int dex = game.getPlayers().indexOf(p);
            if(dex == -1)
            {
                View.playerLeft(dex);
            }
            else
            {
                lobbyView.playerJoined(p);
            }*/
        }
}
