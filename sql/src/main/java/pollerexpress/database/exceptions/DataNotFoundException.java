package pollerexpress.database.exceptions;


/**
 * Used when the data from a query does not exist in the database.
 */
public class DataNotFoundException extends DatabaseException
{
    String dataId;
    String target;

    public DataNotFoundException(String data, String target)
    {
        this.dataId = data;
        this.target = target;
    }
}
