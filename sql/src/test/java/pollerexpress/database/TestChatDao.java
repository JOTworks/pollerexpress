package pollerexpress.database;

import com.shared.models.Chat;
import com.shared.models.ChatHistory;
import com.shared.models.GameInfo;
import com.shared.models.Player;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;

import pollerexpress.database.dao.ChatDao;

import static org.junit.Assert.*;

public class TestChatDao {
    Database db;
    ChatDao cDao;
    GameInfo gi;
    Player p;

    @Before
    public void up() {
        db = new Database();
        cDao = db.cDao;
        gi = new GameInfo("Game",3);
        p = new Player("NobodyEver", gi.getId());

        try {
            db.open();

            db.deleteTables();
            db.createTables();

            db.close(true);

        } catch(Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @After
    public void down() {
        //
    }


    @Test
    public void testChat() {
        assertTrue(true);

        try {
            //add 3 Chats
            Long time = System.currentTimeMillis();
            Chat chat1 = new Chat("Blah blah blah", new Timestamp(time), p);
            Chat chat2 = new Chat("...", new Timestamp(time + 1), p);
            Chat chat3 = new Chat("Can I get uhhhhhhhhhhhhhh", new Timestamp(time + 2), p);
            db.open();
            cDao.addChat(chat1);
            cDao.addChat(chat2);
            cDao.addChat(chat3);

            //test Chat.equals
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            Chat chat = new Chat("Blah blah blah", timestamp, p);
            Chat chatequals = new Chat("Blah blah blah", timestamp, p);
            assertEquals(chat, chatequals);

            //get ChatHistory, size of array should be 3, and the Chats should be within it.
            ChatHistory history = cDao.getChatHistory(gi);
            assertEquals(3, history.getChats().size());
            assertTrue(history.getChats().contains(chat1));
            assertTrue(history.getChats().contains(chat2));
            assertTrue(history.getChats().contains(chat3));
            db.close(false);
        } catch(Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }
}
