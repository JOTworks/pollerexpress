package thePollerExpress.views.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.util.Log;

import com.shared.models.City;
import com.shared.models.Map;
import com.shared.models.Route;


import thePollerExpress.models.ClientData;

public class DrawView extends android.support.v7.widget.AppCompatImageView
{
    public DrawView(Context context) {
        super(context);
    }

    DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    DrawView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        Map map = ClientData.getInstance().map;

        for(Route route: map.getRoutes())
        {
            drawRoute(route, canvas);
        }
        for(City city:map.getCities())
        {
            drawCity(city, canvas);
        }

    }

    /**
     * this should run after draw routes...
     * @param city
     * @param canvas
     */
    private final int cities_color = Color.argb(255,255,0,0);
    private void drawCity(City city, Canvas canvas)
    {
        float x = city.getPoint().getX();
        float y = city.getPoint().getY();


        Paint outerPaint= new Paint();
        outerPaint.setStyle(Paint.Style.FILL);
        outerPaint.setColor(  Color.argb(255, 100, 100, 100));
        canvas.drawCircle(x,y,30,outerPaint);

        Paint innerPaint= new Paint();
        innerPaint.setColor(cities_color);
        innerPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(x,y,20, innerPaint);

        float textSize = 50;
        float cityNameOffsetX = 30;
        float cityNameOffsetY = 0;
        Paint cityName = new Paint();
        cityName.setColor(cities_color);
        cityName.setTextSize(textSize);
        cityName.setFakeBoldText(true);
        canvas.drawText(city.getName(), x + cityNameOffsetX,  y + cityNameOffsetY, cityName );
    }

    private final float max_car_size = 70;
    private final float min_gap_size = 20;
    private void drawRoute(Route route, Canvas canvas)
    {
        Path path;

        float x1 = route.getCities().get(0).getPoint().getX();
        float y1 = route.getCities().get(0).getPoint().getY();

        float x2 = route.getCities().get(1).getPoint().getX();
        float y2 = route.getCities().get(1).getPoint().getY();

        // tangent = m * x + y

        float a = x2 - x1;
        float b = (y2 - y1) ;
        float size = (float)(Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2)));
        float a1 = -b/size;//-b0
        float b1 = a/size;//a0


        float anchor2_x = (x2 + x1)/2 +route.id*5 * a1;
        float anchor2_y = (y2 + y1)/2 +route.id*5 *b1;

        path = new Path();
        path.moveTo(x1, y1);
        path.quadTo( anchor2_x, anchor2_y, x2, y2);

        Log.d("DrawView", String.format("%f, %s", tangent(x1,y1, x2, y2), route.toString()));
        //draw the owner line
        if(route.getOwner() != null)
        {
            Paint oPaint = new Paint();
            oPaint.setColor(0xFFDE1324);//TODO get color based on ownership.
            oPaint.setStrokeWidth(25);
            oPaint.setStyle(Paint.Style.STROKE);
            canvas.drawPath(path, oPaint);
        }

        //draw the dashedline of path length
        Log.d("DrawView", String.valueOf(route.id) );
        Paint paint = new Paint();
        paint.setColor(0xFFFFFFFF);
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.SQUARE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(20); //the width you want
        PathMeasure measure = new PathMeasure(path,false );//no idea what this does...
        float length = measure.getLength();
        float segSize = length/route.getDistance();
        float[] dash = new float[route.getDistance() * 2 +2];

        float car_dash = Math.min(segSize - min_gap_size, max_car_size);
        float gap = segSize - car_dash ;
        dash[0] = 0;
        dash[1] = gap/2;
        for(int i =1 ; i < route.getDistance()+1; ++i)
        {
            dash[i*2] = car_dash;
            dash[i*2+1] = gap;
        }

        paint.setPathEffect(new DashPathEffect(dash, 0));

        canvas.drawPath(path, paint);
    }
    private float tangent(float x1, float y1, float x2, float y2)
    {
        return -(x1-x2)/(y1-y2);
    }
}
