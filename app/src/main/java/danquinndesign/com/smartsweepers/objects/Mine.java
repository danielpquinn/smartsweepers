package danquinndesign.com.smartsweepers.objects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Mines to be swept up by the sweepers
 */
public class Mine {

    /** Mine position */
    private float mX;
    private float mY;
    private float mWidth;
    private float mHeight;

    /** Paint styles */
    private Paint mPaint;

    public Mine() {
        mX = 0;
        mY = 0;
        mWidth = 20;
        mHeight = 20;
        mPaint = new Paint();
        mPaint.setColor(Color.parseColor("#CDDC39"));
    }

    /** Draw the mine onto a canvas */

    public void draw(Canvas canvas) {
        canvas.drawCircle(mX + mWidth / 2, mY + mHeight / 2, mWidth / 2, mPaint);
    }

    /** Move mine to a new position */

    public void move(float x, float y) {
        mX = x;
        mY = y;
    }

    public float getX() {
        return mX;
    }

    public float getY() {
        return mY;
    }

    public float getWidth() {
        return mWidth;
    }

    public float getHeight() {
        return mHeight;
    }
}
