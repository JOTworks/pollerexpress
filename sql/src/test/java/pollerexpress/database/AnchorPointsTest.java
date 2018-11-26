package pollerexpress.database;

import com.shared.utilities.AnchorPoints;

import org.junit.Test;

import static org.junit.Assert.*;

public class AnchorPointsTest
{
    @Test
    public void TestMath()
    {
        AnchorPoints anchor = new AnchorPoints(0f,0f, 4f,6f,8f,0f);
        //System.out.println(anchor.aprox(4f, 9f));
        float dist = anchor.aprox(4f,9f);
        System.out.println(dist);
        assertTrue(dist<= 6);

        //AnchorPoints anchor2 = new AnchorPoints(0f,0f, 4f,6f,4f,0f);
        //float v =anchor2.aprox(4f, 3f);
        //System.out.println(v);
        //assertTrue(v <= 3);
        //assertEquals(6,(int)anchor.distance(4f,9f) );
    }
}