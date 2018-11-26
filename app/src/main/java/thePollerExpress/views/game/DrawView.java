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
import android.view.MotionEvent;

import com.shared.models.City;
import com.shared.models.Map;
import com.shared.models.Route;

import cs340.pollerexpress.R;
import thePollerExpress.presenters.game.interfaces.IMapPresenter;
import com.shared.utilities.AnchorPoints;

import static java.lang.Math.pow;

public class DrawView extends android.support.v7.widget.AppCompatImageView
{
    IMapPresenter presenter = null;
    public DrawView(Context context)
    {
        super(context);
    }

    DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    DrawView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    public void setPresenter(IMapPresenter presenter)
    {
        this.presenter = presenter;
    }
    @Override
    public void onDraw(Canvas canvas)
    {

        super.onDraw(canvas);

        if(this.presenter == null) return;

        Map map = presenter.getMap();// presenter.getMap();

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

    private final float nearness = 20.00f;

    public float distance(float x1,float y1, float x2, float y2)
    {
        return x1;
    }
    public float distanceToRoute(float x, float y, Route r)
    {
        //I will just calculate the distance at three points, the start, middle, and end. and take the average of the smallest two..
        AnchorPoints anchor = new AnchorPoints(r);
        //float m_x = BX(.5f, anchor);
        //float m_y = BY(.5f, anchor);

        return Float.POSITIVE_INFINITY;
    }


    private final float max_car_size = 70;
    private final float min_gap_size = 20;
    private final float car_width = 20;

    private Path createPath(Route route)
    {
        AnchorPoints anchor = new AnchorPoints(route);
        Path path = new Path();
        path.moveTo(anchor.x0, anchor.y0);
        path.quadTo( anchor.x1, anchor.y1, anchor.x2, anchor.y2);
        return path;
    }
    private void drawRoute(Route route, Canvas canvas)
    {
        Path path = createPath(route);
        //draw the owner line
        if(route.getOwner() != null)
        {
            Paint oPaint = new Paint();
            oPaint.setColor(  ( getPlayerColor(route.getOwner().getColor())));//TODO set color based off of color property, but this way is fun too..
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


    private int getPlayerColor(com.shared.models.Color.PLAYER color)
    {
        switch (color)
        {
            case GREEN:
                return getContext().getResources().getInteger(R.integer.GREEN);
            case BLACK:
                return getContext().getResources().getInteger(R.integer.BLACK);
            case BLUE:
                return getContext().getResources().getInteger(R.integer.BLUE);
            case RED:
                return getContext().getResources().getInteger(R.integer.RED);
        }
        return 0xFFFFFFFF;
    }
    private int getRouteColor(com.shared.models.Color.TRAIN color)
    {
       /* System.out.print(String.format("%h", R.integer.PURPLE));
        System.out.print(String.format("%h", R.integer.WHITE));
        System.out.print(String.format("%h", R.integer.YELLOW));
        System.out.print(String.format("%h", R.integer.ORANGE));
        System.out.print(String.format("%h", R.integer.RED));*/
        switch(color)
        {
            case PURPLE:
                return getContext().getResources().getInteger(R.integer.PURPLE);
            case WHITE:
                return getContext().getResources().getInteger(R.integer.WHITE);
            case BLUE:
                return getContext().getResources().getInteger(R.integer.BLUE);
            case YELLOW:
                return getContext().getResources().getInteger(R.integer.YELLOW);
            case ORANGE:
                return getContext().getResources().getInteger(R.integer.ORANGE);
            case BLACK:
                return getContext().getResources().getInteger(R.integer.BLACK);
            case RED:
                return getContext().getResources().getInteger(R.integer.RED);
            case GREEN:
                return getContext().getResources().getInteger(R.integer.GREEN);
            default:
                System.out.println("On grey");
                return getContext().getResources().getInteger(R.integer.GREY);

        }
    }

    public float x=0;
    public float y=0;
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        x = event.getX();
        y = event.getY();
        return super.onTouchEvent(event);
    }

    @Override
    public boolean performClick()
    {
        return super.performClick();
    }

}
