package thePollerExpress.models;

public class PlayerEndResult {
    public String total;
    public String routes;
    public String completedDestination;
    public String incompleteDestination;
    public String username;
    public String longestRoute;
    public String bonus;

    /**
     * Create End Results
     * @param username
     * @param total
     * @param completedDestination
     * @param incompleteDestination
     * @param routes
     * @param longestRoute
     */
    public PlayerEndResult(String username, String total, String completedDestination, String incompleteDestination, String routes, String longestRoute, String bonus)
    {
        this.total = total;
        this.routes = routes;
        this.incompleteDestination = incompleteDestination;
        this.completedDestination = completedDestination;
        this.username = username;
        this.longestRoute = longestRoute;
        this.bonus = bonus;
    }
}
