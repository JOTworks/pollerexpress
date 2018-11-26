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

    public static int getIsTurnColor(PLAYER color) {
        switch(color) {
            case RED:
                return 0xFFFF3333;
            case BLUE:
                return 0xFF2020FF;
            case GREEN:
                return 0xFF209F20;
            case YELLOW:
                return 0xFFD9FF09;
            case BLACK:
                return 0xFF333333;
        }
        return 0;
    }

    public static int getIsNotTurnColor(PLAYER color) {
        switch(color) {
            case RED:
                return 0x88FF3333;
            case BLUE:
                return 0x882020FF;
            case GREEN:
                return 0x88209F20;
            case YELLOW:
                return 0x88D9FF09;
            case BLACK:
                return 0x88333333;
        }
        return 0x00000000;
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