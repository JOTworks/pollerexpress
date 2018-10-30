package pollerexpress.database.dao;

import com.shared.exceptions.database.DatabaseException;
import com.shared.models.Chat;
import com.shared.models.ChatHistory;
import com.shared.models.GameInfo;
import com.shared.models.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ChatDao {
    IDatabase _db;
    public static final String INSERT = "INSERT INTO CHATS(`TIMESTAMP`, `GAME`, `PLAYER`, `MESSAGE`)\n VALUES (?,?,?,?)";
    public static final String SELECT_BY_GAME = "SELECT TIMESTAMP, PLAYER, MESSAGE\n FROM CHATS\n WHERE GAME = ?";

    public ChatDao(IDatabase db) {
        this._db = db;
    }

    public ChatHistory getChatHistory(GameInfo gi) throws DatabaseException {
        ChatHistory history = new ChatHistory();
        _db.open();

        try {
            PreparedStatement stmnt = _db.getConnection().prepareStatement(SELECT_BY_GAME);
            stmnt.setString(1, gi.getId());
            ResultSet rs = stmnt.executeQuery();
            while(rs.next()) {
                history.addChat(new Chat(rs.getString("MESSAGE"), rs.getTimestamp("TIMESTAMP"), new Player(rs.getString("PLAYER"))));
            }
            rs.close();
            stmnt.close();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }

        _db.close(true);
        return history;
    }

    public void addChat(Chat chat) throws DatabaseException {
        _db.open();

        try{
            PreparedStatement stmnt = _db.getConnection().prepareStatement(INSERT);
            stmnt.setTimestamp(1, chat.getTimestamp());
            stmnt.setString(2, chat.getMessageSender().getGameId());
            stmnt.setString(3, chat.getMessageSender().getName());
            stmnt.setString(4, chat.getMessage());
            stmnt.execute();
            stmnt.close();
        } catch(SQLException e) {
            throw new DatabaseException(e.getMessage());
        }

        _db.close(true);
    }
}
