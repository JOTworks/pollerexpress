package thePollerExpress.views.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.shared.models.City;
import com.shared.models.Map;
import com.shared.models.Route;

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
        Map map = new Map(null,null);
        for(City city:map.getCities())
        {

        }
        for(Route route: map.getRoutes())
        {

        }
    }

    /**
     * this should run after draw routes...
     * @param city
     * @param canvas
     */
    private void drawCity(City city, Canvas canvas)
    {
        float x = city.getPoint().getX().floatValue();
        float y = city.getPoint().getY().floatValue();

        Paint innerPaint= new Paint();
        innerPaint.setColor(Color.argb(0,120,120,120));
        innerPaint.set
        Paint outerPaint= new Paint();
        outerPaint.setColor(Color.argb(120, 240, 240, 240) );
        canvas.drawCircle(x,y,20,outerPaint);
        canvas.drawCircle(x,y,30, innerPaint);

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

    }
}
