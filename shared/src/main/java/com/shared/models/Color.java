package com.shared.models;

import java.io.Serializable;

import static com.shared.models.Color.PLAYER.BLACK;
import static com.shared.models.Color.PLAYER.BLUE;
import static com.shared.models.Color.PLAYER.GREEN;
import static com.shared.models.Color.PLAYER.RED;
import static com.shared.models.Color.PLAYER.YELLOW;

public class Color implements Serializable
{

    public enum PLAYER {
        RED, BLUE, GREEN, YELLOW, BLACK
    }

    public enum TRAIN {
        PURPLE, WHITE, BLUE, YELLOW, ORANGE, BLACK, RED, GREEN, RAINBOW
    }
    private static PLAYER colors[] = {RED, BLUE, GREEN, YELLOW, BLACK};

    public static PLAYER convertIndexToColor(int dex)
    {
        return colors[dex % colors.length];

    }

    public static int getIndex(PLAYER c)
    {
        for(int i = 0; i < colors.length; ++i)
        {
            if(colors[i].equals(c))
            {
                return i+colors.length;
            }
        }
        return 0;
    }
}