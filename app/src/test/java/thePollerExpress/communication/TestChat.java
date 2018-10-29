package thePollerExpress.communication;

import com.shared.models.Chat;
import com.shared.models.Color;
import com.shared.models.Command;
import com.shared.models.GameInfo;
import com.shared.models.requests.LoginRequest;
import com.shared.utilities.CommandsExtensions;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Timestamp;

import thePollerExpress.facades.SetupFacade;
import thePollerExpress.models.ClientData;

import static org.junit.Assert.*;

public class TestChat {

    @BeforeClass
    public static void up() {
        //setup test
        SetupFacade facade = new SetupFacade();
        facade.login(new LoginRequest("jackson","password"));
        facade.createGame("this is the game name",1,Color.PLAYER.RED);
        //facade.startGame("this is the game name");
    }

    @After
    public void down() {
        //takedown test
    }

    @Test
    public void test() {
        ClientData CD = ClientData.getInstance();

        //that the chat is empty
        assertTrue(CD.getGame().getChatHistory().getChatsAsString().size() == 0);
        CD.getGame().getChatHistory().addChat(new Chat("chatmessage1",new Timestamp((long)100000),CD.getUser()));
        assertTrue(CD.getGame().getChatHistory().getChatsAsString().size() != 0);

        GameInfo gameInfo = ClientData.getInstance().getGame().getGameInfo();
        Class<?>[] types = {Chat.class, GameInfo.class};
        Object[] values = {new Chat("chatmessage2",new Timestamp((long)100000),CD.getUser()), gameInfo};
        Command chatCommand = new Command(CommandsExtensions.serverSide +"CommandFacade","chat",types,values);

        assertTrue(CD.getGame().getChatHistory().getChatsAsString().size() == 3);

        ClientCommunicator.instance().sendCommand(chatCommand);



        assertTrue(false);
        assertEquals(1,1);
        assertNotNull("hi");
    }
}
