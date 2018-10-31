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
        Map map = ClientData.getInstance().getGame().getMap();

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
    private final int cities_exterior =  Color.argb(255, 100, 100, 100);
    private void drawCity(City city, Canvas canvas)
    {
        float x = city.getPoint().getX();
        float y = city.getPoint().getY();


        Paint outerPaint= new Paint();
        outerPaint.setStyle(Paint.Style.FILL);
        outerPaint.setColor(cities_exterior );
        canvas.drawCircle(x,y,30,outerPaint);

        Paint innerPaint= new Paint();
        innerPaint.setColor(cities_color);
        innerPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(x,y,20, innerPaint);

        float textSize = 50;
        float cityNameOffsetX = 30;
        float cityNameOffsetY = 0;
        {
            Paint cityName = new Paint();
            cityName.setColor(cities_color & 0xFF7F7F7F);
            cityName.setTextSize(textSize);
            cityName.setStrokeWidth(4);
            cityName.setStyle(Paint.Style.STROKE);
            cityName.setFakeBoldText(true);

            canvas.drawText(city.getName(), x + cityNameOffsetX, y + cityNameOffsetY, cityName);
        }
        Paint cityName = new Paint();
        cityName.setColor(0xFFFF0000);
        cityName.setTextSize(textSize);
        cityName.setFakeBoldText(true);

        canvas.drawText(city.getName(), x + cityNameOffsetX,  y + cityNameOffsetY, cityName );
    }

    private final float max_car_size = 70;
    private final float min_gap_size = 20;
    private final float car_width = 20;
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


        float anchor2_x = (x2 + x1)/2 +route.rotation *5 * a1;
        float anchor2_y = (y2 + y1)/2 +route.rotation *5 *b1;

        path = new Path();
        path.moveTo(x1, y1);
        path.quadTo( anchor2_x, anchor2_y, x2, y2);

        Log.d("DrawView", String.format("%f, %s", tangent(x1,y1, x2, y2), route.toString()));
        //draw the owner line
        if(route.getOwner() != null)
        {
            Paint oPaint = new Paint();; //(route.getOwner().gameId.hashCode() & 0x000000FFFFFFFF )|
            oPaint.setColor(  ( route.getOwner().hashCode() | 0xFF000000 ));//TODO set color based off of color property, but this way is fun too..
            oPaint.setStrokeWidth((float) (car_width *1.25) );
            oPaint.setStyle(Paint.Style.STROKE);
            canvas.drawPath(path, oPaint);
        }

        //draw the dashedline of path length
        Log.d("DrawView", String.valueOf(route.rotation) );
        Paint paint = new Paint();
        paint.setColor( getRouteColor( route.getColor() ) );
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.SQUARE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(car_width); //the width you want

        PathMeasure measure = new PathMeasure(path,false );
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

        Paint outline = new Paint();
        outline.setColor(  getRouteColor( route.getColor() ) & 0xFFAFAFAF );
        outline.setAntiAlias(true);
        outline.setStrokeCap(Paint.Cap.SQUARE);
        outline.setStrokeJoin(Paint.Join.ROUND);
        outline.setStyle(Paint.Style.STROKE);
        outline.setStrokeWidth( (float) (car_width * 1.5) );
        outline.setPathEffect(new DashPathEffect(dash, 0));

        canvas.drawPath(path, outline);
        canvas.drawPath(path, paint);

    }
    private int getRouteColor(com.shared.models.Color.TRAIN color)
    {
        switch(color)
        {
            case PURPLE:
                return 0xFFEF70EF;
            case WHITE:
                return 0xFFFFFFFF;
            case BLUE:
                return 0xFF3333FF;
            case YELLOW:
                return 0xFFD9FF09;
            case ORANGE:
                return 0xFFD9D999;
            case BLACK:
                return 0xFF000000;
            case RED:
                return 0xFFFF3333;
            case GREEN:
                return 0xFF209F20;
            case RAINBOW:
                return 0xFF797979;

        }
        return 0xFF999999;
    }
    private float tangent(float x1, float y1, float x2, float y2)
    {
        return -(x1-x2)/(y1-y2);
    }
}
