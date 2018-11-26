package com.shared.utilities;

import com.shared.models.Route;

import java.util.LinkedList;
import java.util.List;

import static java.lang.Math.pow;

//Bx(t) = pow((1-t),2) *x0 +2*t*(1-t)*x1+t^2 * x2
//By(t) = pow((1-t),2) *y0 +2*t*(1-t)*y1+t^2 * y2
public class AnchorPoints
{
   public float x0;
   public float y0;
   public float x1;
   public float y1;
   public float x2;
   public float y2;

    /**
     * For testing only, to make sure the math works.
     * @param x0
     * @param y0
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     */
   public AnchorPoints(float x0,float y0, float x1,float y1, float x2, float y2)
   {
       this.x0 = x0;
       this.y0 = y0;
       this.x1 = x1;
       this.y1 = y1;
       this.x2 = x2;
       this.y2 = y2;

   }
   public AnchorPoints(Route route)
   {
       x0 = route.getCities().get(0).getPoint().getX();
       y0 = route.getCities().get(0).getPoint().getY();
       x2 = route.getCities().get(1).getPoint().getX();
       y2 = route.getCities().get(1).getPoint().getY();

       //begining of math...
       float a = x2 - x0;
       float b = (y2 - y0) ;
       float size = (float)(Math.sqrt(pow(a, 2) + pow(b, 2)));
       float a1 = -b/size;//-b0
       float b1 = a/size;//a0

       x1 = (x2 + x0)/2 +route.rotation *5 * a1;
       y1 = (y2 + y0)/2 +route.rotation *5 * b1;
        //end of math
   }

   public String toString()
   {
       return String.format("(%f, %f) (%f, %f) (%f, %f)", x0,y0,x1,y1,x2,y2);
   }

    public double BX(double t)
    {
        return (  pow((1-t),2) *x0 +2*(1-t)*t*x1 +pow(t,2) * x2  );
    }
    public double BY(double t)
    {
        return  (  pow((1-t),2) *y0 +2*(1-t)*t*y1 +pow(t,2) * y2  );
    }

   private interface Function
   {
       float get(float t);
   }
   private double getArea(double a, double b, double c)
   {
       double s = (a+b+c)/2;
       return Math.sqrt(s*(s-a)*(s-b)*(s-c));
   }
   private static double distance_between_points(double x0, double y0, double x1, double y1)
   {
       return Math.sqrt( (x1-x0)*(x1-x0) + (y1-y0)*(y1-y0));
   }
    public float aprox(float x, float y)
    {
        return aprox(16, x, y);
    }
    public float aprox(int accuracy, float x, float y)
    {
       //get two distances and then return the smaller. We are finding the altitude of the triangle.
        //we need to move the middle anchor point to be on the line....
        float size = 1.0f/accuracy;
        float final_distance= Float.POSITIVE_INFINITY;
        for(int i =0; i  < accuracy; ++ i)
        {
            /*    q
                  /\      |
              A  /  \ B   | this line is what we want.
                /____\    |
              a   C    b
             */
            double ax = BX(i*size);
            double ay = BY(i*size);
            double bx = BX((i+1)*size);
            double by = BY((i+1)*size);
            //System.out.printf("(%f, %f) (%f,%f)\n", ax,ay, bx, by);
            double A = distance_between_points(x,y, ax,ay);
            double B = distance_between_points(x,y,bx,by);
            double C = distance_between_points(bx,by, ax,ay);
            //System.out.printf("A=%f, B=%f, C=%f\n", A, B, C);
            if(C < A || C < B)
            {
                final_distance=(float)Math.min(final_distance, Math.min(A,B));

            }
            else
            {

                double area = getArea(A, B, C);
                double dist_q_to_C = (2 * area) / C;
                final_distance = (float) Math.min(dist_q_to_C, final_distance);
            }
        }

        return final_distance;
    }
   public float distance(float x, float y)
   {
       final float d4 = (float)Math.pow((x0 -2* x1 + x2), 2) + (float)Math.pow((y0 -2* y1 + y2), 2) ;
       final float d3 = (x0 - 2*x1 + x2) * (x1- x0) + (y0 - 2*y1 + y2)*(y1-y0);
       final float d2 = (x0 - 2*x1 + x2) * (x0- x) + (y0 - 2*y1 + y2)*(y0-y) + 2*(x1 - x0)* (x1 - x0) + 2*(y1 - y0)* (y1 - y0);
       final float d1 = (x1 - x0) * (x0 - x)+ (y1 - y1 )*(y0-y);
       final float d0 = (x0 - x)*(x0 - x) +(y0 - y)*(y0 - y);

       float minimum_t =Float.POSITIVE_INFINITY;
       List<Float> t = new LinkedList<>();
       Function f = new Function()
       {
           @Override
           public float get(float t)
           {
               return d4*t*t*t*t + 4*d3*t*t*t + 2*d2*t*t + 4*d1*t * d0;
           }
       };

       if(d4 != 0)
       {
           System.out.print(String.format("In d4 = %f\n", d4));
           //do the following
           final float c3 = 8 * d4 * d4;
           final float c2 = 24*d3*d4;
           final float c1 = 2*(9*d3*d3+d2*d4);
           final float c0 = 3*d2*d3 - d1*d4;
           Function Q = new Function()
            {
                @Override
                public float get(float t)
                {
                    return c3* t*t*t+ c2 * t* t+ c1*t + c0;
                }
            };

           Function Q_prime = new Function()
            {
                @Override
                public float get(float t)
                {
                    return 3*c3* t*t+ 2*c2 * t+ c1;
                }
            };

           float z = -(1 + Math.max(Math.max(Math.abs(c2), Math.abs(c1)), Math.abs(c0))/c3);
           for(int i = 0; i < 30; ++i)
           {
               z = z-Q.get(z)/Q_prime.get(z);
               System.out.print(String.format("z=%f\n", z));
           }

           Function P_not = new Function()
            {
                @Override
                public float get(float t)
                {
                    return 3*d4*t*t + 6*d3*t +d2;
                }
            };
           float yelph = P_not.get(z)/d4;

           t.add(z - (float)Math.sqrt(-yelph));
           t.add(z + (float)Math.sqrt(-yelph));
           t.add(-(2*z + (3*d3/d4)));

       }
       else
       {

           t.add(((x2 - x0)*(x0-x) +(y2-y0)*(y0-y))/((float)pow(x2-x0, 2) + (float)pow(y2-y0, 2)));

       }
       t.add(0f);
       t.add(1f);
       for(float l : t)
       {
            if(!Float.isNaN(l) && l >= 0 && l <= 1)
            {

                minimum_t = Math.min(minimum_t, f.get(l));
            }
        }
        return minimum_t;
        }
}
