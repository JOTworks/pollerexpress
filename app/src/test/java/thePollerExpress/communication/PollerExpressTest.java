package thePollerExpress.communication;

import thePollerExpress.communication.PollerExpress;

public class PollerExpressTest {

    public static void main(String[] args) {
        System.out.println("Test start");
        PollerExpress polar = new PollerExpress();
        if(polar == null) {
            System.out.println("No PollerExpress was created.");
        }
    }
}
