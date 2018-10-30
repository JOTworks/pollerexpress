package thePollerExpress.presenters.game;


import com.shared.exceptions.CommandFailed;
import com.shared.models.Chat;
import com.shared.models.interfaces.ICommand;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import thePollerExpress.communication.ClientCommunicator;
import thePollerExpress.facades.GameFacade;
import thePollerExpress.models.ClientData;
import thePollerExpress.presenters.game.interfaces.IChatPresenter;
import thePollerExpress.utilities.AsyncRunner;
import thePollerExpress.views.game.interfaces.IChatView;

public class ChatPresenter implements IChatPresenter, Observer {

        private IChatView chatView;
        private ClientData clientData;



        public ChatPresenter(IChatView chatView)
        {

            this.chatView = chatView;
            clientData = ClientData.getInstance();
            clientData.getGame().getChatHistory().addObserver(this);
        }


        @Override
        public void PressedSendButton(final String message)
        {


            AsyncRunner commandRunner = new AsyncRunner(chatView);

            commandRunner.execute(new ICommand()
            {
                @Override
                public Object execute() throws CommandFailed
                {
                    return new GameFacade().chat(message);
                }
            });
            chatView.displayError("chat sent");
        }

        @Override
        public void PressedChatViewButton() {
            chatView.displayError("Already in Chat");
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
            if( !(arg instanceof Chat) ) return;

            //todo: actualy update the view
        }
}
