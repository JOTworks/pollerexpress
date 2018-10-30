package thePollerExpress.presenters.game;


import android.os.AsyncTask;

import com.shared.exceptions.CommandFailed;
import com.shared.models.Chat;
import com.shared.models.Command;
import com.shared.models.Game;
import com.shared.models.GameInfo;
import com.shared.models.Player;
import com.shared.models.User;
import com.shared.models.interfaces.ICommand;
import com.shared.models.reponses.ErrorResponse;
import com.shared.models.requests.LoginRequest;
import com.shared.utilities.CommandsExtensions;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.TimeUnit;

import thePollerExpress.communication.ClientCommunicator;
import thePollerExpress.facades.GameFacade;
import thePollerExpress.models.ClientData;
import thePollerExpress.presenters.game.interfaces.IChatPresenter;
import thePollerExpress.presenters.setup.GameSelectionPresenter;
import thePollerExpress.presenters.setup.ILobbyPresenter;
import thePollerExpress.presenters.setup.LoginPresenter;
import thePollerExpress.utilities.AsyncRunner;
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
        public void PressedSendButton(final String message) {


            //todo: this block should all be in a facade, not presenter, but not sure which one.

            AsyncRunner commandRunner = new AsyncRunner(null);

            commandRunner.execute(new ICommand()
            {
                @Override
                public Object execute() throws CommandFailed
                {
                    return new GameFacade().chat(message);
                }
            });
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
