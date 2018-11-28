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

    //todo: Jack added this to get rid of errors. Is there anything wrong with accepting doubles and losing info?
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

    @Override
    public boolean equals(Object o) {
        if(o == null) return false;
        if(o instanceof Point){
            Point p = (Point) o;
            return (this.x == p.x && this.y == p.y);
        }
        return false;
    }
}
