package cs340.pollerexpress;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void clientCommunicatorTest_works() {
        Test_ClientCommunicator_Jack cc = Test_ClientCommunicator_Jack.instance();
        String response = cc.sendTest();
        assertEquals("Hello spaceman!", response);
    }
}