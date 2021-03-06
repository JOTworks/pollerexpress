package thePollerExpress.presenters.game;

import com.shared.exceptions.CommandFailed;
import com.shared.models.Chat;

import com.shared.models.interfaces.ICommand;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
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
        public ArrayList<String> getChat()
        {
            return clientData.getGame().getChatHistory().getChatsAsString();
        }

        @Override
        public void PressedChatViewButton() {
            chatView.displayError("Already in Chat");
        }

//        @Override
//        public void PressedDevViewButton() {
//            chatView.changeToDevView();
//        }

        @Override
        public void PressedGameHistoryViewButton() {
                chatView.changeToGameHistoryView();
        }

        @Override
        public void update(Observable o, Object arg)
        {
            if( !(arg instanceof Chat) ) return;

            // get all of the chats
            // ArrayList<String> chats = clientData.getGame().getChatHistory().getChatsAsString();

            // display the chats
            String message = ((Chat) arg).toString();
            chatView.displayChats(message);
        }
}
