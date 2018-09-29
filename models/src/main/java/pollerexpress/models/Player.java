package pollerexpress.models;

public class Player
{
    public String name;
    public String gameId;
    /**
     * Creates a new Player object
     * @param name username of the player
     *
     * @pre name is not null
     * @pre name is the name of a user in the database
     * @post a n
     */
    public Player(String name)
    {
        this.name = name;
        this.gameId = "";
    }
}
