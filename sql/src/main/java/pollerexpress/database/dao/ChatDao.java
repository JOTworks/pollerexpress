package pollerexpress.database.dao;

import com.shared.exceptions.database.DatabaseException;
import com.shared.models.Chat;
import com.shared.models.ChatHistory;
import com.shared.models.GameInfo;

public class ChatDao {
    public static final String INSERT = "INSERT INTO CHATS(`TIMESTAMP`, `GAME`, `PLAYER`, `MESSAGE`)\n VALUES (?,?,?,?)";
    public static final String SELECT_BY_GAME = "SELECT TIMESTAMP, PLAYER, MESSAGE\n FROM CHATS\n WHERE GAME = ?";


    public ChatHistory getChatHistory(GameInfo gi) throws DatabaseException {
        //
        return null;
    }

    public void addChat(Chat chat) throws DatabaseException {
        //
    }
}
