package com.shared.models;

import java.io.Serializable;

public class Point implements Serializable
{
    float x;
    float y;

    public Point(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    //todo: Jack added this to get rid off errors is ther anything wrong with accepting doubles and lossing info?
    public Point(double x, double y)
    {
        this.x = (float) x;
        this.y = (float) y;
    }


    public float getX()
    {
        return x;
    }

    public float getY()
    {
        return y;
    }
}
