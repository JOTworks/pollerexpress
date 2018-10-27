package thePollerExpress.views.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.widget.ImageView;

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
    public void onDraw(Canvas canvas) {
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
    private void drawCity(City city, Canvas canvas)
    {
        float x = city.getPoint().getX();
        float y = city.getPoint().getY();

        Paint innerPaint= new Paint();
        innerPaint.setColor(Color.argb(255,250,80,80));
        innerPaint.setStrokeWidth(30);
        Paint outerPaint= new Paint();
        outerPaint.setColor(Color.argb(255, 140, 140, 140) );
        outerPaint.setStrokeWidth(30);
        canvas.drawCircle(x,y,30,outerPaint);
        canvas.drawCircle(x,y,20, innerPaint);

        float textSize = 50;
        float cityNameOffsetX = 30;
        float cityNameOffsetY = 0;
        Paint cityName = new Paint();
        cityName.setColor(Color.BLUE);
        cityName.setTextSize(textSize);
        canvas.drawText(city.getName(), x + cityNameOffsetX,  y + cityNameOffsetY, cityName );
    }

    private void drawRoute(Route route, Canvas canvas)
    {
        Path mPath;
        Paint paint;
        float x1 = route.getCities().get(0).getPoint().getX();
        float y1 = route.getCities().get(0).getPoint().getY();
        float x2 = 0;
        mPath = new Path();
        mPath.moveTo(x1, y1);
        //mPath.cubicTo(, anchor2_x, anchor2_y, x2, y2); /*the anchors you want, the curve will tend to reach these anchor points; look at the wikipedia article to understand more */
        paint = new Paint();
        paint.setColor(0xFFFFFFFF);
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStyle(Paint.Style.STROKE);
       // paint.setStrokeWidth(width); //the width you want
        canvas.drawPath(mPath, paint);
    }
}
