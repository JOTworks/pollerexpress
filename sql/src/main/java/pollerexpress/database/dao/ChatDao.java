package pollerexpress.database.dao;

import com.shared.models.Chat;
import com.shared.models.GameInfo;

public class ChatDao {

    IDatabase _db;

    public ChatDao(IDatabase db)
    {
        this._db = db;
    }

    public void addChat(Chat chat, GameInfo gameInfo) {

    }
}
