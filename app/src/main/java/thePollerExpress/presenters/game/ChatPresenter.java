package thePollerExpress.presenters.game;


import com.shared.models.Chat;
import com.shared.models.Command;
import com.shared.models.Game;
import com.shared.models.GameInfo;
import com.shared.models.Player;
import com.shared.utilities.CommandsExtensions;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.TimeUnit;

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
            CC = ClientCommunicator.instance();
        }


        @Override
        public void PressedSendButton(String message) {


            //todo: this block should all be in a facade, not presenter, but not sure which one.
            Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
            Chat chatMessage = new Chat(message, timeStamp, clientData.getUser());
            GameInfo gameInfo = ClientData.getInstance().getGame().getGameInfo();

            Class<?>[] types = {Chat.class, GameInfo.class};
            Object[] params= {chatMessage, gameInfo};
            //todo: make sure methodName really will be chat, and not something else
            Command chatCommand = new Command(CommandsExtensions.serverSide +"CommandFacade","chat",types,params);
            CC.sendCommand(chatCommand);
            //also have to deal with pull response, i think Nate said he was refactoring that to get rid of code duplication

            chatView.displayMessage("chat sent");
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
            if( !(arg instanceof ArrayList) ) return;

            //todo: actualy update the view
        }
}
